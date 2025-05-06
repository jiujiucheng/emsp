package com.edwin.emsp.domain.repository;

import com.edwin.emsp.domain.model.entity.Account;

import java.util.List;

public interface AccountRepository {
    Account findByEmail(String email);
    List<Account> findAccountsByEmail(String email);
    void save(Account account);
    void update(Account account);
}