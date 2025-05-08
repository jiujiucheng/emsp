package com.edwin.emsp.domain.model.entity;

import com.edwin.emsp.domain.model.enums.AccountStatusType;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: Account 领域实体
 * @Date: 2025/4/14
 */
@Data
public class Account {
    private Integer id;
    private String email;
    private String contractId;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    // 领域行为：创建账户
    public Account create() {
        this.status = AccountStatusType.STATUS_CREATED.getAccountStatus();
        this.createTime = new Date();
        return this;
    }

    // 领域行为：更新账户状态
    public Account updateStatus(Integer status) {
        this.status = status;
        this.updateTime = new Date();
        return this;
    }
}