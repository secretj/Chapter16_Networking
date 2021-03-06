package com.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 *  설정데이터 파일을 읽어와 초기화하는 클래스
 *
 */
public abstract class TcpApplication {
	/*
	 *  어플리케이션에 필요한 필드 선언 
	 */
	public static String IP;
	public static int PORT;
	public static String CONFIG = "config/applicationcontext.ini";
	
	/*
	 *  어플리케이션 초기화 
	 */
	public void init() {
		// 설정데이터로부터 필요한 항목을 추출
		Properties settings = new Properties();	//가져오는 클래스
		try {
			settings.load(new FileInputStream(CONFIG));
			IP = settings.getProperty("IP");
			PORT = Integer.parseInt(settings.getProperty("PORT"));	//포트는 인트로 선언되었기때문에 바꿔줘야함
			
			System.out.println("IPAddress : " + IP);;
			System.out.println("PORT      : " + PORT);	
			
			System.out.println(TcpApplication.timeStamp());
			}
		
		catch(IOException e) {
			System.out.println("설정파일 (applicationcontext.ini)을 찾을 수 없습니다.");
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		}
		
		
	}
	//구현 클래스에서 오버라이딩할 추상메소드
	public abstract void start();
 
	
	/*
	 * 현재시간을 리턴해주는 메소드
	 * 반환타입 : String
	 */

	public static String timeStamp() {
		SimpleDateFormat format =new SimpleDateFormat("[hh:mm:ss]");
		return format.format(new Date());
	}
	
}
