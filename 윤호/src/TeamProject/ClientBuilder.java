package TeamProject;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public abstract class ClientBuilder<T>  extends StreamThread<T> implements IGameBuilder{


	String ip = "222.107.4.245";
	Socket socket;
	private List<T> ipsList = new ArrayList<T>();//소켓으로 받는 Object값 저장

	/*** 소켓으로 보내는 메소드
	 * @param obj 소켓으로 보낼 Object
	 */

	public void GameClientStart() {
		System.out.println("클라이언트 실행");
		Thread t = new Thread(()->{
			try {
				socket = new Socket(ip,MainServer.port);
				GameStart();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();}
		});
		t.start();
	}


	public void ClientOutStreamThread(T obj) {
		try {
			Socket socket = new Socket(ip,MainServer.port);
			OutStreamThread(obj,socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/*** 소켓으로 받는 메소드
	 */
	public T ClientInputStreamThread() {
		try {
			Socket socket = new Socket(ip,MainServer.port);
			 ipsList = InputStreamThread(socket, ipsList);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ipsList.get(ipsList.size()-1);
	}

}
