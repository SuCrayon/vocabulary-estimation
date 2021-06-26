package com.crayon.ve.POJO.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "WordForm对象", description = "单词添加表单")
public class WordForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词分类id")
    private String wordCategoryId;

    @ApiModelProperty(value = "单词")
    private String word;

    @ApiModelProperty(value = "中文意思")
    private String chMeaning;

}
