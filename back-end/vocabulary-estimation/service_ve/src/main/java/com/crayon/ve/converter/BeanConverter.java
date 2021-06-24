package com.crayon.ve.converter;

import com.crayon.ve.POJO.DTO.WordCategoryDTO;
import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.POJO.VO.WordCategoryForm;
import com.crayon.ve.POJO.VO.WordForm;
import com.crayon.ve.entity.Word;
import com.crayon.ve.entity.WordCategory;
import org.springframework.beans.BeanUtils;

/**
 * Bean转换器
 */
public class BeanConverter {

    /**
     * WordCategoryForm (VO) -> WordCategory (Entity)
     *
     * @param wordCategoryForm
     * @return
     */
    public static WordCategory wordCategoryForm2WordCategory(WordCategoryForm wordCategoryForm) {
        WordCategory wordCategory = new WordCategory();
        BeanUtils.copyProperties(wordCategoryForm, wordCategory);
        return wordCategory;
    }

    /**
     * WordForm (VO) -> Word (Entity)
     *
     * @param wordForm
     * @return
     */
    public static Word wordForm2Word(WordForm wordForm) {
        Word word = new Word();
        BeanUtils.copyProperties(wordForm, word);
        return word;
    }

    /**
     * Word (Entity) -> WordDTO (DTO)
     *
     * @param word
     * @return
     */
    public static WordDTO word2WordDTO(Word word) {
        WordDTO wordDTO = new WordDTO();
        BeanUtils.copyProperties(word, wordDTO);
        return wordDTO;
    }

    /**
     * WordCategory (Entity) -> WordCategoryDTO (DTO)
     *
     * @param wordCategory
     * @return
     */
    public static WordCategoryDTO wordCategory2WordCategoryDTO(WordCategory wordCategory) {
        WordCategoryDTO wordCategoryDTO = new WordCategoryDTO();
        BeanUtils.copyProperties(wordCategory, wordCategoryDTO);
        return wordCategoryDTO;
    }
}
