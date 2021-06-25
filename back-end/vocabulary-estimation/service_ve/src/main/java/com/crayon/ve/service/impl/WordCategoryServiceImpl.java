package com.crayon.ve.service.impl;

import com.crayon.ve.POJO.DTO.WordCategoryDTO;
import com.crayon.ve.POJO.VO.WordCategoryForm;
import com.crayon.ve.converter.BeanConverter;
import com.crayon.ve.entity.WordCategory;
import com.crayon.ve.mapper.WordCategoryMapper;
import com.crayon.ve.service.WordCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 单词分类表，单词分类分级 服务实现类
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Service
public class WordCategoryServiceImpl extends ServiceImpl<WordCategoryMapper, WordCategory> implements WordCategoryService {

    /**
     * 添加单词分类
     *
     * @param wordCategoryForm
     */
    @Override
    public void save(WordCategoryForm wordCategoryForm) {
        baseMapper.insert(BeanConverter.wordCategoryForm2WordCategory(wordCategoryForm));
    }

    @Transactional
    @Override
    public void saveBatch(List<WordCategoryForm> wordCategoryFormList) {
        for (WordCategoryForm e : wordCategoryFormList) {
            baseMapper.insert(BeanConverter.wordCategoryForm2WordCategory(e));
        }
    }

    @Override
    public List<WordCategoryDTO> listWordCategoryDTO() {
        return baseMapper.listWordCategoryDTO();
    }
}
