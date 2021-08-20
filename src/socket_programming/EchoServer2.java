package socket_programming;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class EchoServer2 {

	public static void main(String[] args)   throws IOException{

		ServerSocket server =new ServerSocket(8112);
		System.out.println("���� �غ� �Ϸ�");
		
		Socket socket =server.accept();  //Ŭ���̾�Ʈ ���������� ���
		System.out.println("Ŭ���̾�Ʈ ���� �Ϸ�");
		
		//Ŭ���̾�Ʈ IP �ּ�
		System.out.println(socket.getInetAddress());
		
		//�о���� ��Ʈ��
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);
		
		//���� ��Ʈ��
		OutputStream out =socket.getOutputStream();
		DataOutputStream dos =new DataOutputStream(out);
	
		
		while(true) {
			String userMsg=dis.readUTF();
			System.out.println("����ڸ޽��� :" + userMsg);
			
			if(userMsg.equalsIgnoreCase("exit")) {
				break;
			}
			// ���� �޽����� �ٽ� ����
			dos.writeUTF(userMsg);
			dos.flush();
		}
		
		dis.close();
		in.close();
		
		socket.close();
		server.close();
		
		System.out.println("���� ����");
		
		
	}

}
