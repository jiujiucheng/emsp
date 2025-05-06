package com.edwin.emsp.service;

import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.common.util.MessageUtils;
import com.edwin.emsp.domain.service.CardDomainService;
import com.edwin.emsp.domain.model.dto.CardRequestDTO;
import com.edwin.emsp.domain.model.entity.Account;
import com.edwin.emsp.domain.model.entity.Card;
import com.edwin.emsp.domain.model.enums.AccountStatusType;
import com.edwin.emsp.domain.model.enums.CardStatusType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author jiucheng
 * @since 2025-04-12
 */
@Service
@Slf4j
public class CardService  {
    @Autowired
    private AccountService accountService;

    private final CardDomainService cardDomainService;

    public CardService(CardDomainService cardDomainService) {
        this.cardDomainService = cardDomainService;
    }


    /**
     * 创建卡片
     * @param cardRequestDTO CardRequestDTO
     * @return boolean
     */
    public Boolean createCard(CardRequestDTO cardRequestDTO) {
        Card existCard = cardDomainService.findCardByUidAndVisibleNumber(cardRequestDTO.getUid(), cardRequestDTO.getVisibleNumber());
        if (Objects.nonNull(existCard)) {
            throw new BizException(MessageUtils.message("error.card.repeat.created", (Object) null));
        }
        return cardDomainService.createCard(cardRequestDTO.getUid(),cardRequestDTO.getVisibleNumber());
    }

    /**
     * 修改卡状态： 3-激活、4-冻结
     * @param cardRequestDTO CardRequestDTO
     * @return boolean
     */
    public boolean changeCardStatus(CardRequestDTO cardRequestDTO) {
       /* LambdaQueryWrapper<Card> qw = new QueryWrapper<Card>()
                .lambda()
                .eq(Card::getId, cardRequestDTO.getCardId());
        Card card = super.getOne(qw);*/

        Card card =  cardDomainService.getCardById(cardRequestDTO.getCardId());

        if (Objects.isNull(card)) {
            throw new BizException(MessageUtils.message("error.card.not.exist", (Object) null));
        }

        if (cardRequestDTO.getStatus().equals(CardStatusType.STATUS_ACTIVE.getCardStatus())
                && !card.getStatus().equals(CardStatusType.STATUS_INACTIVE.getCardStatus())
                && !card.getStatus().equals(CardStatusType.STATUS_ASSIGNED.getCardStatus())
        ) {
            throw new BizException(MessageUtils.message("error.card.op.not.active", (Object) null));
        }
        if (cardRequestDTO.getStatus().equals(CardStatusType.STATUS_INACTIVE.getCardStatus())
                && !card.getStatus().equals(CardStatusType.STATUS_ACTIVE.getCardStatus())) {
            throw new BizException(MessageUtils.message("error.card.op.inactive", (Object) null));
        }
        cardDomainService.changeCardStatus(cardRequestDTO.getCardId(), cardRequestDTO.getStatus());
//        String oldStatus = CardStatusType.getDescByStatus(cardRequestDTO.getStatus());
//
//        card.setStatus(cardRequestDTO.getStatus());
//        Date lastUpdateTime = new Date();
//        card.setUpdateTime(lastUpdateTime);
//        super.updateById(card);
//        CardUpdatedEvent cardUpdatedEvent = CardUpdatedEvent.builder()
//                .cardId(card.getId())
//                .email(card.getEmail())
//                .oldStatus(oldStatus)
//                .newStatus(CardStatusType.getDescByStatus(cardRequestDTO.getStatus()))
//                .build();
//        BaseEvent<CardUpdatedEvent> event = new BaseEvent<>(this, "card status updated", cardUpdatedEvent);
//        publisher.publishEvent(event);
        return true;
    }

    /**
     *
     * @param cardRequestDTO CardRequestDTO
     * @return boolean
     */
    public boolean assignCardToAccount(CardRequestDTO cardRequestDTO) {
    /*    LambdaQueryWrapper<Card> qw = new QueryWrapper<Card>()
                .lambda()
                .eq(Card::getId, cardRequestDTO.getCardId());
        Card card = super.getOne(qw);*/
        Card card =  cardDomainService.getCardById(cardRequestDTO.getCardId());

        if (Objects.isNull(card)) {
            throw new BizException(MessageUtils.message("error.card.not.exist", (Object) null));

        }
        if (!card.getStatus().equals(CardStatusType.STATUS_CREATED.getCardStatus())) {
            throw new BizException(MessageUtils.message("error.card.op.assign", (Object) null));

        }
        Account account = accountService.getAccount(cardRequestDTO.getEmail());
        if (Objects.isNull(account)) {
            throw new BizException(MessageUtils.message("error.account.not.exist", (Object) null));

        }
        if (!account.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
            throw new BizException(MessageUtils.message("error.account.status.inactive", (Object) null));

        }

        card.setStatus(CardStatusType.STATUS_ASSIGNED.getCardStatus());
        card.setEmail(cardRequestDTO.getEmail());
        return cardDomainService.updateCard(card);
    }
}
