package com.edwin.emsp.domain.model.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: jiucheng
 * @Description: Cache
 * @Date: 2025/4/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    /**
     * cache key，可以空，默认是class+method
     * @return
     */
    String cacheKey() default "";
    /**
     * Cache过期时间，单位秒，默认5分钟, 0表示永不过期.
     */
    int expiry() default 300;

    /**Cache
     * 是否cachenull
     * @return
     */
    boolean cacheNull() default true;

    /**
     * key 前缀 默认空
     * @return
     */
    String keyPrefix() default "";

}
