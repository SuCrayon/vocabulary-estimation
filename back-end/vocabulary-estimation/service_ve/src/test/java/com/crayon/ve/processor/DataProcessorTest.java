package com.crayon.ve.processor;

import com.crayon.ve.entity.Word;
import com.crayon.ve.service.WordCategoryService;
import com.crayon.ve.service.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 读取Data-Fin.txt英语词汇处理并入库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataProcessorTest {

    public static String PATH = "C:\\Users\\HP\\Desktop\\workspace\\vocabulary-estimation\\docs\\Data-Fin.txt";

    public static String[] CATEGORY_IDs = {
            "1408270369827102722",
            "1408270429679820801",
            "1408270506003570689",
            "1408270583732412417",
            "1408270654721007618"
    };

    @Autowired
    private WordCategoryService wordCategoryService;
    @Autowired
    private WordService wordService;

    @Test
    public void processAndSave2DB() throws IOException {
        List<Word> wordList = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8));

        String line;
        String[] temp;
        int level;
        int len;
        while ((line = in.readLine()) != null) {
            temp = line.split("/");
            len = temp.length;
            level = Integer.parseInt(temp[len - 1]);

            StringBuilder chMeaning = new StringBuilder();
            // 可能出现中文意思里边出现了/的情况
            for (int i = 1; i < len - 1; i++) {
                chMeaning.append(temp[i]);
            }
            wordList.add(
                    new Word()
                            .setWordCategoryId(CATEGORY_IDs[level - 1])
                            .setWord(temp[0])
                            .setChMeaning(chMeaning.toString())
                            .setLevel(level)
            );
        }
        in.close();
        for (Word e : wordList) {
            System.out.println(e);
        }
        wordService.saveBatch(wordList);
    }
}
