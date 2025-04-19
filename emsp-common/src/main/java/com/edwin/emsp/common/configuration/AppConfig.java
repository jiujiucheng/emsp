package com.edwin.emsp.common.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @Author: jiucheng
 * @Description: 通用配置i18n
 * @Date: 2025/4/17
 */
@Configuration
public class AppConfig {
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);  // 设置默认语言为英文
        return resolver;
    }

    @Bean
    public WebMvcConfigurer localeInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
                interceptor.setParamName("lang");  // 拦截参数名（如 ?lang=zh_CN）
                registry.addInterceptor(interceptor);
            }
        };
    }
}
