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
public class CardService {
    @Autowired
    private AccountService accountService;

    private final CardDomainService cardDomainService;

    public CardService(CardDomainService cardDomainService) {
        this.cardDomainService = cardDomainService;
    }


    /**
     * 创建卡片
     *
     * @param cardDO Card
     * @return boolean
     */
    public Boolean createCard(Card cardDO) {
        Card existCard = cardDomainService.findCardByUidAndVisibleNumber(cardDO.getUid(), cardDO.getVisibleNumber());
        if (Objects.nonNull(existCard)) {
            throw new BizException(MessageUtils.message("error.card.repeat.created", (Object) null));
        }
        return cardDomainService.createCard(cardDO.getUid(), cardDO.getVisibleNumber());
    }

    /**
     * 修改卡状态： 3-激活、4-冻结
     *
     * @param cardDO Card
     * @return boolean
     */
    public boolean changeCardStatus(Card cardDO) {
        Card card = cardDomainService.getCardById(cardDO.getId());

        if (Objects.isNull(card)) {
            throw new BizException(MessageUtils.message("error.card.not.exist", (Object) null));
        }

        if (cardDO.getStatus().equals(CardStatusType.STATUS_ACTIVE.getCardStatus())
                && !card.getStatus().equals(CardStatusType.STATUS_INACTIVE.getCardStatus())
                && !card.getStatus().equals(CardStatusType.STATUS_ASSIGNED.getCardStatus())
        ) {
            throw new BizException(MessageUtils.message("error.card.op.not.active", (Object) null));
        }
        if (cardDO.getStatus().equals(CardStatusType.STATUS_INACTIVE.getCardStatus())
                && !card.getStatus().equals(CardStatusType.STATUS_ACTIVE.getCardStatus())) {
            throw new BizException(MessageUtils.message("error.card.op.inactive", (Object) null));
        }
        try {
            cardDomainService.changeCardStatus(cardDO.getId(), cardDO.getStatus());
        } catch (Exception e) {
            logger.error("update card status exception ", e);
            throw new BizException(MessageUtils.message("error.card.update.failed", (Object) null), e);
        }
        return true;
    }

    /**
     * @param cardDO Card
     * @return boolean
     */
    public boolean assignCardToAccount(Card cardDO) {
        Card card = cardDomainService.getCardById(cardDO.getId());

        if (Objects.isNull(card)) {
            throw new BizException(MessageUtils.message("error.card.not.exist", (Object) null));

        }
        if (!card.getStatus().equals(CardStatusType.STATUS_CREATED.getCardStatus())) {
            throw new BizException(MessageUtils.message("error.card.op.assign", (Object) null));

        }
        Account account = accountService.getAccount(cardDO.getEmail());
        if (Objects.isNull(account)) {
            throw new BizException(MessageUtils.message("error.account.not.exist", (Object) null));

        }
        if (!account.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
            throw new BizException(MessageUtils.message("error.account.status.inactive", (Object) null));

        }
        return cardDomainService.assignCardToAccount(cardDO.getId(), cardDO.getEmail());
    }
}
