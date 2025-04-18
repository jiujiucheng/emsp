package com.edwin.emsp.model.dto;

import com.edwin.emsp.model.dto.validgroup.CreateGroup;
import com.edwin.emsp.model.dto.validgroup.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @Author: jiucheng
 * @Description: 账号RequestDTO
 * @Date: 2025/4/12
 */
@Data
@Builder
public class AccountRequestDTO {

    @NotEmpty(message = "email不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "邮箱格式无效",
            groups = {CreateGroup.class, UpdateGroup.class}

    )
    @Length(min = 6,max = 18)
    private String email;

    /**
     * status
     */
    @NotNull(message = "status不能为空", groups = {UpdateGroup.class})
    @Min(value = 2, message = "status 无效", groups = {UpdateGroup.class})
    @Max(value = 3, message = "status 无效", groups = {UpdateGroup.class})
    Integer status;
}
