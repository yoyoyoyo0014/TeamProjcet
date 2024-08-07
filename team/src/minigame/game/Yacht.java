package minigame.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import lombok.Data;
import minigame.utils.Message;
import minigame.utils.Type;

@Data
public class Yacht {

	public static final String blue = "\u001B[34m";
	public static final String exit = "\u001B[0m";

	static YachtVariable p1 = new YachtVariable();
	static YachtVariable p2 = new YachtVariable();

	static int turn = 1;
	static int reRollCount = 0;

	private String currentTurn;
	private String player1;
	private String player2;

	private String gameResult;

	private String winner = null;
	private String loser = null;
	private boolean isDraw = false;

	private List<String> reDices = new ArrayList<String>();

	public Yacht(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;

		gameResult = Type.blank;

		gameResult = "<게임을 시작합니다.>\n";

		// 게임 룰 출력
		printRule();

		gameResult += "\n<" + player1 + " vs " + player2 + ">\n\n";

		// 차례 정하기
		currentTurnInit();

		gameResult += "<player1은 " + currentTurn + "님 입니다.>\n\n";
		gameResult += "<player1이 선입니다.>\n\n";

		printTest(p1);

	}

	private void printTest(YachtVariable yv) {
		diceRoll(yv.dices);
		print();
		String tmp = "player" + (currentTurn.equals(player1) ? 1 : 2);
		gameResult += "<" + tmp + ":" + currentTurn + ">님 의 차례입니다\n";
		gameResult += "주사위 결과:\n";

		for (int result : yv.dices) {
			gameResult += "[" + result + "] ";
		}

		// 사용자 입력 받기
		gameResult += "\n다시 굴릴 주사위 번호를 입력하세요 (예: 1 2 4, 종료는 0, 남은 기회 : " + (2 - yv.reRollCount) + "번): \n";
	}

	public void turnNext() {
		if (currentTurn.equals(player1)) {
			currentTurn = player2;
		} else {
			currentTurn = player1;
		}
	}

	private void currentTurnInit() {
//		if (new Random().nextBoolean()) {
		currentTurn = player1;
//		} else {
//			currentTurn = player2;
//		}
	}

	public String getResult() {
		String tmp = gameResult;
		gameResult = "";
		return tmp;
	}

	public void run(Message message) {

		String[] input = message.getMsg().split(" ");
		if (input[0].equals(Type.exit) || input[0].toUpperCase().equals(Type.exit)) {

			loser = message.getPName();
			winner = loser.equals(player1)?player2:player1;
			
			gameResult += "<" + loser + " 님이 경기를 포기하였습니다.>\n";
			gameResult += "<게임이 종료됩니다>";

			return;
		}

		if (currentTurn.equals(player1)) {
			if (p1.dices[0] == 0) {
				printTest(p1);
			} else if (p1.reRollCount < 2) {
				diceReroll(p1, message);
				if (p1.reRollCount != 2) {
					gameResult += "\n다시 굴릴 주사위 번호를 입력하세요 (예: 1 2 4, 종료는 0, 남은 기회 : " + (2 - p1.reRollCount) + "번): \n";
				}
			}

			if (p1.reRollCount == 2) {
				gameResult += "\n1~12 중 어디에 넣을 건지를 선택하세요 : ";
				p1.reRollCount++;
			} else if (p1.reRollCount == 3) {
				recPoint(p1, message);
				if (p1.reRollCount == 4) { // 족보에 값이 들어가면 실행
					turnNext();
					
					p1.reRollCount = 0;
				}
			}
		}

		if (currentTurn.equals(player2)) {
			if (p2.dices[0] == 0) {
				printTest(p2);
			} else if (p2.reRollCount < 2) {
				diceReroll(p2, message);
				if (p2.reRollCount != 2) {
					gameResult += "\n다시 굴릴 주사위 번호를 입력하세요 (예: 1 2 4, 종료는 0, 남은 기회 : " + (2 - p2.reRollCount) + "번): \n";
				}
			}
			if (p2.reRollCount == 2) {
				gameResult += "\n1~12 중 어디에 넣을 건지를 선택하세요 : ";
				p2.reRollCount++;
			} else if (p2.reRollCount == 3) {
				recPoint(p2, message);
				if (p2.reRollCount == 4) {
					turnNext();

					p2.reRollCount = 0;
					turn++;

					if (turn <= 12) {
						Message tmp = new Message();
						tmp.setMsg("");
						run(tmp);
					}
				}
				// 12턴이 되면 게임을 종료
				if (turn > 12) {
					turn = 12;

					// 결과 출력
					printFinal();
					victory();
//					reset();

					return;
				}

			}
		}

	}

	private void victory() {
		if (p1.total > p2.total) {
			winner = player1;
			loser = player2;
			gameResult += "승자는 <" + winner + "님입니다>\n";
		} else if (p1.total < p2.total) {
			loser = player1;
			winner = player2;
			gameResult += "승자는 <" + winner + "님입니다>\n";
		} else if (p1.total == p2.total) {
			winner = player1;
			loser = player2;
			gameResult += "무승부입니다.\n";
			isDraw = true;
		}
	}

	public void recPoint(YachtVariable yv, Message msg) {

		String SelHR = "0";

		SelHR = msg.getMsg();

		try {
			int selection = Integer.parseInt(SelHR);
			if (selection < 1 || selection > 12) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			yv.duplicateInput++;
			gameResult += "\n잘못된 입력입니다. 1부터 12까지의 숫자를 입력하세요 : ";
			return;
		}

		switch (SelHR) {
			case "1": {
				if (yv.oneBl == true) { // 족보에 이미 값이 들어가 있으면
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.one = oneCal(yv.dices, yv.one, yv.oneBl);
				yv.oneBl = true;
				break;
			}
			case "2": {
				if (yv.twoBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.two = twoCal(yv.dices, yv.two, yv.twoBl);
				yv.twoBl = true;
				break;
			}
			case "3": {
				if (yv.threeBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.three = threeCal(yv.dices, yv.three, yv.threeBl);
				yv.threeBl = true;
				break;
			}
			case "4": {
				if (yv.fourBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.four = fourCal(yv.dices, yv.four, yv.fourBl);
				yv.fourBl = true;
				break;
			}
			case "5": {
				if (yv.fiveBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.five = fiveCal(yv.dices, yv.five, yv.fiveBl);
				yv.fiveBl = true;
				break;
			}
			case "6": {
				if (yv.sixBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.six = sixCal(yv.dices, yv.six, yv.sixBl);
				yv.sixBl = true;
				break;
			}
			case "7": {
				if (yv.chBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.ch = chCal(yv.dices, yv.ch, yv.chBl);
				yv.chBl = true;
				break;
			}
			case "8": {
				if (yv.fkBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.fk = fkCal(yv.dices, yv.fk, yv.fkBl);
				yv.fkBl = true;
				break;
			}
			case "9": {
				if (yv.fhBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.fh = fhCal(yv.dices, yv.fh, yv.fhBl);
				yv.fhBl = true;
				break;
			}
			case "10": {
				if (yv.ssBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.ss = ssCal(yv.dices, yv.ss, yv.ssBl);
				yv.ssBl = true;
				break;
			}
			case "11": {
				if (yv.lsBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.ls = lsCal(yv.dices, yv.ls, yv.lsBl);
				yv.lsBl = true;
				break;
			}
			case "12": {
				if (yv.yaBl == true) {
					yv.duplicateInput++;
					gameResult += "\n이미 등록되어 있습니다. 다른 족보를 선택해주세요 : ";
					return;
				}
				yv.ya = yaCal(yv.dices, yv.ya, yv.yaBl);
				yv.yaBl = true;
				break;
			}
		}

		yv.reRollCount++;
		yv.duplicateInput = 0;

		// 보너스 점수 획득 가능한지 계산
		yv.oneToSix = yv.one + yv.two + yv.three + yv.four + yv.five + yv.six;
		if (yv.oneToSix >= 63) {
			yv.bonusBl = true;
			yv.bonus = 35;
		}

		for (int i = 0; i < yv.dices.length; i++) {
			yv.dices[i] = 0;
		}

		yv.total = totalCal(yv);

	}

	private void diceReroll(YachtVariable yv, Message message) {

		Random random = new Random();
		String input = message.getMsg();
		// 0을 입력하면 종료

		if (input.equals("0")) {
			yv.reRollCount = 2;
			return;
		}

		// 입력이 비어 있으면 다시 입력받기
		if (input.isEmpty()) {
			gameResult += "입력이 비어 있습니다. 다시 입력하세요.\n";
			return; // 잘못된 입력이므로 다시 입력을 받도록 함
		}

		String[] inputArray = input.split(" ");

		// 크기가 5인 배열 선언 및 초기화
		int[] RerollDices = new int[5];

		for (int i = 0; i < inputArray.length && i < RerollDices.length; i++) {
			try {
				int diceIndex = Integer.parseInt(inputArray[i]);
				if (diceIndex < 1 || diceIndex > 5) {
					throw new NumberFormatException(); // 1~5 사이의 숫자가 아니면 예외 발생
				}
				RerollDices[i] = diceIndex;
			} catch (NumberFormatException e) {
				gameResult += "잘못된 입력입니다. 1부터 5까지의 숫자를 공백으로 구분하여 입력하세요.\n";

				return;
			}
		}

		for (int i = 0; i < RerollDices.length; i++) {
			if (RerollDices[i] != 0) {
				gameResult += RerollDices[i] + "번 ";
			}
		}
		gameResult += "주사위를 다시 돌립니다.\n";

		for (int i = 0; i < 5; i++) {
			if (RerollDices[i] == 0) {
				break;
			}
			yv.dices[RerollDices[i] - 1] = random.nextInt(6) + 1; // 1부터 6까지의 랜덤 숫자 생성
		}

		print();

		gameResult += "다시 굴린 주사위 결과:\n";
		for (int result : yv.dices) {
			gameResult += "[" + result + "] ";
		}

		yv.reRollCount++;

	}

	private static void diceRoll(int[] dices) {
		// 주사위를 굴리기 위한 Random 객체 생성
		Random random = new Random();

		// 주사위를 5번 굴려서 결과를 배열에 저장
		for (int i = 0; i < dices.length; i++) {
			dices[i] = random.nextInt(6) + 1; // 1부터 6까지의 랜덤 숫자 생성
		}
	}

	private void print() {
		gameResult += "\n--------------------------------------------------------------\n";
		gameResult += " ================================== \n";
		gameResult += "|\t   Turn " + turn + "/12  \t\t   |\n";
		gameResult += "|==================================|\n";
		if (p1.dices[0] > 0) {
			gameResult += "| Categorries\t |" + blue + "player1" + exit + " |player2 |\n";
		} else if (p2.dices[0] > 0) {
			gameResult += "| Categorries\t |player1 |" + blue + "player2 " + exit + "|\n";
		} else {
			gameResult += "| Categorries\t |player1 |player2 |\n";
		}
		gameResult += "|==================================|\n";

		// 1 ~ 6
		if (p1.oneBl == true && p2.oneBl == true) {
			gameResult += "|1. Aces\t | " + blue + oneCal(p1.dices, p1.one, p1.oneBl) + exit + "\t     | " + blue
					+ oneCal(p2.dices, p2.one, p2.oneBl) + exit + "\t\t   |\n";
		} else if (p1.oneBl == true) {
			gameResult += "|1. Aces\t | " + blue + oneCal(p1.dices, p1.one, p1.oneBl) + exit + "\t     | "
					+ oneCal(p2.dices, p2.one, p2.oneBl) + "\t   |\n";
		} else if (p2.oneBl == true) {
			gameResult += "|1. Aces\t | " + oneCal(p1.dices, p1.one, p1.oneBl) + "\t  | " + blue
					+ oneCal(p2.dices, p2.one, p2.oneBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|1. Aces\t | " + oneCal(p1.dices, p1.one, p1.oneBl) + "\t  | "
					+ oneCal(p2.dices, p2.one, p2.oneBl) + "\t   |\n";
		}

		if (p1.twoBl == true && p2.twoBl == true && p1.two >= 10 && p2.two >= 10) {
			gameResult += "|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t    | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t    |\n";
		} else if (p1.twoBl == true && p2.twoBl == true && p1.two >= 10 && p2.two < 10) {
			gameResult += "|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t    | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t     |\n";
		} else if (p1.twoBl == true && p2.twoBl == true && p1.two < 10 && p2.two >= 10) {
			gameResult += "|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t     | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t    |\n";
		} else if (p1.twoBl == true && p2.twoBl == true) {
			gameResult += "|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t     | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t     |\n";
		} else if (p1.twoBl == true && p1.two >= 10) {
			gameResult += "|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t    | "
					+ twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |\n";
		} else if (p1.twoBl == true) {
			gameResult += "|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t     | "
					+ twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |\n";
		} else if (p2.twoBl == true) {
			gameResult += "|2. Deuces\t | " + twoCal(p1.dices, p1.two, p1.twoBl) + "\t  | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|2. Deuces\t | " + twoCal(p1.dices, p1.two, p1.twoBl) + "\t  | "
					+ twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |\n";
		}

		if (p1.threeBl == true && p2.threeBl == true && p1.three >= 10 && p2.three >= 10) {
			gameResult += "|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t    | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t    |\n";
		} else if (p1.threeBl == true && p2.threeBl == true && p1.three >= 10 && p2.three < 10) {
			gameResult += "|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t    | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t     |\n";
		} else if (p1.threeBl == true && p2.threeBl == true && p1.three < 10 && p2.three >= 10) {
			gameResult += "|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t     | "
					+ blue + threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t    |\n";
		} else if (p1.threeBl == true && p2.threeBl == true) {
			gameResult += "|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t     | "
					+ blue + threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t     |\n";
		} else if (p1.threeBl == true && p1.three >= 10) {
			gameResult += "|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t    | "
					+ threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |\n";
		} else if (p1.threeBl == true) {
			gameResult += "|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t     | "
					+ threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |\n";
		} else if (p2.threeBl == true) {
			gameResult += "|3. Threes\t | " + threeCal(p1.dices, p1.three, p1.threeBl) + "\t  | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|3. Threes\t | " + threeCal(p1.dices, p1.three, p1.threeBl) + "\t  | "
					+ threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |\n";
		}

		if (p1.fourBl == true && p2.fourBl == true && p1.four >= 10 && p2.four >= 10) {
			gameResult += "|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t    | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t    |\n";
		} else if (p1.fourBl == true && p2.fourBl == true && p1.four >= 10 && p2.four < 10) {
			gameResult += "|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t    | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t     |\n";
		} else if (p1.fourBl == true && p2.fourBl == true && p1.four < 10 && p2.four >= 10) {
			gameResult += "|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t     | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t    |\n";
		} else if (p1.fourBl == true && p2.fourBl == true) {
			gameResult += "|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t     | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t     |\n";
		} else if (p1.fourBl == true && p1.four >= 10) {
			gameResult += "|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t    | "
					+ fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |\n";
		} else if (p1.fourBl == true) {
			gameResult += "|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t     | "
					+ fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |\n";
		} else if (p2.fourBl == true) {
			gameResult += "|4. Fours\t | " + fourCal(p1.dices, p1.four, p1.fourBl) + "\t  | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|4. Fours\t | " + fourCal(p1.dices, p1.four, p1.fourBl) + "\t  | "
					+ fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |\n";
		}

		if (p1.fiveBl == true && p2.fiveBl == true && p1.five >= 10 && p2.five >= 10) {
			gameResult += "|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t    | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t    |\n";
		} else if (p1.fiveBl == true && p2.fiveBl == true && p1.five >= 10 && p2.five < 10) {
			gameResult += "|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t    | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t     |\n";
		} else if (p1.fiveBl == true && p2.fiveBl == true && p1.five < 10 && p2.five >= 10) {
			gameResult += "|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t     | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t    |\n";
		} else if (p1.fiveBl == true && p2.fiveBl == true) {
			gameResult += "|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t     | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t     |\n";
		} else if (p1.fiveBl == true && p1.five >= 10) {
			gameResult += "|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t    | "
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |\n";
		} else if (p1.fiveBl == true) {
			gameResult += "|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t     | "
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |\n";
		} else if (p2.fiveBl == true) {
			gameResult += "|5. Fives\t | " + fiveCal(p1.dices, p1.five, p1.fiveBl) + "\t  | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|5. Fives\t | " + fiveCal(p1.dices, p1.five, p1.fiveBl) + "\t  | "
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |\n";
		}

		if (p1.sixBl == true && p2.sixBl == true && p1.six >= 10 && p2.six >= 10) {
			gameResult += "|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t    | " + blue
					+ sixCal(p2.dices, p2.six, p2.sixBl) + exit + "\t    |\n";
		} else if (p1.sixBl == true && p2.sixBl == true && p1.six >= 10 && p2.six < 10) {
			gameResult += "|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t    | " + blue
					+ sixCal(p2.dices, p2.six, p2.sixBl) + exit + "\t     |\n";
		} else if (p1.sixBl == true && p2.sixBl == true && p1.six < 10 && p2.six >= 10) {
			gameResult += "|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t     | " + blue
					+ sixCal(p2.dices, p2.six, p2.sixBl) + exit + "\t    |\n";
		} else if (p1.sixBl == true && p2.sixBl == true) {
			gameResult += "|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t     | " + blue
					+ sixCal(p2.dices, p2.six, p2.sixBl) + exit + "\t     |\n";
		} else if (p1.sixBl == true && p1.six >= 10) {
			gameResult += "|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t    | "
					+ sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |\n";
		} else if (p1.sixBl == true) {
			gameResult += "|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t     | "
					+ sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |\n";
		} else if (p2.sixBl == true) {
			gameResult += "|6. Sixes\t | " + sixCal(p1.dices, p1.six, p1.sixBl) + "\t  | " + blue
					+ sixCal(p2.dices, p2.six, p2.sixBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|6. Sixes\t | " + sixCal(p1.dices, p1.six, p1.sixBl) + "\t  | "
					+ sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |\n";
		}
		gameResult += "|==================================|\n";

		// 보너스
		if (p1.bonusBl == true && p2.bonusBl == true) {
			gameResult += "|Subtotal\t | " + blue + p1.oneToSix + "/63" + exit + "\t | " + blue + p2.oneToSix + "/63"
					+ exit + "\t |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + blue + "+" + bonusCal(p1.bonusBl) + exit + "\t\t  | " + blue + "+"
					+ bonusCal(p2.bonusBl) + exit + "\t   |\n";
		} else if (p1.bonusBl == true && p2.oneToSix >= 10) {
			gameResult += "|Subtotal\t | " + blue + p1.oneToSix + "/63" + exit + "\t | " + p2.oneToSix + "/63"
					+ "  |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + blue + "+" + bonusCal(p1.bonusBl) + exit + "\t\t  | " + "+"
					+ bonusCal(p2.bonusBl) + "\t   |\n";
		} else if (p1.bonusBl == true && p2.oneToSix < 10) {
			gameResult += "|Subtotal\t | " + blue + p1.oneToSix + "/63" + exit + "\t | " + p2.oneToSix + "/63"
					+ "   |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + blue + "+" + bonusCal(p1.bonusBl) + exit + "\t\t  | " + "+"
					+ bonusCal(p2.bonusBl) + "\t   |\n";
		} else if (p2.bonusBl == true && p1.oneToSix >= 10) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63" + "  | " + blue + p2.oneToSix + "/63" + exit + "  |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + blue + "+" + bonusCal(p2.bonusBl)
					+ exit + "\t   |\n";
		} else if (p2.bonusBl == true && p1.oneToSix < 10) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63" + "\t  | " + blue + p2.oneToSix + "/63" + exit
					+ "  |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + blue + "+" + bonusCal(p2.bonusBl)
					+ exit + "\t   |\n";
		} else if (p1.oneToSix >= 10 && p2.oneToSix >= 10) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63  | " + p2.oneToSix + "/63  |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl)
					+ "\t   |\n";
		} else if (p1.oneToSix >= 10 && p2.oneToSix < 10) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63  | " + p2.oneToSix + "/63   |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl)
					+ "\t   |\n";
		} else if (p1.oneToSix < 10 && p2.oneToSix >= 10) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63   | " + p2.oneToSix + "/63  |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl)
					+ "\t   |\n";
		} else {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63" + "\t  | " + p2.oneToSix + "/63   |\n";
			gameResult += "|----------------------------------|\n";
			gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl)
					+ "\t   |\n";
		}

		gameResult += "|==================================|\n";

		// choice
		if (p1.chBl == true && p2.chBl == true && p1.ch >= 10 && p2.ch >= 10) {
			gameResult += "|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t    | " + blue
					+ chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t    |\n";
		} else if (p1.chBl == true && p2.chBl == true && p1.ch >= 10 && p2.ch < 10) {
			gameResult += "|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t    | " + blue
					+ chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t     |\n";
		} else if (p1.chBl == true && p2.chBl == true && p1.ch < 10 && p2.ch >= 10) {
			gameResult += "|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t     | " + blue
					+ chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t    |\n";
		} else if (p1.chBl == true && p2.chBl == true) {
			gameResult += "|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t     | " + blue
					+ chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t     |\n";
		} else if (p1.chBl == true && p1.ch >= 10) {
			gameResult += "|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t    | "
					+ chCal(p2.dices, p2.ch, p2.chBl) + "\t   |\n";
		} else if (p1.chBl == true) {
			gameResult += "|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t     | "
					+ chCal(p2.dices, p2.ch, p2.chBl) + "\t   |\n";
		} else if (p2.chBl == true) {
			gameResult += "|7. Choice\t | " + chCal(p1.dices, p1.ch, p1.chBl) + "\t  | " + blue
					+ chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|7. Choice\t | " + chCal(p1.dices, p1.ch, p1.chBl) + "\t  | "
					+ chCal(p2.dices, p2.ch, p2.chBl) + "\t   |\n";
		}
		gameResult += "|==================================|\n";

		// 특수 족보
		if (p1.fkBl == true && p2.fkBl == true && p1.fk >= 10 && p2.fk >= 10) {
			gameResult += "|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t    | " + blue
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + exit + "\t    |\n";
		} else if (p1.fkBl == true && p2.fkBl == true && p1.fk >= 10 && p2.fk < 10) {
			gameResult += "|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t    | " + blue
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + exit + "\t     |\n";
		} else if (p1.fkBl == true && p2.fkBl == true && p1.fk < 10 && p2.fk >= 10) {
			gameResult += "|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t     | " + blue
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + exit + "\t    |\n";
		} else if (p1.fkBl == true && p2.fkBl == true) {
			gameResult += "|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t     | " + blue
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + exit + "\t     |\n";
		} else if (p1.fkBl == true && p1.fk >= 10) {
			gameResult += "|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t    | "
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |\n";
		} else if (p1.fkBl == true) {
			gameResult += "|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t     | "
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |\n";
		} else if (p2.fkBl == true) {
			gameResult += "|8. 4 of a Kind  | " + fkCal(p1.dices, p1.fk, p1.fkBl) + "\t  | " + blue
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|8. 4 of a Kind  | " + fkCal(p1.dices, p1.fk, p1.fkBl) + "\t  | "
					+ fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |\n";
		}

		if (p1.fhBl == true && p2.fhBl == true && p1.fh >= 10 && p2.fh >= 10) {
			gameResult += "|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t    | " + blue
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + exit + "\t    |\n";
		} else if (p1.fhBl == true && p2.fhBl == true && p1.fh >= 10 && p2.fh < 10) {
			gameResult += "|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t    | " + blue
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + exit + "\t     |\n";
		} else if (p1.fhBl == true && p2.fhBl == true && p1.fh < 10 && p2.fh >= 10) {
			gameResult += "|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t     | " + blue
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + exit + "\t    |\n";
		} else if (p1.fhBl == true && p2.fhBl == true) {
			gameResult += "|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t     | " + blue
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + exit + "\t     |\n";
		} else if (p1.fhBl == true && p1.fh >= 10) {
			gameResult += "|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t    | "
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |\n";
		} else if (p1.fhBl == true) {
			gameResult += "|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t     | "
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |\n";
		} else if (p2.fhBl == true) {
			gameResult += "|9. Full House   | " + fhCal(p1.dices, p1.fh, p1.fhBl) + "\t  | " + blue
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|9. Full House   | " + fhCal(p1.dices, p1.fh, p1.fhBl) + "\t  | "
					+ fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |\n";
		}

		if (p1.ssBl == true && p2.ssBl == true && p1.ss >= 10 && p2.ss >= 10) {
			gameResult += "|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t    | " + blue
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + exit + "\t    |\n";
		} else if (p1.ssBl == true && p2.ssBl == true && p1.ss >= 10 && p2.ss < 10) {
			gameResult += "|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t    | " + blue
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + exit + "\t     |\n";
		} else if (p1.ssBl == true && p2.ssBl == true && p1.ss < 10 && p2.ss >= 10) {
			gameResult += "|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t     | " + blue
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + exit + "\t    |\n";
		} else if (p1.ssBl == true && p2.ssBl == true) {
			gameResult += "|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t     | " + blue
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + exit + "\t     |\n";
		} else if (p1.ssBl == true && p1.ss >= 10) {
			gameResult += "|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t    | "
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |\n";
		} else if (p1.ssBl == true) {
			gameResult += "|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t     | "
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |\n";
		} else if (p2.ssBl == true) {
			gameResult += "|10. S. Straight | " + ssCal(p1.dices, p1.ss, p1.ssBl) + "\t  | " + blue
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|10. S. Straight | " + ssCal(p1.dices, p1.ss, p1.ssBl) + "\t  | "
					+ ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |\n";
		}

		if (p1.lsBl == true && p2.lsBl == true && p1.ls >= 10 && p2.ls >= 10) {
			gameResult += "|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t    | " + blue
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + exit + "\t    |\n";
		} else if (p1.lsBl == true && p2.lsBl == true && p1.ls >= 10 && p2.ls < 10) {
			gameResult += "|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t    | " + blue
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + exit + "\t     |\n";
		} else if (p1.lsBl == true && p2.lsBl == true && p1.ls < 10 && p2.ls >= 10) {
			gameResult += "|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t     | " + blue
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + exit + "\t    |\n";
		} else if (p1.lsBl == true && p2.lsBl == true) {
			gameResult += "|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t     | " + blue
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + exit + "\t     |\n";
		} else if (p1.lsBl == true && p1.ls >= 10) {
			gameResult += "|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t    | "
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |\n";
		} else if (p1.lsBl == true) {
			gameResult += "|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t     | "
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |\n";
		} else if (p2.lsBl == true) {
			gameResult += "|11. L. Straight | " + lsCal(p1.dices, p1.ls, p1.lsBl) + "\t  | " + blue
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|11. L. Straight | " + lsCal(p1.dices, p1.ls, p1.lsBl) + "\t  | "
					+ lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |\n";
		}

		if (p1.yaBl == true && p2.yaBl == true && p1.ya >= 10 && p2.ya >= 10) {
			gameResult += "|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t    | " + blue
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t    |\n";
		} else if (p1.yaBl == true && p2.yaBl == true && p1.ya >= 10 && p2.ya < 10) {
			gameResult += "|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t    | " + blue
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t     |\n";
		} else if (p1.yaBl == true && p2.yaBl == true && p1.ya < 10 && p2.ya >= 10) {
			gameResult += "|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t     | " + blue
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t    |\n";
		} else if (p1.yaBl == true && p2.yaBl == true) {
			gameResult += "|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t     | " + blue
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t     |\n";
		} else if (p1.yaBl == true && p1.ya >= 10) {
			gameResult += "|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t    | "
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + "\t   |\n";
		} else if (p1.yaBl == true) {
			gameResult += "|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t     | "
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + "\t   |\n";
		} else if (p2.yaBl == true) {
			gameResult += "|12. Yacht\t | " + yaCal(p1.dices, p1.ya, p1.yaBl) + "\t  | " + blue
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t\t   |\n";
		} else {
			gameResult += "|12. Yacht\t | " + yaCal(p1.dices, p1.ya, p1.yaBl) + "\t  | "
					+ yaCal(p2.dices, p2.ya, p2.yaBl) + "\t   |\n";
		}

		// 합계 출력
		gameResult += "|==================================|\n";
		gameResult += "| Total\t\t | " + p1.total + "\t  | " + p2.total + "\t   |\n";
		gameResult += " ================================== \n";
	}

	private static int oneCal(int[] dices, int one, boolean oneBl) {
		// 숫자 1의 개수를 세기 위한 변수 선언
		int count = 0;

		if (oneBl == true) {
			return one;
		}

		// 배열을 순회하면서 숫자 1의 개수를 센다
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == 1) {
				count++;
			}
		}

		return count;
	}

	private static int twoCal(int[] dices, int two, boolean twoBl) {
		// 숫자 2의 개수를 세기 위한 변수 선언
		int count = 0;

		if (twoBl == true) {
			return two;
		}

		// 배열을 순회하면서 숫자 2의 개수를 센다
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == 2) {
				count++;
			}
		}

		return count * 2;
	}

	private static int threeCal(int[] dices, int three, boolean threeBl) {
		// 숫자 3의 개수를 세기 위한 변수 선언
		int count = 0;

		if (threeBl == true) {
			return three;
		}

		// 배열을 순회하면서 숫자 3의 개수를 센다
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == 3) {
				count++;
			}
		}

		return count * 3;
	}

	private static int fourCal(int[] dices, int four, boolean fourBl) {
		// 숫자 4의 개수를 세기 위한 변수 선언
		int count = 0;

		if (fourBl == true) {
			return four;
		}

		// 배열을 순회하면서 숫자 4의 개수를 센다
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == 4) {
				count++;
			}
		}

		return count * 4;
	}

	private static int fiveCal(int[] dices, int five, boolean fiveBl) {
		// 숫자 5의 개수를 세기 위한 변수 선언
		int count = 0;

		if (fiveBl == true) {
			return five;
		}

		// 배열을 순회하면서 숫자 5의 개수를 센다
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == 5) {
				count++;
			}
		}

		return count * 5;
	}

	private static int sixCal(int[] dices, int six, boolean sixBl) {
		// 숫자 6의 개수를 세기 위한 변수 선언
		int count = 0;

		if (sixBl == true) {
			return six;
		}

		// 배열을 순회하면서 숫자 6의 개수를 센다
		for (int i = 0; i < dices.length; i++) {
			if (dices[i] == 6) {
				count++;
			}
		}

		return count * 6;
	}

	private static int bonusCal(boolean bonusBl) {
		if (bonusBl == true) {
			return 35;
		}

		return 0;
	}

	private static int chCal(int[] dices, int ch, boolean chBl) {
		int sum = 0;

		if (chBl == true) {
			return ch;
		}

		for (int i = 0; i < dices.length; i++) {
			sum += dices[i];
		}

		return sum;
	}

	private static int fkCal(int[] dices, int fk, boolean fkBl) {
		// 각 숫자의 빈도를 저장할 배열 (1부터 6까지이므로 크기는 6)
		int[] count = new int[6];
		int sum = 0;

		if (fkBl == true) {
			return fk;
		}

		if (dices[0] == 0) {
			return 0;
		}

		// 빈도 계산
		for (int num : dices) {
			count[num - 1]++;
		}

		// 같은 숫자가 3개 이상 있는지 확인
		boolean hasFourOrMore = false;
		for (int c : count) {
			if (c >= 4) {
				hasFourOrMore = true;
				break;
			}
		}

		if (hasFourOrMore == true) {
			for (int i = 0; i < dices.length; i++) {
				sum += dices[i];
			}
		}

		return sum;
	}

	private static int fhCal(int[] dices, int fh, boolean fhBl) {
		// 각 숫자의 빈도를 저장할 배열 (1부터 6까지이므로 크기는 6)
		int[] count = new int[6];
		int sum = 0;

		if (fhBl == true) {
			return fh;
		}

		if (dices[0] == 0) {
			return 0;
		}

		// 빈도 계산
		for (int num : dices) {
			count[num - 1]++;
		}

		// 같은 숫자가 3개 이상 있는지 확인
		boolean hasTwoOrMore = false;
		boolean hasThreeOrMore = false;
		for (int c : count) {
			if (c == 3) {
				hasThreeOrMore = true;
			} else if (c == 2) {
				hasTwoOrMore = true;
			}
		}

		boolean isFullHouse = hasThreeOrMore && hasTwoOrMore;

		if (isFullHouse == true) {
			for (int i = 0; i < dices.length; i++) {
				sum += dices[i];
			}
		}

		return sum;
	}

	private static int ssCal(int[] dices, int ss, boolean ssBl) {
		int[] straight = new int[5];
		int value = 0;

		if (ssBl == true) {
			return ss;
		}

		for (int i = 0; i < dices.length; i++) {
			straight[i] = dices[i];
		}

		Arrays.sort(straight);

		// 중복 제거를 위한 Set 사용
		Set<Integer> uniqueNumbersSet = new LinkedHashSet<>();
		for (int num : straight) {
			uniqueNumbersSet.add(num);
		}

		// Set을 배열로 변환
		int[] uniqueNumbers = new int[uniqueNumbersSet.size()];
		int index = 0;
		for (int num : uniqueNumbersSet) {
			uniqueNumbers[index++] = num;
		}

		// 연속된 숫자가 4개인지 확인
		boolean hasFourInARow = false;
		for (int i = 0; i <= uniqueNumbers.length - 4; i++) {
			if (uniqueNumbers[i] + 1 == uniqueNumbers[i + 1] && uniqueNumbers[i] + 2 == uniqueNumbers[i + 2]
					&& uniqueNumbers[i] + 3 == uniqueNumbers[i + 3]) {
				hasFourInARow = true;
				break;
			}
		}

		if (hasFourInARow == true) {
			value = 15;
		}

		return value;
	}

	private static int lsCal(int[] dices, int ls, boolean lsBl) {
		int[] straight = new int[5];
		int value = 0;

		if (lsBl == true) {
			return ls;
		}

		for (int i = 0; i < dices.length; i++) {
			straight[i] = dices[i];
		}
		Arrays.sort(straight);

		boolean hasFiveInARow = false;
		for (int i = 0; i <= straight.length - 5; i++) {
			if (straight[i] + 1 == straight[i + 1] && straight[i] + 2 == straight[i + 2]
					&& straight[i] + 3 == straight[i + 3] && straight[i] + 4 == straight[i + 4]) {
				hasFiveInARow = true;
				break;
			}
		}

		if (hasFiveInARow == true) {
			value = 30;
		}

		return value;
	}

	private static int yaCal(int[] dices, int ya, boolean yaBl) {
		// 각 숫자의 빈도를 저장할 배열 (1부터 6까지이므로 크기는 6)
		int[] count = new int[6];
		int value = 0;

		if (yaBl == true) {
			return ya;
		}

		if (dices[0] == 0) {
			return 0;
		}

		// 빈도 계산
		for (int num : dices) {
			count[num - 1]++;
		}

		// 같은 숫자가 3개 이상 있는지 확인
		boolean hasFiveOrMore = false;
		for (int c : count) {
			if (c >= 5) {
				hasFiveOrMore = true;
				break;
			}
		}

		if (hasFiveOrMore == true) {
			value = 50;
		}

		return value;
	}

	private static int totalCal(YachtVariable yv) {
		int total = yv.one + yv.two + yv.three + yv.four + yv.five + yv.six + yv.bonus + yv.ch + yv.fk + yv.fh + yv.ss
				+ yv.ls + yv.ya;

		return total;
	}

	private void printRule() {
		gameResult += "1. 주사위 5개를 던진다.\n";
		gameResult += "2. 이 중 원하는 주사위들은 남겨두고, 나머지 주사위들을 다시 던진다. \n다시 던지기는 한 라운드에 2번까지 가능하며, 앞에서 던지지 않았던 주사위도 원한다면 다시 던질 수 있다.\n";
		gameResult += "3. 주사위 던지기가 끝난 후 나온 최종 조합으로, 아래 제시된 족보 중 아직까지 기록되지 않은 하나를 반드시 선택하여, 점수판에 기록한다.\n";
		gameResult += "4. 만약 조건에 만족하지 않는 족보를 선택하는 경우, 선택한 족보의 점수칸에 0점으로 기록된다.\n";
		gameResult += "5. 모든 플레이어가 점수판을 모두 채우면 게임이 종료되고, 점수 총합이 가장 높은 플레이어가 승리한다.\n\n";

		gameResult += " ======================================================================= \n";
		gameResult += "|\t\t\t\t족보\t\t\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| 이름\t\t| 설명\t\t\t\t| 예시\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| Aces\t\t| " + "1이 나온 주사위 눈의 총합. 최대 5점.\t" + "|[1][1][1][5][6] => 3점\t|\n";
		gameResult += "| Deuces\t| " + "2가 나온 주사위 눈의 총합. 최대 10점.\t" + "|[2][2][2][5][6] => 6점\t|\n";
		gameResult += "| Threes\t| " + "3이 나온 주사위 눈의 총합. 최대 15점.\t" + "|[3][3][3][5][6] => 9점\t|\n";
		gameResult += "| Fours\t\t| " + "4가 나온 주사위 눈의 총합. 최대 20점.\t" + "|[1][2][4][4][4] => 12점\t|\n";
		gameResult += "| Fives\t\t| " + "5가 나온 주사위 눈의 총합. 최대 25점.\t" + "|[1][2][5][5][5] => 15점\t|\n";
		gameResult += "| Sixes\t\t| " + "6이 나온 주사위 눈의 총합. 최대 30점.\t" + "|[1][2][6][6][6] => 18점\t|\n";
		gameResult += " ======================================================================= \n";
		gameResult += "상단 항목의 점수 합계가 63점 이상일 때, 보너스 점수 35점을 추가로 얻는다.\n";

		gameResult += " ======================================================================= \n";
		gameResult += "|\t\t\t\t족보\t\t\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| 이름\t\t| 설명\t\t\t\t| 예시\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| Choice\t| " + "주사위 눈 5개의 총합. 최대 30점.\t" + "|[3][4][5][6][6] => 24점\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| 4 of a Kind\t| " + "동일한 주사위 눈이 4개 이상일 때,\t" + "|[5][6][6][6][6] => 29점\t|\n";
		gameResult += "|\t\t| " + "주사위 눈 5개의 총합. 최대 30점.\t" + "|\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "|\t\t| " + "주사위를 3개, 2개로 묶었을 때 각각의\t" + "|\t\t\t|\n";
		gameResult += "| Full House\t| " + "묶음 안에서 주사위 눈이 서로 동일할 때,\t" + "|[5][5][6][6][6] => 28점\t|\n";
		gameResult += "|\t\t| " + "주사위 눈 5개의 총합. 최대 30점.\t" + "|\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| S. Straight\t| " + "이어지는 주사위 눈이 4개 이상일 때.\t" + "|[1][2][3][4][6] => 15점\t|\n";
		gameResult += "|\t\t| " + "고정 15점.\t\t\t" + "|\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| L. Straight\t| " + "이어지는 주사위 눈이 5개일 때.\t" + "|[1][2][3][4][5] => 30점\t|\n";
		gameResult += "|\t\t| " + "고정 30점.\t\t\t" + "|\t\t\t|\n";
		gameResult += "|=======================================================================|\n";
		gameResult += "| Yacht\t\t| " + "동일한 주사위 눈이 5개일 때.\t\t" + "|[1][1][1][1][1] => 50점\t|\n";
		gameResult += "|\t\t| " + "고정 50점.\t\t\t" + "|\t\t\t|\n";
		gameResult += " ======================================================================= \n\n";
	}

	private void printFinal() {
		gameResult += "\n--------------------------------------------------------------\n";
		gameResult += " ================================== \n";
		gameResult += "|\t   Turn " + turn + "/12  \t\t   |\n";
		gameResult += "|==================================|\n";
		gameResult += "| Categorries\t |player1 |player2 |\n";
		gameResult += "|==================================|\n";

		// 1 ~ 6
		gameResult += "|1. Aces\t | " + oneCal(p1.dices, p1.one, p1.oneBl) + "\t  | "
				+ oneCal(p2.dices, p2.one, p2.oneBl) + "\t   |\n";

		gameResult += "|2. Deuces\t | " + twoCal(p1.dices, p1.two, p1.twoBl) + "\t  | "
				+ twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |\n";

		gameResult += "|3. Threes\t | " + threeCal(p1.dices, p1.three, p1.threeBl) + "\t  | "
				+ threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |\n";

		gameResult += "|4. Fours\t | " + fourCal(p1.dices, p1.four, p1.fourBl) + "\t  | "
				+ fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |\n";

		gameResult += "|5. Fives\t | " + fiveCal(p1.dices, p1.five, p1.fiveBl) + "\t  | "
				+ fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |\n";

		gameResult += "|6. Sixes\t | " + sixCal(p1.dices, p1.six, p1.sixBl) + "\t  | "
				+ sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |\n";

		gameResult += "|==================================|\n";

		// 보너스
		if (p1.oneToSix > 9 && p2.oneToSix > 9) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63" + "  | " + p2.oneToSix + "/63  |\n";
		} else if (p1.oneToSix > 9 && p2.oneToSix < 10) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63" + "  | " + p2.oneToSix + "/63   |\n";
		} else if (p1.oneToSix < 10 && p2.oneToSix > 9) {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63\t" + "  | " + p2.oneToSix + "/63  |\n";
		} else {
			gameResult += "|Subtotal\t | " + p1.oneToSix + "/63\t" + "  | " + p2.oneToSix + "/63   |\n";
		}

		gameResult += "|----------------------------------|\n";
		gameResult += "|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl)
				+ "\t   |\n";
		gameResult += "|==================================|\n";

		// choice
		gameResult += "|7. Choice\t | " + chCal(p1.dices, p1.ch, p1.chBl) + "\t  | " + chCal(p2.dices, p2.ch, p2.chBl)
				+ "\t   |\n";
		gameResult += "|==================================|\n";

		// 특수 족보
		gameResult += "|8. 4 of a Kind  | " + fkCal(p1.dices, p1.fk, p1.fkBl) + "\t  | "
				+ fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |\n";

		gameResult += "|9. Full House   | " + fhCal(p1.dices, p1.fh, p1.fhBl) + "\t  | "
				+ fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |\n";

		gameResult += "|10. S. Straight | " + ssCal(p1.dices, p1.ss, p1.ssBl) + "\t  | "
				+ ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |\n";

		gameResult += "|11. L. Straight | " + lsCal(p1.dices, p1.ls, p1.lsBl) + "\t  | "
				+ lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |\n";

		gameResult += "|12. Yacht\t | " + yaCal(p1.dices, p1.ya, p1.yaBl) + "\t  | " + yaCal(p2.dices, p2.ya, p2.yaBl)
				+ "\t   |\n";

		// 합계 출력
		gameResult += "|==================================|\n";

		if (p1.total > 99 && p2.total > 99) {
			gameResult += "| Total\t\t | " + blue + p1.total + exit + "\t\t  | " + blue + p2.total + exit + "\t   |\n";
		} else if (p1.total > 99 && p2.total < 100) {
			gameResult += "| Total\t\t | " + blue + p1.total + exit + "\t\t  | " + blue + p2.total + exit
					+ "\t\t   |\n";
		} else if (p1.total < 100 && p2.total > 99) {
			gameResult += "| Total\t\t | " + blue + p1.total + exit + "\t\t  | " + blue + p2.total + exit + "\t   |\n";
		} else if (p1.total < 100 && p2.total < 100) {
			gameResult += "| Total\t\t | " + blue + p1.total + exit + "\t\t  | " + blue + p2.total + exit
					+ "\t\t   |\n";
		} else {
			gameResult += "| Total\t\t | " + blue + p1.total + exit + "\t\t  | " + blue + p2.total + exit + "\t\t|\n";
		}

		gameResult += " ================================== \n";
	}

}