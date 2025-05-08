package com.edwin.emsp.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.dao.mapper.CardMapper;
import com.edwin.emsp.domain.repository.facade.CardRepository;
import com.edwin.emsp.domain.repository.po.CardPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * @Author: jiucheng
 * @Description: CardRepositoryImpl Card 仓储实现
 * @Date: 2025/4/14
 */
@Repository
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private CardMapper cardMapper;

    @Override
    public CardPO findById(Integer id) {
        return cardMapper.selectById(id);
    }

    @Override
    public List<CardPO> findByEmail(String email) {
        return cardMapper.selectCardsByEmail(email);
    }

    @Override
    public List<CardPO> findByEmails(List<String> emails) {
        return cardMapper.selectCardsByEmails(emails);
    }


    @Override
    public Boolean save(CardPO cardPO) {
        if (Objects.isNull(cardPO.getId())) {
            return cardMapper.insert(cardPO) > 0;
        } else {
            return cardMapper.updateById(cardPO) > 0;
        }
    }

    @Override
    public CardPO findCardByUidAndVisibleNumber(String uid, String visibleNumber) {
        return cardMapper.selectOne(new QueryWrapper<CardPO>()
                .lambda()
                .eq(CardPO::getUid, uid)
                .eq(CardPO::getVisibleNumber, visibleNumber));
    }

    /**
     * @param cards List
     * @return Integer
     */
    public Integer batchUpdateCards(List<CardPO> cards) {
        return cardMapper.batchUpdateCards(cards);
    }
}
