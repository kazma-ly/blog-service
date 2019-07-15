package com.kazma233.blog.controller.chat;

import com.kazma233.blog.entity.message.SockMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/chat") // 聊天
public class BaseWebSocket {

    private static final Logger logger = LogManager.getLogger(BaseWebSocket.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的BaseWebSocket对象。
     */
    private static CopyOnWriteArraySet<BaseWebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    // 连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
    }

    // 连接关闭调用的方法
    @OnClose
    public void onClose() {
        try {
            this.session.close();
        } catch (IOException ignored) {
        }
        webSocketSet.remove(this);
    }

    /**
     * 收到客户端消息后调用的方法
     * <p>
     * //* @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            SockMessage sockMessage = objectMapper.readValue(message, SockMessage.class);
            sendInfo(sockMessage);
        } catch (IOException ignore) {
        }
    }

    // 发生错误时调用
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("发生错误: {}; 信息: ", this.session.getUserProperties(), error);
        onClose();
    }

    private void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
//        this.session .getBasicRemote().sendText(message);
    }

    private void sendMessage(SockMessage sockMessage) throws JsonProcessingException {
        if (!this.session.isOpen()) {
            onClose();
        } else {
            sockMessage.setOnline(String.valueOf(webSocketSet.size()));
            sockMessage.setId(this.session.getId());
            String json = objectMapper.writeValueAsString(sockMessage);
            sendMessage(json);
        }
    }

    /**
     * 发送信息 会做序列化处理
     *
     * @param sockMessage 消息
     */
    public static void sendInfo(SockMessage sockMessage) {
        try {
            for (BaseWebSocket item : webSocketSet) {
                item.sendMessage(sockMessage);
            }
        }catch (JsonProcessingException ignore) {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseWebSocket that = (BaseWebSocket) o;
        return Objects.equals(session, that.session);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session);
    }
}