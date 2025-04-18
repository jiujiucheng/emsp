package com.edwin.emsp;

/**
 * @Author: jiucheng
 * @Description: EmaidUtils 单元测试
 * @Date: 2025/4/14
 */

import com.edwin.emsp.common.util.EmaidUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmaidUtilsTest {

    @Test
    void testNormalizeEmaid() {
        // 测试移除连字符
        assertEquals("USABC123456789", EmaidUtils.normalizeEmaid("US-ABC-123456789"));
        // 测试转为大写
        assertEquals("USABC123456789", EmaidUtils.normalizeEmaid("us-abc-123456789"));
        // 测试移除校验码
        assertEquals("USABC123456789", EmaidUtils.normalizeEmaid("US-ABC-123456789-X"));
    }

    @Test
    void testIsValidEmaid() {
        // 测试合法格式
        assertTrue(EmaidUtils.isValidEmaid("US-ABC-123456789"));
        assertTrue(EmaidUtils.isValidEmaid("USABC123456789"));
        // 测试非法格式
        assertFalse(EmaidUtils.isValidEmaid("US-ABC-12345678")); // 长度不足
        assertFalse(EmaidUtils.isValidEmaid("USA-ABC-123456789")); // 国家代码错误
    }

    @Test
    void testGenerate() {
        // 测试生成的EMAID格式
        String emaid = EmaidUtils.generate();
        assertNotNull(emaid);
        assertEquals(14, emaid.length());
        assertTrue(emaid.matches("^[A-Z]{2}[A-Z0-9]{3}[A-Z0-9]{9}$"));
    }
}