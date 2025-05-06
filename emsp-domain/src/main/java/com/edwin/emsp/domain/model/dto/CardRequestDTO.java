package com.edwin.emsp.domain.model.dto;

import com.edwin.emsp.domain.model.dto.validgroup.AssignGroup;
import com.edwin.emsp.domain.model.dto.validgroup.CreateGroup;
import com.edwin.emsp.domain.model.dto.valid.ValidUid;
import com.edwin.emsp.domain.model.dto.validgroup.UpdateGroup;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


/**
 * @Author: jiucheng
 * @Description: CardRequestDTO
 * @Date: 2025/4/12
 */
@Data
@Builder
public class CardRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{card_id.not.be.empty}", groups = {AssignGroup.class})
    private Integer cardId;

    @NotEmpty(message = "{email.can.not.be.empty}", groups = {AssignGroup.class})
    @Length(min = 6,max = 18,message = "{email.length.invalid}", groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(
            regexp = "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z]{2,6}$",
            message = "{email.format.invalid}",
            groups = {UpdateGroup.class}
    )
    private String email;

    @NotBlank(message = "{card.uid.not.be.blank}", groups = {CreateGroup.class})
    @ValidUid(groups = {CreateGroup.class})
    private String uid;

    @NotBlank(message = "{card.visible_number.blank}", groups = {CreateGroup.class})
    @Length(min = 8, max = 64, message = "{card.visible_number.invalid}", groups = {CreateGroup.class})
    private String visibleNumber;

    @NotNull(message = "card.status.not.null", groups = {UpdateGroup.class})
    @Min(value = 3, message = "card.status.not.invalid", groups = {UpdateGroup.class})
    @Max(value = 4, message = "card.status.not.invalid", groups = {UpdateGroup.class})
    private Integer status;
}
