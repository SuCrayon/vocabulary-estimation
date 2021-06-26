package com.crayon.ve.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtils {


    /**
     * 获取其他3个随机答案的索引
     *
     * @param collision 正确答案的下标
     * @param bound     最大索引
     * @return
     */
    public static int[] get3OptionIndexs(int collision, int bound) {
        int[] result = new int[3];
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size() < 3) {
            int idx = random.nextInt(bound);
            // 重复或者是冲突
            while (set.contains(idx) || idx == collision) {
                idx = (idx + 1) % bound;
            }
            set.add(idx);
        }

        int i = 0;
        for (Integer optionIndex : set) {
            result[i] = optionIndex;
            i++;
        }
        return result;
    }
}
