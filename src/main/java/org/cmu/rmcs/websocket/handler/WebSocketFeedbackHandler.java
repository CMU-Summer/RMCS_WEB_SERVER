package org.cmu.rmcs.websocket.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.cmu.rmcs.pojo.GroupfeedbackCustomStruct;
import org.cmu.rmcs.pojo.WS_fd_cmd;
import org.cmu.rmcs.pojo.WS_feedback_group;
import org.cmu.rmcs.service.FeedbackSocketService;
import org.cmu.rmcs.service.RedisService;
import org.cmu.rmcs.util.ContantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSONObject;

public class WebSocketFeedbackHandler extends TextWebSocketHandler {
    private static ThreadLocal<Thread> execThread = new ThreadLocal<>();

    private static Logger logger = LoggerFactory
            .getLogger(WebSocketFeedbackHandler.class);
 
    @Resource
    private RedisService redisServiceImp;
    @Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {

        super.handleTextMessage(session, message);
    }

    // 连接建立后
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
           if(execThread .get()== null){
              FeedbackSocketService feedbackSocketService= new FeedbackSocketService();
              feedbackSocketService.setRedisService(redisServiceImp);
              feedbackSocketService.setSession(session);
               execThread.set(new Thread(feedbackSocketService));
           }
           try {
               execThread.get().start();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            logger.debug("fd thread can not create");
            session.close(); //关闭
        }
           
    }

    // 抛出异常时处理
    @Override
    public void handleTransportError(WebSocketSession session,
            Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        logger.debug("websocket fd connection error......");
        if(execThread.get()!=null){
            try {
                execThread.get().interrupt();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                logger.debug(" fd thread can not stop");
            }
            
            
        }
    }

    // 连接关闭后处理
    @Override
    public void afterConnectionClosed(WebSocketSession session,
            CloseStatus closeStatus) throws Exception {
        logger.debug("websocket fd connection closed......");
        if(execThread.get()!=null){
            try {
                execThread.get().interrupt();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                logger.debug(" fd thread can not stop");
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
