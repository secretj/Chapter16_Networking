package socket_programming;

import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class SimpleServer {

	public static void main(String[] args)   throws IOException{

		ServerSocket server =new ServerSocket(8112);
		System.out.println("���� �غ� �Ϸ�");
		
		Socket socket =server.accept();  //Ŭ���̾�Ʈ ���������� ���
		System.out.println("Ŭ���̾�Ʈ ���� �Ϸ�");
		//Ŭ���̾�Ʈ IP �ּ�
		System.out.println(socket.getInetAddress());
		
		byte[] arr = {1,2,3,4,5,6,7,8,9,10};
		
		OutputStream out =socket.getOutputStream();
		
		out.write(arr);
		out.flush();
		out.close();
		
		socket.close();
		server.close();
		
		System.out.println("���� ����");
		
		
	}

}
