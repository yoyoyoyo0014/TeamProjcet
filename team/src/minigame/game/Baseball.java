package minigame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import lombok.Data;
import minigame.utils.Message;
import minigame.utils.Type;

@Data
public class Baseball {

	// <변수>
	// player1, player2
	// 게임 카운트
	// 차례 (누가 게임할 순서인 지)
	private int turnCount = 1;
	private String player1;
	private String player2;
	private String currentTurn;

	private List<Integer> randomNumber;

	// 게임에 관한 모든 출력 값을 gameResult 변수로 받음.
	private String gameResult;

	private String winner = null;
	private String loser = null;

	private int min = 1;
	private int max = 9;

	private final boolean isDraw = false;

	public Baseball(String player1, String player2) {

		this.player1 = player1;
		this.player2 = player2;

		gameResult = Type.blank;

		gameResult = "<게임을 시작합니다.>\n";
		gameResult += "<" + player1 + " vs " + player2 + ">\n";

		gameResult += "<랜덤 번호를 생성합니다.>\n";
		randomGen();

		// 차례 정하기
		currentTurnInit();

		//
		gameResult += "<" + turnCount++ + "차 시도>\n";
		gameResult += "<" + currentTurn + "의 차례입니다.>\n";
		gameResult += "<숫자 3개를 입력해주세요.(예: 3 6 9) > |종료: EXIT| : ";
	}

	private void currentTurnInit() {
		// 순서 랜덤으로 지정.
		// true, false가 랜덤으로 나옴.
		if (new Random().nextBoolean()) {
			currentTurn = player1;
		} else
			currentTurn = player2;
	}

	// winner가 정해졌는 지 판별하는 메소드
	public boolean isEnd() {
		if (winner != null) {
			return true;
		}
		return false;
	}

	public String getResult() {
		String tmp = gameResult;
		gameResult = "";
		return tmp;
	}

	public void turnNext() {

		if (currentTurn.equals(player1)) {
			currentTurn = player2;
		} else {
			currentTurn = player1;
		}
	}

	// 랜덤 번호 생성
	public void randomGen() {

		HashSet<Integer> baseSet = new HashSet<>();
		while (baseSet.size() < 3) {
			baseSet.add((int) (Math.random() * (max - min + 1) + min));
		}

		randomNumber = new ArrayList<Integer>(baseSet);
		System.out.println(randomNumber);
	}

	// User가 입력한 숫자에 대한 결과 값 출력
	public void Play(List<Integer> userNum) {

		String result = Type.blank;
		int s = 0, b = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (randomNumber.get(i) == userNum.get(j)) {
					if (i == j) {
						s++;
					} else
						b++;
				}
			}
		}
		if (s == 0 && b == 0)
			// System.out.println("Out");
			result = "Out!";
		else if (s == 3) {
			// System.out.println("정답");
//			result = "정답";
			winner = currentTurn;
			loser = (currentTurn.equals(player1) ? player2 : player1);
		} else {
			// System.out.println(((s == 0) ? "" : s + "S") + ((b == 0) ? "" : b
			// + "B"));
			result = ((s == 0) ? "" : s + "S") + ((b == 0) ? "" : b + "B");
		}

		if (winner != null) {

			gameResult = "==================================================\n";
			gameResult += "<게임이 끝났습니다. 승자는 : " + currentTurn + "님 입니다.>\n";
			gameResult += "==================================================\n";
			return;
		}

		turnNext();
		gameResult += "==================================================\n";
		gameResult += "<입력 " + userNum + ", 결과 : " + result + "> 입니다. \n";
		gameResult += "<" + turnCount + "차 시도>\n";
		gameResult += "<" + currentTurn + "의 차례입니다.>\n";
		gameResult += "==================================================\n";
		gameResult += "<숫자 3개를 입력해주세요.(예: 3 6 9) > : ";

	}

	public void run(Message message) {

		// String으로 받은 숫자 <예: 3 6 9>를
		// integer list로 생성
		
		if (message.getMsg().toUpperCase().equals(Type.exit) || message.getMsg().equals(Type.exit)) {

			loser = message.getPName();
			winner = loser.equals(player1)?player2:player1;
			
			gameResult += "<" + loser + " 님이 경기를 포기하였습니다.>\n";
			gameResult += "<게임이 종료됩니다>";

			return;
		}

		List<String> strNum = new ArrayList<String>();
		strNum = Arrays.asList(message.getMsg().split(" "));
		List<Integer> userNum = new ArrayList<Integer>();
		try {
			for (String tmp : strNum) {
				int num = Integer.parseInt(tmp);
				if (num > 9 || num <= 0) {
					Exception e = new Exception();
					throw e;
				}
				userNum.add(Integer.parseInt(tmp));
			}
		} catch (Exception e) {
			gameResult += "<올바른 숫자가 입력되지 않았습니다.>\n";
			gameResult += "<숫자 3개를 입력해주세요.(예: 3 6 9) > |종료: EXIT| : ";
			return;
		}

		// 게임 시작
		Play(userNum);

	}

}
