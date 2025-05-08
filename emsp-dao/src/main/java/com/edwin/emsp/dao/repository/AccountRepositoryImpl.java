package com.edwin.emsp.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.dao.mapper.AccountMapper;
import com.edwin.emsp.domain.repository.facade.AccountRepository;
import com.edwin.emsp.domain.repository.po.AccountPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: jiucheng
 * @Description: AccountRepositoryImpl Account 仓储实现
 * @Date: 2025/4/14
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountPO findByEmail(String email) {
        return accountMapper.selectOne(new QueryWrapper<AccountPO>().lambda().eq(AccountPO::getEmail, email));
    }

    @Override
    public List<AccountPO> findAccountsByEmail(String email) {
        return accountMapper.selectAccountsByEmail(email);
    }

    @Override
    public void save(AccountPO account) {
        if (account.getId() == null) {
            accountMapper.insert(account);
        } else {
            accountMapper.updateById(account);
        }
    }

    @Override
    public void update(AccountPO account) {
        accountMapper.updateById(account);
    }
}