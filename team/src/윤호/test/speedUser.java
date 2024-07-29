package 윤호.test;

public class speedUser {
	public String id ="";
	public String chat = "";
	public int roomNum =0;
	
	public speedUser(String id, String chat, int rooNum) {
		this.id = id;
		this.chat = chat;
		this.roomNum = rooNum;
	}
	
	public int getRoomNum() {
		return  roomNum;
	}
}
