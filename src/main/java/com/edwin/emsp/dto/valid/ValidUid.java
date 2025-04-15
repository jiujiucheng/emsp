package com.edwin.emsp.dto.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: jiucheng
 * @Description: TODO
 * @Date: 2025/4/14
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidUidValidator.class) // 指定校验器
public @interface ValidUid {
    String message() default "uid格式无效"; // 默认错误消息

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
