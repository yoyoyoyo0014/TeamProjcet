package minigame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import program.Program;

@Data
@RequiredArgsConstructor
public class Server implements Program {

	// private static List<ObjectOutputStream> oosList = new ArrayList<>();
	// private static List<ObjectInputStream> oisList = new ArrayList<>();

	// oosList 대체, oos와 userId를 동시에 받음.
	private static List<ConnectedUser> cUserList = new ArrayList<>();
	private static List<User> totalUser = new ArrayList<User>();
	private static List<Room> roomList = new ArrayList<Room>();
	private static int userCount = 0;

	@NonNull
	private Socket socket;

	private ObjectOutputStream oos;
	private ConnectedUser cUser;
	private Room userRoom;

	private void sendUserLogin(Message msg) {
		// Id와 Password 입력하도록 요청한다.
		// 비밀번호가 틀렸거나 이미 접속중인 경우에도
		// message와 함께 재 로그인하도록 요청.
		if (msg == null) {
			msg = new Message();
		}
		msg.setType(Type.login);
		send(oos, msg);

	}

	public void sendAll(Message message) {
		// 나를 제외한 모든 client에게 전송
		for (int i = 0; i < cUserList.size(); i++) {
			ObjectOutputStream tOos = cUserList.get(i).getOos();
			if (tOos == null) {
				return;
			}
			// client 본인이거나 접속은 했으나 로그인을 안했다면 전송하지 않음.
			if (tOos.equals(oos) || cUserList.get(i).getUser().getId() == null) {
				continue;
			}

			try {
				synchronized (oos) {
					tOos.writeObject(message);
					tOos.flush();
				}
			} catch (IOException e) {
				cUserList.remove(cUser);
				// oosList.remove(oos);
				userCount--;
			}
		}

	}

	public void send(ObjectOutputStream oos, String message) {
		if (oos == null) {
			return;
		}

		try {
			synchronized (oos) {
				oos.writeUTF(message);
				oos.flush();
			}
		} catch (IOException e) {
			cUserList.remove(cUser);
			// oosList.remove(oos);
			userCount--;
		}

	}

	public void send(ObjectOutputStream oos, Message message) {
		if (oos == null) {
			return;
		}

		try {
			synchronized (oos) {
				oos.writeObject(message);
				oos.flush();
			}
		} catch (IOException e) {
			cUserList.remove(cUser);
			// oosList.remove(oos);
			userCount--;
		}

	}

	@Override
	public void printMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runMenu(int menu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {

		// 임시로 유저 추가.
		totalUser.add(new User("qwe", "1234"));
		totalUser.add(new User("asd", "1234"));
		totalUser.add(new User("zxc", "1234"));
		totalUser.add(new User("wer", "1234"));

		Thread t = new Thread(() -> {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				cUser = new ConnectedUser(oos);
				cUserList.add(cUser);
				userCount++;

				sendUserLogin(null);
				while (true) {
					ObjectInputStream ois = new ObjectInputStream(
							socket.getInputStream());
					Message msg = (Message) ois.readObject();
					readMessage(msg);
				}
			} catch (IOException e) {
				if (cUserList.size() != 0) {
					for (ConnectedUser tmp : cUserList) {
						String userId = cUser.getUser().getId();
						if (tmp.getUser().getId().equals(userId)) {
							Message msg = new Message();
							msg.setType(Type.alert);
							msg.setMsg("[<" + userId + ">님이 나갔습니다.]");
							System.out.println("[<" + userId + ">님이 나갔습니다.]");
							sendAll(msg);
							cUserList.remove(tmp);
							break;
						}
					}
					cUserList.remove(cUser);
					userCount--;
				} else {
					System.out.println("[클라이언트 접속 종료]");
				}
			} catch (ClassNotFoundException e) {
				// (Message)ois.readObject(); 관련 에러
				e.printStackTrace();
			}

		});
		t.start();

	}

	private void readMessage(Message message) {
//		if(message.getMsg().equals("")) {
//			System.out.println("");
//		}
		String type = message.getType();

		switch (type) {
			case Type.login:
				receiveUserLogin(message.getMsg());
				break;
			case Type.join:
				receiveUserJoin(message.getMsg());
				break;
			case Type.createRoom:
				// opt1에 gameTitle
				if (message.getOpt1() == null) {
					// Error 구현 예정
				}
				String gameTitle = message.getOpt1();
				createRoom(gameTitle, message.getMsg());
				break;
			case Type.roomList:
				// 방 리스트를 client에게 보여주고
				// 방 번호를 선택할 수 있도록 한다.
				// 생성된 방이 없다면 방이없다고 알려주고
				// 이전화면으로 이동, 이전으로 기능도 같이 구현
				if (message.getMsg() == null) {
					sendRoomList();
				} else {
					int roomNum = Integer.parseInt(message.getMsg()) - 1;
					enterRoom(roomNum);
				}
				break;
			case Type.playing:
				runGame(userRoom, message);
			default:

		}

	}

	private void enterRoom(int roomNum) {
		// 생성된 방에 입장합니다.
		// 방에 play가 있다면 꽉찼다고 안내하고 다시 선택하도록 유도.
		// 방에 play가 없다면(=player 정보가 null이라면)
		// room 객체에 player(oos, userId)를 추가한다.

		Room tmpRoom = roomList.get(roomNum);
		Message msg = new Message();
		if (tmpRoom.getPlayer() != null) {
			System.out.println("[인원이 꽉 찼습니다.]");
			msg.setType(Type.roomList);
			msg.setMsg(Type.full);
			send(oos, msg);
		} else { // 방에 인원이 모두 차서 게임을 시작합니다.
			msg.setType(Type.start);
			userRoom = tmpRoom;
			userRoom.setPlayer(cUser);
			runGame(userRoom, msg);
		}

	}

	private void runGame(Room currentRoom, Message message) {
		// TODO Auto-generated method stub

//	게임을 관리하는 메소드 
//	게임 태그는 크게 start, playing, end로 나뉨 
//	start : 처음 게임을 시작할 때 안내 멘트와 유저 인터페이스를 제공 
//	playing : client의 입력 값을 토대로 result를 구하여 client에게 반환 
//	결과와 다음 차례를 넣어서 보냄 
//	end : 게임이 끝난 경우로 승자를 안내하고, 데이터를 기록한다. 
//	end는 server에서 client로 보낼 때 사용하는 태그라 server 받을 일은 없을 듯

		Message msg = new Message();
		String currTurn; // 현재 차례
		switch (message.getType()) {
			case Type.start:
				currentRoom.gameInit();
				msg = currentRoom.gameRun(message);
				currTurn = currentRoom.getCurrentTurn();
				msg.setType(Type.start);
				msg.setOpt1(currTurn);

				break;
			case Type.playing:

				msg = currentRoom.gameRun(message);

				if (currentRoom.getBaseball().getWinner() == null) {

					currTurn = currentRoom.getCurrentTurn();

					msg.setType(Type.playing);
					msg.setOpt1(currTurn);

				} else {
					// 승자가 정해짐
					// 승패 기록
					// 방 폭파시키기
					String gameTitle = currentRoom.getGameTitle();
					String winner = currentRoom.getBaseball().getWinner();
					String loser = currentRoom.getBaseball().getLoser();
					msg.setType(Type.end);
					msg.setMsg(winner);

					recordScore(gameTitle, winner, loser);
					roomList.remove(userRoom);
					userRoom = null;
				}

				break;

		}

		send(currentRoom.getPlayer().getOos(), msg);
		send(currentRoom.getRoomManager().getOos(), msg);
	}

	public User getUserInfo(String userId) {
		for (User tUser : totalUser) {
			if (tUser.getId().equals(userId)) {
				return tUser;
			}
		}
		return null;
	}

	private Game getUserGameInfo(User user, String gameTitle) {
		// user의 게임 성적 정보를 받아온다.

		Game tGame = new Game(gameTitle);

		boolean isExist = false;
		for (Game tmp : user.getGames()) {
			// 이전에 했던 게임이라면 객체를 반환
			if (tmp.getName().equals(gameTitle)) {
				isExist = true;
				return tmp;
			}
		}

		// 처음한 게임이라면 게임의 객체를 생성하고 반환한다.
		if (!isExist) {
			user.getGames().add(tGame);
			return user.getGames().get(user.getGames().size());
		}
		return null;
	}

	private void recordScore(String gameTitle, String winner, String loser) {
		// 게임의 결과를 user의 game 객체에 update한다.
		// 승자와 패자의 객체를 가져와서 승패를 업데이트한다.

		User tmpUser = getUserInfo(winner);
		Game gameTmp = getUserGameInfo(tmpUser, gameTitle);
		gameTmp.updateWin();

		tmpUser = getUserInfo(loser);
		gameTmp = getUserGameInfo(tmpUser, gameTitle);
		gameTmp.updateLose();

	}

	private void sendRoomList() {
		// 유저가 방 참여를 요청했을 때
		// 저장된 방 목록을 메세지에 담아서 보내준다.
		// 저장된 방이 없다면 Type만 보낸다.
		Message message = new Message();
		String msg;
		if (roomList.size() != 0) {
			msg = "";
			for (int i = 0; i < roomList.size(); i++) {
				msg += i + 1 + ". [" + roomList.get(i).getGameTitle() + "][" + roomList.get(i) + "]\n";
			}

			message.setMsg(msg);
		}
		message.setType(Type.roomList);

		send(oos, message);

	}

	private void createRoom(String gameTitle, String roomTitle) {
		// 방 생성하기
		for (ConnectedUser tmp : cUserList) {
			if (oos.equals(tmp.getOos())) {
				userRoom = new Room(tmp, gameTitle, roomTitle);
				roomList.add(userRoom);
				System.out.println(roomList);
				System.out.println();
				break;
			}
		}

	}

	private void receiveUserJoin(String message) {
		// message는 id + " " + password로 형태
		// id가 totalUser에 있는 지 확인 후 없다면 id 등록
		// 이미 있는 id라면 재로그인
		String[] tmpStr = message.split(" ");
		String id = tmpStr[0];
		String password = tmpStr[1];
		userJoin(id, password);

	}

	private void userJoin(String id, String password) {
		// user 회원가입
		Message msg = new Message();
		User joinUser = new User(id, password);
		if (totalUser.contains(joinUser)) {
			msg.setMsg("이미 등록된 아이디입니다.");
			sendUserLogin(msg);
			return;
		}

		totalUser.add(joinUser);
		msg.setMsg("회원가입 되었습니다. 로그인하세요.");
		sendUserLogin(msg);
	}

	private void receiveUserLogin(String message) {
		// message는 id + " " + password로 형태
		// 서버는 list(db)에서 로그인 정보를 확인
		// id가 없다면 새로 등록.
		// id가 있는데 password가 다르다면 로그인 실패. <종료>
		String[] tmp = message.split(" ");
		String id = tmp[0];
		String password = tmp[1];
		userLogin(id, password);

	}

	private void userLogin(String id, String password) {

		// totalUser에서 아이디와 비밀번호가 일치한 지
		// 현재 접속한 유저인 지
		Message msg = new Message();

		if (totalUser.size() == 0) {
			msg.setMsg("등록된 유저가 없습니다.");
			sendUserLogin(msg);
			return;

		}
		User loginUser = getUser(id, password);

		for (ConnectedUser tmp : cUserList) {
			if (tmp.getUser() != null && tmp.getUser().equals(loginUser)) {
				msg.setMsg("이미 접속한 유저입니다.");
				sendUserLogin(msg);
				return;
			}
		}

		if (loginUser == null) {
			msg.setMsg("아이디 혹은 비밀번호가 일치하지 않습니다.");
			sendUserLogin(msg);
			return;
			// 재로그인 요구.

		} else {
			// loginedUser 객체에 추가
			// 게임을 선택할 수 있도록 메뉴판 제공
			// "<로그인 성공>";
			msg.setMsg("<로그인 성공>\n");
			cUser.setUser(loginUser);
			msg.setType(Type.menu);
			send(oos, msg);
			Message welcomeMsg = new Message();
			welcomeMsg.setMsg("[<" + id + ">님이 접속하셨습니다.]");
			welcomeMsg.setType(Type.alert);
			sendAll(welcomeMsg);
		}

	}

	private User getUser(String id, String password) {
		// 입력받은 id와 password로
		// id가 일치할 때 password도 일치한다면 해당하는 객체 반환
		// 없다면 null 반환, id가 일치하지만 비밀번호가 다르다면 null 반환
		for (User tmp : totalUser) {
			if (tmp.getId().equals(id) && tmp.isValidPassword(password)) {
				return tmp;
			}
		}
		return null;
	}

	@Override
	public void save(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void load(String fileName) {
		// TODO Auto-generated method stub

	}

}
