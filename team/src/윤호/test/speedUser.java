package 윤호.test;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class speedUser implements Serializable{
	public String id ="";
	public String chat = "";
	public int roomNum =0;
	public ObjectOutputStream oos;
	
	public speedUser(String id, String chat, int rooNum, ObjectOutputStream oos) {
		this.id = id;
		this.chat = chat;
		this.roomNum = rooNum;
		this.oos = oos;
	}
	
	public int getRoomNum() {
		return  roomNum;
	}
}
