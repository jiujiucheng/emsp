package com.edwin.emsp.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.dao.mapper.CardMapper;
import com.edwin.emsp.domain.repository.CardRepository;
import com.edwin.emsp.domain.model.entity.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class CardRepositoryImpl implements CardRepository {

    @Autowired
    private CardMapper cardMapper;

    @Override
    public Card findById(Integer id) {
        return cardMapper.selectById(id);
    }

    @Override
    public List<Card> findByEmail(String email) {
        return cardMapper.selectCardsByEmail(email);
    }

    @Override
    public List<Card> findByEmails(List<String> emails) {
        return cardMapper.selectCardsByEmails(emails);
    }


    @Override
    public Boolean save(Card card) {
        if (Objects.isNull(card.getId())) {
            return cardMapper.insert(card) > 0 ;
        } else {
            return cardMapper.updateById(card) > 0;
        }
    }

    @Override
    public Card findCardByUidAndVisibleNumber(String uid, String visibleNumber) {
        return cardMapper.selectOne(new QueryWrapper<Card>()
                .lambda()
                .eq(Card::getUid, uid)
                .eq(Card::getVisibleNumber, visibleNumber));
    }

    /**
     *
     * @param cards List
     * @return Integer
     */
    public  Integer batchUpdateCards(List<Card> cards) {
        return cardMapper.batchUpdateCards(cards);
    }
}
