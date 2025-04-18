package com.edwin.emsp.aop;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: jiucheng
 * @Description: 请求日志切面
 * @Date: 2025/4/12
 */
@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    public RequestLogAspect() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping))")
    public void requestMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping))")
    public void postMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping))")
    public void getMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping))")
    public void putMapping() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping))")
    public void deleteMapping() {
    }

    @Around("(requestMapping() || postMapping() || getMapping() || putMapping() || deleteMapping())")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = null;
        ServletRequestAttributes atts = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (atts != null) {
            request = atts.getRequest();
        }
        String uri = request.getRequestURI();

        Object[] args = pjp.getArgs();
        String[] paramNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            if (args[i] instanceof MultipartFile || args[i] instanceof HttpServletRequest ||  args[i] instanceof HttpServletResponse) {
                continue;
            }
            params.put(paramNames[i], args[i]);
        }

        logger.info("[request_in] request_uri:{}, param:{}", uri, JSON.toJSONString(params));

        Object result = pjp.proceed();
        String data = Objects.isNull(result) ? "" : JSON.toJSONString(result);

        logger.info("[request_out] request_uri:{}, result:{}", uri, data);

        return result;
    }
}
