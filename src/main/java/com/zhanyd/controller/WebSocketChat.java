package com.zhanyd.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@ServerEndpoint("/chat/{userId}")
@Component
public class WebSocketChat {

	//private static CopyOnWriteArraySet<WebSocketChat> webSocketSet = new CopyOnWriteArraySet<WebSocketChat>();
	
	private static Map<String,Session> routeTable = new ConcurrentHashMap<String,Session>();
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	private String userId;
	private String toUserId;
	
	@OnOpen
	public void onOpen(@PathParam("userId")String userId,Session session){
		this.session = session;
		this.userId = userId;
		routeTable.put(userId, session);
		System.out.println("用户" + userId + " 加入");
	}
	
	@OnClose
	public void onClose(){
		routeTable.remove(this.userId);
		System.out.println(this.userId + " 离开");
	}
	
	
	@OnMessage
	public void onMessage(String message,Session sesion) throws IOException{
		JSONObject messageJson = JSONObject.parseObject(message);
		System.out.println("接收到客户端的消息:" + message);
		
		Session toSession = routeTable.get(messageJson.getString("toUserId"));
		if(toSession == null){
			System.out.println(messageJson.getString("toUserId") + "已经离开");
			return;
		}
		toSession.getBasicRemote().sendText(messageJson.getString("message"));
		
	}
	
	
}
