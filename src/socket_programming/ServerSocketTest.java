package socket_programming;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {
	public static void main(String[] args) {
		
		try {
		ServerSocket serverSocket = new ServerSocket(8111);
		
		System.out.println("Ŭ���̾�Ʈ ���� ����� . . .");
		Socket clientSocket;
			clientSocket = serverSocket.accept();
		
		clientSocket.close();
		serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
