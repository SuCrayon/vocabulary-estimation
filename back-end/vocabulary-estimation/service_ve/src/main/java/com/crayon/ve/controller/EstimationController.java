package com.crayon.ve.controller;

import com.crayon.ve.POJO.VO.VocabularyEstimationVO;
import com.crayon.ve.entity.Response;
import com.crayon.ve.service.WordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 估测 前端控制器
 * </p>
 *
 * @author Crayon
 * @since 2021-06-23
 */
@Api(tags = "词汇量估测接口")
@RestController
@RequestMapping("/estimation")
@CrossOrigin
public class EstimationController {
    @Autowired
    private WordService wordService;

    @ApiOperation("获取测试单词")
    @GetMapping("/listEstimationWords")
    public Response listEstimationWords() {
        long start = System.currentTimeMillis();
        VocabularyEstimationVO estimation = wordService.listEstimationWords();
        System.out.println("耗时: " + (System.currentTimeMillis() - start));
        return Response.success()
                .putItem("item", estimation);
    }

    @ApiOperation("计算词汇量")
    @PostMapping("/calculate")
    public Response calculate(
            @ApiParam("每个等级答对的题目数量")
            @RequestBody List<Integer> levelRightCounts
    ) {
        int result = wordService.calculate(levelRightCounts);
        return Response.success().putItem("result", result);
    }
}
