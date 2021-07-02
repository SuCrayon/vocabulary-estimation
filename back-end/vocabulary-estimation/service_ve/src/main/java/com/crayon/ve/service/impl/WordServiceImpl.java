package com.crayon.ve.service.impl;

import com.crayon.ve.POJO.DTO.EstimationWordDTO;
import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.POJO.VO.VocabularyEstimationVO;
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

import java.util.*;
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
        // TODO: 检查单词分类id
        baseMapper.insert(BeanConverter.wordForm2Word(wordForm));
    }

    @Override
    public List<WordDTO> listWordDTO() {
        return baseMapper.listWordDTO();
    }

    @Transactional
    @Override
    public void saveBatch(List<WordForm> wordFormList) {
        for (WordForm e : wordFormList) {
            baseMapper.insert(BeanConverter.wordForm2Word(e));
        }
    }

    /**
     * 生成随机的4个选项
     *
     * @param vocabularyEstimationVO
     */
    private void genAndSetOptions(VocabularyEstimationVO vocabularyEstimationVO) {
        List<EstimationWordDTO> estimationWordList = vocabularyEstimationVO.getEstimationWordList();
        for (int i = 0; i < estimationWordList.size(); i++) {
            int[] optionIndexs = RandomUtils.get3OptionIndexs(i, estimationWordList.size(), estimationWordList);
            List<String> options = new LinkedList<>();
            for (int optionIndex : optionIndexs) {
                options.add(estimationWordList.get(optionIndex).getChMeaning());
            }
            options.add(estimationWordList.get(i).getChMeaning());
            // 选项打乱
            Collections.shuffle(options);
            estimationWordList.get(i).setOptions(options);
        }
    }

    /**
     * 获取测验单词
     * 100个单词
     * 6个等级：1(A1) 2(A2) 3(B1) 4(B2) 5(C1) 6(C2)
     * 个数：10 15 25 25 15 10
     * <p>
     * 50个单词
     * 5个等级：1 2 3 4 5
     * 个数：5 10 15 10 10
     *
     * @return
     */
    @Override
    public VocabularyEstimationVO listEstimationWords() {
        // 无需考虑容量问题
        List<EstimationWordDTO> estimationWordList = new LinkedList<>();
        // TODO: 数据库单词的level和难度是负相关的（5最简单），后面改一下
        for (int i = 1; i <= Common.ESTIMATE_LEVEL_NUM; i++) {
            List<EstimationWordDTO> levelWordList = baseMapper.getRandomEstimationWords(i, Common.ESTIMATE_WORD_NUMS[i - 1]);
            estimationWordList.addAll(levelWordList);
        }
        VocabularyEstimationVO vocabularyEstimationVO = new VocabularyEstimationVO();
        vocabularyEstimationVO.setEstimationWordList(estimationWordList);
        this.genAndSetOptions(vocabularyEstimationVO);
        return vocabularyEstimationVO;
    }

    /**
     * 计算词汇量
     *
     * @param levelRightCounts 每个等级答对的题目数量
     * @return
     */
    @Override
    public int calculate(List<Integer> levelRightCounts) {
        int result = 0;
        double rightRate = 0;
        for (int i = 0; i < Common.ESTIMATE_LEVEL_NUM; i++) {
            rightRate = levelRightCounts.get(i) / (double) Common.ESTIMATE_WORD_NUMS[i];

            if (i > 0) {
                /*
                    如果题目不属于第一级，则将上一级答对的比率和本级相乘
                    然后用这个比率乘以固定的预估值作为该级别的预估次数
                */
                double lastRightRate = levelRightCounts.get(i - 1) / (double) Common.ESTIMATE_WORD_NUMS[i - 1];
                if (lastRightRate == 0) {
                    lastRightRate = 0.1;
                }
                rightRate *= lastRightRate;
            }
            result += Common.SCALE * rightRate;
        }
        return result;
    }
}
