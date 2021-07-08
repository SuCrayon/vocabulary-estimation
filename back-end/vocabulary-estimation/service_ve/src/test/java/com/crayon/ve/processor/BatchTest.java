package com.crayon.ve.processor;

import com.crayon.ve.constant.Common;
import com.crayon.ve.service.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // 测试时Spring不会启动服务器，websocket会报错
public class BatchTest {
    @Autowired
    private WordService wordService;

    public List<Integer> genOneList() {
        int rightCount = 40; // 20%正确率
        Random random = new Random();
        int tempCount = 0;
        ArrayList<Integer> levelRightCounts = new ArrayList<>(Common.ESTIMATE_LEVEL_NUM);
        for (int i = 1; i <= Common.ESTIMATE_LEVEL_NUM; i++) {
            // 生成随机正确的个数，[0, min(25, 10))
            int randomRight = random.nextInt(Math.min(rightCount, Common.ESTIMATE_WORD_NUMS[i - 1]));
            rightCount -= randomRight;
            tempCount += randomRight;
            levelRightCounts.add(randomRight);
        }
        // 少了
        while (rightCount != 0) {
            for (int i = 1; i <= Common.ESTIMATE_LEVEL_NUM; i++) {
                if (rightCount == 0) {
                    break;
                }
                if (levelRightCounts.get(i - 1) < Common.ESTIMATE_WORD_NUMS[i - 1]) {
                    levelRightCounts.set(i - 1, levelRightCounts.get(i - 1) + 1);
                    rightCount--;
                    tempCount++;
                }
            }
        }

        System.out.println(Arrays.toString(Common.ESTIMATE_WORD_NUMS));
        System.out.println(levelRightCounts + "=" + tempCount);
        return levelRightCounts;
    }

    public List<List<Integer>> genBatchData(int batch) {
        List<List<Integer>> batchData = new LinkedList<>();
        for (int i = 0; i < batch; i++) {
            batchData.add(genOneList());
        }
        return batchData;
    }

    @Test
    public void process() {
        int batchSize = 100_000;
        List<Integer> resultList = new LinkedList<>();
        int sum = 0;

        for (List<Integer> list : genBatchData(batchSize)) {
            int calculate = wordService.calculate(list);

            resultList.add(calculate);
            sum += calculate;
            System.out.println(list + " result: " + calculate);
        }

        double average = (double) sum / batchSize;
        double tempSum = 0;
        for (Integer e : resultList) {
            tempSum += Math.pow(e - average, 2);
        }

        System.out.println("均值：" + average);
        System.out.println("方差：" + tempSum / (batchSize - 1));
        System.out.println("最小值：" + Collections.min(resultList));
        System.out.println("最大值：" + Collections.max(resultList));
    }
}
