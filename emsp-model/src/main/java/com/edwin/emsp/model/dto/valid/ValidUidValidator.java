package com.edwin.emsp.model.dto.valid;

import com.edwin.emsp.common.util.EmaidUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @Author: jiucheng
 * @Description: 自定义校验器
 * @Date: 2025/4/14
 */
public class ValidUidValidator implements ConstraintValidator<ValidUid, String> {
    @Override
    public void initialize(ValidUid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return EmaidUtils.isValidEmaid(value);
    }
}
