package com.crayon.ve.coders;

import com.crayon.ve.entity.WordBattleTransFrame;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/*
JSON -> Entity
 */
public class WebSocketDecoder implements Decoder.Text<WordBattleTransFrame> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WordBattleTransFrame decode(String s) {
        WordBattleTransFrame result = null;
        try {
            result = objectMapper.readValue(s, WordBattleTransFrame.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
