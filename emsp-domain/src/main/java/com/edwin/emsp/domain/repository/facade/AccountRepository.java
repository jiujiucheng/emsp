package com.edwin.emsp.domain.repository.facade;

import com.edwin.emsp.domain.model.entity.Account;
import com.edwin.emsp.domain.repository.po.AccountPO;

import java.util.List;

/**
 * @Author: jiucheng
 * @Description: AccountRepository Account 存储接口
 * @Date: 2025/4/14
 */
public interface AccountRepository {
    AccountPO findByEmail(String email);

    List<AccountPO> findAccountsByEmail(String email);

    void save(AccountPO account);

    void update(AccountPO account);
}