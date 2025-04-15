package com.edwin.emsp.dao.mapper;

import com.edwin.emsp.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edwin.emsp.entity.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
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
     * @param lastUpdated
     * @return
     */
    List<Account> selectAccountsByLastUpdated(@Param("lastUpdated") Date lastUpdated);

    /**
     * 查询账户关联的卡片（批量查询优化）
     *
     * @param emails
     * @return
     */
    List<Card> selectCardsByEmails(@Param("emails") List<String> emails);

    /**
     * @param email
     * @return
     */
    List<Card> selectCardsByEmail(String email);

    /**
     * @param cards
     * @return
     */
    int batchUpdateCards(List<Card> cards);
}
