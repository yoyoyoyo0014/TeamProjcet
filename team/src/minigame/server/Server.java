package minigame.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import minigame.db.controller.GameController;
import minigame.db.controller.ScoreController;
import minigame.db.controller.UserController;
import minigame.utils.ConnectedUser;
import minigame.utils.Message;
import minigame.utils.Room;
import minigame.utils.Type;
import minigame.utils.User;

@Data
@RequiredArgsConstructor
public class Server {

	// private static List<ObjectOutputStream> oosList = new ArrayList<>();
	// private static List<ObjectInputStream> oisList = new ArrayList<>();

	private UserController userController = new UserController();
	private ScoreController scoreController = new ScoreController();
	private GameController gameController = new GameController();

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
			if (tOos.equals(oos) || cUserList.get(i).getUser() == null) {
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

	public void run() {

//		임시로 유저 추가.
//		totalUser.add(new User("qwe", "1234"));
//		totalUser.add(new User("asd", "1234"));
//		totalUser.add(new User("zxc", "1234"));
//		totalUser.add(new User("wer", "1234"));

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
				playerExit();
			} catch (ClassNotFoundException e) {
				// (Message)ois.readObject(); 관련 에러
				e.printStackTrace();
			}

		});
		t.start();

	}

	private void playerExit() {

		if (cUserList.size() != 0) {
			// 접속은 했지만 로그인은 안한 상태에서 종료
			if (cUser.getUser() == null) {
				System.out.println("[클라이언트 접속 종료]");
				cUserList.remove(cUser);
				cUser.setUser(null);
				userCount--;
				return;
			}
			for (ConnectedUser tmp : cUserList) {
				String userId = cUser.getUser().getId();
				if (tmp.getUser().getId().equals(userId)) {
					Message msg = new Message();
					msg.setType(Type.alert);
					msg.setMsg("[<" + userId + ">님이 나갔습니다.]");
					System.out.println("[<" + userId + ">님이 나갔습니다.]");

					sendAll(msg);

					if (userRoom != null) {
						if (userRoom.getIsPlaying() == Type.playing) {

							userRoom.setIsPlaying(Type.end);

							String p1 = userRoom.getRoomManager().getUser().getId();
							String p2 = userRoom.getPlayer().getUser().getId();
							String winner, loser;
							String gameTitle = userRoom.getGameTitle();

							if (p1.equals(userId)) {
								loser = p1;
								winner = p2;
							} else {
								loser = p2;
								winner = p1;
							}

							recordScore(gameTitle, winner, loser, false);

							msg = new Message();

							msg.setMsg("[<" + userId + "> 님이 나가서 게임이 종료 됩니다.]");
							msg.setType(Type.end);
							msg.setOpt1("exit");

							send(userRoom.getPlayer().getOos(), msg);
							send(userRoom.getRoomManager().getOos(), msg);

						}
						roomList.remove(userRoom);
						userRoom = null;

						cUserList.remove(cUser);
						userCount--;
					}

					cUser.setUser(null);

					break;
				}
			}

//			// userList에서 접속한 사용자 제거
//			cUserList.remove(cUser);
//			userCount--;
		}

	}

	private void readMessage(Message message) {
		// if(message.getMsg().equals("")) {
		// System.out.println("");
		// }
		String type = message.getType();

		switch (type) {
			case Type.login:
				receiveUserLogin(message.getMsg());
				break;
			case Type.join:
				receiveUserJoin(message.getMsg());
				break;
			case Type.reset:
				findPassword(message.getMsg());
				break;
			case Type.update_pwd:
				updatePwd(message.getMsg());
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
			case Type.rank:
				Message msg = null;
				// opt1
				// personal : 개인전적
				// topplayer : 상위 플레이어 3명 성적 출력
				if (message.getOpt1().equals(Type.personal)) {
					msg = scoreController.getScore(message.getPName());
					msg.setType(Type.personal);
				} else if (message.getOpt1().equals(Type.topPlayer)) {
					msg = scoreController.getTopScore();
					msg.setType(Type.topPlayer);
				}

				send(oos, msg);

				break;
			case Type.playing:

				if (!roomList.contains(userRoom)) {
					if (userRoom != null) {
						userRoom = null;
					}
					return;
				}

				runGame(userRoom, message);

				break;
			case Type.exit:
				playerExit();
				break;
			default:

		}

	}

	private void updatePwd(String msg) {

		// 디비에 아이디와 비밀번호가 일치하는 사용자의 비밀번호를
		// 새로운 비밀번호로 변경.

		String[] tmp = msg.split(" ");
		String pwd = tmp[0];
		String newPwd = tmp[1];

		String id = cUser.getUser().getId();

		Message message = new Message();
		message.setType(Type.update_pwd);

		if (userController.updatePassword(id, pwd, newPwd)) {
			message.setOpt1(Type.success);
		} else {
			message.setOpt1(Type.fail);
		}

		send(oos, message);

	}

	private void findPassword(String message) {

		String[] tmp = message.split(" ");
		String id = tmp[0];
		String email = tmp[1];

		// id, email을 DB로 보냄.
		// DB에서 일치하는 정보를 확인하여 비밀번호를 가져옴

		String pwd = userController.findPassword(id, email);

		Message msg = new Message();
		msg.setType(Type.reset);

		if (pwd == null) {
			msg.setOpt1(Type.fail);
		} else {
			msg.setMsg(pwd);
			msg.setOpt1(Type.success);
		}

		send(oos, msg);

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
			// msg.setType(Type.playing);
			userRoom = tmpRoom;
			userRoom.setPlayer(cUser);
			runGame(userRoom, msg);
		}

	}

	private void runGame(Room currentRoom, Message message) {
		// TODO Auto-generated method stub

		// 게임을 관리하는 메소드
		// 게임 태그는 크게 start, playing, end로 나뉨
		// start : 처음 게임을 시작할 때 안내 멘트와 유저 인터페이스를 제공
		// playing : client의 입력 값을 토대로 result를 구하여 client에게 반환
		// 결과와 다음 차례를 넣어서 보냄
		// end : 게임이 끝난 경우로 승자를 안내하고, 데이터를 기록한다.
		// end는 server에서 client로 보낼 때 사용하는 태그라 server 받을 일은 없을 듯

		Message msg = new Message();
		String currTurn = null; // 현재 차례
		switch (message.getType()) {
			case Type.start:
				currentRoom.gameInit();
				msg = currentRoom.gameRun(message);
				currTurn = currentRoom.getCurrentTurn();
				msg.setType(Type.playing);
				msg.setOpt1(currTurn);
				send(currentRoom.getPlayer().getOos(), msg);
				send(currentRoom.getRoomManager().getOos(), msg);
				break;
			case Type.playing:
				if (!userRoom.getIsPlaying().equals("end")) {
					msg = currentRoom.gameRun(message);
				}

				if (currentRoom.getWinner() == null) {

					currTurn = currentRoom.getCurrentTurn();

					msg.setType(Type.playing);
					msg.setOpt1(currTurn);
					if (currTurn.equals(Type.allTurn) && !msg.isEnd()) {
						send(oos, msg);
					} else {
						send(currentRoom.getPlayer().getOos(), msg);
						send(currentRoom.getRoomManager().getOos(), msg);
					}
				} else {
					// 승자가 정해짐
					// 승패 기록
					// 방 폭파시키기

					// [DB 등록 필요]
					String gameTitle;
					String winner;
					String loser;

					gameTitle = currentRoom.getGameTitle();
					winner = currentRoom.getWinner();
					loser = currentRoom.getLoser();

					if (currentRoom.isDraw()) {
						recordScore(gameTitle, winner, loser, true);
						msg.setOpt1(Type.drawEnd);
					} else {
						recordScore(gameTitle, winner, loser, false);
						msg.setOpt1(winner);
					}

					if (!currentRoom.getIsPlaying().equals("end")) {
						msg.setType(Type.end);
						send(currentRoom.getPlayer().getOos(), msg);
						send(currentRoom.getRoomManager().getOos(), msg);
						currentRoom.setIsPlaying("end");
						roomList.remove(userRoom);
					}

					return;
				}

				break;

		}

	}

	public User getUserInfo(String userId) {
		for (User tUser : totalUser) {
			if (tUser.getId().equals(userId)) {
				return tUser;
			}
		}
		return null;
	}

	private void recordScore(String gameTitle, String winner, String loser, boolean isDraw) {
		// 게임의 결과를 user의 game 객체에 update한다.
		// 승자와 패자의 객체를 가져와서 승패를 업데이트한다.

		int win, lose;
		if (isDraw) {
			win = lose = 0;
		} else {
			win = 1;
			lose = 2;
		}

		if (scoreController.updateScore(gameTitle, winner, win)) {
			System.out.println("승자 등록 성공");
		}

		if (scoreController.updateScore(gameTitle, loser, lose)) {
			System.out.println("패자 등록 성공");
		}

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
				msg += (i + 1) + ". " + roomList.get(i).toString() + "\n";
			}

			message.setMsg(msg);
		}
		message.setOpt1("" + roomList.size());
		message.setType(Type.roomList);

		send(oos, message);

	}

	private void createRoom(String gameTitle, String roomTitle) {
		// 방 생성하기
		for (ConnectedUser tmp : cUserList) {
			if (oos.equals(tmp.getOos())) {
				userRoom = new Room(tmp, gameTitle, roomTitle);
				userRoom.setRoomManager(cUser);
				roomList.add(userRoom);
				System.out.println("방 생성:" + userRoom);
				break;
			}
		}

	}

	private void receiveUserJoin(String message) {
		// message는 id + " " + password로 형태
		// id가 totalUser에 있는 지 확인 후 없다면 id 등록
		// 이미 있는 id라면 재로그인
		String[] tmpStr = message.split(" ");
		if (tmpStr[0] != null && tmpStr[1] != null && tmpStr[2] != null) {
			String id = tmpStr[0];
			String password = tmpStr[1];
			String email = tmpStr[2];
			userJoin(id, password, email);
		} else {
			userJoin(null, null, null);
		}

	}

	private void userJoin(String id, String password, String email) {
		// user 회원가입

		// ****디비 아이디 넣고 없으면
		// 아이디랑 비밀번호 넣어서 회원가입.

		Message msg = new Message();

		if (id == null || password == null || email == null) {
			msg.setMsg("잘 못 입력하셨습니다.");
			sendUserLogin(msg);
			return;
		}

		if (userController.join(id, password, email)) {
			msg.setMsg("회원가입 되었습니다. 로그인하세요.");
		} else {
			msg.setMsg("회원가입에 실패하였습니다.");
		}
		sendUserLogin(msg);
	}

	private void receiveUserLogin(String message) {
		// message는 id + " " + password로 형태
		
		String[] tmp = message.split(" ");
		String id = tmp[0];
		String password = tmp[1];
		userLogin(id, password);

	}

	private void userLogin(String id, String password) {

		Message msg = new Message();

		// ****디비에 id랑 password를 넘겨주고 로그인
		if (!userController.login(id, password)) {
			msg.setMsg("아이디 혹은 비밀번호가 일치하지 않습니다.");
			// 재로그인 요구.
			sendUserLogin(msg);
			return;

		}

		User loginUser = new User(id, password);
		for (ConnectedUser tmp : cUserList) {
			if (tmp.getUser() != null && tmp.getUser().equals(loginUser)) {
				msg.setMsg("이미 접속한 유저입니다.");
				// 재로그인 요구.
				sendUserLogin(msg);
				return;
			}
		}

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
