package co.sukbinggo.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import co.sukbinggo.domain.MemberVO;
import co.sukbinggo.domain.NoteVO;
import co.sukbinggo.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

//ServerSocket
@Log4j
@EnableWebSocket
public class NoteHandler extends TextWebSocketHandler{
	// 접속자 관리 객체
	// session이라는 접속자를 가지고 있고 접속자들을 관리해야함
	
	
	// 접속자 관리 객체
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	
	@Autowired
	private NoteService noteService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		// 현재 접속된 사람
		log.warn("입장한 사람 : " + getIdBySession(session));
		sessions.add(session);
		log.warn("현재 접속자 수 : " + sessions.size());
		log.warn("현재 접속자 정보");
		sessions.forEach(log::warn);
		log.warn(sessions.stream().map(s -> getIdBySession(s)).collect(Collectors.toList())); 
		
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.warn(session.getId() + " 로그아웃");
		sessions.remove(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//		String msg = message.getPayload();
//		Gson gson = new Gson();
//		NoteVO vo =  gson.fromJson(msg, NoteVO.class);
//		MemberVO memberVO = (MemberVO)session.getAttributes().get("member");
//		log.warn(msg);		
//		log.warn(vo);
//		log.warn(memberVO);  // 내 정보
//		for(WebSocketSession s : sessions){
//			s.sendMessage(new TextMessage(session.getId() + " :: " + msg));			
//		}
//		
		
		
		log.warn(noteService);
		String receiver = message.getPayload(); // js, ws.send() 수신자
		String sender = getIdBySession(session);
		List<NoteVO> list0 = noteService.getSendList(getIdBySession(session));
		List<NoteVO> list1 = noteService.getReceiveList(receiver);
		List<NoteVO> list2 = noteService.getReceiveUncheckedList(receiver);
		
		Map<String, Object> map = new HashMap<>();
		map.put("sendList", list0);
		map.put("receiveList", list1);
		map.put("receiveUncheckedList", list2);
		map.put("sender", sender);
		
		Gson gson = new Gson();
		// A > B
		for(WebSocketSession s :sessions){
			if(receiver.equals(getIdBySession(s)) || session == s){
				s.sendMessage(new TextMessage(gson.toJson(map)));
			}
		}
	}
	
	private String getIdBySession(WebSocketSession session){ // 현재 접속되어 있는 회원들의 아이디 확인 용도
		Object obj = session.getAttributes().get("member");
		String id = null;
		if(obj != null && obj instanceof MemberVO){
			id = ((MemberVO)obj).getUserid();
		}
		return id;
	}

	// lifecycle
	// 접속, 실제할일, 종료

}
