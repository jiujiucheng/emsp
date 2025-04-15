package com.edwin.emsp.common.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @Author: jiucheng
 * @Description: EmaidUtils
 * @Date: 2025/4/12
 */
public class EmaidUtils {

    /**
     * 部分国家代码（完整列表可扩展）
     */
    private static final List<String> VALID_COUNTRY_CODES = Arrays.asList(
            "US", "DE", "CN", "JP", "FR", "GB", "IT", "CA", "KR", "RU"
    );

    private static final Random random = new Random();


    /**
     * 移除连字符、校验码，转为大写
     *
     * @param rawEmaid
     * @return
     */
    public static String normalizeEmaid(String rawEmaid) {
        String normalized = rawEmaid.replaceAll("-", "").toUpperCase();
        if (normalized.length() > 14) {
            // 移除末尾的校验码（如果存在）
            normalized = normalized.substring(0, 14);
        }
        return normalized;
    }


    /**
     * 校验输入是否合法（兼容两种格式）
     *
     * @param rawEmaid
     * @return
     */
    public static boolean isValidEmaid(String rawEmaid) {
        return rawEmaid.matches("^([A-Za-z]{2}(-?)[A-Za-z0-9]{3}\\2[A-Za-z0-9]{9}(\\2[A-Za-z0-9])?)$");
    }

    /**
     * emaid generator
     *
     * @return
     */
    public static String generate() {
        String countryCode = VALID_COUNTRY_CODES.get(random.nextInt(VALID_COUNTRY_CODES.size()));
        String providerId = randomAlphanumeric(3);
        String emaInstance = randomAlphanumeric(9);
        return countryCode + providerId + emaInstance;
    }

    private static String randomAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }
}
