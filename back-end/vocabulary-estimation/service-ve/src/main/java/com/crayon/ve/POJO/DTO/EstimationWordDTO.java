package com.crayon.ve.POJO.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "EstimationWordDTO对象", description = "词汇量测试单词DTO")
public class EstimationWordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词id")
    private String id;

    @ApiModelProperty(value = "单词分类id")
    private String wordCategoryId;

    @ApiModelProperty(value = "词汇等级")
    private Integer level;

    @ApiModelProperty(value = "单词")
    private String word;

    @ApiModelProperty(value = "中文意思，也就是答案")
    private String chMeaning;

    @ApiModelProperty(value = "选项")
    private List<String> options;
}
