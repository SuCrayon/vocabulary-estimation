package com.crayon.ve.service.impl;

import com.crayon.ve.entity.WordCategory;
import com.crayon.ve.mapper.WordCategoryMapper;
import com.crayon.ve.service.WordCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
