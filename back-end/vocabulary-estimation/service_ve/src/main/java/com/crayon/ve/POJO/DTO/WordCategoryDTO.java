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
@ApiModel(value = "WordCategoryDTO对象", description = "WordCategoryDTO")
public class WordCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词分类id")
    private String id;

    @ApiModelProperty(value = "词汇等级")
    private Integer level;

    @ApiModelProperty(value = "描述")
    private String description;

}
