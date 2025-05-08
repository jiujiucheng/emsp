package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.model.enums.CardStatusType;
import com.edwin.emsp.domain.repository.facade.CardRepository;
import com.edwin.emsp.domain.model.entity.Card;
import com.edwin.emsp.domain.model.event.model.BaseEvent;
import com.edwin.emsp.domain.model.event.model.CardUpdatedEvent;
import com.edwin.emsp.domain.repository.po.CardPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: jiucheng
 * @Description: CardDomainService
 * @Date: 2025/05/07
 */
@Service
public class CardDomainService {
    private final CardRepository cardRepository;
    private final EventPublisher publisher;

    @Autowired
    CardFactory cardFactory;

    public CardDomainService(CardRepository cardRepository, EventPublisher publisher) {
        this.cardRepository = cardRepository;
        this.publisher = publisher;
    }

    /**
     * @param cardId
     * @return
     */
    public Card getCardById(Integer cardId) {
        return Optional.ofNullable(cardRepository.findById(cardId))
                .map(cardFactory::createCard)
                .orElse(null);
    }

    /**
     * @param uid
     * @param visibleNumber
     * @return
     */
    public Card findCardByUidAndVisibleNumber(String uid, String visibleNumber) {
        return Optional.ofNullable(cardRepository.findCardByUidAndVisibleNumber(uid, visibleNumber))
                .map(cardFactory::createCard)
                .orElse(null);
    }

    /**
     * 创建卡片
     *
     * @param uid           卡片UID
     * @param visibleNumber 卡片可见编号
     * @return boolean
     */
    public Boolean createCard(String uid, String visibleNumber) {
        Card card = new Card();
        card.create(uid, visibleNumber);
        return cardRepository.save(cardFactory.createCardPO(card));
    }


    /**
     * 修改卡片状态: 激活2 冻结3
     *
     * @param cardId
     * @param status
     */
    public void changeCardStatus(Integer cardId, Integer status) {
        Card card = this.getCardById(cardId);
        String oldStatus = CardStatusType.getDescByStatus(card.getStatus());
        card.changeStatus(status);
        cardRepository.save(cardFactory.createCardPO(card));

        CardUpdatedEvent cardUpdatedEvent = CardUpdatedEvent.builder()
                .cardId(cardId)
                .email(card.getEmail())
                .oldStatus(oldStatus)
                .newStatus(CardStatusType.getDescByStatus(status))
                .build();
        publisher.publishEvent("card status updated", cardUpdatedEvent);
    }

    /**
     * 分配卡片到账户
     *
     * @param cardId Integer
     * @param email  String
     * @return boolean
     */
    public Boolean assignCardToAccount(Integer cardId, String email) {
        Card card = this.getCardById(cardId);
        card.assignToAccount(email);
        return cardRepository.save(cardFactory.createCardPO(card));
    }

    /**
     * @param email String
     * @return List
     */
    public List<Card> selectCardsByEmail(String email) {
        List<CardPO> cardPOList = cardRepository.findByEmail(email);
        return cardPOList.stream().map(cardFactory::createCard).collect(Collectors.toList());
    }

    /**
     * @param emails List<String>
     * @return List
     */
    public List<Card> selectCardsByEmails(List<String> emails) {

        List<CardPO> cardPOList = cardRepository.findByEmails(emails);
        return cardPOList.stream().map(cardFactory::createCard).collect(Collectors.toList());
    }

    /**
     * @param cards
     * @return
     */
    public Integer batchUpdateCards(List<Card> cards) {
        return cardRepository.batchUpdateCards(cards.stream()
                .map(cardFactory::createCardPO)
                .collect(Collectors.toList()));
    }

}