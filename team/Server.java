package team;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Server {

	public static void main(String args[]) {

		int port = 5003;
		try { // 192.168.30.200 5003
			System.out.println("서버 연결중");
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(port);
			Socket socket = serverSocket.accept();

			System.out.println("서버 연결됨");

//			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//			sendinfo(oos);
			while (true) {
			System.out.println("서버 기다리는중");

			Message str = (Message) ois.readObject();
//			play(str);
			if(str.getType().equals("단순안내"))
				System.out.println(str.getMsg());
//			System.out.println(str);

			System.out.println("서버 다기다림");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//	private static void sendinfo(ObjectOutputStream oos) {
//		// TODO Auto-generated method stub
//
//		Message m = new Message("시작", "ㅁㅇㄴ");
//		oos.writeObject(m);
//		oos.flush();
//
//	}

//	private static void play(Message str) {
//		// TODO Auto-generated method stub
//		switch (str.getType()) {
//		case "단순안내":
//			break;
//		case "정보전달":
////			sendinfo();
//			break;
//		}
//	}
	
//
//	private static void sendinfo() {
//		// TODO Auto-generated method stub
//		
//	}

}

@Data
@AllArgsConstructor
class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	String msg;
	
	
}