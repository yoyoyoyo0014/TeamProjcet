package minigame.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMain {

	// 내 ip를 찾아주는 메소드
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

		String ip = getServerIp();
		// String ip = "192.168.30.206";
		int port = 5001;

		try {
			// List1<Socket> sockets = new ArrayList<Socket>();
			Socket socket = new Socket(ip, port);
			System.out.println("[서버 연결 성공]");
			Client client = new Client(socket);

			// client.send();
			client.run();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
