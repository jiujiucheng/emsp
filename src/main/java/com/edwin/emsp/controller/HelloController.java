package com.edwin.emsp.controller;

import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.vo.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiucheng
 * @Description: TODO
 * @Date: 2025/4/12
 */
@Slf4j
@RestController
public class HelloController {
    @GetMapping("/api/v1/hello")
    public Message<String> hello() {
        System.currentTimeMillis();
        logger.info("132123");
//        throw new BizException("123");
        return Message.ok("hello");
    }
}
