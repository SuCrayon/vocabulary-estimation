package com.crayon.ve.service;

import com.crayon.ve.POJO.DTO.WordBattleGamePlayer;

/**
 * @author Crayon
 * @date 2025/11/4 23:42
 *
 */
public interface WordBattleGameService {
    void playerJoin(WordBattleGamePlayer player);

    void playerLeave(WordBattleGamePlayer player);
}
