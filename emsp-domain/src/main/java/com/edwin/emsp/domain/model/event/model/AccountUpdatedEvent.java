package com.edwin.emsp.domain.model.event.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: AccountUpdatedEvent
 * @Date: 2025/4/12
 */
@Data
@Builder
public class AccountUpdatedEvent {

    private String email;

    private String oldStatus;

    private String newStatus;

    private Date updateTime;
}
