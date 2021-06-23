package com.crayon.ve.controller;


import com.crayon.ve.entity.Response;
import com.crayon.ve.service.WordCategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 单词分类表，单词分类分级 前端控制器
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@RestController
@RequestMapping("/ve/word-category")
public class WordCategoryController {
    @Autowired
    private WordCategoryService wordCategoryService;

    // 增
    @ApiOperation("添加单词分类")
    @PostMapping("/insertWordCategory")
    public Response insertWordCategory() {
        return Response.success();
    }

    // 删
    @ApiOperation("删除单词分类")
    @DeleteMapping
    public Response deleteWordCategory() {
        return Response.success();
    }

    // 改
    public Response updateWordCategory() {
        return Response.success();
    }

    // 查
    public Response listWordCategory() {
        return Response.success();
    }
}

