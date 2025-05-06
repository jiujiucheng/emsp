package com.edwin.emsp.domain.model.dto;

import com.edwin.emsp.domain.model.dto.validgroup.CreateGroup;
import com.edwin.emsp.domain.model.dto.validgroup.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


/**
 * @Author: jiucheng
 * @Description: 账号RequestDTO
 * @Date: 2025/4/12
 */
@Data
@Builder
public class AccountRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "{email.can.not.be.empty}", groups = {CreateGroup.class, UpdateGroup.class})
    @Length(min = 6,max = 18,message = "{email.length.invalid}", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z]{2,6}$",
            message = "{email.format.invalid}",
            groups = {CreateGroup.class, UpdateGroup.class}

    )
    private String email;

    /**
     * status
     */
    @NotNull(message = "{account.status.not.null}", groups = {UpdateGroup.class})
    @Min(value = 2, message = "{account.status.invalid}", groups = {UpdateGroup.class})
    @Max(value = 3, message = "{account.status.invalid}", groups = {UpdateGroup.class})
    Integer status;
}
