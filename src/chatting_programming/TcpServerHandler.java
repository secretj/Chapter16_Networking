package chatting_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.framework.TcpApplication;

/**
 * AppServer로부터 전달받은 소켓을 이용하여
 * 클라이언트의 접속 정보 및 송수신 기능을 관리한다. 
 * 
 */

public class TcpServerHandler implements Runnable {
	
	/* 클라이언트의 ID를 키(K)로 하는 메시지를 송신(V)하기 위한 맵 자료구조
	 */
	public static HashMap<String, PrintWriter> sendMap = new HashMap<>(); 
	
	// 클라이언트와 연결된 소켓 객체
	private Socket sock;
	
	// 클라이언트 IP 주소
	private String cAddr;
	
	// 클라이언트 ID
	private String id;
	

	/*	생성자
	 * 	: 받아온 소켓을 맵에 저장
	 */
	
	public TcpServerHandler(Socket cSocket) {
		this.sock=cSocket;
		this.cAddr =sock.getInetAddress().getHostAddress();

	}
	
	
	/*
	 * 참여자 입/퇴실 관리
	 * 브로드캐스팅(각 참여자에게 채팅을 다 전달해주는 역할)
	 * 참여자 송수신 관리
	 */
	@Override
	public void run() {
	
		try {
			// 1. 송신 스트림 얻기
			PrintWriter pw = new PrintWriter(
							 new OutputStreamWriter(
							 sock.getOutputStream()));
			// 2. 수신 스트림 얻기
			BufferedReader br = new BufferedReader(
							    new InputStreamReader(
							    sock.getInputStream()));
					
			
			// 3. 클라이언트 접속정보 저장
			id = br.readLine();
			TcpServerHandler.sendMap.put(id, pw);
			
			// 4.클라이언트의 입장정보를 브로드캐스팅
			TcpServerHandler.broadCast(TcpApplication.timeStamp()+ 
				"["+ id + "]님이 들어오셨습니다.");
			System.out.println(TcpApplication.timeStamp() + cAddr + " < connected");
			System.out.println(TcpApplication.timeStamp() +
					        "참여인원 : " + sendMap.size()+"명");
			
			// 5. 수신/송신
			String line =null;
			while((line =br.readLine()) != null) {
				//퇴장하는 경우
				if(line.equalsIgnoreCase("/quit")) {
					TcpServerHandler.broadCast(TcpApplication.timeStamp()+
							"["+id+"님이 퇴장하셨습니다.]");
					break;
				}
				//귓속말 하는 경우  -> /to가 문자열에 들어있으면~ -1보다 크다는건 들어있다는 뜻
				else if(line.indexOf("/to") > -1) {
					//귓속말 메소드 호출
					whisper(id, line);
					
				}
				//일반 메세지 전송
				else {
					String msg = "["+id+"]"+" " + line;
					TcpServerHandler.broadCast(msg);
				}
			}
			//퇴장하는 경우 처리
			System.out.println(TcpApplication.timeStamp() + 
				cAddr + " > disconnected");
			// 맵 삭제
			TcpServerHandler.sendMap.remove(id);
			
			System.out.println(TcpApplication.timeStamp() +
			        "참여인원 : " + sendMap.size()+"명");
			
			pw.close();
			br.close();
			sock.close();
			
	
		}catch(IOException ex) {
			ex.printStackTrace();
		}finally {
			
		}
		
	}
	
	/*
	 * 귓속말 전송 메서드
	 * (String 전달대상, String 전달할 메세지) 
	 */
	private void whisper(String name, String msg) {
		
		
		
	}
	
	/* 
	 * 메세지 일괄 전송 메소드
	 * :모든 참여자에게 일괄적으로 전송
	 */
	private static void broadCast(String message) {
		
		//sendMap에 여러 스레드가 접근하므로 동기화(synchronized) 처리필요
		synchronized (sendMap) {
			
			//접속한 모든 클라이언트들에게 메시지를 전송
			for(PrintWriter cpw : TcpServerHandler.sendMap.values()) {
				cpw.println(message);
				cpw.flush();
		}
		
		}
	}
}
