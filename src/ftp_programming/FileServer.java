package ftp_programming;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
	public static final int PORT=10002; 
	
	
	
	public static void main(String[] args) throws IOException {
		
		
		ServerSocket server =new ServerSocket(PORT);
		System.out.println("Ŭ���̾�Ʈ ���� �����...");
		
		Socket client =server.accept();
		
		System.out.println(client.getInetAddress().getHostName()
				+ "���� �����ϼ̽��ϴ�.");
		
		OutputStream out =client.getOutputStream();
		OutputStreamWriter outw = new OutputStreamWriter(out);
		PrintWriter pw = new PrintWriter(outw);
		
		FileInputStream fin = null;				//������ �о��
		DataOutputStream dout = new DataOutputStream(out);
		
		/*
		 * ���ҽ� �ڵ� ����
		 */
		File rescs = new File("resource\\");	// ������ ���
		System.out.println(rescs.list().length+ "���� ������ �ֽ��ϴ�."); //��ξȿ� �ִ� ������ ����
		
		String filePath =null;
		for(String file : rescs.list()) {			//���ϸ� ����
			// ���� ����(�̸� / ũ��)
			filePath= rescs.getName()+"\\"+file;
			File sendFile= new File(filePath);
			System.out.println(sendFile.getName()+"�۽��� . . .");
			
			//���� �۽�
			pw.println(sendFile);
			pw.flush();
			pw.println(sendFile.length());
			pw.flush();
		
			fin = new FileInputStream(filePath);
			int cnt = 0 ;
			for(int i =0; i<sendFile.length(); i++){
				//���� �б�
				int data =fin.read();
			
				//���� ����
				dout.write((data));
				if(cnt %  3000 == 0) {
					System.out.print("��");
				} 
				cnt++;
				
			}
			
			System.out.println("�Ϸ� \n");
		
		}
		try {
		fin.close();
		dout.close();
		pw.close();
		client.close();
		server.close();
		}catch(Exception e) {}
		
		System.out.println();
		System.out.println("���� ���� ���� ���α׷� ����");
	}
	
}
