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

	private String id;

	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					socket.getInputStream());

			while (true) {
				String msg = ois.readUTF();
				readMessage(msg);
			}

			// ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readMessage(String message) {
		// TODO Auto-generated method stub
		String[] tmpMsg = message.split(Tag.split);
		String tag = tmpMsg[0];
		String msg = Tag.blank;
		// tag랑 msg를 같이 보냈다면
		if (tmpMsg.length >= 2) {
			msg = tmpMsg[1];
		}

		switch (tag) {
			case Tag.alert:
				System.out.println(msg);
				break;
			case Tag.login:
				if (!msg.equals(Tag.blank)) {
					System.out.println(msg);
				}
				inputUserLogin();
				break;
			case Tag.menu:
				// System.out.println("<로그인 성공>");
				runRoomMenu();
				break;
			case Tag.roomList:
				if (msg.equals(Tag.blank)) {
					System.out.println("[생성된 방이 없습니다.]");
					printPrev();
					runRoomMenu();
					break;
				} else if (msg.equals(Tag.full)) {
					System.out.println("[방이 꽉찼습니다.]");
					printPrev();
					runRoomMenu();
					break;
				}
				runRoomList(msg);
				break;
			case Tag.start:
				System.out.println(msg);
				if (id.equals(tmpMsg[2])) {
					String input = sc.nextLine();
					msg = Tag.playing + Tag.split + input;
					send(msg);
				} else {
					System.out.println("<상대방의 차례입니다.>");
				}
				break;
			case Tag.playing:
				System.out.println(msg);
				if (id.equals(tmpMsg[2])) {
					String input = sc.nextLine();
					msg = Tag.playing + Tag.split + input;
					send(msg);
				} else {
					System.out.println("<상대방의 차례입니다.>");
				}
				break;
			case Tag.end:
				if (msg.equals(id)) {
					System.out.println("당신이 승리하였습니다.");

				} else {
					System.out.println("패배하였습니다.");
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
		System.out.println("이전으로 (-1)");
		System.out.print("방 번호 입력 : ");
		sc.nextLine();
		String menu = sc.nextLine();
		if (menu.equals("-1")) {
			runRoomMenu();
			return;
		}
		String msg = Tag.roomList + Tag.split + menu;
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
		String msg = Tag.roomList + Tag.split;
		send(msg);
	}

	private void insertRoom() {

		String roomTitle;
		String gameName;
		// 새로 만드는 게임을 이곳에서 추가하면 됩니다.
		// 선택된 게임의 Tag를 통해 gameName을 설정합니다.
		// roomTitle은 방의 제목입니다.

		System.out.println("<방 만들기>");
		System.out.println("1. 야구<구현>");
		System.out.println("2. 사탕찾기<미구현><가제목>");
		System.out.println("3. 타자연습<미구현><가제목>");
		System.out.println("4. 빙고<미구현><가제목>");
		System.out.println("5. 이전으로 ");
		System.out.print("게임 선택 : ");
		int gameNum = sc.nextInt();

		if (gameNum == 5) { // 이전으로
			runRoomMenu();
			return;
		}
		switch (gameNum) {
			case 1:
				gameName = Tag.baseBall;
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
		String msg = Tag.createRoom + Tag.split + gameName + Tag.split
				+ roomTitle;
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

		System.out.println("메뉴");
		System.out.println("1. 방 만들기");
		System.out.println("2. 방 검색하기");
		System.out.println("3. 전적 조회<구현예정>");
		System.out.println("4. 회원 정보 변경<구현예정>");
		System.out.println("5. 종료<구현예정>");
		System.out.print("메뉴 선택 : ");

	}

	public void printLoginMenu() {

		System.out.println("<로그인>");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료<구현예정>");
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

		String msg = id + " " + password;
		if (menu == 1) {
			msg = Tag.login + Tag.split + msg;
		} else {
			msg = Tag.join + Tag.split + msg;
		}
		send(msg);
	}

	public void send(String message) {

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					socket.getOutputStream());

			oos.writeUTF(message);
			oos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
