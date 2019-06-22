package com.kazma233.blog.controller.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

//@Controller
//@EnableScheduling
public class ChatWebSocket {

    // 用于转发数据(sendTo)
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // 获得IP
    @RequestMapping("/ip")
    @ResponseBody
    public String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    // 前往Socket页面
    @RequestMapping("/socket")
    public String socketPage() {
        return "chat";
    }

    // 接受到消息后, 再往所有人发， 包括自己
    @MessageMapping("/change-notice")
    // 订阅了/topic/notice的用户会受到返回的消息
    @SendTo("/topic/notice")
    public String notice(String message) {
        return message;
    }

    //@Scheduled(cron = "*/30 * * * * ?")
    public void scheduled() {
        simpMessagingTemplate.convertAndSend("/topic/notice", ""); // 相当于@SendTo("/topic/notice")
    }

}
