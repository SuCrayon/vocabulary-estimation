package com.crayon.ve.POJO;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

// 单词对战用户匹配池
public class WordBattleMatchPool {

    // 在线用户
    private static ConcurrentHashMap<Session, MatchUser> userMap = new ConcurrentHashMap<>();

    // 匹配队列 阻塞队列
    private static LinkedBlockingDeque<MatchUser> matchQueue = new LinkedBlockingDeque<>();

    private WordBattleMatchPool() {
        // 匹配线程
        new Thread("wordBattleMatch-thread") {
            @Override
            public void run() {
                while (true) {
                    try {
                        MatchUser user1 = matchQueue.take();
                        System.out.println("拿到user1");
                        MatchUser user2 = matchQueue.take();
                        System.out.println("拿到user2");

                        user1.rival = user2;
                        user2.rival = user1;
                        user1.notifyUser("匹配成功，对手：" + user1.rival.session.getId());
                        user2.notifyUser("匹配成功，对手：" + user2.rival.session.getId());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    // 静态内部类实现单例
    private static class Singleton {
        private static final WordBattleMatchPool INSTANCE = new WordBattleMatchPool();
    }

    // 获取单例实例
    public static WordBattleMatchPool getInstance() {
        return Singleton.INSTANCE;
    }

    public MatchUser getUser(Session session) {
        return userMap.get(session);
    }

    public void putUser(Session session) {
        System.out.println("用户上线");
        userMap.put(session, new MatchUser(session));
    }

    public void removeUser(Session session) {
        System.out.println("用户下线");
        userMap.remove(session);
    }

    // 匹配用户内部类
    public static class MatchUser {
        // 长连接session，根据这个通知用户
        private Session session;
        // 对手
        private MatchUser rival;

        public MatchUser(Session session) {
            this.session = session;
        }

        public void startMatch() {
            this.notifyUser("匹配中...");
            matchQueue.add(this);
        }

        public void cancelMatch() {
            this.notifyUser("取消匹配");
            matchQueue.remove(this);
        }

        public void notifyUser(String message) {
            this.session.getAsyncRemote().sendText(message);
        }
    }
}
