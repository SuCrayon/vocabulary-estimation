package com.crayon.ve.service.impl;

import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.POJO.VO.EstimationWord;
import com.crayon.ve.POJO.VO.WordCategoryForm;
import com.crayon.ve.POJO.VO.WordForm;
import com.crayon.ve.constant.Common;
import com.crayon.ve.converter.BeanConverter;
import com.crayon.ve.entity.Word;
import com.crayon.ve.mapper.WordMapper;
import com.crayon.ve.service.WordCategoryService;
import com.crayon.ve.service.WordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crayon.ve.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 单词表 服务实现类
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements WordService {

    @Autowired
    private WordCategoryService wordCategoryService;

    @Override
    public void save(WordForm wordForm) {
//        if (wordCategoryService.getById(wordForm.getWordCategoryId()) == null) {
//
//        }
        baseMapper.insert(BeanConverter.wordForm2Word(wordForm));
    }

    @Override
    public List<WordDTO> listWordDTO() {
        return baseMapper
                .selectList(null)
                .stream()
                .map(BeanConverter::word2WordDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void saveBatch(List<WordForm> wordFormList) {
        for (WordForm e : wordFormList) {
            baseMapper.insert(BeanConverter.wordForm2Word(e));
        }
    }

    /**
     * 获取测验单词
     * 100个单词
     * 6个等级：1(A1) 2(A2) 3(B1) 4(B2) 5(C1) 6(C2)
     * 个数：10 15 25 25 15 10
     *
     * @return
     */
    @Override
    public List<EstimationWord> listEstimationWords() {
        List<EstimationWord> estimationWordList = new ArrayList<>(100);
        for (int i = 1; i <= 6; i++) {
            List<EstimationWord> levelWordList = baseMapper.getEstimationWords(i, Common.ESTIMATE_WORD_NUMS[i - 1]);
            estimationWordList.addAll(levelWordList);
        }
        return estimationWordList;
    }
}
