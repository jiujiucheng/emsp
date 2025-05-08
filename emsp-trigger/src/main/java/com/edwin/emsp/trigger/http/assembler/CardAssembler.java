package com.edwin.emsp.trigger.http.assembler;

import com.edwin.emsp.domain.model.dto.CardRequestDTO;
import com.edwin.emsp.domain.model.entity.Card;

public class CardAssembler {
    public static Card toDO(CardRequestDTO dto) {
        Card cardDO = new Card();
        cardDO.setId(dto.getCardId());
        cardDO.setEmail(dto.getEmail());
        cardDO.setUid(dto.getUid());
        cardDO.setVisibleNumber(dto.getVisibleNumber());
        cardDO.setStatus(dto.getStatus());
        return cardDO;
    }
}
