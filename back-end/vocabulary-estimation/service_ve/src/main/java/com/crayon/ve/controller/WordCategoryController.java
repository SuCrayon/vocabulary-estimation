package com.crayon.ve.controller;


import com.crayon.ve.POJO.DTO.WordCategoryDTO;
import com.crayon.ve.POJO.VO.WordCategoryForm;
import com.crayon.ve.entity.Response;
import com.crayon.ve.entity.WordCategory;
import com.crayon.ve.service.WordCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 单词分类表，单词分类分级 前端控制器
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Api(tags = "单词分类接口")
@RestController
@RequestMapping("/ve/word-category")
@CrossOrigin
public class WordCategoryController {
    @Autowired
    private WordCategoryService wordCategoryService;

    // 增
    @ApiOperation("添加单词分类（批量添加）")
    @PostMapping("/insertWordCategories")
    public Response insertWordCategories(
            @RequestBody List<WordCategoryForm> wordCategoryFormList
    ) {
        wordCategoryService.saveBatch(wordCategoryFormList);
        return Response.success();
    }

    @ApiOperation("添加单词分类（单个添加）")
    @PostMapping("/insertWordCategory")
    public Response insertWordCategory(
            @RequestBody WordCategoryForm wordCategoryForm
    ) {
        wordCategoryService.save(wordCategoryForm);
        return Response.success();
    }

    // 删
    @ApiOperation("删除单词分类")
    @DeleteMapping("/deleteWordCategory")
    public Response deleteWordCategory() {
        return Response.success();
    }

    // 改
    @ApiOperation("修改单词分类")
    @PutMapping("/updateWordCategory")
    public Response updateWordCategory() {
        return Response.success();
    }

    // 查
    @ApiOperation("查询单词分类")
    @GetMapping("/listWordCategories")
    public Response listWordCategories() {
        List<WordCategoryDTO> wordCategoryDTOList = wordCategoryService.listWordCategoryDTO();
        return Response.success().putItem("items", wordCategoryDTOList);
    }
}

