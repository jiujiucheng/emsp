package com.edwin.emsp.domain.model.event.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.ApplicationEvent;

/**
 * @Author: jiucheng
 * @Description: BaseEvent
 * @Date: 2025/4/12
 */
@Getter
@Setter
public class BaseEvent<T> extends ApplicationEvent {

    private String eventType;

    private T data;

    public BaseEvent(Object source, String eventType, T data) {
        super(source);
        this.eventType = eventType;
        this.data = data;
    }

}
