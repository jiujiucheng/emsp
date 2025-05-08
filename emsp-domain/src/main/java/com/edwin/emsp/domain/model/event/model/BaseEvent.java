package com.edwin.emsp.domain.model.event.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.ApplicationEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: jiucheng
 * @Description: BaseEvent
 * @Date: 2025/4/12
 */
@Getter
@Setter
public class BaseEvent<T> extends ApplicationEvent {

    private String eventId;

    private Date eventTime;

    private String source;

    private String eventType;

    private T data;

    public BaseEvent(Object source, String eventType, T data) {
        super(source);
        this.eventId = UUID.randomUUID().toString();
        this.eventTime = Calendar.getInstance().getTime();
        this.source = source.getClass().getSimpleName();
        this.eventType = eventType;
        this.data = data;
    }

}
