package com.edwin.emsp.domain.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

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
@TableName("emsp_card")
public class CardPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * uid
     */
    @TableField("uid")
    private String uid;

    /**
     * 卡号
     */
    @TableField("visible_number")
    private String visibleNumber;

    /**
     * 状态（1-已创建 ，2-已分配,3-已激活，4-未激活）
     */
    @TableField("status")
    private Integer status;


    /**
     * email
     */
    @TableField("email")
    private String email;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;
}
