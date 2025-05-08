package com.edwin.emsp.trigger.http.assembler;

import com.edwin.emsp.domain.model.dto.AccountRequestDTO;
import com.edwin.emsp.domain.model.entity.Account;

public class AccountAssembler {

    public static Account toDO(AccountRequestDTO dto) {
        Account accountDO = new Account();
        accountDO.setEmail(dto.getEmail());
        accountDO.setStatus(dto.getStatus());
        return accountDO;
    }


}
