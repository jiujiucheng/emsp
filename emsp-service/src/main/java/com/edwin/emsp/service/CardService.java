package com.edwin.emsp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.common.util.MessageUtils;
import com.edwin.emsp.dao.mapper.CardMapper;
import com.edwin.emsp.model.dto.CardRequestDTO;
import com.edwin.emsp.model.entity.Account;
import com.edwin.emsp.model.entity.Card;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edwin.emsp.model.enums.AccountStatusType;
import com.edwin.emsp.model.enums.CardStatusType;
import com.edwin.emsp.model.event.model.BaseEvent;
import com.edwin.emsp.model.event.model.CardUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class CardService extends ServiceImpl<CardMapper, Card> {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AccountService accountService;

    /**
     * 创建卡片
     * @param cardRequestDTO CardRequestDTO
     * @return boolean
     */
    public boolean createCard(CardRequestDTO cardRequestDTO) {
        LambdaQueryWrapper<Card> qw = new QueryWrapper<Card>()
                .lambda()
                .eq(Card::getUid, cardRequestDTO.getUid())
                .eq(Card::getVisibleNumber, cardRequestDTO.getVisibleNumber());
        if (Objects.nonNull(super.getOne(qw))) {
            throw new BizException(MessageUtils.message("error.card.repeat.created", (Object) null));
        }
        Card card = new Card();
        card.setUid(cardRequestDTO.getUid());
        card.setVisibleNumber(cardRequestDTO.getVisibleNumber());
        Date createdTime = new Date();
        card.setCreateTime(createdTime);
        return super.save(card);
    }

    /**
     * 修改卡状态： 3-激活、4-冻结
     * @param cardRequestDTO CardRequestDTO
     * @return boolean
     */
    public boolean changeCardStatus(CardRequestDTO cardRequestDTO) {
        LambdaQueryWrapper<Card> qw = new QueryWrapper<Card>()
                .lambda()
                .eq(Card::getId, cardRequestDTO.getCardId());
        Card card = super.getOne(qw);
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

//            throw new BizException("卡号不是激活状态,不能失效");
        }
        String oldStatus = CardStatusType.getDescByStatus(cardRequestDTO.getStatus());

        card.setStatus(cardRequestDTO.getStatus());
        Date lastUpdateTime = new Date();
        card.setUpdateTime(lastUpdateTime);
        super.updateById(card);
        CardUpdatedEvent cardUpdatedEvent = CardUpdatedEvent.builder()
                .cardId(card.getId())
                .email(card.getEmail())
                .oldStatus(oldStatus)
                .newStatus(CardStatusType.getDescByStatus(cardRequestDTO.getStatus()))
                .build();
        BaseEvent<CardUpdatedEvent> event = new BaseEvent<>(this, "card status updated", cardUpdatedEvent);
        publisher.publishEvent(event);
        return true;
    }

    /**
     *
     * @param cardRequestDTO CardRequestDTO
     * @return boolean
     */
    public boolean assignCardToAccount(CardRequestDTO cardRequestDTO) {
        LambdaQueryWrapper<Card> qw = new QueryWrapper<Card>()
                .lambda()
                .eq(Card::getId, cardRequestDTO.getCardId());
        Card card = super.getOne(qw);
        if (Objects.isNull(card)) {
//            throw new BizException("卡号不存在");
            throw new BizException(MessageUtils.message("error.card.not.exist", (Object) null));

        }
        if (!card.getStatus().equals(CardStatusType.STATUS_CREATED.getCardStatus())) {
//            throw new BizException("卡不是创建状态，不能分配");
            throw new BizException(MessageUtils.message("error.card.op.assign", (Object) null));

        }
        Account account = accountService.getAccount(cardRequestDTO.getEmail());
        if (Objects.isNull(account)) {
//            throw new BizException("邮箱错误");
            throw new BizException(MessageUtils.message("error.account.not.exist", (Object) null));

        }
        if (!account.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
//            throw new BizException("邮箱状态无效,请激活账号");
            throw new BizException(MessageUtils.message("error.account.status.inactive", (Object) null));

        }
        card.setStatus(CardStatusType.STATUS_ASSIGNED.getCardStatus());
        card.setEmail(cardRequestDTO.getEmail());
        card.setUpdateTime(new Date());
        return super.updateById(card);
    }
}
