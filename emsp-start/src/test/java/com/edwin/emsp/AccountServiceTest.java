package com.edwin.emsp;

/**
 * @Author: jiucheng
 * @Description: 单元测试AccountService
 * @Date: 2025/4/14
 */

import com.edwin.emsp.dao.mapper.AccountMapper;
import com.edwin.emsp.domain.model.dto.AccountRequestDTO;
import com.edwin.emsp.domain.model.dto.AccountWithCardsDTO;
import com.edwin.emsp.domain.model.entity.Account;
import com.edwin.emsp.domain.model.enums.AccountStatusType;
import com.edwin.emsp.service.AccountService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EmspApplication.class)
@WebAppConfiguration
class AccountServiceTest {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Test
    void testCreateAccount() {
        AccountRequestDTO requestDTO = AccountRequestDTO.builder().email("test@example.com").build();
        boolean result = accountService.createAccount(requestDTO);
        assertTrue(result);
    }

    @Test
    void testUpdateAccount() {
        AccountRequestDTO requestDTO = AccountRequestDTO.builder()
                .email("test@example.com")
                .build();
        requestDTO.setStatus(AccountStatusType.STATUS_ACTIVE.getAccountStatus());

        boolean result = accountService.updateAccount(requestDTO);
        assertTrue(result);
    }

    @Test
    void testGetAccountsWithCards() {
        String email = "test@example.com";
        PageInfo<AccountWithCardsDTO> result = accountService.getAccountsWithCards(email, 1, 10);
        assertNotNull(result);
        assertEquals(0, result.getList().size());
    }

    @Test
    void testGetAccount() {
        String email = "test@example.com";
        Account account = new Account();
        account.setEmail(email);
        Account result = accountService.getAccount(email);
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }
}