package com.crayon.ve.POJO;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WordBattleGame {

    // 房间列表 线程安全的实现
    private static final Queue<Room> roomList = new ConcurrentLinkedDeque<>();

    // 正在等待的房间
    private static Room waitingRoom = new Room();
    private static final Lock waitingRoomLock = new ReentrantLock(true);


    public static class Player {
        private final Session session;
        // 所在房间
        private Room room;

        public Player(Session session) {
            this.session = session;
        }

        public void notifyPlayer(String message) {
            // this.session.getAsyncRemote().sendText(message);
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 开始匹配
        public void startMatch() {
            if (this.room == null) {
                Room.joinWaitingRoom(this);
            }
        }

        // 取消匹配
        public void cancelMatch() {
            if (this.room != null) {
                Room.leaveWaitingRoom(this);
            }
        }

        // 离开
        public void leave() {
            if (this.room != null) {
                this.room.playerLeave(this);
            }
        }

        // 更新对局信息
        public void updateGameInfo(String message) {

        }
    }

    public static class Room {
        // 额定2个人就可以开始游戏
        public static final int PLAYER_NUM = 4;
        // 当前人数
        private final AtomicInteger playerNum = new AtomicInteger(0);
        // 房间中的玩家
        private final LinkedList<Player> playerList = new LinkedList<>();

        public void submitAndInit() {
            // 加入roomList
            roomList.add(waitingRoom);
            // 刷新
            waitingRoom = new Room();
        }

        /**
         * 玩家匹配，加入等待房间
         *
         * @param player
         */
        public static void joinWaitingRoom(Player player) {
            // 锁同步
            waitingRoomLock.lock();
            try {
                // 玩家加入到等待的房间中
                waitingRoom.playerJoin(player);
            } finally {
                waitingRoomLock.unlock();
            }
        }

        /**
         * 玩家取消匹配，移出等待房间
         *
         * @param player
         */
        public static void leaveWaitingRoom(Player player) {
            // 锁同步
            waitingRoomLock.lock();
            try {
                // 玩家移出等待的房间
                waitingRoom.playerCancel(player);
            } finally {
                waitingRoomLock.unlock();
            }
        }


        /**
         * 玩家加入等待房间
         *
         * @param player
         */
        public void playerJoin(Player player) {
            player.room = this;
            this.playerList.add(player);
            this.notifyExceptOne(player, "新用户加入了");
            // 玩家数+1，玩家数达到额定值，则提交到roomList中并刷新等待的房间
            if (this.playerNum.incrementAndGet() == PLAYER_NUM) {
                this.submitAndInit();
                this.notifyAllPlayers("人数齐了");
            }
        }

        /**
         * 玩家取消匹配
         *
         * @param player
         */
        public void playerCancel(Player player) {
            player.room = null;
            if (this.playerList.remove(player)) {
                this.notifyAllPlayers("有一个用户取消了匹配");
                // 玩家数-1
                this.playerNum.decrementAndGet();
            }
        }

        /**
         * 玩家离开游戏
         * 锁：这个房间
         *
         * @param player
         */
        public synchronized void playerLeave(Player player) {
            // 判空，双检
            if (player.room == null) {
                player.notifyPlayer("房间已解散");
                return;
            }

            // 不为空则判断是否是在匹配中
            if (player.room.playerNum.get() < PLAYER_NUM) {
                // 不够人数，说明还是在匹配中的
                leaveWaitingRoom(player);
                return;
            }

            // 是在游戏中的状态

            // 房间置空
            player.room = null;
            // 先把把该玩家移出房间
            this.playerList.remove(player);
            this.playerNum.decrementAndGet();
            // 通知其他玩家他离开了
            this.notifyAllPlayers("有玩家离开了，游戏中止");
            // 房间解散
            while (this.playerList.size() > 0) {
                this.playerList.removeFirst().room = null;
                this.playerNum.decrementAndGet();
            }

            // 从房间列表中移除这个房间
            roomList.remove(this);
        }

        public void pushGameInfo(Player player, String message) {
            // 向房间中的其他玩家推送信息
            this.notifyExceptOne(player, message);
        }


        public void notifyAllPlayers(String message) {
            for (Player player : this.playerList) {
                player.notifyPlayer(message);
            }
        }

        public void notifyExceptOne(Player one, String message) {
            for (Player player : this.playerList) {
                if (Objects.equals(one, player)) {
                    continue;
                }
                player.notifyPlayer(message);
            }
        }
    }

}
