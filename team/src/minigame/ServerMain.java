package minigame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int port = 5001;
		try {
			// 서버용 소켓 객체 생성
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("[연결 대기중]");
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("[클라이언트 연결 성공]");
				Server server = new Server(socket);
				server.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("예외발생");
		}

	}

}
