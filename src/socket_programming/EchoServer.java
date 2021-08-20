package socket_programming;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class EchoServer {

	public static void main(String[] args)   throws IOException{

		ServerSocket server =new ServerSocket(8112);
		System.out.println("서버 준비 완료");
		
		Socket socket =server.accept();  //클라이언트 오기전까지 대기
		System.out.println("클라이언트 연결 완료");
		
		//클라이언트 IP 주소
		System.out.println(socket.getInetAddress());
		
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);
		
	
		
		while(true) {
			String userMsg=dis.readUTF();
			System.out.println("사용자메시지 :" + userMsg);
			if(userMsg.equalsIgnoreCase("exit")) {
				break;
			}
		}
		
		dis.close();
		in.close();
		
		socket.close();
		server.close();
		
		System.out.println("서버 종료");
		
		
	}

}
