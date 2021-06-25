package com.crayon.ve.mapper;

import com.crayon.ve.POJO.DTO.WordCategoryDTO;
import com.crayon.ve.entity.WordCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 单词分类表，单词分类分级 Mapper 接口
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
public interface WordCategoryMapper extends BaseMapper<WordCategory> {
    /**
     * 获取所有单词分类
     * @return
     */
    List<WordCategoryDTO> listWordCategoryDTO();
}
