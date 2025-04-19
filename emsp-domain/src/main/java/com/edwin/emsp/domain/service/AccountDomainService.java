package com.edwin.emsp.domain.service;

import com.edwin.emsp.domain.repository.AccountRepository;
import com.edwin.emsp.model.entity.Account;
import com.edwin.emsp.model.enums.AccountStatusType;
import com.edwin.emsp.model.event.model.AccountCreatedEvent;
import com.edwin.emsp.model.event.model.AccountUpdatedEvent;
import com.edwin.emsp.model.event.model.BaseEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountDomainService {
    private final AccountRepository accountRepository;

    private final ApplicationEventPublisher publisher;

    public AccountDomainService(AccountRepository accountRepository, ApplicationEventPublisher publisher) {
        this.accountRepository = accountRepository;
        this.publisher = publisher;
    }

    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public List<Account> findAccountsByEmail(String  email) {
        return accountRepository.findAccountsByEmail(email);
    }

    public void createAccount(Account account) {
        accountRepository.save(account);
        //发送事件
        Date createdTime = new Date();
        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .email(account.getEmail())
                .status(AccountStatusType.STATUS_CREATED.getDesc())
                .createTime(createdTime)
                .build();
        BaseEvent<AccountCreatedEvent> event = new BaseEvent<>(this, "account created", accountCreatedEvent);
        publisher.publishEvent(event);
    }

    public void updateAccount(Account account,Integer status) {
        String oldStatus = AccountStatusType.getDescByStatus(account.getStatus());
        Date lastUpdateTime = new Date();
        account.setUpdateTime(lastUpdateTime);
        account.setStatus(status);
        accountRepository.update(account);

        // 发送事件： 邮箱通知
        AccountUpdatedEvent accountUpdatedEvent = AccountUpdatedEvent.builder()
                .email(account.getEmail())
                .oldStatus(oldStatus)
                .newStatus(AccountStatusType.getDescByStatus(status))
                .updateTime(lastUpdateTime)
                .build();
        BaseEvent<AccountUpdatedEvent> accountEvent = new BaseEvent<>(this, "account update", accountUpdatedEvent);

        publisher.publishEvent(accountEvent);
    }
}