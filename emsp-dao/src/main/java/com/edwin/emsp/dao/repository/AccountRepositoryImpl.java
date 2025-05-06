package com.edwin.emsp.dao.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edwin.emsp.dao.mapper.AccountMapper;
import com.edwin.emsp.domain.repository.AccountRepository;
import com.edwin.emsp.domain.model.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findByEmail(String email) {
        return accountMapper.selectOne(new QueryWrapper<Account>().lambda().eq(Account::getEmail, email));
    }

    @Override
    public List<Account> findAccountsByEmail(String email) {
        return accountMapper.selectAccountsByEmail(email);
    }

    @Override
    public void save(Account account) {
        if (account.getId() == null) {
            accountMapper.insert(account);
        } else {
            accountMapper.updateById(account);
        }
    }

    @Override
    public void update(Account account) {
        accountMapper.updateById(account);
    }
}