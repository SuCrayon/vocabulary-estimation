package com.crayon.ve.crawl;

import com.crayon.ve.POJO.VO.WordCategoryForm;
import com.crayon.ve.entity.Word;
import com.crayon.ve.entity.WordCategory;
import com.crayon.ve.service.WordCategoryService;
import com.crayon.ve.service.WordService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * EVP英语词汇爬取并入库
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EVPCrawlerTest {

    public static String url = "http://www.englishprofile.org/wordlists/evp";           //  单词获取网址

    public static int numOfLevel = 6;                                                   //  单词级别分为6种(A1-C2)

    public static String[] levels = {"A1", "A2", "B1", "B2", "C1", "C2"};

    @Autowired
    private WordCategoryService wordCategoryService;
    @Autowired
    private WordService wordService;

    @Test
    public void crawlAndSave2DB() {
        for (int i = 0; i < numOfLevel; i++) {
            //  创建请求
            Connection conect = Jsoup.connect(url);
            //  设置请求头信息
            conect.header("User-Agent", "Mozilla/5.0");
            //  设置参数
            String level = (i + 1) + "";
            conect.data("filter_custom_Level[]", level);
            conect.data("limit", "0");
            conect.data("directionTable", "asc");
            conect.data("sortTable", "base");
            conect.data("filter_order", "pos_rank");
            conect.data("filter_order_Dir", "asc");
            conect.data("boxchecked", "0");
            //  设置超时时间
            conect.timeout(30000);
            // 存单词等级
            WordCategory wordCategory = new WordCategory();
            wordCategoryService.save(
                    wordCategory
                            .setLevel(i + 1)
                            .setDescription(levels[i])
            );
            System.out.println("分类id: " + wordCategory.getId());
            try {
                Document doc = conect.post();

                Elements elements = doc.select("#reportList tbody tr");

                // for (int j = 0; j < elements.size(); j++) {
                // 先各存1000用来测试
                for (int j = 0; j < 1000; j++) {
                    String word = elements.get(j).select("td:nth-child(1)").text();
                    if (word.length() < 32 && word.matches("([a-z]|[A-Z])+")) {
                        System.out.println(word);
                        wordService.save(
                                new Word()
                                        .setWordCategoryId(wordCategory.getId())
                                        .setWord(word)
                        );
                    }
                }
                System.out.println(elements.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void crawlLevel4() {
        String word_category_id = "1408023424760025089";

        //  创建请求
        Connection conect = Jsoup.connect(url);
        //  设置请求头信息
        conect.header("User-Agent", "Mozilla/5.0");
        //  设置参数
        String level = 4 + "";
        conect.data("filter_custom_Level[]", level);
        conect.data("limit", "0");
        conect.data("directionTable", "asc");
        conect.data("sortTable", "base");
        conect.data("filter_order", "pos_rank");
        conect.data("filter_order_Dir", "asc");
        conect.data("boxchecked", "0");
        //  设置超时时间
        conect.timeout(30000);

        try {
            Document doc = conect.post();
            System.out.println("成功请求到页面");
            Elements elements = doc.select("#reportList tbody tr");

            for (int j = 0, count = 0; j < elements.size() && count < 200; j++) {
                String word = elements.get(j).select("td:nth-child(1)").text();
                System.out.println(word);

                if (word.length() < 32 && word.matches("([a-z]|[A-Z])+")) {
                    wordService.save(
                            new Word()
                                    .setWordCategoryId(word_category_id)
                                    .setWord(word)
                    );
                    count++;
                }
            }
            System.out.println(elements.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求页面失败");
        }
    }

    @Test
    public void crawlLevel6() {
        String word_category_id = "1408023649067208706";

        //  创建请求
        Connection conect = Jsoup.connect(url);
        //  设置请求头信息
        conect.header("User-Agent", "Mozilla/5.0");
        //  设置参数
        String level = 6 + "";
        conect.data("filter_custom_Level[]", level);
        conect.data("limit", "0");
        conect.data("directionTable", "asc");
        conect.data("sortTable", "base");
        conect.data("filter_order", "pos_rank");
        conect.data("filter_order_Dir", "asc");
        conect.data("boxchecked", "0");
        //  设置超时时间
        conect.timeout(30000);

        try {
            Document doc = conect.post();
            System.out.println("成功请求到页面");
            Elements elements = doc.select("#reportList tbody tr");

            for (int j = 0, count = 0; j < elements.size() && count < 200; j++) {
                String word = elements.get(j).select("td:nth-child(1)").text();
                System.out.println(word);

                if (word.length() < 32 && word.matches("([a-z]|[A-Z])+")) {
                    wordService.save(
                            new Word()
                                    .setWordCategoryId(word_category_id)
                                    .setWord(word)
                    );
                    count++;
                }
            }
            System.out.println(elements.size());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求页面失败");
        }
    }
}
