package 윤호.test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerEx05 {
	public static List<Room> roomList =new ArrayList<Room>();
	public static void main(String[] args) {
		int port = 5001;
		System.out.println("서버 실행");
		List<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();
		try(ServerSocket serverSocket = 
				new ServerSocket(port)){
			while(true) {
				Socket socket = serverSocket.accept();
				System.out.println("유저 입장");
				Server server = new Server(list, socket);
				server.receive();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}