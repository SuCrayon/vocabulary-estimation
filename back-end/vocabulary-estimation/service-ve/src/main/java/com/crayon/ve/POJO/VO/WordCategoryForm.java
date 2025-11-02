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
@ApiModel(value = "WordCategoryForm对象", description = "单词分类添加表单")
public class WordCategoryForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "词汇等级")
    private Integer level;

    @ApiModelProperty(value = "描述")
    private String description;
}
