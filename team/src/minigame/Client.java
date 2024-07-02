package minigame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

// 연결된 소켓을 이용하여 데이터를 주고 받는 클래스
@RequiredArgsConstructor
public class Client {

	@NonNull
	private Socket socket;
	private static Scanner sc = new Scanner(System.in);

	// 로그인 시 입력받은 id
	private String id;

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());

			while (true) {
				Message msg = (Message) ois.readObject();
				readMessage(msg);
			}

			// ois.close();
		} catch (IOException e) {
			//
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// (Message) ois.readObject() 에러
			e.printStackTrace();
		}
	}

	private void readMessage(Message message) {
		// TODO Auto-generated method stub
		String tag = message.getType();
		System.out.println(message);

		switch (tag) {
			case Type.alert:
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
				runRoomList(message.getMsg());
				break;
			case Type.start:
				System.out.print(message.getMsg());
				if (id.equals(message.getOpt1())) {
					String input = sc.nextLine();
					Message msg = new Message();
					msg.setType(Type.playing);
					msg.setMsg(input);
					send(msg);
				} else {
					System.out.println("<상대방의 차례입니다.>");
				}
				break;
			case Type.playing:
				System.out.print(message.getMsg());
				if (id.equals(message.getOpt1())) {
					String input = sc.nextLine();
					Message msg = new Message();
					msg.setType(Type.playing);
					msg.setMsg(input);
					send(msg);
				} else {
					System.out.println("<상대방의 차례입니다.>");
				}
				break;
			case Type.end:
				if (message.getMsg().equals(id)) {
					System.out.println("<당신이 승리하였습니다.>");

				} else {
					System.out.println("<당신이 패배하였습니다.>");
				}
				printPrev();
				runRoomMenu();
				return;
			default:

		}

	}

	private void runRoomList(String message) {

		System.out.println("<방 목록>");
		System.out.println(message);
		System.out.println("(-1). 이전으로");
		System.out.print("방 번호 입력 : ");
		sc.nextLine();
		String menu = sc.nextLine();
		if (menu.equals("-1")) {
			runRoomMenu();
			return;
		}
		Message msg = new Message();
		msg.setType(Type.roomList);
		msg.setMsg(menu);
		send(msg);
	}

	private void runRoomMenu() {

		printRoomMenu();
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
			case 5:
				// exit(); 종료, 클라이언트 연결끊기 <구현 예정>
				break;
			default:
				break;
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
		System.out.println("-----------------------");
		System.out.println("<방 만들기>");
		System.out.println("1. 야구<구현>");
		System.out.println("2. 블랙잭<미구현><가제목>");
		System.out.println("3. 타자연습<미구현><가제목>");
		System.out.println("4. 빙고<미구현><가제목>");
		System.out.println("5. 이전으로 ");
		System.out.println("-----------------------");
		System.out.print("게임 선택 : ");
		int gameNum = sc.nextInt();

		if (gameNum == 5) { // 이전으로
			runRoomMenu();
			return;
		}
		switch (gameNum) {
			case 1:
				gameName = Type.baseBall;
				break;
			case 2:
				gameName = "";
				break;
			case 3:
				gameName = "";
				break;
			case 4:
				gameName = "";
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
		System.out.println("잘못된 메뉴를 선택하였습니다.");
	}

	private void printPrev() {
		System.out.println("이전으로 돌아갑니다.");
	}

	private void printRoomMenu() {
		// TODO Auto-generated method stub
		System.out.println("-----------------------");
		System.out.println("<메뉴>");
		System.out.println("1. 방 만들기");
		System.out.println("2. 방 검색하기");
		System.out.println("3. 전적 조회<구현예정>");
		System.out.println("4. 회원 정보 변경<구현예정>");
		System.out.println("5. 종료<구현예정>");
		System.out.println("-----------------------");
		System.out.print("메뉴 선택 : ");

	}

	public void printLoginMenu() {
		System.out.println("-----------------------");
		System.out.println("<로그인>");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료<구현예정>");
		System.out.println("-----------------------");
		System.out.print("메뉴선택 : ");
	}

	private void inputUserLogin() {
		// ID와 Password를 입력해서 서버에 전송

		printLoginMenu();
		int menu = sc.nextInt();

		if (menu == 3) {
			return; // 종료(구현예정)
		}

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
	}

	public void send(Message msg) {

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
