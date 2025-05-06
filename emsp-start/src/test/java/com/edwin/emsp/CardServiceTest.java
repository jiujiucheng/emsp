package com.edwin.emsp;

/**
 * @Author: jiucheng
 * @Description: 单元测试CardService
 * @Date: 2025/4/14
 */

import com.edwin.emsp.dao.mapper.CardMapper;
import com.edwin.emsp.domain.model.dto.CardRequestDTO;
import com.edwin.emsp.domain.model.enums.CardStatusType;
import com.edwin.emsp.service.AccountService;
import com.edwin.emsp.service.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EmspApplication.class)
@WebAppConfiguration
class CardServiceTest {

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;


    @Test
    void testCreateCard() {
        CardRequestDTO requestDTO = CardRequestDTO.builder()
                .uid("USABC123456789")
                .visibleNumber("45647").build();
        Boolean result = cardService.createCard(requestDTO);
        assertTrue(result);
    }


    @Test
    void testAssignCardToAccount() {
        CardRequestDTO requestDTO = CardRequestDTO.builder().cardId(1).build();
        requestDTO.setEmail("test@example.com");

        boolean result = cardService.assignCardToAccount(requestDTO);
        assertTrue(result);
    }

    @Test
    void testChangeCardStatus() {
        CardRequestDTO requestDTO = CardRequestDTO.builder().cardId(1).build();
        requestDTO.setStatus(CardStatusType.STATUS_ACTIVE.getCardStatus());

        boolean result = cardService.changeCardStatus(requestDTO);
        assertTrue(result);
    }
}