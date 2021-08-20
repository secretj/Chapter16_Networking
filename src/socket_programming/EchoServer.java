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
		System.out.println("���� �غ� �Ϸ�");
		
		Socket socket =server.accept();  //Ŭ���̾�Ʈ ���������� ���
		System.out.println("Ŭ���̾�Ʈ ���� �Ϸ�");
		
		//Ŭ���̾�Ʈ IP �ּ�
		System.out.println(socket.getInetAddress());
		
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);
		
	
		
		while(true) {
			String userMsg=dis.readUTF();
			System.out.println("����ڸ޽��� :" + userMsg);
			if(userMsg.equalsIgnoreCase("exit")) {
				break;
			}
		}
		
		dis.close();
		in.close();
		
		socket.close();
		server.close();
		
		System.out.println("���� ����");
		
		
	}

}
