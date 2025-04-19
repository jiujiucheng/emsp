package com.edwin.emsp.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: jiucheng
 * @Description: bean utils
 * @Date: 2025/4/18
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 获取Spring容器中的Bean
     * @param clazz Bean类型
     * @return Bean实例
     * @param <T> Bean类型
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取Spring容器中的Bean
     * @param beanName Bean名称
     * @return Bean实例
     */
    public static Object getBean(String beanName) {
        checkApplicationContext();
        return applicationContext.getBean(beanName);
    }
    
    /**
     * 检查ApplicationContext是否已初始化
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext has not been initialized");
        }
    }
}