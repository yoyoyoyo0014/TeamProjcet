package team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import lombok.Data;
import program.Program;

@Data
public class Omok implements Program {

	private List<String> list = new ArrayList<>();
	private static Scanner sc = new Scanner(System.in);

	private final static String row_init = "   0 1 2 3 4 5 6 7 8 9 A B C D E F";
	private final static String row = "□ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □";

	private final static int BOARD_SIZE = 16;

	public static void clearConsoleScreen() {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}

	public void resetBoard() {

		if (list.size() == 0) {
			for (int i = 0; i < BOARD_SIZE; i++) {
				list.add(row);
			}
		}

		System.out.println(row_init);
		for (int i = 0x0; i < BOARD_SIZE; i++) {
			System.out.printf(" %X %s\n", i, list.get(i));

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

	@Override
	public void run() {
		resetBoard();

		List<String> select = new ArrayList<String>();
		int sel_r, sel_c;

		List<List<Integer>> twhiteStone = new ArrayList<>();
		List<List<Integer>> tblackStone = new ArrayList<>();
		stoneInit(twhiteStone);
		stoneInit(tblackStone);

		int count = 0;

		while (true) {
			String nowTurn = (count++ % 2 == 0) ? "흑돌" : "백돌";
			System.out.print(nowTurn + " 위치 선택 (예: a 9, 3 3)(종료: exit): ");
			select = Arrays.asList(sc.nextLine().split(" "));
			if (select.get(0).equals("exit")) {
				System.out.println("종료합니다.");
				break;
			}
			int row = 0;
			int col = 1;
			
			// 띄어쓰기 처리
			if (select.get(0).equals("")) {
				row++; col++;
			}

			// 16진수 값을 정수로 변환하여 저장
			sel_r = Integer.parseInt(select.get(row), 16);
			sel_c = Integer.parseInt(select.get(col), 16);

			// 범위를 벗어난 숫자 입력. 반칙패로 규정
			// 재선택으로 구현 예정
			if (!isValidNum(sel_r) || !isValidNum(sel_c)) {
				System.out.println("올바르지 않은 위치입니다." + nowTurn + " 패배<반칙패>");
				return;
			}

			// 흑돌이나 백돌에 이미 등록된 위치라면 반칙패 처리
			// 재선택으로 구현 예정
			if (twhiteStone.get(sel_r).get(sel_c) != 0 || tblackStone.get(sel_r).get(sel_c) != 0) {
				System.out.println("이미 선택한 위치입니다. " + nowTurn + " 패배<반칙패>");
				return;
			}

			// c는 board에 둔 돌을 업데이트 함.
			char[] c = list.get(sel_c).toCharArray();

			boolean isEnd = false;
			if (nowTurn.equals("흑돌")) {
				tblackStone.get(sel_r).set(sel_c, 1);
//				blackStone[sel_r][sel_c]++;
				c[sel_r * 2] = 'B';
				isEnd = checkWin(tblackStone, sel_r, sel_c);
			} else { // 백돌
				twhiteStone.get(sel_r).set(sel_c, 1);
				c[sel_r * 2] = 'W';
				isEnd = checkWin(twhiteStone, sel_r, sel_c);
			}

			list.set(sel_c, new String(c));

			clearConsoleScreen();
			resetBoard();

			System.out.printf("선택한 위치: %X %X\n", sel_r, sel_c);
			if (isEnd) {
				System.out.println("게임이 종료되었습니다. 승자는 <" + nowTurn + ">입니다.");
				break;
			}
		}

	}

	@Override
	public void save(String fileName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void load(String fileName) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// test용 Main
		Omok gm = new Omok();
		gm.run();
	}

}
