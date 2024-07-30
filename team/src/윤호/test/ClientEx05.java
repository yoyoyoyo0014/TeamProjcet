package 윤호.test;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientEx05 {

	
	public static String getServerIp() {

		InetAddress local = null;
		try {
			local = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if (local == null) {
			return "";
		} else {
			String ip = local.getHostAddress();
			return ip;
		}
 
	}
	public static void main(String[] args) {
		int port = 5001;
		String ip = getServerIp();
		try{
			System.out.print("아이디 입력 : ");
			Scanner scan = new Scanner(System.in);
			String id = scan.next();
			Socket socket = new Socket(ip, port);
			System.out.println("[연결 성공]");
			Client client = new Client(id, socket);
			
			System.out.println("아 게임은 스피드 퀴즈입니다. 누구보다 빠르게 답해주세요. 엔터를 누르면 "
					+ "방에 입장됩니다.");
			
			client.recive();
			client.send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}