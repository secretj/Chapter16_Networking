package chatting_programming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.framework.TcpApplication;

/**
 * AppServer�κ��� ���޹��� ������ �̿��Ͽ�
 * Ŭ���̾�Ʈ�� ���� ���� �� �ۼ��� ����� �����Ѵ�. 
 * 
 */

public class TcpServerHandler implements Runnable {
	
	/* Ŭ���̾�Ʈ�� ID�� Ű(K)�� �ϴ� �޽����� �۽�(V)�ϱ� ���� �� �ڷᱸ��
	 */
	public static HashMap<String, PrintWriter> sendMap = new HashMap<>(); 
	
	// Ŭ���̾�Ʈ�� ����� ���� ��ü
	private Socket sock;
	
	// Ŭ���̾�Ʈ IP �ּ�
	private String cAddr;
	
	// Ŭ���̾�Ʈ ID
	private String id;
	

	/*	������
	 * 	: �޾ƿ� ������ �ʿ� ����
	 */
	
	public TcpServerHandler(Socket cSocket) {
		this.sock=cSocket;
		this.cAddr =sock.getInetAddress().getHostAddress();

	}
	
	
	/*
	 * ������ ��/��� ����
	 * ��ε�ĳ����(�� �����ڿ��� ä���� �� �������ִ� ����)
	 * ������ �ۼ��� ����
	 */
	@Override
	public void run() {
	
		try {
			// 1. �۽� ��Ʈ�� ���
			PrintWriter pw = new PrintWriter(
							 new OutputStreamWriter(
							 sock.getOutputStream()));
			// 2. ���� ��Ʈ�� ���
			BufferedReader br = new BufferedReader(
							    new InputStreamReader(
							    sock.getInputStream()));
					
			
			// 3. Ŭ���̾�Ʈ �������� ����
			id = br.readLine();
			TcpServerHandler.sendMap.put(id, pw);
			
			// 4.Ŭ���̾�Ʈ�� ���������� ��ε�ĳ����
			TcpServerHandler.broadCast(TcpApplication.timeStamp()+ 
				"["+ id + "]���� �����̽��ϴ�.");
			System.out.println(TcpApplication.timeStamp() + cAddr + " < connected");
			System.out.println(TcpApplication.timeStamp() +
					        "�����ο� : " + sendMap.size()+"��");
			
			// 5. ����/�۽�
			String line =null;
			while((line =br.readLine()) != null) {
				//�����ϴ� ���
				if(line.equalsIgnoreCase("/quit")) {
					TcpServerHandler.broadCast(TcpApplication.timeStamp()+
							"["+id+"���� �����ϼ̽��ϴ�.]");
					break;
				}
				//�ӼӸ� �ϴ� ���  -> /to�� ���ڿ��� ���������~ -1���� ũ�ٴ°� ����ִٴ� ��
				else if(line.indexOf("/to") > -1) {
					//�ӼӸ� �޼ҵ� ȣ��
					whisper(id, line);
					
				}
				//�Ϲ� �޼��� ����
				else {
					String msg = "["+id+"]"+" " + line;
					TcpServerHandler.broadCast(msg);
				}
			}
			//�����ϴ� ��� ó��
			System.out.println(TcpApplication.timeStamp() + 
				cAddr + " > disconnected");
			// �� ����
			TcpServerHandler.sendMap.remove(id);
			
			System.out.println(TcpApplication.timeStamp() +
			        "�����ο� : " + sendMap.size()+"��");
			
			pw.close();
			br.close();
			sock.close();
			
	
		}catch(IOException ex) {
			ex.printStackTrace();
		}finally {
			
		}
		
	}
	
	/*
	 * �ӼӸ� ���� �޼���
	 * (String ���޴��, String ������ �޼���) 
	 */
	private void whisper(String name, String msg) {
		
		
		
	}
	
	/* 
	 * �޼��� �ϰ� ���� �޼ҵ�
	 * :��� �����ڿ��� �ϰ������� ����
	 */
	private static void broadCast(String message) {
		
		//sendMap�� ���� �����尡 �����ϹǷ� ����ȭ(synchronized) ó���ʿ�
		synchronized (sendMap) {
			
			//������ ��� Ŭ���̾�Ʈ�鿡�� �޽����� ����
			for(PrintWriter cpw : TcpServerHandler.sendMap.values()) {
				cpw.println(message);
				cpw.flush();
		}
		
		}
	}
}