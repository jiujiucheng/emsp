package com.edwin.emsp.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.common.util.EmaidUtils;
import com.edwin.emsp.dao.mapper.AccountMapper;
import com.edwin.emsp.dao.mapper.CardMapper;
import com.edwin.emsp.model.dto.AccountRequestDTO;
import com.edwin.emsp.model.dto.AccountWithCardsDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edwin.emsp.model.entity.Account;
import com.edwin.emsp.model.entity.Card;
import com.edwin.emsp.model.enums.AccountStatusType;
import com.edwin.emsp.model.enums.CardStatusType;
import com.edwin.emsp.model.event.model.AccountCreatedEvent;
import com.edwin.emsp.model.event.model.AccountUpdatedEvent;
import com.edwin.emsp.model.event.model.BaseEvent;
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

    @Autowired
    private CardMapper cardMapper;

    /**
     *
     * @param accountRequestDTO AccountRequestDTO
     * @return boolean
     */
    public boolean createAccount(AccountRequestDTO accountRequestDTO) {
        logger.info("create Account accountRequestDTO ： {}", JSON.toJSONString(accountRequestDTO));
        LambdaQueryWrapper<Account> qw = new QueryWrapper<Account>()
                .lambda()
                .eq(Account::getEmail, accountRequestDTO.getEmail());
        if (Objects.nonNull(super.getOne(qw))) {
            throw new BizException("邮箱已存在");
        }
        Account account = new Account();
        Date createdTime = new Date();
        try{
            account.setEmail(accountRequestDTO.getEmail());
            account.setCreateTime(createdTime);
            super.save(account);
        } catch (Exception e) {
            logger.error("create account exception ",e);
        }

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

    /**
     *   更改账号状态，同步更改卡片状态
     * @param accountRequestDTO AccountRequestDTO
     * @return boolean
     */
    public boolean updateAccount(AccountRequestDTO accountRequestDTO) {
        logger.info("update Account accountRequestDTO ： {}", JSON.toJSONString(accountRequestDTO));
        LambdaQueryWrapper<Account> qw = new QueryWrapper<Account>()
                .lambda()
                .eq(Account::getEmail, accountRequestDTO.getEmail());
        Account account = super.getOne(qw);
        String oldStatus = AccountStatusType.getDescByStatus(accountRequestDTO.getStatus());
        Date lastUpdateTime = new Date();

        try{
            if (Objects.isNull(account)) {
                throw new BizException("账号不存在");
            }
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
            account.setUpdateTime(lastUpdateTime);
            super.updateById(account);
            //激活、冻结账户的同时激活、冻结卡
            List<Card> cards = cardMapper.selectCardsByEmail(account.getEmail());
            if (!cards.isEmpty()) {
                cards.forEach(card -> {
                    card.setStatus(CardStatusType.STATUS_INACTIVE.getCardStatus());
                    card.setUpdateTime(new Date());
                });
                cardMapper.batchUpdateCards(cards);
            }

        } catch (Exception e) {
            logger.error("update account status exception ",e);
        }
        // 发送事件： 邮箱通知
        AccountUpdatedEvent accountUpdatedEvent = AccountUpdatedEvent.builder()
                .email(account.getEmail())
                .oldStatus(oldStatus)
                .newStatus(AccountStatusType.getDescByStatus(accountRequestDTO.getStatus()))
                .updateTime(lastUpdateTime)
                .build();
        BaseEvent<AccountUpdatedEvent> accountEvent = new BaseEvent<>(this, "account update", accountUpdatedEvent);

        publisher.publishEvent(accountEvent);
        return true;
    }

    /**
     * @param email String
     * @return Account
     */
    public Account getAccount(String email) {
        LambdaQueryWrapper<Account> qw = new QueryWrapper<Account>()
                .lambda()
                .eq(Account::getEmail, email);
        return super.getOne(qw);
    }

    /**分页查询账号及卡片
     * @param lastUpdated Date
     * @param page int
     * @param size int
     * @return PageInfo<AccountWithCardsDTO>
     */
    public PageInfo<AccountWithCardsDTO> getAccountsWithCards(Date lastUpdated, int page, int size) {
        PageInfo<AccountWithCardsDTO> info = null;
        try{
           PageHelper.startPage(page, size);
           List<Account> accounts = accountMapper.selectAccountsByLastUpdated(lastUpdated);

           List<String> emails = accounts.stream()
                   .map(Account::getEmail)
                   .collect(Collectors.toList());
           List<Card> cards = !emails.isEmpty() ? cardMapper.selectCardsByEmails(emails) : Collections.emptyList();
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

           info = PageInfo.of(dtos);
       } catch (Exception e) {
           logger.warn("getAccountsWithCards got an exception",e);
       }finally {
           PageHelper.clearPage();
       }
        return info;
    }
}
