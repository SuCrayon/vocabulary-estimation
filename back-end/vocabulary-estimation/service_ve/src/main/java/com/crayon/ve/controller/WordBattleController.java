package com.crayon.ve.controller;

import com.crayon.ve.POJO.WordBattleGame;
import com.crayon.ve.coders.WebSocketDecoder;
import com.crayon.ve.coders.WebSocketEncoder;
import com.crayon.ve.entity.WordBattleTransFrame;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/wordBattle", decoders = {WebSocketDecoder.class}, encoders = {WebSocketEncoder.class})
@Controller
public class WordBattleController {

    private WordBattleGame.Player player;

    @OnOpen
    public void onOpen(Session session) {
        this.player = new WordBattleGame.Player(session);
        System.out.println("新连接：" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        this.player.leave();
        System.out.println("连接关闭：" + session.getId());
    }

    @OnMessage
    public void onMessage(WordBattleTransFrame frame, Session session) throws IOException, EncodeException {
        System.out.println("收到消息：" + session.getId());
        Integer cmd = frame.getCmd();
        session.getBasicRemote().sendObject(frame);
        System.out.println(frame);
//        if (cmd == 2) {
//            // 开始匹配
//            this.player.startMatch();
//        } else if (cmd == 3) {
//            // 取消匹配
//            this.player.cancelMatch();
//        } else {
//            this.player.updateGameInfo("xxx");
//        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        // this.player.leave();
        this.player.notifyPlayer("参数错误，连接即将关闭");
        System.out.println(session.getId() + "; 发生错误; 连接是否打开：" + session.isOpen());
        // error.printStackTrace();
    }

}
