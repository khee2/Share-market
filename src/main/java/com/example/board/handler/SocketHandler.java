package com.example.board.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
	// HashMap<String, WebSocketSession> sessionMap = new HashMap<>(); // 웹소켓 session을 담아둘 맵	
	List<HashMap<String, Object>> rls = new ArrayList<>(); // 웹소켓 세션을 담아둘 리스트 ---roomListSessions
	// List는 방이고 HashMap은 방의 Object. Map이므로 String에는 session.getId() 혹은 roomNumber이 들어갈 수 있음.
	// 방정보 + 세션정보
	/*
	 * 클라이언트가 웹소켓 서버로 메시지를 전송했을 때 실행되는 메서드
	 */
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// 상속받은 TextWebSocketHandler는 handleTextMessage를 실행시킴.
		// 메시지 타입에 따라 handleBinaryMessage 또는 handleTextMessage가 실행됨.
		String msg = message.getPayload(); // 메시지에 담긴 텍스트값 얻기 (닉네임)
		JSONObject obj = jsonToObjectParser(msg);
		String rN = (String) obj.get("roomNumber");
		HashMap<String, Object> temp = new HashMap<String, Object>();
		if (rls.size() > 0) {
			for (int i=0; i< rls.size(); i++) {
				String roomNumber = (String) rls.get(i).get("roomNumber"); // 세션리스트의 저장된 방번호를 가져와서
				if(roomNumber.equals(rN)) { // 같은 값의 방이 존재한다면
					temp = rls.get(i); // 해당 방번호의 세션리스트의 존재하는 모든 Object 값을 가져온다.
					break;
				}
			}
			
			// 해당 방의 세션들만 찾아서 메시지를 발송해준다.
			for (String k : temp.keySet()) {
				if (k.equals("roomNumber")) { // 키가 방번호일 경우는 제외(키가 세션일 때만 메시지 보내기로 함.)
					continue;
				}
				WebSocketSession wss = (WebSocketSession) temp.get(k);
				if (wss != null) {
					try {
						wss.sendMessage(new TextMessage(obj.toJSONString()));
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	/**
	 * 클라이언트와 연결된 이후에 실행되는 메서드(소켓 연결)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		super.afterConnectionEstablished(session);
		boolean flag = false;
		String url = session.getUri().toString();
		System.out.println(url);
		String roomNumber = url.split("/chating/")[1];
		int idx = rls.size(); // 방의 사이즈
		if (rls.size() > 0) {
			for (int i=0; i<rls.size(); i++) {
				String rN = (String) rls.get(i).get("roomNumber");
				if (rN.equals(roomNumber)) {
					flag = true;
					idx = i; // 예를 들어 i가 2이면 3번째 방이므로
					break;
				}
				
			}
		}
		if (flag) { // 존재하는 방이라면 세션만 추가한다.
			HashMap<String, Object> map = rls.get(idx); // 3번째 방에
			map.put(session.getId(), session); // 세션만 추가
		} else { // 최초 생성하는 방이라면 방번호와 세션을 추가한다.
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("roomNumber", roomNumber);
			map.put(session.getId(), session);
			rls.add(map);
		}
		//세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
		JSONObject obj = new JSONObject();
		obj.put("type", "getId"); // 생성된 세션을 저장하면 발신메시지의 타입은 getID라고 명시
		obj.put("sessionId", session.getId()); // 생성된 세션 ID값을 클라이언트단에 발송한다. 
		// type값을 통해 메시지와 초기 설정값을 구분하기 
		session.sendMessage(new TextMessage(obj.toString()));
		// log.info("{} connected", session.getId());
		// 2. List
		// sessionList.add(session);
	}
	/*
	 * 클라이언트가 연결을 끊었을 때 실행되는 메서드(소켓 종료)
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		if (rls.size() > 0) {  //소켓이 종료되면 해당 세션값(HashMap의 String임)들을 찾아서 지운다.
			for(int i=0;i<rls.size(); i++) { 
				rls.get(i).remove(session.getId());
			}
		}
		// 2. List
		// sessionList.remove(session);
		super.afterConnectionClosed(session, status);
		log.info("{} Connection Closed", session.getId());
	}
	
	
	// JSON 파일이 들어오면 파싱해주는 함수 추가
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}
}
