package minigame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// 연결된 소켓을 이용하여 데이터를 주고 받는 클래스
@RequiredArgsConstructor
public class Client {

	static final String black = "\u001B[30m";
	static final String red = "\u001B[31m";
	static final String green = "\u001B[32m";
	static final String yellow = "\u001B[33m";
	static final String blue = "\u001B[34m";
	static final String purple = "\u001B[35m";
	static final String cyan = "\u001B[36m";
	static final String white = "\u001B[37m";

	static final String exit = "\u001B[0m";

	@NonNull
	private Socket socket;
	private static Scanner sc = new Scanner(System.in);
	private ObjectInputStream ois;

	private List<Message> list = new ArrayList<Message>();

	// 로그인 시 입력받은 id
	private String id;

	private boolean isExit = false;

	private boolean mainTurn = true;
	private boolean threadTurn = false;

	private void runSub() {

		Thread t = new Thread(() -> {
			try {
				while (true) {
					while (!threadTurn) {
						mainTurn = true;
						Thread.sleep(200);
					}

					Message msg = (Message) ois.readObject();
					if (msg.getType().equals(Type.alert)) {
//						System.out.println("sub >> ");
						readMessage(msg);
					} else {
						list.add(msg);
					}

				}
			} catch (ClassNotFoundException | IOException | InterruptedException e) {

			}

		});
		t.start();

	}

	public void run() {

		runSub();

		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while (true) {

				if (isExit) {
					System.out.println("종료합니다.");
					break;
				}

				while (!mainTurn) {
					Thread.sleep(200);
				}

				Message msg;
				while (list.size() != 0) {
					msg = list.get(0);
					list.remove(0);
					readMessage(msg);
					continue;
				}

				msg = (Message) ois.readObject();

				threadTurn = true;
				mainTurn = false;

				readMessage(msg);

				threadTurn = false;
			}

			// ois.close();
		} catch (

		IOException e) {
			//
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// (Message) ois.readObject() 에러
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readMessage(Message message) {
		// TODO Auto-generated method stub
		String tag = message.getType();
		// System.out.println(message);

		switch (tag) {
			case Type.alert:
				if (message.getMsg() != null && message.getMsg().length() != 0)
					System.out.println(message.getMsg());
				break;
			case Type.login:
				if (message.getMsg() != null) {
					id = Type.blank; // id 초기화
					System.out.println(message.getMsg());
				}
				inputUserLogin();
				break;
			case Type.menu:
				// System.out.println("<로그인 성공>");
				runRoomMenu();
				break;

			case Type.roomList:
				if (message.getMsg() == null) {
					System.out.println("<생성된 방이 없습니다.>");
					printPrev();
					runRoomMenu();
					break;
				} else if (message.getMsg().equals(Type.full)) {
					System.out.println("<방이 꽉찼습니다.>");
					printPrev();
					runRoomMenu();
					break;
				}
				// message.getOpt1() : room의 갯수
				runRoomList(message.getMsg(), Integer.parseInt(message.getOpt1()));
				break;

			case Type.start:
				// gameStart(message);
				// break;
			case Type.playing:
				gamePlay(message);
				break;
			case Type.end:
				if (message.getMsg() != null) {
					System.out.println(message.getMsg());
				}
				if (message.getOpt1().equals("exit")) {
					System.out.println("게임이 비정상적으로 종료되었습니다.");
				} else if (message.getOpt1().equals(id)) {
					System.out.println("<당신이 승리하였습니다.>");

				} else {
					System.out.println("<당신이 패배하였습니다.>");
				}

				System.out.println("방을 나갑니다. Enter를 눌러주세요.");
				sc.nextLine();
				printPrev();
				runRoomMenu();
				return;
			default:

		}

	}

	private void gamePlay(Message message) {

		System.out.print(message.getMsg());

		if (id.equals(message.getOpt1()) || message.getOpt1().equals(Type.allTurn)) {
			Message msg = new Message();
			msg.setType(Type.playing);
			if (message.getOptStr() != null) {
				List<String> words = message.getOptStr();
				List<String> answer = new ArrayList<String>();
				System.out.println("게임을 시작하려면 Enter를 눌러주세요");
				sc.nextLine();

				for (int i = 0; i < 10; i++) {
					System.out.println("" + words.get(i));
					answer.add(sc.nextLine());
					if (answer.get(i).equals(Type.exit)) {
						msg.setOpt1(Type.exit);
						send(msg);
						return;
					}
				}
				msg.setOptStr(answer);
			} else {
				String input = sc.nextLine();
				msg.setMsg(input);
			}
			send(msg);
		} else {
			System.out.println("상대방이 입력 중입니다.");
		}
	}

	// private void gameStart(Message message) {
	//
	// System.out.print(message.getMsg());
	// if (id.equals(message.getOpt1())) {
	// String input = sc.nextLine();
	// Message msg = new Message();
	// msg.setType(Type.playing);
	// msg.setMsg(input);
	// send(msg);
	// } else {
	// System.out.println("<상대방의 차례입니다.>");
	// }
	// }

	private void runRoomList(String message, int num) {

		System.out.println("<방 목록>");
		System.out.println("====================================");
		System.out.print(message);
		System.out.println("====================================");
		System.out.print(">> 방 번호 입력 |이전으로:-1| : ");
		int menu;
		try {
			menu = sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("번호를 잘 못 입력하셨습니다. 이전으로 돌아갑니다.");
			menu = -1;
			sc.nextLine();
		}
		if (menu == -1) {
			runRoomMenu();
			return;
		}
		if (menu > num || menu <= 0) {
			System.err.println("잘못된 방 번호를 입력하셨습니다. 이전으로 돌아갑니다.");
			runRoomMenu();
			return;
		}

		sc.nextLine();
		Message msg = new Message();
		msg.setType(Type.roomList);
		msg.setMsg("" + menu);
		send(msg);
	}

	private void runRoomMenu() {

		printRoomMenu();
		try {
			int menu = sc.nextInt();
			switch (menu) {
				case 1: // 방 만들기
					insertRoom();
					break;
				case 2: // 방 검색 및 참여
					serarchRoom();
					break;
				case 3: // 전적 조회
					// 개인 성적 조회
					// 전체 성적(랭크) 조회
					// checkScore();
					break;
				case 4: // 회원 정보 변경 <구현 예정>
					// updateUser()
					// 아이디, 비밀번호, 새비밀번호
					// 계정 삭제
				case 5: // 로그아웃
					Message msg = new Message();
					msg.setType(Type.exit);
					msg.setOpt1(Type.logout);
					send(msg);
					id = Type.blank;
					inputUserLogin();
					break;
				case 6: // 종료
					Message msg1 = new Message();
					msg1.setType(Type.exit);
					msg1.setOpt1(Type.exit);
					send(msg1);
					isExit = true;
					break;
				default:
					System.err.println("잘 못 입력했습니다. <메뉴>로 돌아갑니다.");
					runRoomMenu();
					break;
			}
		} catch (InputMismatchException e) {
			System.err.println("잘 못 입력하셨습니다. <메뉴>로 돌아갑니다.");
			sc.nextLine();
			runRoomMenu();
			return;
		}
	}

	private void serarchRoom() {
		System.out.println("방 검색을 요청합니다.");
		Message msg = new Message();
		msg.setType(Type.roomList);
		send(msg);
	}

	private void insertRoom() {

		String roomTitle;
		String gameName;
		// 새로 만드는 게임을 이곳에서 추가하면 됩니다.
		// 선택된 게임의 Tag를 통해 gameName을 설정합니다.
		// roomTitle은 방의 제목입니다.

		printGameList();

		int gameNum;
		try {
			gameNum = sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("잘 못 입력했습니다. 이전으로 돌아갑니다.");
			gameNum = -1;
			sc.nextLine();
		}

		if (gameNum == -1) { // 이전으로
			runRoomMenu();
			return;
		}
		switch (gameNum) {
			case 1:
				gameName = Type.baseBall;
				break;
			case 2:
				gameName = Type.omok;
				break;
			case 3:
				gameName = Type.typing;
				break;
			case 4:
				gameName = Type.yacht;
				break;
			case 5:
				gameName = Type.speedGame;
				break;
			default:
				printWrongMenu();
				printPrev();
				runRoomMenu();
				return;

		}

		sc.nextLine();
		System.out.print("방 제목 입력 : ");
		roomTitle = sc.nextLine();

		Message msg = new Message();
		msg.setType(Type.createRoom);
		msg.setMsg(gameName);
		msg.setOpt1(roomTitle);

		send(msg);

	}

	private void printWrongMenu() {
		System.err.println("잘못된 메뉴를 선택하였습니다.");
	}

	private void printPrev() {
		System.out.println("이전으로 돌아갑니다.");
	}

	private void printGameList() {

		System.out.println("====================================");
		System.out.println(" <방 만들기> ");
		System.out.println(" 1. 야구");
		System.out.println(" 2. 오목");
		System.out.println(" 3. Typing");
		System.out.println(" 4. Yacht <미구현>");
		System.out.println(" 5. SpeedQuiz <미구현>");
		System.out.println("====================================");
		System.out.print("게임 선택 |이전으로:-1| : ");

	}

	private void printRoomMenu() {
		System.out.println("====================================");
		System.out.println("<메뉴>");
		System.out.println("1. 방 만들기");
		System.out.println("2. 방 검색하기");
		System.out.println("3. 전적 조회<구현예정>");
		System.out.println("4. 회원 정보 변경<구현예정>");
		System.out.println("5. 로그아웃");
		System.out.println("6. 종료");
		System.out.println("====================================");
		System.out.print("메뉴 선택 : ");

	}

	public void printLoginMenu() {
		System.out.println("====================================");
		System.out.println("<로그인>");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료");
		System.out.println("====================================");
		System.out.print("메뉴선택 : ");
	}

	private void inputUserLogin() {
		// ID와 Password를 입력해서 서버에 전송

		printLoginMenu();

		int menu;
		try {
			menu = sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("잘 못 입력하셨습니다. 이전으로 돌아갑니다.");
			sc.nextLine();
			inputUserLogin();
			return;
		}

		switch (menu) {
			case 1: // 로그인
			case 2: // 회원가입

				System.out.print("아이디 : ");
				id = sc.next();
				System.out.print("비밀번호 : ");
				String password = sc.next();

				Message message = new Message();
				message.setMsg(id + " " + password);

				if (menu == 1) {
					message.setType(Type.login);
				} else {
					message.setType(Type.join);
				}
				send(message);
				break;

			case 3:
				isExit = true;
				return; // 종료
			default:
				System.err.println("메뉴를 잘 못 입력했습니다.");
				inputUserLogin();
				return;
		}
	}

	public void send(Message msg) {
		msg.setPName(id);

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());

			oos.writeObject(msg);
			oos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
