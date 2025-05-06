package com.edwin.emsp.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jiucheng
 * @Description: CardStatusType
 * @Date: 2025/4/12
 */
@Getter
@AllArgsConstructor
public enum CardStatusType {
    STATUS_CREATED(1, "created"),
    STATUS_ASSIGNED(2, "assigned"),
    STATUS_ACTIVE(3, "active"),
    STATUS_INACTIVE(4, "inactive");
    /**
     * status
     */
    private final Integer cardStatus;

    /**
     * desc
     */
    private final String desc;

    public static String getDescByStatus(Integer code) {
        for (CardStatusType cardStatusType : CardStatusType.values()) {
            if (cardStatusType.getCardStatus().equals(code)) {
                return cardStatusType.getDesc();
            }
        }
        return null;
    }
}
