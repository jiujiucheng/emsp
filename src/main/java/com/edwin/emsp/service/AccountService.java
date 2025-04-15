package com.edwin.emsp.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.common.util.EmaidUtils;
import com.edwin.emsp.dto.AccountRequestDTO;
import com.edwin.emsp.dto.AccountWithCardsDTO;
import com.edwin.emsp.entity.Account;
import com.edwin.emsp.dao.mapper.AccountMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edwin.emsp.entity.Card;
import com.edwin.emsp.enums.AccountStatusType;
import com.edwin.emsp.enums.CardStatusType;
import com.edwin.emsp.event.model.AccountCreatedEvent;
import com.edwin.emsp.event.model.AccountUpdatedEvent;
import com.edwin.emsp.event.model.BaseEvent;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
public class AccountService extends ServiceImpl<AccountMapper, Account> {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private AccountMapper accountMapper;

    public boolean createAccount(AccountRequestDTO accountRequestDTO) {
        LambdaQueryWrapper<Account> qw = new QueryWrapper<Account>()
                .lambda()
                .eq(Account::getEmail, accountRequestDTO.getEmail());
        if (Objects.nonNull(super.getOne(qw))) {
            throw new BizException("邮箱已存在");
        }
        Account account = new Account();
        account.setEmail(accountRequestDTO.getEmail());
        Date createdTime = new Date();
        account.setCreateTime(createdTime);
        super.save(account);
        //发送事件
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .email(account.getEmail())
                .status(AccountStatusType.STATUS_CREATED.getDesc())
                .createTime(createdTime)
                .build();
        BaseEvent<AccountCreatedEvent> event = new BaseEvent<>(this, "account created", accountCreatedEvent);
        publisher.publishEvent(event);
        return true;

    }

    public boolean updateAccount(AccountRequestDTO accountRequestDTO) {

        // 更改账号状态
        LambdaQueryWrapper<Account> qw = new QueryWrapper<Account>()
                .lambda()
                .eq(Account::getEmail, accountRequestDTO.getEmail());

        Account account = super.getOne(qw);
        if (Objects.isNull(account)) {
            throw new BizException("账号不存在");
        }
        String oldStatus = AccountStatusType.getDescByStatus(accountRequestDTO.getStatus());
        if (accountRequestDTO.getStatus().equals(account.getStatus())) {
            throw new BizException("修改账号不能重复操作");
        }
        
        if (accountRequestDTO.getStatus().equals(AccountStatusType.STATUS_INACTIVE.getAccountStatus())
                && !account.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
            throw new BizException("账号不是激活状态,不能失效");
        }
        account.setStatus(accountRequestDTO.getStatus());
        // 激活生成ContractId
        if (accountRequestDTO.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
            account.setContractId(EmaidUtils.generate());
        }
        Date lastUpdateTime = new Date();
        account.setUpdateTime(lastUpdateTime);
        super.updateById(account);

        if (accountRequestDTO.getStatus().equals(AccountStatusType.STATUS_INACTIVE.getAccountStatus())) {
            List<Card> cards = accountMapper.selectCardsByEmail(account.getEmail());
            if (!cards.isEmpty()) {
                cards.forEach(card -> {
                    card.setStatus(CardStatusType.STATUS_INACTIVE.getCardStatus());
                    card.setUpdateTime(new Date());
                });
                accountMapper.batchUpdateCards(cards);
            }
        }

        // 发送事件： 邮箱通知
        AccountUpdatedEvent accountUpdatedEvent = AccountUpdatedEvent.builder()
                .email(account.getEmail())
                .oldStatus(oldStatus)
                .newStatus(AccountStatusType.getDescByStatus(accountRequestDTO.getStatus()))
                .updateTime(lastUpdateTime)
                .build();
        BaseEvent<AccountUpdatedEvent> event = new BaseEvent<>(this, "account update", accountUpdatedEvent);

        publisher.publishEvent(event);
        return true;
    }

    /**
     * @param email
     * @return
     */
    public Account getAccount(String email) {
        LambdaQueryWrapper<Account> qw = new QueryWrapper<Account>()
                .lambda()
                .eq(Account::getEmail, email);
        return super.getOne(qw);
    }

    /**
     * @param lastUpdated
     * @param page
     * @param size
     * @return
     */
    public PageInfo<AccountWithCardsDTO> getAccountsWithCards(Date lastUpdated, int page, int size) {
        PageHelper.startPage(page, size);

        List<Account> accounts = accountMapper.selectAccountsByLastUpdated(lastUpdated);

        List<String> emails = accounts.stream()
                .map(Account::getEmail)
                .collect(Collectors.toList());
        List<Card> cards = !emails.isEmpty() ? accountMapper.selectCardsByEmails(emails) : Collections.emptyList();
        Map<String, List<Card>> emailToCardsMap = cards.stream()
                .collect(Collectors.groupingBy(Card::getEmail));

        List<AccountWithCardsDTO> dtos = accounts.stream()
                .map(acc -> {
                    AccountWithCardsDTO dto = new AccountWithCardsDTO();
                    BeanUtils.copyProperties(acc, dto);
                    dto.setCards(emailToCardsMap.getOrDefault(acc.getEmail(), Collections.emptyList()));
                    return dto;
                })
                .collect(Collectors.toList());

        return new PageInfo<>(dtos);
    }

}
