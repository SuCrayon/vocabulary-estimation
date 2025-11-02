package com.crayon.ve.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单词表
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "Word对象", description = "单词表")
public class Word implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "单词分类id")
    private String wordCategoryId;

    @ApiModelProperty(value = "单词等级")
    private Integer level;

    @ApiModelProperty(value = "单词")
    private String word;

    @ApiModelProperty(value = "中文意思")
    private String chMeaning;

    @ApiModelProperty(value = "是否已删除，0：未删除，1：已删除")
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
