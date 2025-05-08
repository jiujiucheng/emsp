package com.edwin.emsp.service;

import com.alibaba.fastjson.JSON;
import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.common.util.EmaidUtils;
import com.edwin.emsp.common.util.MessageUtils;
import com.edwin.emsp.domain.service.AccountDomainService;
import com.edwin.emsp.domain.service.CardDomainService;
import com.edwin.emsp.domain.model.aop.Cache;
import com.edwin.emsp.domain.model.dto.AccountWithCardsDTO;
import com.edwin.emsp.domain.model.entity.Account;
import com.edwin.emsp.domain.model.entity.Card;
import com.edwin.emsp.domain.model.enums.AccountStatusType;
import com.edwin.emsp.domain.model.enums.CardStatusType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
public class AccountService {

    private final AccountDomainService accountDomainService;

    private final CardDomainService cardDomainService;

    public AccountService(AccountDomainService accountDomainService, CardDomainService cardDomainService) {
        this.accountDomainService = accountDomainService;
        this.cardDomainService = cardDomainService;
    }

    /**
     * @param accountDO Account
     * @return boolean
     */
    public boolean createAccount(Account accountDO) {
        logger.info("create Account accountRequest ： {}", JSON.toJSONString(accountDO));
        Account existAccount = accountDomainService.getAccountByEmail(accountDO.getEmail());
        if (Objects.nonNull(existAccount)) {
            throw new BizException(MessageUtils.message("error.email.exists", (Object) null));
        }
        try {
            accountDomainService.createAccount(accountDO);
        } catch (Exception e) {
            logger.error("create account exception ", e);
        }

        return true;

    }

    /**
     * 更改账号状态，同步更改卡片状态
     *
     * @param accountDO Account
     * @return boolean
     */
    public boolean updateAccount(Account accountDO) {
        logger.info("update Account accountRequestDTO ： {}", JSON.toJSONString(accountDO));
        Account account = accountDomainService.getAccountByEmail(accountDO.getEmail());
        if (Objects.isNull(account)) {
            throw new BizException(MessageUtils.message("error.account.not.exist", (Object) null));
        }
        if (accountDO.getStatus().equals(account.getStatus())) {
            throw new BizException(MessageUtils.message("error.account.status.duplicate", (Object) null));
        }
        if (accountDO.getStatus().equals(AccountStatusType.STATUS_INACTIVE.getAccountStatus())
                && !account.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
            throw new BizException(MessageUtils.message("error.account.status.update.inactive", (Object) null));
        }
        try {
            // 激活生成ContractId
            if (accountDO.getStatus().equals(AccountStatusType.STATUS_ACTIVE.getAccountStatus())) {
                account.setContractId(EmaidUtils.generate());
            }
            //激活、冻结账户的同时激活、冻结卡
            List<Card> cards = cardDomainService.selectCardsByEmail(account.getEmail());
            if (!cards.isEmpty()) {
                cards.forEach(card -> {
                    card.setStatus(CardStatusType.STATUS_INACTIVE.getCardStatus());
                    card.setUpdateTime(new Date());
                });
                cardDomainService.batchUpdateCards(cards);
            }
            accountDomainService.updateAccount(account, accountDO.getStatus());

        } catch (Exception e) {
            logger.error("update account status exception ", e);
            throw new BizException(MessageUtils.message("error.account.update.failed", (Object) null), e);
        }
        return true;
    }

    /**
     * @param email String
     * @return Account
     */
    public Account getAccount(String email) {
        return accountDomainService.getAccountByEmail(email);
    }

    /**
     * 分页查询账号及卡片
     *
     * @param email String
     * @param page  int
     * @param size  int
     * @return PageInfo<AccountWithCardsDTO>
     */
    @Cache(cacheNull = false, expiry = 60)
    public PageInfo<AccountWithCardsDTO> getAccountsWithCards(String email, int page, int size) {
        PageInfo<AccountWithCardsDTO> info = null;
        try {
            PageHelper.startPage(page, size);
            List<Account> accounts = accountDomainService.findAccountsByEmail(email);

            List<String> emails = accounts.stream()
                    .map(Account::getEmail)
                    .collect(Collectors.toList());
            List<Card> cards = !emails.isEmpty() ? cardDomainService.selectCardsByEmails(emails) : Collections.emptyList();
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
            logger.warn("getAccountsWithCards got an exception", e);
        } finally {
            PageHelper.clearPage();
        }
        return info;
    }
}
