package com.crayon.ve.controller;

import com.crayon.ve.POJO.WordBattleMatchPool;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Objects;

@ServerEndpoint(value = "/ws/one/{userId}")
@Controller
public class WebSocketController {

    private static WordBattleMatchPool wordBattleMatchPool = WordBattleMatchPool.getInstance();

    @OnOpen
    public void onOpen(
            @PathParam("userId") String userId,
            Session session
    ) {

        wordBattleMatchPool.putUser(session);
        System.out.println("新连接加入：" + session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        wordBattleMatchPool.removeUser(session);
        System.out.println("关闭链接");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (Objects.equals(message, "匹配")) {
            // 开始匹配
            wordBattleMatchPool.getUser(session).startMatch();
        } else if (Objects.equals(message, "取消匹配")) {
            // 取消匹配
            wordBattleMatchPool.getUser(session).cancelMatch();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
}
