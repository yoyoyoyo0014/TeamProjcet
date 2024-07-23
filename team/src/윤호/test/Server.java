package 윤호.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


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
					id = ois.readUTF();
					String chat = ois.readUTF();
					int roomNum = ois.readInt();
					
					User user = new User(id,oos);
					
					boolean makeRoom = false;  //유저가 남는 방이 없어서 방을 만들었으면 true
					//어떠한 방에도 들어가지 않을 때
					if(roomNum==-1) {
						//중복 아이디가 있을 때
						if(idList.contains(id)) 
							id=null;
						//방 들어가기
						else {
							boolean getRoom = false;
							for(int i=0;i<ServerEx05.roomList.size();i++) {
								if(ServerEx05.roomList.get(i).InsertRoom(user)) {
									getRoom =true;
									roomNum=i;
									break;
								}
							}
							//모든 방이 꽉찼을 시 방만듬
							if(!getRoom) {
								ServerEx05.roomList.add(new Room());
								ServerEx05.roomList.get(ServerEx05.roomList.size()-1).InsertRoom(user);
								System.out.println(ServerEx05.roomList.size()-1+ " 번 방 생성");
								roomNum = ServerEx05.roomList.size()-1;
								makeRoom = true;
							}
							//방 입장시 모든 방안에 있는 유저에게 알림
							
							for(User tmp :ServerEx05.roomList.get(roomNum).userList) {
								//해당 유저가 아닌 방에 있는 유저
								if(tmp.oos != oos) {
									//유저가 들어 왔을 때 인원이 2명이면 방안에 있는 유저에게도 문제 나감
									if(ServerEx05.roomList.get(roomNum).userList.size()==2) 
										send(tmp.oos,tmp.id,id+" 님이 입장하였습니다. 현재 인원 : "+
												ServerEx05.roomList.get(roomNum).userList.size()
												+"\n 문제 : "+ServerEx05.roomList.get(roomNum).GetCurrentQuizProblem(),roomNum);
									
									else if(!tmp.id.equals(id)) 
										send(tmp.oos,tmp.id,id+" 님이 입장하였습니다. 현재 인원 : "+
												ServerEx05.roomList.get(roomNum).userList.size(),roomNum);
								}
								
							}
						}
						//방 입장시 해당 유저에게 문제 알려줌
						if(makeRoom)
							send(oos,id,roomNum+"번 방에 입장하였습니다. 현재 인원 : 1명 다른 유저가 올 때까지 기다려주세요. ",roomNum);
						
						else send(oos,id,roomNum+"번 방에 입장하였습니다. 현재 인원 : "
								+ ServerEx05.roomList.get(roomNum).userList.size()+""
										+ "\n 문제 : "+ServerEx05.roomList.get(roomNum).GetCurrentQuizProblem(),roomNum);
					}
					//방에 입장인 상태에서 보낸 상태면 채팅으로 간주
					else if(roomNum !=-1){
						boolean bingo = false;
						
						if(ServerEx05.roomList.get(roomNum).GetCurrentQuizAnser(chat)) {
							bingo = true;
							send(oos,id,"정답입니다!! 다음 문제는\n"
									+ ServerEx05.roomList.get(roomNum).GetCurrentQuizProblem(),roomNum);
						}
						if(!bingo) {
							for(User tmp :ServerEx05.roomList.get(roomNum).userList) {
								if(!tmp.id.equals(id)) 
									send(tmp.oos,tmp.id,id+" : "+chat,roomNum);
							}
						}
						else {
							for(User tmp :ServerEx05.roomList.get(roomNum).userList) {
								if(!tmp.id.equals(id)) {
									send(tmp.oos,tmp.id,id+" 님이 정답을 맞췄습니다. 정답은 "+chat+" !! 다음 문제는 \n"
											+ ServerEx05.roomList.get(roomNum).GetCurrentQuizProblem(),roomNum);
								}
									
							}
						}
					}
					
					
					/*System.out.println(id + " : " + chat);
					for(ObjectOutputStream tmp : list) {
						//메세지를 보낸 소켓을 제외한 다른 소켓에 메세지를 전송
						if(tmp != oos) {
							send(tmp, id, chat,roomNum);
						}
					}*/
					
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
	
	public void send(ObjectOutputStream oos, String id, String message,int roomNum) {
		if(oos == null) {
			return;
		}
		
		try {
			synchronized (oos) {
				oos.writeUTF(id);
				oos.writeUTF(message);
				oos.writeInt(roomNum);
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
