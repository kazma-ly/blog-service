package com.kazma233.blog.config.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
//@EnableWebSocket // websocket (ws://127.0.01:8080/chat) 这种 websocket
//@EnableWebSocketMessageBroker // 启用WebSocket消息处理，由消息代理支持(stomp, sockjs)
public class WebSocketConfig // extends AbstractWebSocketMessageBrokerConfigurer
{

    /*@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 所有socket的前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 简单的消息代理
        registry.enableSimpleBroker("/topic");
    }

    // web的连接往/chat这连
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 启用socket回调
        registry.addEndpoint("/chat").setHandshakeHandler(new HandshakeHandler() {
            @Override
            public boolean doHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
                return true;
            }
        }).addInterceptors(new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

            }
        }).setAllowedOrigins("*");
    }*/

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
