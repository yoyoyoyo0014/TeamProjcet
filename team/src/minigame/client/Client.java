package minigame.client;

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
import minigame.utils.Message;
import minigame.utils.Type;

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

	// 로그인 시 입력받은 id
	private String id;

	private boolean isExit = false;
	private boolean isDuplicated = false;

	public void run() {

		// runSub();

		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while (true) {

				if (isExit) {
					System.out.println("종료합니다.");
					break;
				}

				Message msg = (Message) ois.readObject();
				
				readMessage(msg);

			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void readMessage(Message message) {

		if (message == null || message.getType() == null) {
			return;
		}

		String tag = message.getType();

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

			case Type.reset:
				System.out.println("====================================");
				if (message.getOpt1().equals(Type.success)) {
					System.out.println("비밀번호 : " + message.getMsg());
				} else {
					System.out.println("정보가 일치하지 않아 비밀번호를 가져올 수 없습니다.");
				}
				System.out.println("====================================");
				inputUserLogin();
				break;

			case Type.menu:
				System.out.println("<로그인 성공>");
				runRoomMenu();
				break;

			case Type.personal:
			case Type.topPlayer:
				System.out.println(message.getMsg());
				System.out.print("이전 메뉴로 돌아갑니다. ENTER를 눌러주세요.");
				sc.nextLine();
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

			case Type.update_pwd:
				System.out.println("====================================");
				if (message.getOpt1().equals(Type.success)) {
					System.err.println("<비밀번호가 변경되었습니다.>");
				} else {
					System.err.println("<비밀번호 변경에 실패하였습니다.>");
				}
				runRoomMenu();
				break;
			case Type.start:
			case Type.playing:
				gamePlay(message);
				break;
			case Type.end:
				isDuplicated = false;
				if (message.getMsg() != null) {
					System.out.println(message.getMsg());
				}
				if (message.getOpt1().equals("exit")) {
					System.err.println("게임이 비정상적으로 종료되었습니다.");
					System.out.println("<당신이 승리하였습니다.>");
				} else if (message.getOpt1().equals(id)) {
					System.out.println("<당신이 승리하였습니다.>");

				} else if (message.getOpt1().equals(Type.drawEnd)) {
					System.out.println("<게임이 무승부로 끝났습니다.>");
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


		if (id.equals(message.getOpt1()) || message.getOpt1().equals(Type.allTurn)) {
			isDuplicated = false;
			System.out.print(message.getMsg());
			Message msg = new Message();
			msg.setType(Type.playing);
			if (message.getStrList() != null) {
				List<String> words = message.getStrList();
				List<String> answer = new ArrayList<String>();
				System.out.println("게임을 시작하려면 Enter를 눌러주세요");
				sc.nextLine();

				for (int i = 0; i < 10/*words.size()*/; i++) {
					System.out.println("" + words.get(i));
					answer.add(sc.nextLine());
					if (answer.get(i).equals(Type.exit)) {
						msg.setMsg(Type.exit);
						send(msg);
						//answer.add(sc.nextLine());
						return;
					}
				}
				msg.setStrList(answer);
			} else {
				String input = sc.nextLine();
				msg.setMsg(input);
			}
			send(msg);
		} else {
			if(!isDuplicated) {
			System.out.println(message.getMsg());
			System.out.println("<상대방이 입력 중입니다>.");
			}
			isDuplicated = true;
		}
	}

	private void runRoomList(String message, int num) {

		System.out.println("====================================");
		System.out.println("<방 목록>");
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
					searchScore();
					break;
				case 4: // 회원 정보 변경
					updateUser();
					// 아이디, 비밀번호, 새비밀번호
					// 계정 삭제
					break;
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

	private void printScore() {

		System.out.println("====================================");
		System.out.println(" <회원 전적 조회> ");
		System.out.println(" 1. 개인 전적 조회");
		System.out.println(" 2. 상위 전적 조회");
		System.out.println("====================================");
		System.out.print(">> 메뉴 선택 |이전으로:-1| : ");

	}

	private void searchScore() {

//		1. 게임별로 내 전적만 보여주기
//		2. 게임별로 상위 3위 리스트

		printScore();

		int menu;

		try {
			menu = sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("잘 못 입력했습니다. 이전으로 돌아갑니다.");
			menu = -1;
			sc.nextLine();
		}

		if (menu == -1) { // 이전으로
			runRoomMenu();
			return;
		}

		Message msg = new Message();
		msg.setType(Type.rank);

		switch (menu) {

			case 1:
				msg.setOpt1(Type.personal);
				send(msg);
				break;

			case 2:
				msg.setOpt1(Type.topPlayer);
				send(msg);
				break;

			default: {
				System.err.println("잘 못 입력했습니다. 이전으로 돌아갑니다.");
				runRoomMenu();
				return;
			}
			
		}
		sc.nextLine();
	}

	private void printUpdateMenu() {
		System.out.println("====================================");
		System.out.println(" <회원 정보 변경> ");
		System.out.println(" 1. 비밀번호 재설정");
		System.out.println("====================================");
		System.out.print(">> 메뉴 선택 |이전으로:-1| : ");
	}

	private void updateUser() {

		printUpdateMenu();
		int menu;

		try {
			menu = sc.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("잘 못 입력했습니다. 이전으로 돌아갑니다.");
			menu = -1;
			sc.nextLine();
		}

		if (menu == -1) { // 이전으로
			runRoomMenu();
			return;
		}

		switch (menu) {
			case 1:
				updatePassword();
				break;

		}

	}

	private void updatePassword() {

		Message msg = new Message();

		String pwd, newPwd, newPwd2;

		System.out.print("기존 비밀번호 : ");
		pwd = sc.next();

		System.out.print("새 비밀번호 : ");
		newPwd = sc.next();

		System.out.print("새 비밀번호 확인: ");
		newPwd2 = sc.next();

		if (!newPwd.equals(newPwd2) || pwd.equals(newPwd)) {
			System.err.println("변경할 비밀번호를 다시 한번 확인 해주세요.");
			updateUser();
			return;
		}
		msg.setMsg(pwd + " " + newPwd);
		msg.setType(Type.update_pwd);
		send(msg);

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
				gameName = Type.baseball;
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
				gameName = Type.speedQuiz;
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
		System.out.println("<방 만들기> ");
		System.out.println("1. Baseball(야구)");
		System.out.println("2. Omok(오목)");
		System.out.println("3. Typing(한컴타자)");
		System.out.println("4. Yacht(요트)");
		System.out.println("5. SpeedQuiz(스피드)");
		System.out.println("====================================");
		System.out.print(">> 게임 선택 |이전으로:-1| : ");

	}

	private void printRoomMenu() {
		System.out.println("====================================");
		System.out.println("<메뉴>");
		System.out.println("1. 방 만들기");
		System.out.println("2. 방 검색하기");
		System.out.println("3. 전적 조회");
		System.out.println("4. 회원 정보 변경");
		System.out.println("5. 로그아웃");
		System.out.println("6. 종료");
		System.out.println("====================================");
		System.out.print(">> 메뉴 선택 : ");

	}

	public void printLoginMenu() {
		System.out.println("====================================");
		System.out.println("<로그인>");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 비밀번호 찾기");
		System.out.println("4. 종료");
		System.out.println("====================================");
		System.out.print(">> 메뉴선택 : ");
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

				if (menu == 1) {
					message.setMsg(id + " " + password);
					message.setType(Type.login);
				} else {
					System.out.print("비밀번호 확인: ");
					String password2 = sc.next();
					if (password2.equals(password)) {
						System.out.print("이메일 : ");
						String email = sc.next();
						message.setMsg(id + " " + password + " " + email);
						message.setType(Type.join);
					} else {
						System.out.println("비밀번호가 일치하지 않습니다.");
						inputUserLogin();
						break;
					}
				}
				send(message);
				break;
			case 3:
				// 비밀번호 초기화.
				Message msg = new Message();

				System.out.print("아이디 : ");
				id = sc.next();
				System.out.print("이메일 : ");
				String email = sc.next();
				msg.setMsg(id + " " + email);
				msg.setType(Type.reset);
				send(msg);
				break;

			case 4:
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
