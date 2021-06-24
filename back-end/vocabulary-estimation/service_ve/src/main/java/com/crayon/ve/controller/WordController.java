package com.crayon.ve.controller;


import com.crayon.ve.POJO.DTO.WordDTO;
import com.crayon.ve.POJO.VO.WordForm;
import com.crayon.ve.entity.Response;
import com.crayon.ve.service.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 单词表 前端控制器
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Api(tags = "单词接口")
@RestController
@RequestMapping("/ve/word")
@CrossOrigin
public class WordController {
    @Autowired
    private WordService wordService;

    @ApiOperation("添加单词（批量）")
    @PostMapping("/insertWords")
    public Response insertWords(
            @RequestBody List<WordForm> wordFormList
    ) {
        wordService.saveBatch(wordFormList);
        return Response.success();
    }

    // 增
    @ApiOperation("添加单词")
    @PostMapping("/insertWord")
    public Response insertWord(
            @RequestBody WordForm wordForm
    ) {
        wordService.save(wordForm);
        return Response.success();
    }

    // 删
    @ApiOperation("删除单词分类")
    @DeleteMapping("/deleteWord")
    public Response deleteWord() {
        return Response.success();
    }

    // 改
    @ApiOperation("修改单词")
    @PutMapping("/updateWord")
    public Response updateWord() {
        return Response.success();
    }

    // 查
    @ApiOperation("查询单词")
    @GetMapping("/listWords")
    public Response listWords() {
        List<WordDTO> wordDTOList = wordService.listWordDTO();
        return Response.success().putItem("items", wordDTOList);
    }
}

