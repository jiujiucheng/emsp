package com.edwin.emsp.domain.model.aop;

import com.edwin.emsp.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jiucheng
 * @Description: CacheAspectJ
 * @Date: 2025/4/18
 */
@Slf4j
@Aspect
@Component
public class CacheAspectJ implements Ordered {
    private static final String EXECUTION = "@annotation(Cache)";
    /**
     * 所有以类名+接口名的key ，get所有的set
     */
    public static final String TOTAL_SIMPLE_KEY = "totalSimpleKey";

    //是否命中缓存，目前用于log记录时命中缓存不统计
    public static final ThreadLocal<Boolean> hitCache = new ThreadLocal<Boolean>();


    @Autowired
    RedissonClient defaultRedissonClient;

    public void pointCut() {
    }

    @Around(EXECUTION)
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();

        Cache cache = method.getAnnotation(Cache.class);
        Object result;
        String cacheKey = null;
        if (null != cache) {
            try {
                cacheKey = cache.cacheKey();

                String keyPrefix=cache.keyPrefix()+"kryo";
                int expiry = cache.expiry();
                if (StringUtils.isEmpty(cacheKey)) {
                    cacheKey = StringUtils.join(pjp.getTarget().getClass().getSimpleName(), ".", method.getName());
                }
                if(StringUtils.isNotEmpty(keyPrefix)){
                    cacheKey=keyPrefix+"V1:"+cacheKey;
                }
                Object[] arguments = pjp.getArgs();
                cacheKey = getCacheKey(cacheKey, arguments);

                RBucket<Object> rBucket = defaultRedissonClient.getBucket(cacheKey);
                result = rBucket.get();
                if (result == null) {
                    result = pjp.proceed();
                    cacheValue(cache, result, cacheKey, expiry);
                    logger.info("CacheAspectJ 无缓存加载，塞入缓存 $$key:" + cacheKey + ":$$" + result);
                } else {
                    hitCache.set(true);
                    logger.info("CacheAspectJ 缓存加载成功 $$key:" + cacheKey + ":$$" + result);
                }
            } catch (Exception e) {
                logger.warn("CacheAspectJ 缓存加载异常(不影响业务) $$key:" + cacheKey + ":$$" + e.getMessage(), e);
                result = pjp.proceed();
                cleanCache(cacheKey);
            }

        } else {
            result = pjp.proceed();
        }
        return result;
    }

    /**
     * 清除缓存
     *
     * @param cacheKey 缓存key
     */
    public void cleanCache(String cacheKey) {
        try {
            if (StringUtils.isNotBlank(cacheKey)) {
                defaultRedissonClient.getBucket(cacheKey).delete();
            }
        } catch (Exception e) {
            logger.warn("缓存清除异常 key:{}", cacheKey, e);
        }
    }

    /**
     * 缓存数据
     *
     * @param cache    执行切点
     * @param result   结果
     * @param cacheKey 缓存key
     * @param expiry   超时时间
     */
    private void cacheValue(Cache cache, Object result, String cacheKey, int expiry) {
        if (cache.cacheNull()) {
            defaultRedissonClient.getBucket(cacheKey).set(result,expiry, TimeUnit.SECONDS);
        } else if (result != null) {
            defaultRedissonClient.getBucket(cacheKey).set(result,expiry, TimeUnit.SECONDS);
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }

    private String getCacheKey(String cacheKey, Object[] arguments) {
        StringBuilder sb = new StringBuilder(cacheKey);
        if (arguments != null && arguments.length > 0) {
            for (Object argument : arguments) {
                if (argument == null) {
                    sb.append("/");
                } else if (argument instanceof Date) {
                    sb.append("/").append(DateUtil.getYMDHMS((Date) (argument)));
                } else if (argument instanceof List<?>) {
                    List<?> list = (List<?>) argument;
                    sb.append("/").append(StringUtils.join(list, "_"));
                } else {
                    sb.append("/").append(argument);
                }
            }
        }
        return sb.toString();
    }
}
