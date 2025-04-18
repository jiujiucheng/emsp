package com.edwin.emsp;

import com.edwin.emsp.common.util.EmaidUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.TransactionUsageException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = EmspApplication.class)
class EmspApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testNormalizeEmaidWithHyphenAndCheckDigit() {
        String rawEmaid = "de-12A-3456789BCd-1";
        String normalized = EmaidUtils.normalizeEmaid(rawEmaid);
        assertEquals("DE12A3456789BC", normalized);
    }


}
