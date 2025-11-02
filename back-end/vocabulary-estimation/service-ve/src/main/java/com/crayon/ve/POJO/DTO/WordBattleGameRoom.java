package com.crayon.ve.POJO.DTO;

import com.crayon.ve.POJO.VO.VocabularyEstimationVO;
import com.crayon.ve.entity.WordBattleTransFrame;
import com.crayon.ve.service.WordService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Crayon
 * @date 2025/11/2 16:18
 *
 */
@Slf4j
public class WordBattleGameRoom implements Comparable<WordBattleGameRoom> {
    private WordService wordService;

    public WordBattleGameRoom(WordService wordService) {
        this.wordService = wordService;
    }

    // 额定2个人就可以开始游戏
    public static final int PLAYER_NUM = 2;

    @Override
    public int compareTo(WordBattleGameRoom o) {
        if (Objects.equals(this.getStatus(), o.getStatus())) {
            return 0;
        }
        if (Objects.equals(this.getStatus(), Status.WAITING)) {
            return -1;
        }
        return 1;
    }

    public static class Status {
        public static final String WAITING = "waiting";
        public static final String PLAYING = "playing";
    }

    // 房间中的玩家
    private final BlockingQueue<WordBattleGamePlayer> playerList = new ArrayBlockingQueue<>(PLAYER_NUM);

    // 房间状态
    private final AtomicReference<String> status = new AtomicReference<>(Status.WAITING);

    // 玩家分数
    private final ConcurrentMap<WordBattleGamePlayer, Integer> score = new ConcurrentHashMap<>();

    public int playerCount() {
        return playerList.size();
    }

    public String getStatus() {
        return status.get();
    }

    public void playerJoin(WordBattleGamePlayer player) {
        if (playerList.size() + 1 > PLAYER_NUM) {
            throw new RoomFullException();
        }
        playerList.add(player);
        notifyPlayer(player, "");
        notifyPlayerExceptOne(player, "对手加入了...");
    }

    public void playerLeave(WordBattleGamePlayer player) {
        playerList.remove(player);
        notifyPlayerExceptOne(player, "对手离开了...");
    }

    public void notifyAllPlayer(String message) {
        for (WordBattleGamePlayer p : playerList) {
            p.notifyPlayer(new WordBattleTransFrame().setCmd(101).setDescription(message).setCurrentNum(playerCount()).setNeedNum(PLAYER_NUM));
        }
    }

    public void notifyPlayer(WordBattleGamePlayer p, String message) {
        p.notifyPlayer(new WordBattleTransFrame().setCmd(101).setDescription(message).setCurrentNum(playerCount()).setNeedNum(PLAYER_NUM));
    }

    public void notifyPlayerExceptOne(WordBattleGamePlayer expectOne, String message) {
        for (WordBattleGamePlayer p : playerList) {
            if (Objects.equals(expectOne, p)) {
                continue;
            }
            p.notifyPlayer(new WordBattleTransFrame().setCmd(101).setDescription(message).setCurrentNum(playerCount()).setNeedNum(PLAYER_NUM));
        }
    }

    public void tryStartGame() {
        if (playerCount() < WordBattleGameRoom.PLAYER_NUM) {
            log.info("lack of player, cannot start game");
            return;
        }

        Thread thread = new Thread(() -> {
            while (true) {
                if (Objects.equals(getStatus(), WordBattleGameRoom.Status.WAITING)) {
                    notifyAllPlayer("游戏开始");
                    status.set(WordBattleGameRoom.Status.PLAYING);
                    for (WordBattleGamePlayer p : getPlayerList()) {
                        p.notifyPlayer(new WordBattleTransFrame().setCmd(103));
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                VocabularyEstimationVO vo = wordService.listEstimationWords();
                EstimationWordDTO q = vo.getEstimationWordList().stream().findFirst().get();
                for (WordBattleGamePlayer p : getPlayerList()) {
                    HashMap<String, Integer> score = new HashMap<>();
                    score.put("self", this.score.getOrDefault(p, 0));
                    p.notifyPlayer(new WordBattleTransFrame().setCmd(104).setDescription("单词推送").setGameInfo(
                            new WordBattleTransFrame.GameInfo().setWord(q.getWord()).setOptions(q.getOptions()).setScore(score)
                    ));
                }

                // 定时推送新题
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public BlockingQueue<WordBattleGamePlayer> getPlayerList() {
        return playerList;
    }

    public static class RoomException extends RuntimeException {
        public RoomException() {
            super();
        }

        public RoomException(String message) {
            super(message);
        }
    }

    public static class RoomFullException extends RoomException {
        public RoomFullException() {
            super();
        }
    }
}
