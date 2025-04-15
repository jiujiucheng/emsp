package com.edwin.emsp.event.model;

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
public class AccountCreatedEvent {
    private String email;

    private String status;

    private Date createTime;
}
