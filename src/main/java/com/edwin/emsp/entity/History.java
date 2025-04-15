package com.edwin.emsp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author jiucheng
 * @since 2025-04-12
 */
@Getter
@Setter
@TableName("emsp_history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 1-account,2-card
     */
    @TableField("entity_type")
    private Byte entityType;

    /**
     * 邮箱 or 卡号
     */
    @TableField("entity_id")
    private String entityId;

    /**
     * 旧状态
     */
    @TableField("old_status")
    private Byte oldStatus;

    /**
     * 新状态
     */
    @TableField("new_status")
    private Byte newStatus;

    @TableField("created_time")
    private LocalDateTime createdTime;
}
