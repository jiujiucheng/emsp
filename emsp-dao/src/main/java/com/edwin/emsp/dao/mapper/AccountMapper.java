package com.edwin.emsp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edwin.emsp.domain.model.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author jiucheng
 * @since 2025-04-12
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    /**
     * @param email String
     * @return List<Account>
     */
    List<Account> selectAccountsByEmail(@Param("email") String email);


}
