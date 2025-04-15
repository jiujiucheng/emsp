package com.edwin.emsp.dto;

import com.edwin.emsp.dto.validgroup.CreateGroup;
import com.edwin.emsp.dto.validgroup.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: jiucheng
 * @Description: TODO
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
    private String email;

    /**
     * status
     */
    @NotNull(message = "status不能为空", groups = {UpdateGroup.class})
    @Min(value = 2, message = "status 无效", groups = {UpdateGroup.class})
    @Max(value = 3, message = "status 无效", groups = {UpdateGroup.class})
    Integer status;
}
