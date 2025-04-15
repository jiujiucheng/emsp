package com.edwin.emsp.event.model;

import com.edwin.emsp.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: TODO
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
