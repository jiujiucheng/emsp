package com.edwin.emsp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edwin.emsp.domain.repository.po.AccountPO;
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
public interface AccountMapper extends BaseMapper<AccountPO> {
    /**
     * @param email String
     * @return List<Account>
     */
    List<AccountPO> selectAccountsByEmail(@Param("email") String email);


}
