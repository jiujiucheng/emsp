package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.repository.facade.AccountRepository;
import com.edwin.emsp.domain.model.entity.Account;
import com.edwin.emsp.domain.model.enums.AccountStatusType;
import com.edwin.emsp.domain.model.event.model.AccountCreatedEvent;
import com.edwin.emsp.domain.model.event.model.AccountUpdatedEvent;
import com.edwin.emsp.domain.repository.po.AccountPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author: jiucheng
 * @Description: AccountDomainService
 * @Date: 2025/05/07
 */
@Service
public class AccountDomainService {
    private final AccountRepository accountRepository;

    private final EventPublisher publisher;
    @Autowired
    AccountFactory accountFactory;

    public AccountDomainService(AccountRepository accountRepository, EventPublisher publisher) {
        this.accountRepository = accountRepository;
        this.publisher = publisher;
    }

    public Account getAccountByEmail(String email) {
        return Optional.ofNullable(accountRepository.findByEmail(email))
                .map(accountFactory::createAccount)
                .orElse(null);
    }

    public List<Account> findAccountsByEmail(String email) {
        List<AccountPO> accountPOList = accountRepository.findAccountsByEmail(email);
        return accountPOList.stream().map(accountFactory::createAccount).collect(Collectors.toList());
    }

    public void createAccount(Account account) {
        account.create();
        accountRepository.save(accountFactory.createAccountPO(account));
        //发送事件
        Date createdTime = new Date();
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .email(account.getEmail())
                .status(AccountStatusType.STATUS_CREATED.getDesc())
                .createTime(createdTime)
                .build();
        publisher.publishEvent("account created", accountCreatedEvent);
    }

    public void updateAccount(Account account, Integer status) {
        String oldStatus = AccountStatusType.getDescByStatus(account.getStatus());
        account.updateStatus(status);
        accountRepository.update(accountFactory.createAccountPO(account));

        // 发送事件： 邮箱通知
        AccountUpdatedEvent accountUpdatedEvent = AccountUpdatedEvent.builder()
                .email(account.getEmail())
                .oldStatus(oldStatus)
                .newStatus(AccountStatusType.getDescByStatus(status))
                .updateTime(new Date())
                .build();
        publisher.publishEvent("account update", accountUpdatedEvent);
    }
}