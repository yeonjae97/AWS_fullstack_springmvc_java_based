package co.sukbinggo.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import lombok.extern.log4j.Log4j;


@Log4j
public class ChatHandler extends TextWebSocketHandler{

	// session이라는 접속자를 가지고 있고 접속자들을 관리해야함
	
	// 접속자 관리 객체
	List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
		log.warn("현재 접속자 수 : " + sessions.size());
		log.warn("현재 접속자 정보");
		sessions.forEach(log::warn);
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.warn(session.getId() + " 로그아웃");
		sessions.remove(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String msg = message.getPayload();
		Gson gson = new Gson();
		Map<?,?> map =  gson.fromJson(msg, Map.class);
		log.warn(map.get("msg")); // string을 map형태로 바뀔려면 gson 라이브러리를 활용한다.
		log.warn(map.get("id")); // string을 map형태로 바뀔려면 gson 라이브러리를 활용한다.
		log.warn(msg);
		
		// 로그인 된 회원의 로그를 기록해준다.(안되어 있으면 null)
		log.warn(session.getAttributes().get("member"));
		
		for(WebSocketSession s : sessions){
			s.sendMessage(new TextMessage(session.getId() + " :: " + msg));			
		}
		
		
	}

	// lifecycle
	// 접속, 실제할일, 종료

}
