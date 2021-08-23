package ftp_programming;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient {
	public static final int PORT=10002; 
	public static final String IP ="localhost";
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket =new Socket(IP,PORT);
		
		InputStream in = socket.getInputStream();
		InputStreamReader inr =new InputStreamReader(in);
		BufferedReader br =new BufferedReader(inr);
		
		DataInputStream din =new DataInputStream(in);
		FileOutputStream fout =null;
		
		
		while(true) {
			// 파일 이름 수신
			String strline= br.readLine();
			//파일 길이 수신
			String strlen = br.readLine();
			
			if(strline == null) {
				break;
			}
			System.out.println(strline + "파일 수신중 . . .");
			System.out.println("(파일크기 :" + strlen + ")");
			
			String Path = "C:/Temp/Download/" + strline;
			fout = new FileOutputStream(Path);
		
			int cnt = 0 ;
			for(int i =0; i<Integer.parseInt(strlen); i++){
				//파일 읽기
				int data =din.read();
				
				//파일 쓰기
				fout.write((data));
				if(cnt %  3000 == 0) {
					System.out.print("♥");
				} 
				cnt++;
		}
			System.out.println("완료\n");
		
		
	}
		try {
		fout.close();
		din.close();
		br.close();
		socket.close();
		}catch(IOException e) {}
		System.out.println();
		System.out.println("파일 요청 프로그램을 종료");
}
}