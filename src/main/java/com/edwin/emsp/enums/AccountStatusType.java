package com.edwin.emsp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: jiucheng
 * @Description: AccountStatusType
 * @Date: 2025/4/12
 */
@Getter
@AllArgsConstructor
public enum AccountStatusType {

    STATUS_CREATED(1, "created"),
    STATUS_ACTIVE(2, "active"),
    STATUS_INACTIVE(3, "inactive");
    /**
     * status
     */
    private final Integer accountStatus;
    /**
     * desc
     */
    private final String desc;

    public static String getDescByStatus(Integer code) {
        for (AccountStatusType accountStatusType : AccountStatusType.values()) {
            if (accountStatusType.getAccountStatus().equals(code)) {
                return accountStatusType.getDesc();
            }
        }
        return null;
    }
}
