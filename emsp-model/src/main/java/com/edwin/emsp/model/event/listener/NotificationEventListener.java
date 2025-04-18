package com.edwin.emsp.model.event.listener;

import com.alibaba.fastjson.JSON;
import com.edwin.emsp.model.event.model.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: jiucheng
 * @Description: EventListener
 * @Date: 2025/4/12
 */
@Component
@Slf4j
public class NotificationEventListener {
    /**
     * 模拟发送邮件
     *
     * @param event
     */
    @Async
    @EventListener
    public void sendEmail(BaseEvent<?> event) {
        logger.info("事件类型：" + event.getEventType() + "事件数据:" + JSON.toJSONString(event.getData()));
    }
}
