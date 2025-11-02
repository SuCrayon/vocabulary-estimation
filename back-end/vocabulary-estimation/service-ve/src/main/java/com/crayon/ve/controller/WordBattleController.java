package com.crayon.ve.controller;

import com.crayon.ve.POJO.DTO.WordBattleGamePlayer;
import com.crayon.ve.coders.WebSocketDecoder;
import com.crayon.ve.coders.WebSocketEncoder;
import com.crayon.ve.entity.WordBattleTransFrame;
import com.crayon.ve.service.impl.WordBattleGame;
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

    private static WordBattleGame wordBattleGame;

    @Autowired
    public void setWordBattleGame(WordBattleGame wordBattleGame) {
        WordBattleController.wordBattleGame = wordBattleGame;
    }

    private WordBattleGamePlayer player;

    @OnOpen
    public void onOpen(Session session) {
        this.player = new WordBattleGamePlayer(session);
        System.out.println("新连接：" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        wordBattleGame.playerLeave(player);
        System.out.println("连接关闭：" + session.getId());
    }

    @OnMessage
    public void onMessage(WordBattleTransFrame frame, Session session) throws IOException, EncodeException {
        System.out.println("收到消息：" + session.getId());
        Integer cmd = frame.getCmd();
        if (cmd == 1) {
            // 开始匹配
            wordBattleGame.playerJoin(player);
        } else if (cmd == 2) {
            // 取消匹配

        }
//        else {
//            this.player.updateGameInfo("xxx");
//        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // this.player.leave();
        this.player.notifyPlayer("服务器错误，对局结束");
        // System.out.println(session.getId() + "; 发生错误; 连接是否打开：" + session.isOpen());
        log.error(error.getMessage(), error);
    }

}
