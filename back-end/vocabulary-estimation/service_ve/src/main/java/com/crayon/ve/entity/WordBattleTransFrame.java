package com.crayon.ve.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

// 传输数据格式
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "WordBattleTransFrame对象", description = "单词对战长连接数据传输格式")
public class WordBattleTransFrame {
    /*
    指令
    0：服务器通知
    1：对局信息
    2：对战匹配
    3：取消匹配
     */
    private Integer cmd;

    private String description;

    private GameInfo data;

    @Data
    public static class GameInfo {
        // 当前单词
        private String currentWord;

        // 用户选择
        private String optionSelect;
    }


}
