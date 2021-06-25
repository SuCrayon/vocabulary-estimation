package com.crayon.ve.POJO.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "WordDTO对象", description = "WordDTO")
public class WordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词id")
    private String id;

    @ApiModelProperty(value = "单词分类id")
    private String wordCategoryId;

    @ApiModelProperty(value = "词汇等级")
    private Integer level;

    @ApiModelProperty(value = "单词")
    private String word;

}
