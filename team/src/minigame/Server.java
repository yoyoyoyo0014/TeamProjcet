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
	private static List<User> loginedUser = new ArrayList<User>();
	private static List<User> totalUser = new ArrayList<User>();
	private static List<Room> roomList = new ArrayList<Room>();
	private static int userCount = 0;

	@NonNull
	private Socket socket;

	private ObjectOutputStream oos;
	private ConnectedUser cUser;
	private Room userRoom;

	private void sendUserLogin(String message) {
		// Id와 Password 입력하도록 요청한다.
		// 비밀번호가 틀렸거나 이미 접속중인 경우에도
		// message와 함께 재 로그인하도록 요청.

		message = Tag.login + Tag.split + message;
		send(oos, message);

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

		totalUser.add(new User("qwe", "1234"));
		totalUser.add(new User("asd", "1234"));

		Thread t = new Thread(() -> {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				cUser = new ConnectedUser(oos);
				cUserList.add(cUser);
				userCount++;

				sendUserLogin("");
				while (true) {
					ObjectInputStream ois = new ObjectInputStream(
							socket.getInputStream());
					String msg = ois.readUTF();
					readMessage(msg);
				}
			} catch (IOException e) {
				if (loginedUser.size() != 0) {
					for (User tmp : loginedUser) {
						if (tmp.getId().equals(cUser.getUserId())) {
							System.out.println("[" + cUser.getUserId() + "]님이 나갔습니다.");
							loginedUser.remove(tmp);
							break;
						}
					}
					cUserList.remove(cUser);
					userCount--;
				} else {
					System.out.println("[클라이언트 접속 종료]");
				}
			}

		});
		t.start();

	}

	private void readMessage(String message) {

		String[] tmpMsg = message.split(Tag.split);
		String tag = tmpMsg[0];
		String msg = "";
		// tag랑 msg를 같이 보냈다면
		if (tmpMsg.length == 2) {
			msg = tmpMsg[1];
		}

		switch (tag) {
			case Tag.login:
				receiveUserLogin(msg);
				break;
			case Tag.join:
				receiveUserJoin(msg);
				break;
			case Tag.createRoom:
				if (tmpMsg.length != 3) {
					// Tag::gameName::message 형태가 아니라면
					// error
				}
				String gameTitle = tmpMsg[1];
				msg = tmpMsg[2];
				createRoom(gameTitle, msg);
				break;
			case Tag.roomList:
				// 방 리스트를 client에게 보여주고
				// 방 번호를 선택할 수 있도록 한다.
				// 생성된 방이 없다면 방이없다고 알려주고
				// 이전화면으로 이동, 이전으로 기능도 같이 구현
				if (msg == "") {
					sendRoomList();
				} else {
					int roomNum = Integer.parseInt(msg) - 1;
					enterRoom(roomNum);
				}
				break;
			case Tag.playing:
				runGame(userRoom, tag, msg);
			default:

		}

	}

	private void enterRoom(int roomNum) {
		// 생성된 방에 입장합니다.
		// 방에 play가 있다면 꽉찼다고 안내하고 다시 선택하도록 유도.
		// 방에 play가 없다면(=player 정보가 null이라면)
		// room 객체에 player(oos, userId)를 추가한다.

		Room tmpRoom = roomList.get(roomNum);
		String msg = "";
		if (tmpRoom.getPlayer() != null) {
			System.out.println("[인원이 꽉 찼습니다.]");
			msg = Tag.roomList + Tag.split + Tag.full;
			send(oos, msg);
		} else { // 방에 인원이 모두 차서 게임을 시작합니다.
			userRoom = tmpRoom;
			userRoom.setPlayer(cUser);
			// tmpRoom.setPlayer(cUser);
			runGame(userRoom, Tag.start, "");
		}

	}

	private void runGame(Room currentRoom, String tag, String message) {

		/*
		 * 게임을 관리하는 메소드 게임 태그는 크게 start, playing, end로 나뉨 start : 처음 게임을 시작할 때 안내 멘트와 유저
		 * 인터페이스를 제공 playing : client의 입력 값을 토대로 result를 구하여 client에게 반환 result에는 입력한 값의
		 * 결과와 다음 차례를 넣어서 보냄 end : 게임이 끝난 경우로 승자를 안내하고, 데이터를 기록한다. end는 server에 client에
		 * 보낼 때 사용하는 태그라 server 받을 일은 없을 듯
		 */

		String msg = "";
		String cTurn; // 현재 차례
		switch (tag) {
			case Tag.start:
				currentRoom.gameInit();
				msg = currentRoom.gameRun("");
				cTurn = currentRoom.getCurrentTurn();

				msg = Tag.start + Tag.split + msg + Tag.split + cTurn;

				break;
			case Tag.playing:

				msg = currentRoom.gameRun(message);

				if (currentRoom.getBaseball().getWinner() == null) {

					cTurn = currentRoom.getCurrentTurn();
					msg = Tag.playing + Tag.split + msg + Tag.split + cTurn;

				} else {
					// 승자가 정해짐
					// 승패 기록
					// 방 폭파시키기
					String gameTitle = currentRoom.getGameTitle();
					String winner = currentRoom.getBaseball().getWinner();
					String loser = currentRoom.getBaseball().getLoser();
					msg = Tag.end + Tag.split + winner;
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
		for (User tmp : totalUser) {
			if (tmp.getId().equals(userId)) {
				return tmp;
			}
		}
		return null;
	}

	private Game getUserGameInfo(User user, String gameTitle) {

		Game tmpGame = new Game(gameTitle);
		if (user.getGames().size() == 0) {
			user.getGames().add(tmpGame);
			return user.getGames().get(0);
		}
		boolean isExist = false;
		for (Game tmp : user.getGames()) {
			if (tmp.getName().equals(gameTitle)) {
				isExist = true;
				return tmp;
			}
		}
		if (!isExist) {
			user.getGames().add(tmpGame);
			return user.getGames().get(user.getGames().size());
		}
		return null;
	}

	private void recordScore(String gameTitle, String winner, String loser) {

		User tmpUser = getUserInfo(winner);
		Game gameTmp = getUserGameInfo(tmpUser, gameTitle);
		gameTmp.increaseWin();

		tmpUser = getUserInfo(loser);
		gameTmp = getUserGameInfo(tmpUser, gameTitle);
		gameTmp.increaseLose();

	}

	private void sendRoomList() {
		String msg = "";
		for (int i = 0; i < roomList.size(); i++) {
			msg += i + 1 + ". " + roomList.get(i);
		}
		// 생성된 방이 없다면 Tag만 전송된다.
		msg = Tag.roomList + Tag.split + msg;
		send(oos, msg);

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
		String[] tmp = message.split(" ");
		String id = tmp[0];
		String password = tmp[1];
		userJoin(id, password);

	}

	private void userJoin(String id, String password) {
		// user 회원가입
		String msg = null;
		User joinUser = new User(id, password);
		if (totalUser.contains(joinUser)) {
			msg = "이미 등록된 아이디입니다.";
			sendUserLogin(msg);
			return;
		}

		totalUser.add(joinUser);
		msg = "회원가입 되었습니다. 로그인하세요.";
		sendUserLogin(msg);
	}

	private void receiveUserLogin(String message) {
		// message는 id + " " + password로 형태
		// 서버는 list(db)에서 로그인 정보를 확인
		// id가 없다면 새로 등록.
		// id가 있는데 password가 없다면 로그인 실패. <종료>
		String[] tmp = message.split(" ");
		String id = tmp[0];
		String password = tmp[1];
		userLogin(id, password);

	}

	private void userLogin(String id, String password) {

		// totalUser에서 아이디와 비밀번호가 일치한 지
		// 현재 접속한 유저인 지
		String msg = Tag.blank;

		if (totalUser.size() == 0) {
			msg = "등록된 유저가 없습니다.";
			sendUserLogin(msg);
			return;

		}
		User loginUser = getUser(id, password);

		if (loginedUser.contains(loginUser)) {
			msg = "이미 접속한 유저입니다.";
			sendUserLogin(msg);
			return;
		}

		if (loginUser == null) {
			msg = "아이디 혹은 비밀번호가 일치하지 않습니다.";
			sendUserLogin(msg);
			return;
			// 재로그인 요구.

		} else {
			// loginedUser 객체에 추가
			// 게임을 선택할 수 있도록 메뉴판 제공
			// "<로그인 성공>";
			loginedUser.add(loginUser);
			msg = "<로그인 성공>\n";
			cUser.setUserId(id);
			msg = Tag.menu + Tag.split + msg;
			send(oos, msg);
		}
	}

	private User getUser(String id, String password) {
		// 입력받은 id와 password로
		// id가 일치할 때 password도 일치한다면 해당하는 객체 반환
		// 없다면 null 반환
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
