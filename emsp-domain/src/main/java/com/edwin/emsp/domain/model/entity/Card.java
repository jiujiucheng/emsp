package com.edwin.emsp.domain.model.entity;

import com.edwin.emsp.domain.model.enums.CardStatusType;
import lombok.Data;

import java.util.Date;

/**
 * @Author: jiucheng
 * @Description: Card 领域实体
 * @Date: 2025/4/14
 */
@Data
public class Card {
    private Integer id;
    private String email;
    private String uid;
    private String visibleNumber;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    // 领域行为：创建卡片
    public Card create(String uid, String visibleNumber) {
        this.uid = uid;
        this.visibleNumber = visibleNumber;
        this.createTime = new Date();
        this.status = CardStatusType.STATUS_CREATED.getCardStatus();
        return this;
    }

    // 领域行为：更新卡片信息
    public Card update(String uid, String visibleNumber) {
        this.uid = uid;
        this.visibleNumber = visibleNumber;
        this.updateTime = new Date();
        return this;
    }

    // 领域行为：变更卡片状态
    public Card changeStatus(Integer status) {
        this.status = status;
        this.updateTime = new Date();
        return this;
    }

    // 领域行为：分配卡片到账户
    public Card assignToAccount(String email) {
        this.email = email;
        this.status = CardStatusType.STATUS_ASSIGNED.getCardStatus();
        this.updateTime = new Date();
        return this;
    }
}