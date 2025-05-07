package com.edwin.emsp.trigger.http;

import com.edwin.emsp.common.exception.BizException;

import com.edwin.emsp.domain.model.dto.AccountRequestDTO;
import com.edwin.emsp.domain.model.dto.AccountWithCardsDTO;
import com.edwin.emsp.domain.model.dto.validgroup.CreateGroup;
import com.edwin.emsp.domain.model.dto.validgroup.UpdateGroup;
import com.edwin.emsp.domain.model.message.Message;
import com.edwin.emsp.service.AccountService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: jiucheng
 * @Description: account
 * @Date: 2025/4/12
 */
@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "api.group.name.account", description = "api.group.desc.account")  // 类级别分组
public class AccountController {
    @Autowired
    private AccountService accountService;
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "api.account.create.summary", description = "api.account.create.description")
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public Message<?> create(@Validated(CreateGroup.class) @RequestBody AccountRequestDTO accountRequestDTO) throws BizException {
        return Message.ok(accountService.createAccount(accountRequestDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "api.status.account.update.summary", description = "api.status.account.update.description")
    @RequestMapping(value = "/account/status", method = RequestMethod.PATCH)
    public Message<?> update(@Validated(UpdateGroup.class) @RequestBody AccountRequestDTO accountRequestDTO) throws BizException {
        return Message.ok(accountService.updateAccount(accountRequestDTO));
    }

    @Operation(summary = "api.list.account.summary", description = "api.list.account.description")
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public Message<PageInfo<AccountWithCardsDTO>> list(@RequestParam(defaultValue = "",required = false)  String email,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size) throws BizException {
        PageInfo<AccountWithCardsDTO> accountsWithCards = accountService.getAccountsWithCards(email, page, size);
        return Message.ok(accountsWithCards);
    }

}
