package TeamProject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public abstract class ServerBuilder<T> extends StreamThread <T>implements IGameBuilder {
	private ServerSocket serverSocket;//서버 소켓. GameServerStart에서 할당
	
	
	public List<User> userList = new ArrayList<User>();
	protected List<T> ipsList = new ArrayList<T>();//소켓으로 받는 Object값 저장
	
	
	
	
	//게임 실행 시키는 메소드
	public void GameServerStart() {
		System.out.println("서버 실행");
		Thread t = new Thread(()->{
			try {
				serverSocket = new ServerSocket(MainServer.port);
				GameStart();
			} catch (IOException e) {
				e.printStackTrace();
			}});
		t.start();
	}

	/***소켓으 보내는 함수
	 * @param 소켓으로 보낼 변수
	 */
	protected void ServerOutStreamThread(T obj) {
		try {
			Socket socket = serverSocket.accept();
			OutStreamThread(obj,socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/***소켓으로 받는 함수
	 */
	protected T ServerInputStreamThread() {
		try {
			Socket socket = serverSocket.accept();
			ipsList = InputStreamThread(socket, ipsList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ipsList.get(ipsList.size()-1);
	}
	
}
