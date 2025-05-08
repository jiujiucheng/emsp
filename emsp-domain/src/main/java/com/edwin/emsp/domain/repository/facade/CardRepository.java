package com.edwin.emsp.domain.repository.facade;

import com.edwin.emsp.domain.model.entity.Card;
import com.edwin.emsp.domain.repository.po.CardPO;

import java.util.List;

/**
 * @Author: jiucheng
 * @Description: CardRepository Card 仓储接口
 * @Date: 2025/4/14
 */
public interface CardRepository {
    CardPO findById(Integer id);

    List<CardPO> findByEmail(String email);

    List<CardPO> findByEmails(List<String> emails);

    Boolean save(CardPO card);

    CardPO findCardByUidAndVisibleNumber(String uid, String visibleNumber);

    Integer batchUpdateCards(List<CardPO> cards);
}