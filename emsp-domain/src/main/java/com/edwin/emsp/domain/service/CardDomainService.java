package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.repository.CardRepository;
import com.edwin.emsp.model.entity.Card;
import com.edwin.emsp.model.event.model.BaseEvent;
import com.edwin.emsp.model.event.model.CardUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CardDomainService {
    private final CardRepository cardRepository;
    private final ApplicationEventPublisher publisher;

    public CardDomainService(CardRepository cardRepository, ApplicationEventPublisher publisher) {
        this.cardRepository = cardRepository;
        this.publisher = publisher;
    }

    public Card getCardById(Integer cardId) {
        return cardRepository.findById(cardId);
    }

    public Card findCardByUidAndVisibleNumber(String uid, String visibleNumber) {
        return cardRepository.findCardByUidAndVisibleNumber(uid, visibleNumber);
    }

    /**
     * 创建卡片
     * @param uid 卡片UID
     * @param visibleNumber 卡片可见编号
     * @return boolean
     */
    public Boolean createCard(String uid, String visibleNumber) {
        Card card = new Card();
        card.setUid(uid);
        card.setVisibleNumber(visibleNumber);
        card.setCreateTime(new Date());
        return cardRepository.save(card);
    }

    /**
     * 更新卡片
     * @param card 卡片实体
     * @return boolean
     */
    public Boolean updateCard(Card card) {
        // 更新卡片信息
        card.setUpdateTime(new Date());
        return cardRepository.save(card);
    }

    /**
     *
     * @param cardId
     * @param status
     */
    public void changeCardStatus(Integer cardId, Integer status) {
        Card card = this.getCardById(cardId);
        String oldStatus = card.getStatus().toString();
        card.setStatus(status);
        cardRepository.save(card);

        CardUpdatedEvent cardUpdatedEvent = CardUpdatedEvent.builder()
                .cardId(cardId)
                .email(card.getEmail())
                .oldStatus(oldStatus)
                .newStatus(status.toString())
                .build();
        BaseEvent<com.edwin.emsp.model.event.model.CardUpdatedEvent> event = new BaseEvent<>(this, "card status updated", cardUpdatedEvent);

        publisher.publishEvent(event);
    }

    /**
     *
     * @param email String
     * @return List
     */
    public List<Card> selectCardsByEmail(String email) {
        return cardRepository.findByEmail(email);
    }

    /**
     *
     * @param emails List<String>
     * @return List
     */
    public List<Card> selectCardsByEmails(List<String> emails) {
        return cardRepository.findByEmails(emails);
    }

    /**
     *
     * @param cards
     * @return
     */
    public Integer batchUpdateCards(List<Card> cards) {
        return cardRepository.batchUpdateCards(cards);
    }

}