package com.edwin.emsp.trigger.http;

import com.edwin.emsp.common.exception.BizException;
import com.edwin.emsp.domain.model.dto.CardRequestDTO;
import com.edwin.emsp.domain.model.dto.validgroup.AssignGroup;
import com.edwin.emsp.domain.model.dto.validgroup.CreateGroup;
import com.edwin.emsp.domain.model.dto.validgroup.UpdateGroup;
import com.edwin.emsp.domain.model.message.Message;
import com.edwin.emsp.service.CardService;
import com.edwin.emsp.trigger.http.assembler.CardAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api")
@Tag(name = "api.group.name.card", description = "api.group.desc.card")  // 类级别分组
public class CardController {
    @Autowired
    private CardService cardService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "api.card.create.summary", description = "api.card.create.description")
    @RequestMapping(value = "/cards", method = RequestMethod.POST)
    public Message<?> create(@Validated(CreateGroup.class) @RequestBody CardRequestDTO cardRequestDTO) throws BizException {
        return Message.ok(cardService.createCard(CardAssembler.toDO(cardRequestDTO)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "api.status.card.update.summary", description = "api.status.card.update.description")
    @RequestMapping(value = "/card/status", method = RequestMethod.PATCH)
    public Message<?> changeCardStatus(@Validated(UpdateGroup.class) @RequestBody CardRequestDTO cardRequestDTO) throws BizException {
        return Message.ok(cardService.changeCardStatus(CardAssembler.toDO(cardRequestDTO)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "api.assign.card.summary", description = "api.assign.card.description")
    @RequestMapping(value = "/card/assign", method = RequestMethod.PATCH)
    public Message<?> assignCard(@Validated(AssignGroup.class) @RequestBody CardRequestDTO cardRequestDTO) throws BizException {
        return Message.ok(cardService.assignCardToAccount(CardAssembler.toDO(cardRequestDTO)));
    }

}
