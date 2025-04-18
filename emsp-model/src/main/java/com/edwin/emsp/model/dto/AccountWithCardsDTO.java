package com.edwin.emsp.model.dto;

import com.edwin.emsp.model.entity.Card;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: jiucheng
 * @Description: AccountWithCardsDTO
 * @Date: 2025/4/14
 */
@Data
public class AccountWithCardsDTO {
    private Integer id;

    private String email;

    private String contractId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private List<Card> cards;
}