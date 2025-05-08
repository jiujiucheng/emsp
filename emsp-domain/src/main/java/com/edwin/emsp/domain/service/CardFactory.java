package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.model.entity.Card;
import com.edwin.emsp.domain.repository.po.CardPO;
import org.springframework.stereotype.Service;

@Service
public class CardFactory {

    private CardPO cardPO;

    public CardPO createCardPO(Card card) {
        CardPO cardPO = new CardPO();
        cardPO.setId(card.getId());
        cardPO.setUid(card.getUid());
        cardPO.setVisibleNumber(card.getVisibleNumber());
        cardPO.setStatus(card.getStatus());
        cardPO.setEmail(card.getEmail());
        cardPO.setCreateTime(card.getCreateTime());
        return cardPO;
    }

    public Card createCard(CardPO cardPO) {
        Card card = new Card();
        card.setId(cardPO.getId());
        card.setUid(cardPO.getUid());
        card.setVisibleNumber(cardPO.getVisibleNumber());
        card.setStatus(cardPO.getStatus());
        card.setEmail(cardPO.getEmail());
        card.setCreateTime(cardPO.getCreateTime());
        card.setUpdateTime(cardPO.getUpdateTime());
        return card;
    }
}
