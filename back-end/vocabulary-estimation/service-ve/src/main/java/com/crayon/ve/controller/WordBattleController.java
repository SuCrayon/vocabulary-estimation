package com.crayon.ve.controller;

import com.crayon.ve.POJO.DTO.WordBattleGamePlayer;
import com.crayon.ve.coders.WebSocketDecoder;
import com.crayon.ve.coders.WebSocketEncoder;
import com.crayon.ve.constant.enumeration.WordBattleGameCmdEnum;
import com.crayon.ve.entity.WordBattleTransFrame;
import com.crayon.ve.service.WordBattleGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/wordBattle", decoders = {WebSocketDecoder.class}, encoders = {WebSocketEncoder.class})
@Controller
@Slf4j
public class WordBattleController {

    private static WordBattleGameService wordBattleGameService;

    @Autowired
    public void setWordBattleGame(WordBattleGameService wordBattleGameService) {
        WordBattleController.wordBattleGameService = wordBattleGameService;
    }

    private WordBattleGamePlayer player;

    @OnOpen
    public void onOpen(Session session) {
        this.player = new WordBattleGamePlayer(session);
        log.info("session open, sessionId: {}", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        wordBattleGameService.playerLeave(player);
        log.info("session close, sessionId: {}", session.getId());
    }

    @OnMessage
    public void onMessage(WordBattleTransFrame frame, Session session) throws IOException, EncodeException {
        System.out.println("收到消息：" + session.getId());
        WordBattleGameCmdEnum cmd = frame.getCmd();
        if (WordBattleGameCmdEnum.PLAYER_START_MATCH.equals(cmd)) {
            // 开始匹配
            log.info("player start match");
            wordBattleGameService.playerJoin(player);
        } else if (WordBattleGameCmdEnum.PLAYER_CANCEL_MATCH.equals(cmd)) {
            // 取消匹配
            log.info("player cancel match");
            wordBattleGameService.playerLeave(player);
        } else {
            log.warn("invalid cmd");
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("session error, sessionId: {}", session.getId(), error);
        wordBattleGameService.playerLeave(player);
    }

}
