package com.crayon.ve.controller;

import com.crayon.ve.POJO.DTO.EstimationWordDTO;
import com.crayon.ve.entity.Response;
import com.crayon.ve.service.WordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/ve/estimation")
@CrossOrigin
public class EstimationController {
    @Autowired
    private WordService wordService;

    @GetMapping("/listEstimationWords")
    public Response listEstimationWords() {
        long start = System.currentTimeMillis();
        List<EstimationWordDTO> estimationWordList = wordService.listEstimationWords();
        System.out.println("耗时: " + (System.currentTimeMillis() - start));
        return Response.success().putItem("items", estimationWordList);
    }
}
