package com.crayon.ve.entity;

import com.crayon.ve.POJO.DTO.WordBattleGameRoom;
import com.crayon.ve.constant.enumeration.WordBattleGameCmdEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

// 传输数据格式
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "WordBattleTransFrame对象", description = "单词对战长连接数据传输格式")
public class WordBattleTransFrame {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private WordBattleGameCmdEnum cmd;

    // 用户选择的答案，仅在cmd为3的时候有效
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String optionSelect;

    // 服务器响应的提示
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    // 当前玩家数
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer currentNum;

    // 需要玩家数，达到人数才能开局
    private Integer needNum = WordBattleGameRoom.PLAYER_NUM;

    /*
    服务器响应的对局信息
    当前题目: currentQuestion{String word, List<String> options}
    玩家得分列表: List<Integer> scoreList
    */
    //    @JsonUnwrapped
    //    private Map<String, Object> gameInfo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GameInfo gameInfo;

    @Data
    public static class GameInfo {
        String word;
        List<String> options;

        Map<String, Integer> score;
    }
}
