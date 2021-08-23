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
			// ���� �̸� ����
			String strline= br.readLine();
			//���� ���� ����
			String strlen = br.readLine();
			
			if(strline == null) {
				break;
			}
			System.out.println(strline + "���� ������ . . .");
			System.out.println("(����ũ�� :" + strlen + ")");
			
			String Path = "C:/Temp/Download/" + strline;
			fout = new FileOutputStream(Path);
		
			int cnt = 0 ;
			for(int i =0; i<Integer.parseInt(strlen); i++){
				//���� �б�
				int data =din.read();
				
				//���� ����
				fout.write((data));
				if(cnt %  3000 == 0) {
					System.out.print("��");
				} 
				cnt++;
		}
			System.out.println("�Ϸ�\n");
		
		
	}
		try {
		fout.close();
		din.close();
		br.close();
		socket.close();
		}catch(IOException e) {}
		System.out.println();
		System.out.println("���� ��û ���α׷��� ����");
}
}