package org.cmu.rmcs.websocket.handler;
import java.util.List;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
public class WebSocketFeedbackHandler extends TextWebSocketHandler  {
    private ThreadLocal<List<WebSocketSession>> aLocal=new ThreadLocal<>();
    @Override
    protected void handleTextMessage(WebSocketSession session,
            TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        aLocal.get().add(session);
        //如果是多线程的，这里应该永远打印1 
        System.out.print("size:"+aLocal.get().size());
        TextMessage returnMessage = new TextMessage(message.getPayload()+" received at server");
        session.sendMessage(returnMessage);
    }
}
