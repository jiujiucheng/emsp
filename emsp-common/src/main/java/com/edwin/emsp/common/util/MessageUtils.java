package com.edwin.emsp.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @Author: jiucheng
 * @Description: 获取i18n 资源文件
 * @Date: 2025/4/18
 */
public class MessageUtils {
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */

    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
