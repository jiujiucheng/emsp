package com.edwin.emsp.event.listener;

import com.alibaba.fastjson.JSON;
import com.edwin.emsp.event.model.BaseEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Author: jiucheng
 * @Description: TODO
 * @Date: 2025/4/12
 */
@Component
public class NotificationEventListener {
    /**
     * 模拟发送邮件
     *
     * @param event
     */
    @Async
    @EventListener
    public void sendEmail(BaseEvent<?> event) {
        System.out.println("事件类型：" + event.getEventType() + "事件数据:" + JSON.toJSONString(event.getData()));
    }
}
