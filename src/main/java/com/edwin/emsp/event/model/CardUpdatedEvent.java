package com.edwin.emsp.event.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: TODO
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
