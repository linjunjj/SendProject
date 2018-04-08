package com.linjun.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linjun.MessageVO;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author 林俊
 * @create 2018/4/8.
 * @desc
 **/
@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    //定义一个全局的初始化值count=0
    private static long count = 0;

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    private MessageVO messageVO = new MessageVO();

    @OnOpen
    public void onOpen(Session session) throws IOException, InterruptedException {
        this.session = session;
        webSockets.add(this);


        messageVO.setType(1);

        messageVO.setMessage("有新的连接");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";


        while (count<=100000000){
            messageVO.setUserNum((int) count);
            try {
                Json = mapper.writeValueAsString(messageVO);
            } catch (Exception ex) {
            }
            this.sendMessage(Json);
            count++;
            Thread.sleep(1000);
        }
    }


    @OnClose
    public void onClose() {
        webSockets.remove(this);

        messageVO.setType(2);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage("有用户离开");

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
        }

        this.sendMessage(Json);


    }

    @OnMessage
    public void onMessage(String message) {

        messageVO.setType(3);
        messageVO.setUserNum(webSockets.size());
        messageVO.setMessage(message);

        ObjectMapper mapper = new ObjectMapper();

        String Json = "";
        try {
            Json = mapper.writeValueAsString(messageVO);
        } catch (Exception ex) {
        }

        this.sendMessage(Json);

    }

    public void sendMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
