package socket_programming;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;



public class EchoClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket =new Socket("localhost", 8112);
		System.out.println("�������� �Ϸ�");
		
		OutputStream out = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(out);
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("������ ���� �� �޽����� �Է��� �ּ���.");
		
		while(true) {
			System.out.println(">");
			String msg =sc.nextLine();
			dos.writeUTF(msg);
			dos.flush();
		
			if(msg.equalsIgnoreCase("exit")) {
				break;
			}
		}
		

		
		/*
		InputStream in =socket.getInputStream();
		DataInputStream dis =new DataInputStream(in);
		*/
		
		
		dos.close();
		sc.close();
		socket.close();
		
		System.out.println("Ŭ���̾�Ʈ ����");
	}
}