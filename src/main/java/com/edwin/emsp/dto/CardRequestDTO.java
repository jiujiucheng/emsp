package com.edwin.emsp.dto;

import com.edwin.emsp.dto.valid.ValidUid;
import com.edwin.emsp.dto.validgroup.AssignGroup;
import com.edwin.emsp.dto.validgroup.CreateGroup;
import com.edwin.emsp.dto.validgroup.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


/**
 * @Author: jiucheng
 * @Description: TODO
 * @Date: 2025/4/12
 */
@Data
@Builder
public class CardRequestDTO {

    @NotNull(message = "cardId不能为空", groups = {AssignGroup.class})
    private Integer cardId;

    @NotEmpty(message = "email不能为空", groups = {AssignGroup.class})
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "邮箱格式无效",
            groups = {UpdateGroup.class}
    )
    private String email;

    @NotBlank(message = "uid不能为空", groups = {CreateGroup.class})
    @ValidUid(groups = {CreateGroup.class})
    private String uid;

    @NotBlank(message = "卡号不能为空", groups = {CreateGroup.class})
    @Length(min = 8, max = 64, message = "卡号长度必须在[8,64]", groups = {CreateGroup.class})
    private String visibleNumber;

    @NotNull(message = "status不能为空", groups = {UpdateGroup.class})
    @Min(value = 3, message = "status 无效", groups = {UpdateGroup.class})
    @Max(value = 4, message = "status 无效", groups = {UpdateGroup.class})
    private Integer status;
}
