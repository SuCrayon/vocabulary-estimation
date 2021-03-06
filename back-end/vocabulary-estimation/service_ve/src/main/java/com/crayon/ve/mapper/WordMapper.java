package com.crayon.ve.mapper;

import com.crayon.ve.POJO.DTO.EstimationWordDTO;
import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.entity.Word;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 单词表 Mapper 接口
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
public interface WordMapper extends BaseMapper<Word> {
    /**
     * 查询所有单词
     * @return
     */
    List<WordDTO> listWordDTO();

    /**
     * 获取指定等级单词数量
     *
     * @param level
     * @return
     */
    Long countLevelWords(Integer level);

    /**
     * 获取指定等级的单词
     *
     * @param level
     * @param count
     * @return
     */
    List<EstimationWordDTO> getRandomEstimationWords(Integer level, Integer count);
}
