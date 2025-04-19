package com.edwin.emsp;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.redisson.api.RAtomicLong; // 导入 RAtomicLong 类

/**
 * @Author: jiucheng
 * @Description: RedissonClientTest
 * @Date: 2025/4/18
 */
@SpringBootTest(classes = EmspApplication.class)
public class RedissonClientTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testBasicOperations() {
        // 测试字符串操作
        RBucket<String> bucket = redissonClient.getBucket("testKey");
        bucket.set("testValue");
        assertEquals("testValue", bucket.get());

        // 测试删除操作
        bucket.delete();
        assertNull(bucket.get());

        // 测试过期时间
        bucket.set("testValueWithTTL", 10, TimeUnit.SECONDS); // 10秒过期
        assertEquals("testValueWithTTL", bucket.get());
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        RAtomicLong counter = redissonClient.getAtomicLong("counter");
        counter.set(0);

        // 模拟并发操作
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.incrementAndGet();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.incrementAndGet();
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        assertEquals(200, counter.get());
    }
}
