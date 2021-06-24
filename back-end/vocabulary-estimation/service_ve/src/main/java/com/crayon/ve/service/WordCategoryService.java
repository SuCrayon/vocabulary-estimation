package com.crayon.ve.service;

import com.crayon.ve.POJO.DTO.WordCategoryDTO;
import com.crayon.ve.POJO.VO.WordCategoryForm;
import com.crayon.ve.entity.WordCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 单词分类表，单词分类分级 服务类
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
public interface WordCategoryService extends IService<WordCategory> {

    void save(WordCategoryForm wordCategoryForm);

    void saveBatch(List<WordCategoryForm> wordCategoryFormList);

    List<WordCategoryDTO> listWordCategoryDTO();
}
