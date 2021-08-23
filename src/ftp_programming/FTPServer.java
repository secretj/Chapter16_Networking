package ftp_programming;
/**  [파일 서비스 프로그래밍]
 * 	1. 클라이언트는 특정한 파일명을 서버에 요청한다.
 * 	2. 서버는 클라이언트가 요청한 파일을 찾는다.
 * 	3. 요청한 파일을 찾으면 파일(리소스폴더 내)에서 데이터를 읽어들인다.
 * 	4. 읽어 온 파일의 데이터를 클라이언트에게 전송한다.
 *  5. 클라이언트는 받은 파일의 내용을 화면에 출력하고 저장한다.   
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FTPServer {
	private static final int PORT =1234;
		
	//메소드
	public static void main(String[] args)  {
		
		
		System.out.println();
		
		// 입출력 스트림
		InputStream in = null;
		OutputStream out =null;
		DataInputStream din = null;
		DataOutputStream dout =null;
		
		FileInputStream fin =null;
		
		// 소켓
		ServerSocket serverSoc =null;
		Socket 		 clientSoc =null;
		
		
		// 호스트 주소	
		String 		 clientAddr =null;
		
		
		/*클라이언트 접속 대기 및 소켓생성
		* 수신 / 송신
		*/
		
		try {
			serverSoc =new ServerSocket(PORT);
			System.out.println(timeStamp() + "클라이언트 접속을 대기중입니다...");
			System.out.println();

			clientSoc = serverSoc.accept();
			
			clientAddr =clientSoc.getInetAddress().getHostName();
			System.out.println(timeStamp() +clientAddr +"<-connected");

			
			 // 수신(IN) : 클라이언트가 보낸 파일명
			 
			in = clientSoc.getInputStream();
			din =new DataInputStream(in);
			
			// 송신(OUT) : 서버소스폴더내 파일을 클라이언트에게 보냄.
			out =clientSoc.getOutputStream();
			dout =new  DataOutputStream(out);
			
			
			// 단계1 : 사용자가 무슨 파일을 필요로 하는지 파악
			String fileName = din.readUTF();
			
			// 단계2 : 파일 데이터를 찾아내고 데이터를 읽는다.
			fin =new FileInputStream("resources\\"+fileName);	//상대경로 컴퓨터마다 경로가 다르니까 절대경로를 안씀
			// 단계3 : 파일을 읽고 데이터를 보낸다.
			System.out.println("파일을 보내는중 . . . ");
			System.out.println();
			
			// 파일을 읽고 보내기
			while(true) {
				//읽기
				int data = fin.read();
				if(data ==-1) {
					break;
				}
				//쓰기
				dout.write(data);
			} 

		}catch(IOException e) {
			
		}finally {
			
				try {
					if(din != null) {din.close();}
					if(dout != null) {dout.close();}
					if(fin !=null) {fin.close();}
					if(clientSoc != null) {clientSoc.close();}
					if(serverSoc != null) {serverSoc.close();}
				
			}catch(IOException ex) {
				
			}
		}

}
																							
	
	/*
	 * 현재시간을 리턴해주는 메소드 
	 * 반환타입 : String
	 */
	private static String timeStamp() {
		SimpleDateFormat format =new SimpleDateFormat("[hh:mm:ss]");
		return format.format(new Date());
	}
}
