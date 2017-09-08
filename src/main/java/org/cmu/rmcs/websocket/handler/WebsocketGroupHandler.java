package org.cmu.rmcs.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.pojo.FamilyStruct;
import org.cmu.rmcs.pojo.GroupStruct;
import org.cmu.rmcs.pojo.NameStruct;
import org.cmu.rmcs.pojo.WS_group_info;
import org.cmu.rmcs.pojo.WS_group_sock_cmd;
import org.cmu.rmcs.service.GroupSocketService;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;
@Component
public class WebsocketGroupHandler extends TextWebSocketHandler {
    // 这个地方用来group信息变化的情况
    //
    private static Logger logger = LoggerFactory
            .getLogger(WebSocketFeedbackHandler.class);
    private static final Map<String, Thread> sessionThreadMap=new HashMap<>();
    @Resource
    private RedisService redisServiceImp; 
    @Resource
    private GroupSocketService groupSocketService;
    
    // 接收文本消息，并发送出去

    @Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {
      
        super.handleTextMessage(session, message);
    }

   

    // 连接建立后处理
    // 这里面要处理的信息就是
    // 先抽取redis里面的group
    // 然后对比该链接的group list，
    // 多了发送group add 命令
    // 少了发送 group delete 命令
    //

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        System.out.println("session's id:"+ session.getId());
        System.out.println("connect to the  group websocket success......");
        System.out.println("session is open:"+ session.isOpen());
        GroupSocketService gService=new GroupSocketService();
        gService.setRedisService(redisServiceImp);
        
        gService.setSession(session);
        Thread thread=new Thread(gService);
        try {
            thread.start();
            sessionThreadMap.put(session.getId(), thread);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.debug("can not create thread!!");
            sessionThreadMap.remove(session.getId());
            session.close();
        }
       


    }

    // 抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session,
            Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
       
        logger.debug("websocket gp connection closed......");

    }

    // 连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session,
            CloseStatus closeStatus) throws Exception {
 
        if(sessionThreadMap.containsKey(session.getId())){
            try {
                sessionThreadMap.get(session.getId()).interrupt();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                logger.debug("gp socket can stop the thread"+e.getMessage());
            }
            sessionThreadMap.remove(session.getId());
        }
        logger.debug("websocket gp connection closed......");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}