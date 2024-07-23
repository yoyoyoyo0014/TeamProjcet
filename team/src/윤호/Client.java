package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

//연결 소켓을 이용하여 데이터를 주고 받는(Scanner를 통해) 클래스

public class Client {
	
	
	private String id;
	private Socket socket;
	private int roomNumber=-1;
	public final static String EXIT = "-1";
	//소켓에서 보내온 문자열을 받아서 출력하는 쓰레드를 생성하고 실행하는 메소드
	public void recive() {
		Thread t= new Thread(()->{
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				while(true) {
					id = ois.readUTF();
					String chat = ois.readUTF();
					int room_number = ois.readInt();
					if(chat.equals(EXIT)) {
						break;
					}
					//중복 아이디가 있을 때 null값으로 받음
					if(id==null) {
						System.out.print("중복된 아이디입니다 다시 입력 : ");
						Scanner scan = new Scanner(System.in);
						id = scan.next();
					}
					//새로운 방 입장 시
					else if(roomNumber==-1&&room_number != -1) 
						roomNumber = room_number;
					
					System.out.println(chat);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		});
		t.start();
	}
	
	//문자열을 입력해서 소켓으로 전송하는 쓰레드를 생성하고 실행하는 메소드
	public void send() {
		Thread t = new Thread(()->{
			try {
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				Scanner scan = new Scanner(System.in);
				while(true) {
					String str = scan.nextLine();
					oos.writeUTF(id);
					oos.writeUTF(str);
					oos.writeInt(roomNumber);
					oos.flush();
					if(str.equals(EXIT)) {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		t.start();
	}

	public Client(String id, Socket socket) {
		this.id = id;
		this.socket = socket;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(id, other.id);
	}
}