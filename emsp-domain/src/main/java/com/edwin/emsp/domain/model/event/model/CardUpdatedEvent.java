package com.edwin.emsp.domain.model.event.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: CardUpdatedEvent
 * @Date: 2025/4/14
 */
@Data
@Builder
public class CardUpdatedEvent {
    private Integer cardId;

    private String email;

    private String oldStatus;

    private String newStatus;

    private Date updatedTime;
}
