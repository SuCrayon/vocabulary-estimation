package com.crayon.ve.POJO.VO;

import com.crayon.ve.POJO.DTO.EstimationWordDTO;
import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.constant.Common;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "VocabularyEstimationVO对象", description = "请求词汇量测试返回的VO")
public class VocabularyEstimationVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总共等级")
    private Integer maxLevel = Common.ESTIMATE_LEVEL_NUM;

    @ApiModelProperty(value = "各等级单词数量")
    private List<Integer> wordNums = new LinkedList<>(Arrays.asList(Common.ESTIMATE_WORD_NUMS));

    @ApiModelProperty(value = "测试单词列表，包括单词，单词意思一级")
    private List<EstimationWordDTO> estimationWordList;
}
