package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.model.event.model.BaseEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @Author: jiucheng
 * @Description: 统一事件发布逻辑
 * @Date: 2025/05/07
 */
@Service
public class EventPublisher {
    private final ApplicationEventPublisher publisher;

    public EventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public <T> void publishEvent(String eventType, T payload) {
        BaseEvent<T> event = new BaseEvent<>(this, eventType, payload);
        publisher.publishEvent(event);
    }
}