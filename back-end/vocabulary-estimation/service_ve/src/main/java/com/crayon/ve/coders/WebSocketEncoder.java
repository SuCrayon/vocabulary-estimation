package com.crayon.ve.coders;

import com.crayon.ve.entity.WordBattleTransFrame;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/*
Entity -> JSON
 */
@Component
public class WebSocketEncoder implements Encoder.Text<WordBattleTransFrame> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(WordBattleTransFrame object) {
        String result = null;
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
