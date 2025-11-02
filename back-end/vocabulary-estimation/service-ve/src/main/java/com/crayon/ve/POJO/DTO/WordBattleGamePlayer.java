package com.crayon.ve.POJO.DTO;

import com.crayon.ve.entity.WordBattleTransFrame;
import lombok.Data;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;

/**
 * @author Crayon
 * @date 2025/11/2 16:18
 *
 */
@Data
public class WordBattleGamePlayer {
    private final Session session;

    public WordBattleGamePlayer(Session session) {
        this.session = session;
    }

    public void notifyPlayer(String message) {
        // this.session.getAsyncRemote().sendText(message);
        notifyPlayer(
                new WordBattleTransFrame()
                        .setCmd(101)
                        .setDescription(message)
        );
    }

    public void notifyPlayer(WordBattleTransFrame frame) {
        // this.session.getAsyncRemote().sendText(message);
        try {
            this.session.getBasicRemote().sendObject(frame);
        } catch (EncodeException | IOException e) {
            e.printStackTrace();
        }
    }
}
