package 윤호.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import minigame.Message;


public class Server {
	
	private List<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();
	private List<String> idList = new ArrayList<String>();
	
	private Socket socket;
	public void receive() {
		Thread t = new Thread(()->{
			String id = "";
			try {
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				list.add(oos);
				while(true) {
					//id = ois.readUTF();
					//String chat = ois.readUTF();
					//int roomNum = ois.readInt();
					
					Message msg = new Message();
					msg.spduser = new speedUser(ois.readUTF(), ois.readUTF(),ois.readInt());
					User user = new User(id,oos);
					
					boolean makeRoom = false;  //유저가 남는 방이 없어서 방을 만들었으면 true
					//어떠한 방에도 들어가지 않을 때
					if(msg.spduser.getRoomNum()==-1) {
						//중복 아이디가 있을 때
						if(idList.contains(id)) 
							id=null;
						//방 들어가기
						else {
							boolean getRoom = false;
							for(int i=0;i<ServerEx05.roomList.size();i++) {
								if(ServerEx05.roomList.get(i).InsertRoom(user)) {
									getRoom =true;
									msg.spduser.roomNum=i;
									break;
								}
							}
							//모든 방이 꽉찼을 시 방만듬
							if(!getRoom) {
								ServerEx05.roomList.add(new Room());
								ServerEx05.roomList.get(ServerEx05.roomList.size()-1).InsertRoom(user);
								System.out.println(ServerEx05.roomList.size()-1+ " 번 방 생성");
								msg.spduser.roomNum = ServerEx05.roomList.size()-1;
								makeRoom = true;
							}
							//방 입장시 모든 방안에 있는 유저에게 알림
							
							for(User tmp :ServerEx05.roomList.get(msg.spduser.roomNum).userList) {
								//해당 유저가 아닌 방에 있는 유저
								if(tmp.oos != oos) {
									//유저가 들어 왔을 때 인원이 2명이면 방안에 있는 유저에게도 문제 나감
									if(ServerEx05.roomList.get(msg.spduser.roomNum).userList.size()==2) {
										msg.spduser.id = tmp.id;
										msg.spduser.chat = id+" 님이 입장하였습니다. 현재 인원 : "+
												ServerEx05.roomList.get(msg.spduser.roomNum).userList.size()
												+"\n 문제 : "+ServerEx05.roomList.get(msg.spduser.roomNum).GetCurrentQuizProblem();
										msg.spduser.roomNum = msg.spduser.roomNum;
										send(tmp.oos,msg);
									}
										
									
									else if(!tmp.id.equals(id)) {
										msg.spduser.id = tmp.id;
										msg.spduser.chat = id+" 님이 입장하였습니다. 현재 인원 : "+
												ServerEx05.roomList.get(msg.spduser.roomNum).userList.size();
										msg.spduser.roomNum = msg.spduser.roomNum;
										send(tmp.oos,msg);
									}
										
								}
								
							}
						}
						//방 입장시 해당 유저에게 문제 알려줌
						if(makeRoom) {
							msg.spduser.id=id;
							msg.spduser.chat = msg.spduser.roomNum+"번 방에 입장하였습니다. 현재 인원 : 1명 다른 유저가 올 때까지 기다려주세요. ";
							msg.spduser.roomNum = msg.spduser.roomNum;
							send(oos,msg);
						}
							
						
						else {
							msg.spduser.roomNum = msg.spduser.roomNum;
							msg.spduser.id = id;
							msg.spduser.chat = msg.spduser.roomNum+"번 방에 입장하였습니다. 현재 인원 : "
									+ ServerEx05.roomList.get(msg.spduser.roomNum).userList.size()+""
									+ "\n 문제 : "+ServerEx05.roomList.get(msg.spduser.roomNum).GetCurrentQuizProblem();
							send(oos,msg);
						}
					}
					//방에 입장인 상태에서 보낸 상태면 채팅으로 간주
					else if(msg.spduser.roomNum !=-1){
						boolean bingo = false;
						
						if(ServerEx05.roomList.get(msg.spduser.roomNum).GetCurrentQuizAnser(msg.spduser.chat)) {
							bingo = true;
							
							msg.spduser.id = id;
							msg.spduser.chat ="정답입니다!! 다음 문제는\n"
									+ ServerEx05.roomList.get(msg.spduser.roomNum).GetCurrentQuizProblem();
							msg.spduser.roomNum = msg.spduser.roomNum;
							send(oos,msg);
						}
						if(!bingo) {
							for(User tmp :ServerEx05.roomList.get(msg.spduser.roomNum).userList) {
								if(!tmp.id.equals(id)) {
									msg.spduser.id = tmp.id;
									msg.spduser.chat = id+" : "+msg.spduser.chat;
									msg.spduser.roomNum = msg.spduser.roomNum;
									send(tmp.oos,msg);
								}
									
							}
						}
						else {
							for(User tmp :ServerEx05.roomList.get(msg.spduser.roomNum).userList) {
								if(!tmp.id.equals(id)) {
									msg.spduser.id = tmp.id;
									msg.spduser.chat = id+" 님이 정답을 맞췄습니다. 정답은 "+msg.spduser.chat+" !! 다음 문제는 \n"
											+ ServerEx05.roomList.get(msg.spduser.roomNum).GetCurrentQuizProblem();
									msg.spduser.roomNum = msg.spduser.roomNum;
									send(tmp.oos,msg);
								}
									
							}
						}
					}
					
				}
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println("["+id +"님이 나갔습니다.]");
			} catch(Exception e) {
				System.out.println("[예외 발생]");
			}
		});
		t.start();
	}
	
	public void send(ObjectOutputStream oos, Message msg) {
		if(oos == null) {
			return;
		}
		
		try {
			synchronized (oos) {
				oos.writeObject(msg);
//				oos.writeUTF(msg.spduser.id);
//				oos.writeUTF(msg.spduser.chat);
//				oos.writeInt(msg.spduser.roomNum);
				oos.flush();
			}
		} catch (IOException e) {
			list.remove(oos);
		}
	
	}
	public Server(List<ObjectOutputStream> list, Socket socket) {
		super();
		this.list = list;
		this.socket = socket;
	}
}
