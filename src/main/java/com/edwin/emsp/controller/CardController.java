package com.edwin.emsp.controller;

import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.dto.CardRequestDTO;
import com.edwin.emsp.dto.validgroup.AssignGroup;
import com.edwin.emsp.dto.validgroup.CreateGroup;
import com.edwin.emsp.dto.validgroup.UpdateGroup;
import com.edwin.emsp.service.CardService;
import com.edwin.emsp.vo.message.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jiucheng
 * @Description: card
 * @Date: 2025/4/12
 */
@Slf4j
@RestController
@RequestMapping("/api/card")
@Tag(name = "卡", description = "卡相关接口")  // 类级别分组
public class CardController {
    @Autowired
    private CardService cardService;

    @Operation(summary = "创建卡", description = "创建卡")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Message<?> create(@Validated(CreateGroup.class) @RequestBody CardRequestDTO cardRequestDTO) throws BizException {
        return Message.ok(cardService.createCard(cardRequestDTO));
    }

    @Operation(summary = "修改卡状态", description = "修改卡状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public Message<?> changeCardStatus(@Validated(UpdateGroup.class) @RequestBody CardRequestDTO cardRequestDTO) throws BizException {
        return Message.ok(cardService.changeCardStatus(cardRequestDTO));
    }

    @Operation(summary = "分配卡", description = "绑定卡到账户")
    @RequestMapping(value = "/assign", method = RequestMethod.POST)
    public Message<?> assignCard(@Validated(AssignGroup.class) @RequestBody CardRequestDTO cardRequestDTO) throws BizException {
        return Message.ok(cardService.assignCardToAccount(cardRequestDTO));
    }

}
