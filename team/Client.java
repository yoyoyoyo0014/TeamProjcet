package team;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ip = "192.168.30.200";
		int port = 5003;
		List<String> list = Arrays.asList("21", "100", "23", "-1");
		Message m = new Message("단순알내","게임을시작합니다.");
		try {

			Socket socket = new Socket(ip, port);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
//			for (String tmp : list) {
//				oos.writeUTF("환영인사");
//				//"21", "100", "23", "-1"
//			}
			oos.writeObject(m);
			oos.flush();
			//"21"

			System.out.println("[전송 완료]");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

@Data
@AllArgsConstructor
class Message2 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	String msg;
}
