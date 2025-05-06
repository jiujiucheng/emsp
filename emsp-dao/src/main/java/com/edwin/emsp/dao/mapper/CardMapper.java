package com.edwin.emsp.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edwin.emsp.domain.model.entity.Card;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jiucheng
 * @since 2025-04-12
 */
@Mapper
public interface CardMapper extends BaseMapper<Card> {
    /**
     * 查询账户关联的卡片（批量查询优化）
     *
     * @param emails List<String>
     * @return  List<Card>
     */
    List<Card> selectCardsByEmails(@Param("emails") List<String> emails);

    /**
     * @param email String
     * @return  List<Card>
     */
    List<Card> selectCardsByEmail(String email);

    /**
     * @param cards List<Card>
     * @return int
     */
    int batchUpdateCards(List<Card> cards);
}
