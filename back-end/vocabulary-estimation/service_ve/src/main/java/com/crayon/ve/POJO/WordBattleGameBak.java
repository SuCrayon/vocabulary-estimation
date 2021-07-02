package com.crayon.ve.POJO;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WordBattleGameBak {

    private static final List<Room> roomList = new ArrayList<>();
    private static volatile Room waitingRoom;


    public static class Player {
        private final Session session;
        private Room room;
        /*
        0：初始状态
        1：匹配中
        2：完成匹配，开始对战
         */
        private int status = 0;

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
            synchronized (Player.class) {
                if (this.status == 0) {
                    if (waitingRoom == null) {
                        // 没有人等待就创建房间进行等待
                        waitingRoom = new Room();
                        waitingRoom.playerList.add(this);
                        this.status = 1;
                        System.out.println("创建房间并等待");
                        this.notifyPlayer("创建房间并等待");
                    } else {
                        // 加入那个房间
                        waitingRoom.playerJoin(this);
                        System.out.println("成功加入房间");
                        this.room.startGame();
                    }
                }
            }
        }

        // 取消匹配
        public void cancelMatch() {
            if (this.status == 1) {
                // 匹配中
                synchronized (Player.class) {
                    this.status = 0;
                    if (waitingRoom.playerList.size() - 1 == 0) {
                        waitingRoom = null;
                        this.notifyPlayer("取消匹配");
                    }
                }
            }
        }

        public void leave() {
            synchronized (Player.class) {
                if (this.status != 0) {
                    if (this.status == 1) {
                        // 匹配中，在房间等待
                        waitingRoom = null;
                    } else if (this.status == 2) {
                        // 已经匹配成功，正在对局
                        this.room.someOneleaveRoom(this);
                    }
                }
            }
        }

        public void updateGameInfo(String message) {
            if (this.status == 2) {
                this.room.pushGameInfo(this, message);
            }
        }
    }

    public static class Room {
        // 额定2个人就可以开始游戏
        public static final int PLAYER_NUM = 2;
        private LinkedList<Player> playerList = new LinkedList<>();

        public void playerJoin(Player player) {
            synchronized (Room.class) {
                this.playerList.add(player);
                // 更新用户状态
                for (Player p : this.playerList) {
                    p.status = 2;
                    p.room = this;
                }
                // 加入房间列表
                roomList.add(this);
                waitingRoom = null;
            }
        }

        public void pushGameInfo(Player player, String message) {
            // 向房间中的其他玩家推送信息
            for (Player p : this.playerList) {
                if (Objects.equals(p, player)) {
                    // 跳过自己
                    continue;
                }

                p.notifyPlayer(message);
            }
        }


        public void startGame() {
            // 人数到齐，开始游戏，给玩家发送初始通知
            this.notifyAllPlayers("匹配成功");
        }

        public void someOneleaveRoom(Player someOne) {
            synchronized (Room.class) {

                // 房间加锁，可能多个用户同时下线
                if (this.playerList.remove(someOne)) {
                    someOne.status = 0;
                    someOne.room = null;
                    // 移除成功
                    this.notifyExceptOne(someOne, "用户：" + someOne.session.getId() + "已离开");

                    if (this.playerList.size() < PLAYER_NUM) {
                        // 房间少于2个人，解散房间
                        for (Player player : this.playerList) {
                            // 设置状态
                            // 单线程遍历，不需要加锁
                            player.status = 0;
                            player.room = null;
                        }

                        // 加锁移除房间
                        roomList.remove(this);

                        this.notifyAllPlayers("房间解散，请重新匹配");
                    }
                }
            }
        }

        public void notifyAllPlayers(String message) {
            System.out.println("playList长度：" + this.playerList.size());
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
