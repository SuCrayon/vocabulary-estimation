package com.crayon.ve.service;

import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.POJO.VO.VocabularyEstimationVO;
import com.crayon.ve.POJO.VO.WordForm;
import com.crayon.ve.entity.Word;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 单词表 服务类
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
public interface WordService extends IService<Word> {

    void save(WordForm wordForm);

    List<WordDTO> listWordDTO();

    void saveBatch(List<WordForm> wordFormList);

    VocabularyEstimationVO listEstimationWords();
}
