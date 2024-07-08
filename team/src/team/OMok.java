package team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import lombok.Data;
import program.Program;

@Data
public class OMok implements Program {

	private List<String> list = new ArrayList<>();
	private static Scanner sc = new Scanner(System.in);

	private final static String row_init = "   0 1 2 3 4 5 6 7 8 9 A B C D E F";
	private final static String row = "□ □ □ □ □ □ □ □ □ □ □ □ □ □ □ □";

	public static void clearConsoleScreen() {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}

	public void resetBoard() {

		if (list.size() == 0) {
			for (int i = 0; i < 16; i++) {
				list.add(row);
			}
		}

		System.out.println(row_init);
		for (int i = 0x0; i < 16; i++) {
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

	public boolean checkWin(int[][] stone, int curRow, int curCol) {
		int count = 1;
		int tmpRow;
		int tmpCol;

		tmpRow = curRow;
		tmpCol = curCol;

		tmpRow--;
		while (tmpRow > 0) { // 좌 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpRow--;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpRow = curRow;
				break;
			}
		}

		tmpRow++;
		while (tmpRow < 16) { // 우 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpRow++;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpRow = curRow;
				break;
			}
		}

		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}

		tmpCol--;
		while (tmpCol > 0) { // 위 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpCol--;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpCol = curCol;
				break;
			}
		}

		tmpCol++;
		while (tmpCol < 16) { // 아래 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpCol++;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpCol = curCol;
				break;
			}
		}

		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}

		tmpRow--;
		tmpCol--;
		while (tmpRow > 0 && tmpCol > 0) { // 위좌 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpRow--;
				tmpCol--;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpRow = curRow;
				tmpCol = curCol;
				break;
			}
		}
		tmpRow++;
		tmpCol++;
		while (tmpRow < 16 && tmpCol < 16) { // 아래우 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpRow++;
				tmpCol++;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpRow = curRow;
				tmpCol = curCol;
				break;
			}
		}

		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}

		tmpRow++;
		tmpCol--;
		while (tmpCol > 0 && tmpRow < 16) { // 위우 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpRow++;
				tmpCol--;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpRow = curRow;
				tmpCol = curCol;
				break;
			}
		}

		tmpRow--;
		tmpCol++;
		while (tmpCol < 16 && tmpRow > 16) { // 아래좌 탐색
			if (stone[tmpRow][tmpCol] != 0) {
				count++;
				tmpRow--;
				tmpCol++;
			} else if (stone[tmpRow][tmpCol] == 0) {
				tmpRow = curRow;
				tmpCol = curCol;
				break;
			}
		}

		if (count >= 5) {
			return true;
		} else {
			count = 1;
		}

		return false;
	}

	@Override
	public void run() {
		resetBoard();

		List<String> select = new ArrayList<String>();
		int sel_r, sel_c;
		int[][] whiteStone = new int[16][16];
		int[][] blackStone = new int[16][16];
		int count = 0;

		while (true) {
			String nowTurn = (count++ % 2 == 0) ? "흑돌" : "백돌";
			System.out.print(nowTurn + " 위치 선택 (종료: exit): ");
			select = Arrays.asList(sc.nextLine().split(" "));
			if (select.get(0).equals("exit")) {
				System.out.println("종료합니다.");
				break;
			}

			// 16진수 값을 정수로 변환하여 저장
			sel_r = Integer.parseInt(select.get(0), 16);
			sel_c = Integer.parseInt(select.get(1), 16);

			// 흑돌이나 백돌에 이미 등록된 위치라면 반칙패 처리
			// 추후 재선택으로 변경 가능
			if (whiteStone[sel_r][sel_c] != 0 || blackStone[sel_r][sel_c] != 0) {
				System.out.println("이미 선택한 위치입니다. " + nowTurn + " 패배<반칙패>");
				return;
			}

			// c는 맵을 돌을 둔 위치를 업데이트하는데 사용되는 변수
			char[] c = list.get(sel_c).toCharArray();

			if (nowTurn.equals("흑돌")) {
				blackStone[sel_r][sel_c]++;
				c[sel_r * 2] = 'B';
				if (checkWin(blackStone, sel_r, sel_c)) {
					System.out.println("흑돌 승리");
					return;
				}
			} else { // 백돌
				whiteStone[sel_r][sel_c]++;
				c[sel_r * 2] = 'W';
				if (checkWin(whiteStone, sel_r, sel_c)) {
					System.out.println("백돌 승리");
					return;
				}
			}

			list.set(sel_c, new String(c));

			clearConsoleScreen();
			resetBoard();

			System.out.println("선택한 위치: " + sel_r + ", " + sel_c);
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
		OMok gm = new OMok();
		gm.run();
	}

}
