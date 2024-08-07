package minigame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import lombok.Data;
import minigame.utils.Message;
import minigame.utils.Type;

@Data
public class Omok {

	public static final String yellow = "\u001B[33m";
	public static final String blue = "\u001B[34m";
	public static final String exit = "\u001B[0m";

	private List<String> list = new ArrayList<>();
	private final static String row_init = "   0 1 2 3 4 5 6 7 8 9 A B C D E F";
	private final static String row = "□ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □";

	private final static int BOARD_SIZE = 16;

	List<List<Integer>> twhiteStone = new ArrayList<>();
	List<List<Integer>> tblackStone = new ArrayList<>();

	private final boolean isDraw = false;

	private String player1;
	private String player2;
	// 게임에 관한 모든 출력 값을 gameResult 변수로 받음.
	private String gameResult;

	private String blackPlayer;
	private String whitePlayer;

	private String currentTurn;

	public String getResult() {
		String tmp = gameResult;
		gameResult = "";
		return tmp;
	}

	private String winner = null;
	private String loser = null;

	private void currentTurnInit() {
		if (new Random().nextBoolean()) {
			currentTurn = player1;
		} else
			currentTurn = player2;
	}

	public Omok(String player1, String player2) {

		this.player1 = player1;
		this.player2 = player2;

		gameResult = Type.blank;

		gameResult = "<게임을 시작합니다.>\n";
		gameResult += "<" + player1 + " vs " + player2 + ">\n";

		// 차례 정하기
		currentTurnInit();

		gameResult += "<흑돌은 " + currentTurn + "님 입니다.>\n";
		gameResult += "<흑돌이 선공입니다.>\n";

		if (player1.equals(currentTurn)) {
			blackPlayer = player1;
			whitePlayer = player2;
		} else {
			whitePlayer = player1;
			blackPlayer = player2;
		}
		// 보드 초기화
		boardInit();

		// 맵 정보 저장하기
		resetBoard();

		// 흑돌, 백돌 초기화
		stoneInit(twhiteStone);
		stoneInit(tblackStone);

		gameResult += "<좌표를 입력해주세요(ex. A A, 9 9) [종료: EXIT]>: ";
	}

	public void turnNext() {

		if (currentTurn.equals(player1)) {
			currentTurn = player2;
		} else {
			currentTurn = player1;
		}
	}

	public void boardInit() {
		if (list.size() == 0) {
			for (int i = 0; i < BOARD_SIZE; i++) {
				list.add(row);
			}
		}
	}

	public void resetBoard() {
		gameResult += row_init + "\n";
		for (int i = 0x0; i < BOARD_SIZE; i++) {
			String tmp = list.get(i).replace("B", blue + "●" + exit);
			tmp = tmp.replace("W", yellow + "●" + exit);
			gameResult += " " + Integer.toHexString(i) + " " + tmp + "\n";
			// ●
		}
	}

	public boolean isValidNum(int num) {

		if (num < 0)
			return false;
		if (num >= BOARD_SIZE)
			return false;
		return true;

	}

	public int checkCount(List<List<Integer>> stone, int curRow, int curCol, boolean isRight, boolean isLeft, boolean isBottom, boolean isTop) {

		int count = 0;

		curRow += (isRight) ? +1 : (isLeft) ? -1 : 0;
		curCol += (isBottom) ? +1 : (isTop) ? -1 : 0;

		while (isValidNum(curRow) && isValidNum(curCol)) {
			if (stone.get(curRow).get(curCol) != 0) {
				count++;
				curRow += (isRight) ? +1 : (isLeft) ? -1 : 0;
				curCol += (isBottom) ? +1 : (isTop) ? -1 : 0;
			} else if (stone.get(curRow).get(curCol) == 0) {
				break;
			}
		}

		return count;
	}

	public boolean checkWin(List<List<Integer>> tblackStone, int curRow, int curCol) {
		int count = 1;

		// 좌 탐색.
		count += checkCount(tblackStone, curRow, curCol, false, true, false, false);
		// 우 탐색.
		count += checkCount(tblackStone, curRow, curCol, true, false, false, false);

		// 오목 판별.
		if (count >= 5)
			return true;
		count = 1;

		// 위 탐색.
		count += checkCount(tblackStone, curRow, curCol, false, false, false, true);
		// 아래 탐색.
		count += checkCount(tblackStone, curRow, curCol, false, false, true, false);
		if (count >= 5)
			return true;
		count = 1;

		// 왼쪽 위 탐색.
		count += checkCount(tblackStone, curRow, curCol, false, true, false, true);
		// 오른쪽 아래 탐색.
		count += checkCount(tblackStone, curRow, curCol, true, false, true, false);
		if (count >= 5)
			return true;
		count = 1;

		// 오른쪽 위 탐색.
		count += checkCount(tblackStone, curRow, curCol, true, false, false, true);
		// 왼쪽 아래 탐색.
		count += checkCount(tblackStone, curRow, curCol, false, true, true, false);
		if (count >= 5)
			return true;

		return false;
	}

	private void stoneInit(List<List<Integer>> stoneList) {
		// 리스트의 크기를 boardsize(16)으로 모든 변수 값을 (0)으로 초기화
		for (int i = 0; i < BOARD_SIZE; i++) {
			stoneList.add(new ArrayList<Integer>());

			for (int j = 0; j < BOARD_SIZE; j++) {
				stoneList.get(i).add(0);
			}
		}
	}

	public void run(Message msg) {

		gameResult += "\n";

		List<String> select = new ArrayList<String>();
		int sel_r, sel_c;

		select = Arrays.asList(msg.getMsg().trim().split(" "));

		if (select.get(0).equals(Type.exit) || select.get(0).toUpperCase().equals(Type.exit)) {

			gameResult += "<" + currentTurn + " 님이 경기를 포기하였습니다.>\n";
			gameResult += "<게임이 종료됩니다>";

			turnNext();
			victory();

			return;
		}

		try {
			int row = 0;
			int col = 1;
			// 16진수 값을 정수로 변환하여 저장
			sel_r = Integer.parseInt(select.get(row), 16);
			sel_c = Integer.parseInt(select.get(col), 16);
		} catch (Exception e) {
			resetBoard();
			gameResult += "<올바르지 않은 입력입니다. 다시 입력해주세요>\n";
			gameResult += "<좌표를 입력해주세요(ex. A A, 9 9)> [종료: EXIT]: ";

			return;
		}
		// 범위를 벗어난 숫자 입력. 반칙패로 규정
		// 재선택으로 구현 예정
		if (!isValidNum(sel_r) || !isValidNum(sel_c)) {
			resetBoard();
			gameResult += "<올바르지 않은 위치입니다. 다시 입력해주세요>\n";
			gameResult += "<좌표를 입력해주세요(ex. A A, 9 9)> [종료: EXIT]: ";
			return;
		}

		// 흑돌이나 백돌에 이미 등록된 위치라면 반칙패 처리
		// 재선택으로 구현 예정
		if (twhiteStone.get(sel_r).get(sel_c) != 0 || tblackStone.get(sel_r).get(sel_c) != 0) {
			resetBoard();
			gameResult += "<이미 선택한 위치입니다. 다시 입력해주세요>\n";
			gameResult += "<좌표를 입력해주세요(ex. A A, 9 9)> [종료: EXIT]: ";
			return;
		}

		// c는 board에 둔 돌을 업데이트 함.
		char[] c = list.get(sel_c).toCharArray();

		boolean isEnd = false;
		if (currentTurn.equals(blackPlayer)) {
			tblackStone.get(sel_r).set(sel_c, 1);
			c[sel_r * 2] = 'B';
			isEnd = checkWin(tblackStone, sel_r, sel_c);
		} else { // 백돌
			twhiteStone.get(sel_r).set(sel_c, 1);
			c[sel_r * 2] = 'W';
			isEnd = checkWin(twhiteStone, sel_r, sel_c);
		}

		list.set(sel_c, new String(c));

		resetBoard();

		gameResult += "선택한 위치: (" + Integer.toHexString(sel_r).toUpperCase() + " , " + Integer.toHexString(sel_c).toUpperCase() + ")\n";
		if (isEnd) {
			victory();
			return;
		}

		gameResult += "<좌표를 입력해주세요(ex. A A, 9 9)> [종료: EXIT]: ";

		turnNext();
	}

	private void victory() {

		if (player1.equals(currentTurn)) {
			winner = player1;
			loser = player2;
		} else {
			loser = player1;
			winner = player2;
		}
		gameResult += "<승자는 " + winner + "님입니다>\n";

	}

}
