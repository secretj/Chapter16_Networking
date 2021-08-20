package socket_programming;

import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class SimpleServer {

	public static void main(String[] args)   throws IOException{

		ServerSocket server =new ServerSocket(8112);
		System.out.println("서버 준비 완료");
		
		Socket socket =server.accept();  //클라이언트 오기전까지 대기
		System.out.println("클라이언트 연결 완료");
		//클라이언트 IP 주소
		System.out.println(socket.getInetAddress());
		
		byte[] arr = {1,2,3,4,5,6,7,8,9,10};
		
		OutputStream out =socket.getOutputStream();
		
		out.write(arr);
		out.flush();
		out.close();
		
		socket.close();
		server.close();
		
		System.out.println("서버 종료");
		
		
	}

}
