package minigame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import lombok.Data;
import program.Program;

@Data
public class Typing implements Program {

	private Scanner scan = new Scanner(System.in);

	private List<String> words = new ArrayList<String>();
	private List<String> answer = new ArrayList<String>();

	private List<Integer> sfirst = new ArrayList<>();
	private List<Integer> slast = new ArrayList<>();

	private String player1;
	private String player2;

	private String gameResult;

	private String first;
	private String last;

	private String currentTurn;

	private String winner = null;
	private String loser = null;

	// 점수 p1,p2 분리했습니다.
	// int totalScore = 0;
	int p1TotalScore = 0;
	int p2TotalScore = 0;

	LocalDateTime now;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	String formatedNow, formatedAfter;
	LocalDateTime after;

	int turn = 0;

	public String getResult() {
		String tmp = gameResult;
		gameResult = "";
		return tmp;
	}

	private void currentTurnInit() {
		if (new Random().nextBoolean()) {
			currentTurn = player1;
		} else
			currentTurn = player2;
	}

	public Typing(String player1, String player2) {

		this.player1 = player1;
		this.player2 = player2;

		gameResult = Type.blank;

		gameResult = "<게임을 시작합니다.>\n";
		gameResult += "<" + player1 + " vs " + player2 + ">\n";

		gameResult += "======게임을 설명합니다======\n";
		gameResult += "게임이 시작되면 문장이 나옵니다\n";
		gameResult += "문장이 나오는 순간부터 시간이 흐릅니다!\n";
		gameResult += "빠르고 정확하게 문장을 따라 입력합니다\n";
		gameResult += "시간과 정확도에 따라 점수가 설정되고, 점수가 높은 사람이 승리하게 됩니다.\n";

		currentTurnInit();

		gameResult += currentTurn + "님 차례입니다.>\n";

		if (player1.equals(currentTurn)) {
			first = player1;
			last = player2;
		} else {
			last = player1;
			first = player2;
		}

		centens();
		Collections.shuffle(words);

		timeReset();
	}

	private void start() {

		// (수정1) init 함수에서 변수 선언했습니다.
		// centens();
		// Collections.shuffle(words);
		//
		// (수정2) 전연 변수로 뺏습니다.
		// int totalScore = 0;
		// (수정2-1)계산하는 부분으로 옮겼습니다.
		// int score = 0;

		// System.out.println("게임을 시작하려면 Enter를 눌러주세요");

		// (수정) 일단 주석 처리했습니다.
		// System.out.println("Enter가 아닌 다른 버튼을 입력한 경우 패배하게 됩니다");
		// scan.nextLine();
		// String str1 = scan.nextLine();
		//
		// if (!str1.isBlank()) {
		// System.out.println("게임을 포기하셨습니다.");
		// return;
		// } else {
		// System.out.println("게임 시작!");
		// }

		// (수정 3) 전역 변수로 뺏습니다.
		// now, formatter, formaterdNow, after, formatter2, formatedAfter

		// (수정 3-5) timeReset 메소드로 따로 만들었습니다.
		// now = LocalDateTime.now();
		// formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		// formatedNow = now.format(formatter);

		// (수정 4) 해당 for문은 Client로 넘겼습니다.
		// for (int i = 0; i < 10; i++) {
		// System.out.println(words.get(i));
		// answer.add(scan.nextLine());
		// }

		//
		// (수정 5) 점수 계산하는 부분으로 메소드 따로 만들었습니다.
		// 메소드명 : calScore();

		/*
		 * after = LocalDateTime.now(); formatedAfter = after.format(formatter);
		 * 
		 * long diff = 0;
		 * 
		 * try { diff = timeOper(formatedNow, formatedAfter); } catch
		 * (ParseException e) { e.printStackTrace(); }
		 * 
		 * score = (int) Math.max(15000 - diff, 0);
		 * 
		 * for (int i = 0; i < 10; i++) { words.get(i).contains(answer.get(i));
		 * if (words.get(i) != answer.get(i)) { score -= 100; } }
		 * 
		 * totalScore += score;
		 */

		return;

	}

	private void centens() {

		words.add("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세");
		words.add("간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 장 공장장이다.");
		words.add("네가 떨어뜨린 도끼가 이 쇠도끼냐 아니면 금도끼냐 아니면 은도끼냐");
		words.add("토끼와 거북이가 경주를 하는데 토끼가 중간에 낮잠을 자고 말았어요.");
		words.add("조금 가다가 강가에 다다르니, 장마로 인해 물이 많아진 강물에 한 아이가 빠져 허우적거리고 있었지요.");
		words.add("어머니는 늘 두 아들 때문에 마음이 편할 날이 없었어요.");
		words.add("언제부턴가 갈대는 속으로 조용히 울고 있었다. "
				+ "그런 어느 밤이었을 것이다. 그의 온몸이 흔들리고 있는 것을 알았다. "
				+ "바람도 달빛도 아닌 것. 갈대는 저를 흔드는 것이 제 조용한 울음인 것을 까맣게 몰랐다. "
				+ "......산다는 것은 속으로 이렇게 조용히 울고 있는 것이라는 것을 그는 몰랐다.");

		words.add("호랑이 그리려다 고양이 그린다.");
		words.add("종로에서 뺨 맞고 행랑 뒤에서 눈 흘긴다");
		words.add("배고픈 여우 한 마리가 포도밭 옆을 지나가게 되었어요.");
		words.add("옛날 어느 나라에 임금님이 살고 계셨어요.");
		words.add("죽는 날까지 하늘을 우러러 한 점 부끄럼이 없기를, 잎새에 이는 바람에도 나는 괴로워했다.");
		words.add("자세히 보아야 예쁘다. 오래 보아야 사랑스럽다. 너도 그렇다.");

		words.add("망건 쓰자 파장");
		words.add("아이고, 망했다. 망했어!");
		words.add("여름비는 잠비고, 가을비는 떡비");
		words.add("단단한 땅에 물이 괸다.");
		words.add("물이 깊어야 고기가 모인다.");
		words.add("첫술에 배부를까");
		words.add("가는 날이 장날");

	}

	private long timeOper(String str1, String str2) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		String time1 = str1;
		String time2 = str2;

		Date date1 = sdf.parse(time1);
		Date date2 = sdf.parse(time2);

		long diff = date2.getTime() - date1.getTime();

		return diff / 1000;

	}

	@Override
	public void printMenu() {
		// TODO Auto-generated method stub

	}

	@Override
	public void runMenu(int menu) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	
	public void turnNext() {

		if (currentTurn.equals(player1)) {
			currentTurn = player2;
		} else {
			currentTurn = player1;
		}
	}

	public void run(Message message) {
		
		if(message.getOpt1().equals("exit")) {
			if(currentTurn.equals(player1)) {
				winner = player2;
				loser = player1;
			} else {
				winner = player1;
				loser = player2;
			}
			return;
		}

		answer = message.getOptStr();
		calScore(currentTurn);
		timeReset();
		

		if (turn == 2) {
			victory();
		}
		else {
			turnNext();
		}

	}

	private void timeReset() {
		
		now = LocalDateTime.now();
		formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		formatedNow = now.format(formatter);
		
	}

	private void victory() {

		if (p1TotalScore > p2TotalScore) {
			winner = player1;
			loser = player2;
		} else if (p1TotalScore < p2TotalScore) {
			loser = player1;
			winner = player2;
		} else {
			// p1TotalScore == p2TotalScore
			// 무승부도 구현하실거?
		}

		System.out.println("게임이 종료되었습니다. 승자는 <" + winner + ">입니다.");
	}

	private void calScore(String currentTurn) {

		int score;

		if (currentTurn.equals(player1)) {
		} else {
		}

		after = LocalDateTime.now();
		formatedAfter = after.format(formatter);

		long diff = 0;

		try {
			diff = timeOper(formatedNow, formatedAfter);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		score = (int) Math.max(15000 - diff, 0);

		for (int i = 0; i < 10; i++) {
			
			if (!words.get(i).equals(answer.get(i))) {
				score -= 100;
			}
		}
		if (currentTurn.equals(player1)) {
			p1TotalScore += score;
		} else {
			p2TotalScore += score;
		}

		turn++;

	}
}