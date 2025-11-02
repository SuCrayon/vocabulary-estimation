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
 * 单词分类表，单词分类分级
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "WordCategory对象", description = "单词分类表，单词分类分级")
public class WordCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单词分类id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "词汇等级")
    private Integer level;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "是否已删除，0：未删除，1：已删除")
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
