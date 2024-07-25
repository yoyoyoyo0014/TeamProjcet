package team;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Yacht2 {

	static Scanner scanner = new Scanner(System.in);
	public static final String blue = "\u001B[34m";
	public static final String exit = "\u001B[0m";

	static YachtVariable p1 = new YachtVariable();
	static YachtVariable p2 = new YachtVariable();

	static int turn = 1;

	// 실행 테스트용. 임시 Main
	public static void main(String[] args) {
		Yacht2 y = new Yacht2();
		y.run();
	}

	public void run() {

		// 게임의 턴 저장 변수

		printRule();

		// 12턴 동안 반복
		while (turn <= 12) {

			// player1의 차례
			{
				// 주사위 굴리기

				diceRoll(p1.dices);

				// 화면 출력
				print();

				// 배열의 내용을 출력
				System.out.println("player1의 차례입니다");
				System.out.println("주사위 결과:");
				for (int result : p1.dices) {
					System.out.print("[" + result + "]");
				}
				System.out.println();

				// 주사위 다시 굴리기(최대 2회)
				for (int r = 2; r > 0;) {
					// 주사위를 다시 굴리기
					r = diceReroll(p1.dices, r);

					// r = n;

					print();
					// 배열의 내용을 출력
					System.out.println("다시 굴린 주사위 결과:");
					for (int result : p1.dices) {
						System.out.print("[" + result + "]");
					}
					System.out.println();

					r--;
				}

				recPoint(p1);
			}
			// player2의 차례
			{
				// 주사위 굴리기
				diceRoll(p2.dices);

				// 화면 출력
				print();
				// 배열의 내용을 출력
				System.out.println("player2의 차례입니다");
				System.out.println("주사위 결과:");
				for (int result : p2.dices) {
					System.out.print("[" + result + "]");
				}
				System.out.println();

				// 주사위 다시 굴리기(최대 2회)
				for (int r = 2; r > 0;) {
					// 주사위를 다시 굴리기
					r = diceReroll(p2.dices, r);

					// r = n;

					print();
					// 배열의 내용을 출력
					System.out.println("다시 굴린 주사위 결과:");
					for (int result : p2.dices) {
						System.out.print("[" + result + "]");
					}
					System.out.println();

					r--;
				}

				// player2 차례
				recPoint(p2);
				
				System.out.println("-------------------------------------------------------------------\n");
				turn++;
			}

		}
//		turn = 12;

		print();

		if (p1.total > p2.total) {
			System.out.println("\nplayer1의 승리입니다!");
		} else if (p1.total < p2.total) {
			System.out.println("\nplayer2의 승리입니다!");
		} else {
			System.out.println("\n무승부입니다.");
		}
	}

	private static void diceRoll(int[] dices) {
		// 주사위를 굴리기 위한 Random 객체 생성
		Random random = new Random();

		// 주사위를 5번 굴려서 결과를 배열에 저장
		for (int i = 0; i < dices.length; i++) {
			dices[i] = random.nextInt(6) + 1; // 1부터 6까지의 랜덤 숫자 생성
		}
	}

	private static int diceReroll(int[] dices, int r) {
		Random random = new Random();

		while (true) {
			// 사용자 입력 받기
			System.out.print("다시 굴릴 주사위 번호를 입력하세요 (예: 1 2 4, 종료는 0, 남은 기회 : " + r + "번): ");
			String input = scanner.nextLine();

			// 0을 입력하면 종료
			if (input.equals("0")) {
				// scanner.close();
				return 0;
			}

			// 입력이 비어 있으면 다시 입력받기
			if (input.isEmpty()) {
				System.out.println("입력이 비어 있습니다. 다시 입력하세요.");
				continue; // 잘못된 입력이므로 다시 입력을 받도록 함
			}

			// 입력된 문자열을 공백을 기준으로 분리하여 배열에 저장
			String[] inputArray = input.split(" ");

			// 크기가 5인 배열 선언 및 초기화
			int[] RerollDices = new int[5];

			boolean validInput = true;
			for (int i = 0; i < inputArray.length && i < RerollDices.length; i++) {
				try {
					int diceIndex = Integer.parseInt(inputArray[i]);
					if (diceIndex < 1 || diceIndex > 5) {
						throw new NumberFormatException(); // 1~5 사이의 숫자가 아니면 예외 발생
					}
					RerollDices[i] = diceIndex;
				} catch (NumberFormatException e) {
					System.out.println("잘못된 입력입니다. 1부터 5까지의 숫자를 공백으로 구분하여 입력하세요.");
					validInput = false;
					break;
				}
			}

			if (!validInput) {
				continue; // 잘못된 입력이므로 다시 입력을 받도록 함
			}

			/*
			 * // 결과 배열 출력 System.out.print("결과 배열: ["); for (int i = 0; i <
			 * RerollDices.length; i++) { System.out.print(RerollDices[i]); if (i <
			 * RerollDices.length - 1) { System.out.print(", "); } }
			 * System.out.println("]");
			 */

			for (int i = 0; i < RerollDices.length; i++) {
				if (RerollDices[i] != 0) {
					System.out.print(RerollDices[i] + "번 ");
				}
			}
			System.out.println("주사위를 다시 돌립니다.");

			for (int i = 0; i < 5; i++) {
				if (RerollDices[i] == 0) {
					break;
				}
				dices[RerollDices[i] - 1] = random.nextInt(6) + 1; // 1부터 6까지의 랜덤 숫자 생성
			}
			return r;
		}
	}

	private void recPoint(YachtVariable yv) {

		// while문 조건용 변수
		yv.validInput = false;

		while (yv.validInput == false) {
			String SelHR = "0";

			System.out.print("1~12 중 어디에 넣을 건지를 선택하세요 : ");
			SelHR = scanner.next();
			scanner.nextLine();

			switch (SelHR) {
				case "1": {
					if (yv.oneBl == true) { // 족보에 이미 값이 들어가 있으면
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue; // whlie문 다시 시작
					}
					yv.one = oneCal(yv.dices, yv.one, yv.oneBl);
					yv.oneBl = true;
					yv.validInput = true; // while문 빠져나오는 용
					break;
				}
				case "2": {
					if (yv.twoBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.two = twoCal(yv.dices, yv.two, yv.twoBl);
					yv.twoBl = true;
					yv.validInput = true;
					break;
				}
				case "3": {
					if (yv.threeBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.three = threeCal(yv.dices, yv.three, yv.threeBl);
					yv.threeBl = true;
					yv.validInput = true;
					break;
				}
				case "4": {
					if (yv.fourBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.four = fourCal(yv.dices, yv.four, yv.fourBl);
					yv.fourBl = true;
					yv.validInput = true;
					break;
				}
				case "5": {
					if (yv.fiveBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.five = fiveCal(yv.dices, yv.five, yv.fiveBl);
					yv.fiveBl = true;
					yv.validInput = true;
					break;
				}
				case "6": {
					if (yv.sixBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.six = sixCal(yv.dices, yv.six, yv.sixBl);
					yv.sixBl = true;
					yv.validInput = true;
					break;
				}
				case "7": {
					if (yv.chBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ch = chCal(yv.dices, yv.ch, yv.chBl);
					yv.chBl = true;
					yv.validInput = true;
					break;
				}
				case "8": {
					if (yv.fkBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.fk = fkCal(yv.dices, yv.fk, yv.fkBl);
					yv.fkBl = true;
					yv.validInput = true;
					break;
				}
				case "9": {
					if (yv.fhBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.fh = fhCal(yv.dices, yv.fh, yv.fhBl);
					yv.fhBl = true;
					yv.validInput = true;
					break;
				}
				case "10": {
					if (yv.ssBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ss = ssCal(yv.dices, yv.ss, yv.ssBl);
					yv.ssBl = true;
					yv.validInput = true;
					break;
				}
				case "11": {
					if (yv.lsBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ls = lsCal(yv.dices, yv.ls, yv.lsBl);
					yv.lsBl = true;
					yv.validInput = true;
					break;
				}
				case "12": {
					if (yv.yaBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ya = yaCal(yv.dices, yv.ya, yv.yaBl);
					yv.yaBl = true;
					yv.validInput = true;
					break;
				}
			}
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
	}

	private void print() {
		System.out.println(" ================================== ");
		System.out.println("|\t   Turn " + turn + "/12  \t\t   |");
		System.out.println("|==================================|");
		if (p1.dices[0] > 0) {
			System.out.println("| Categorries\t |" + blue + "player1" + exit + " |player2 |");
		} else if (p2.dices[0] > 0) {
			System.out.println("| Categorries\t |player1 |" + blue + "player2 " + exit + "|");
		} else {
			System.out.println("| Categorries\t |player1 |player2 |");
		}
		System.out.println("|==================================|");

		// 1 ~ 6
		if (p1.oneBl == true && p2.oneBl == true) {
			System.out.println("|1. Aces\t | " + blue + oneCal(p1.dices, p1.one, p1.oneBl) + exit + "\t     | " + blue + oneCal(p2.dices, p2.one, p2.oneBl)
					+ exit + "\t\t   |");
		} else if (p1.oneBl == true) {
			System.out
					.println("|1. Aces\t | " + blue + oneCal(p1.dices, p1.one, p1.oneBl) + exit + "\t     | " + oneCal(p2.dices, p2.one, p2.oneBl) + "\t   |");
		} else if (p2.oneBl == true) {
			System.out
					.println("|1. Aces\t | " + oneCal(p1.dices, p1.one, p1.oneBl) + "\t  | " + blue + oneCal(p2.dices, p2.one, p2.oneBl) + exit + "\t\t   |");
		} else {
			System.out.println("|1. Aces\t | " + oneCal(p1.dices, p1.one, p1.oneBl) + "\t  | " + oneCal(p2.dices, p2.one, p2.oneBl) + "\t   |");
		}

		if (p1.twoBl == true && p2.twoBl == true && p1.two >= 10 && p2.two >= 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t    | " + blue + twoCal(p2.dices, p2.two, p2.twoBl)
					+ exit + "\t    |");
		} else if (p1.twoBl == true && p2.twoBl == true && p1.two >= 10 && p2.two < 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t    | " + blue + twoCal(p2.dices, p2.two, p2.twoBl)
					+ exit + "\t     |");
		} else if (p1.twoBl == true && p2.twoBl == true && p1.two < 10 && p2.two >= 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t     | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t    |");
		} else if (p1.twoBl == true && p2.twoBl == true) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t     | " + blue
					+ twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t     |");
		} else if (p1.twoBl == true && p1.two >= 10) {
			System.out
					.println("|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t    | " + twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |");
		} else if (p1.twoBl == true) {
			System.out.println(
					"|2. Deuces\t | " + blue + twoCal(p1.dices, p1.two, p1.twoBl) + exit + "\t     | " + twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |");
		} else if (p2.twoBl == true) {
			System.out
					.println("|2. Deuces\t | " + twoCal(p1.dices, p1.two, p1.twoBl) + "\t  | " + blue + twoCal(p2.dices, p2.two, p2.twoBl) + exit + "\t\t   |");
		} else {
			System.out.println("|2. Deuces\t | " + twoCal(p1.dices, p1.two, p1.twoBl) + "\t  | " + twoCal(p2.dices, p2.two, p2.twoBl) + "\t   |");
		}

		if (p1.threeBl == true && p2.threeBl == true && p1.three >= 10 && p2.three >= 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t    | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t    |");
		} else if (p1.threeBl == true && p2.threeBl == true && p1.three >= 10 && p2.three < 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t    | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t     |");
		} else if (p1.threeBl == true && p2.threeBl == true && p1.three < 10 && p2.three >= 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t     | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t    |");
		} else if (p1.threeBl == true && p2.threeBl == true) {
			System.out.println("|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t     | " + blue
					+ threeCal(p2.dices, p2.three, p2.threeBl) + exit + "\t     |");
		} else if (p1.threeBl == true && p1.three >= 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t    | "
					+ threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |");
		} else if (p1.threeBl == true) {
			System.out.println("|3. Threes\t | " + blue + threeCal(p1.dices, p1.three, p1.threeBl) + exit + "\t     | "
					+ threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |");
		} else if (p2.threeBl == true) {
			System.out.println("|3. Threes\t | " + threeCal(p1.dices, p1.three, p1.threeBl) + "\t  | " + blue + threeCal(p2.dices, p2.three, p2.threeBl)
					+ exit + "\t\t   |");
		} else {
			System.out
					.println("|3. Threes\t | " + threeCal(p1.dices, p1.three, p1.threeBl) + "\t  | " + threeCal(p2.dices, p2.three, p2.threeBl) + "\t   |");
		}

		if (p1.fourBl == true && p2.fourBl == true && p1.four >= 10 && p2.four >= 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t    | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t    |");
		} else if (p1.fourBl == true && p2.fourBl == true && p1.four >= 10 && p2.four < 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t    | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t     |");
		} else if (p1.fourBl == true && p2.fourBl == true && p1.four < 10 && p2.four >= 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t     | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t    |");
		} else if (p1.fourBl == true && p2.fourBl == true) {
			System.out.println("|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t     | " + blue
					+ fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t     |");
		} else if (p1.fourBl == true && p1.four >= 10) {
			System.out.println(
					"|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t    | " + fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |");
		} else if (p1.fourBl == true) {
			System.out.println(
					"|4. Fours\t | " + blue + fourCal(p1.dices, p1.four, p1.fourBl) + exit + "\t     | " + fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |");
		} else if (p2.fourBl == true) {
			System.out.println(
					"|4. Fours\t | " + fourCal(p1.dices, p1.four, p1.fourBl) + "\t  | " + blue + fourCal(p2.dices, p2.four, p2.fourBl) + exit + "\t\t   |");
		} else {
			System.out.println("|4. Fours\t | " + fourCal(p1.dices, p1.four, p1.fourBl) + "\t  | " + fourCal(p2.dices, p2.four, p2.fourBl) + "\t   |");
		}

		if (p1.fiveBl == true && p2.fiveBl == true && p1.five >= 10 && p2.five >= 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t    | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t    |");
		} else if (p1.fiveBl == true && p2.fiveBl == true && p1.five >= 10 && p2.five < 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t    | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t     |");
		} else if (p1.fiveBl == true && p2.fiveBl == true && p1.five < 10 && p2.five >= 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t     | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t    |");
		} else if (p1.fiveBl == true && p2.fiveBl == true) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t     | " + blue
					+ fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t     |");
		} else if (p1.fiveBl == true && p1.five >= 10) {
			System.out.println(
					"|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t    | " + fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |");
		} else if (p1.fiveBl == true) {
			System.out.println(
					"|5. Fives\t | " + blue + fiveCal(p1.dices, p1.five, p1.fiveBl) + exit + "\t     | " + fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |");
		} else if (p2.fiveBl == true) {
			System.out.println(
					"|5. Fives\t | " + fiveCal(p1.dices, p1.five, p1.fiveBl) + "\t  | " + blue + fiveCal(p2.dices, p2.five, p2.fiveBl) + exit + "\t\t   |");
		} else {
			System.out.println("|5. Fives\t | " + fiveCal(p1.dices, p1.five, p1.fiveBl) + "\t  | " + fiveCal(p2.dices, p2.five, p2.fiveBl) + "\t   |");
		}

		if (p1.sixBl == true && p2.sixBl == true && p1.six >= 10 && p2.six >= 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t    | " + blue + sixCal(p2.dices, p2.six, p2.sixBl)
					+ exit + "\t    |");
		} else if (p1.sixBl == true && p2.sixBl == true && p1.six >= 10 && p2.six < 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t    | " + blue + sixCal(p2.dices, p2.six, p2.sixBl)
					+ exit + "\t     |");
		} else if (p1.sixBl == true && p2.sixBl == true && p1.six < 10 && p2.six >= 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t     | " + blue + sixCal(p2.dices, p2.six, p2.sixBl)
					+ exit + "\t    |");
		} else if (p1.sixBl == true && p2.sixBl == true) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t     | " + blue + sixCal(p2.dices, p2.six, p2.sixBl)
					+ exit + "\t     |");
		} else if (p1.sixBl == true && p1.six >= 10) {
			System.out
					.println("|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t    | " + sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |");
		} else if (p1.sixBl == true) {
			System.out
					.println("|6. Sixes\t | " + blue + sixCal(p1.dices, p1.six, p1.sixBl) + exit + "\t     | " + sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |");
		} else if (p2.sixBl == true) {
			System.out
					.println("|6. Sixes\t | " + sixCal(p1.dices, p1.six, p1.sixBl) + "\t  | " + blue + sixCal(p2.dices, p2.six, p2.sixBl) + exit + "\t\t   |");
		} else {
			System.out.println("|6. Sixes\t | " + sixCal(p1.dices, p1.six, p1.sixBl) + "\t  | " + sixCal(p2.dices, p2.six, p2.sixBl) + "\t   |");
		}
		System.out.println("|==================================|");

		// 보너스
		if (p1.bonusBl == true && p2.bonusBl == true) {
			System.out.println("|Subtotal\t | " + blue + p1.oneToSix + "/63" + exit + "\t | " + blue + p2.oneToSix + "/63" + exit + "\t |");
			System.out.println("|----------------------------------|");
			System.out
					.println("|+35 Bonus\t | " + blue + "+" + bonusCal(p1.bonusBl) + exit + "\t\t  | " + blue + "+" + bonusCal(p2.bonusBl) + exit + "\t   |");
		} else if (p1.bonusBl == true && p2.oneToSix >= 10) {
			System.out.println("|Subtotal\t | " + blue + p1.oneToSix + "/63" + exit + "\t | " + p2.oneToSix + "/63" + "  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + blue + "+" + bonusCal(p1.bonusBl) + exit + "\t\t  | " + "+" + bonusCal(p2.bonusBl) + "\t   |");
		} else if (p1.bonusBl == true && p2.oneToSix < 10) {
			System.out.println("|Subtotal\t | " + blue + p1.oneToSix + "/63" + exit + "\t | " + p2.oneToSix + "/63" + "   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + blue + "+" + bonusCal(p1.bonusBl) + exit + "\t\t  | " + "+" + bonusCal(p2.bonusBl) + "\t   |");
		} else if (p2.bonusBl == true && p1.oneToSix >= 10) {
			System.out.println("|Subtotal\t | " + p1.oneToSix + "/63" + "  | " + blue + p2.oneToSix + "/63" + exit + "  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + blue + "+" + bonusCal(p2.bonusBl) + exit + "\t   |");
		} else if (p2.bonusBl == true && p1.oneToSix < 10) {
			System.out.println("|Subtotal\t | " + p1.oneToSix + "/63" + "\t  | " + blue + p2.oneToSix + "/63" + exit + "  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + blue + "+" + bonusCal(p2.bonusBl) + exit + "\t   |");
		} else if (p1.oneToSix >= 10 && p2.oneToSix >= 10) {
			System.out.println("|Subtotal\t | " + p1.oneToSix + "/63  | " + p2.oneToSix + "/63  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl) + "\t   |");
		} else if (p1.oneToSix >= 10 && p2.oneToSix < 10) {
			System.out.println("|Subtotal\t | " + p1.oneToSix + "/63  | " + p2.oneToSix + "/63   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl) + "\t   |");
		} else if (p1.oneToSix < 10 && p2.oneToSix >= 10) {
			System.out.println("|Subtotal\t | " + p1.oneToSix + "/63   | " + p2.oneToSix + "/63  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl) + "\t   |");
		} else {
			System.out.println("|Subtotal\t | " + p1.oneToSix + "/63" + "\t  | " + p2.oneToSix + "/63   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(p1.bonusBl) + "\t  | " + "+" + bonusCal(p2.bonusBl) + "\t   |");
		}

		System.out.println("|==================================|");

		// choice
		if (p1.chBl == true && p2.chBl == true && p1.ch >= 10 && p2.ch >= 10) {
			System.out.println(
					"|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t    | " + blue + chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t    |");
		} else if (p1.chBl == true && p2.chBl == true && p1.ch >= 10 && p2.ch < 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t    | " + blue + chCal(p2.dices, p2.ch, p2.chBl)
					+ exit + "\t     |");
		} else if (p1.chBl == true && p2.chBl == true && p1.ch < 10 && p2.ch >= 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t     | " + blue + chCal(p2.dices, p2.ch, p2.chBl)
					+ exit + "\t    |");
		} else if (p1.chBl == true && p2.chBl == true) {
			System.out.println("|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t     | " + blue + chCal(p2.dices, p2.ch, p2.chBl)
					+ exit + "\t     |");
		} else if (p1.chBl == true && p1.ch >= 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t    | " + chCal(p2.dices, p2.ch, p2.chBl) + "\t   |");
		} else if (p1.chBl == true) {
			System.out
					.println("|7. Choice\t | " + blue + chCal(p1.dices, p1.ch, p1.chBl) + exit + "\t     | " + chCal(p2.dices, p2.ch, p2.chBl) + "\t   |");
		} else if (p2.chBl == true) {
			System.out.println("|7. Choice\t | " + chCal(p1.dices, p1.ch, p1.chBl) + "\t  | " + blue + chCal(p2.dices, p2.ch, p2.chBl) + exit + "\t\t   |");
		} else {
			System.out.println("|7. Choice\t | " + chCal(p1.dices, p1.ch, p1.chBl) + "\t  | " + chCal(p2.dices, p2.ch, p2.chBl) + "\t   |");
		}
		System.out.println("|==================================|");

		// 특수 족보
		if (p1.fkBl == true && p2.fkBl == true && p1.fk >= 10 && p2.fk >= 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t    | " + blue + fkCal(p2.dices, p2.fk, p2.fkBl)
					+ exit + "\t    |");
		} else if (p1.fkBl == true && p2.fkBl == true && p1.fk >= 10 && p2.fk < 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t    | " + blue + fkCal(p2.dices, p2.fk, p2.fkBl)
					+ exit + "\t     |");
		} else if (p1.fkBl == true && p2.fkBl == true && p1.fk < 10 && p2.fk >= 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t     | " + blue + fkCal(p2.dices, p2.fk, p2.fkBl)
					+ exit + "\t    |");
		} else if (p1.fkBl == true && p2.fkBl == true) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t     | " + blue + fkCal(p2.dices, p2.fk, p2.fkBl)
					+ exit + "\t     |");
		} else if (p1.fkBl == true && p1.fk >= 10) {
			System.out
					.println("|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t    | " + fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |");
		} else if (p1.fkBl == true) {
			System.out
					.println("|8. 4 of a Kind  | " + blue + fkCal(p1.dices, p1.fk, p1.fkBl) + exit + "\t     | " + fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |");
		} else if (p2.fkBl == true) {
			System.out
					.println("|8. 4 of a Kind  | " + fkCal(p1.dices, p1.fk, p1.fkBl) + "\t  | " + blue + fkCal(p2.dices, p2.fk, p2.fkBl) + exit + "\t\t   |");
		} else {
			System.out.println("|8. 4 of a Kind  | " + fkCal(p1.dices, p1.fk, p1.fkBl) + "\t  | " + fkCal(p2.dices, p2.fk, p2.fkBl) + "\t   |");
		}

		if (p1.fhBl == true && p2.fhBl == true && p1.fh >= 10 && p2.fh >= 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t    | " + blue + fhCal(p2.dices, p2.fh, p2.fhBl)
					+ exit + "\t    |");
		} else if (p1.fhBl == true && p2.fhBl == true && p1.fh >= 10 && p2.fh < 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t    | " + blue + fhCal(p2.dices, p2.fh, p2.fhBl)
					+ exit + "\t     |");
		} else if (p1.fhBl == true && p2.fhBl == true && p1.fh < 10 && p2.fh >= 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t     | " + blue + fhCal(p2.dices, p2.fh, p2.fhBl)
					+ exit + "\t    |");
		} else if (p1.fhBl == true && p2.fhBl == true) {
			System.out.println("|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t     | " + blue + fhCal(p2.dices, p2.fh, p2.fhBl)
					+ exit + "\t     |");
		} else if (p1.fhBl == true && p1.fh >= 10) {
			System.out
					.println("|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t    | " + fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |");
		} else if (p1.fhBl == true) {
			System.out
					.println("|9. Full House   | " + blue + fhCal(p1.dices, p1.fh, p1.fhBl) + exit + "\t     | " + fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |");
		} else if (p2.fhBl == true) {
			System.out
					.println("|9. Full House   | " + fhCal(p1.dices, p1.fh, p1.fhBl) + "\t  | " + blue + fhCal(p2.dices, p2.fh, p2.fhBl) + exit + "\t\t   |");
		} else {
			System.out.println("|9. Full House   | " + fhCal(p1.dices, p1.fh, p1.fhBl) + "\t  | " + fhCal(p2.dices, p2.fh, p2.fhBl) + "\t   |");
		}

		if (p1.ssBl == true && p2.ssBl == true && p1.ss >= 10 && p2.ss >= 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t    | " + blue + ssCal(p2.dices, p2.ss, p2.ssBl)
					+ exit + "\t    |");
		} else if (p1.ssBl == true && p2.ssBl == true && p1.ss >= 10 && p2.ss < 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t    | " + blue + ssCal(p2.dices, p2.ss, p2.ssBl)
					+ exit + "\t     |");
		} else if (p1.ssBl == true && p2.ssBl == true && p1.ss < 10 && p2.ss >= 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t     | " + blue + ssCal(p2.dices, p2.ss, p2.ssBl)
					+ exit + "\t    |");
		} else if (p1.ssBl == true && p2.ssBl == true) {
			System.out.println("|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t     | " + blue + ssCal(p2.dices, p2.ss, p2.ssBl)
					+ exit + "\t     |");
		} else if (p1.ssBl == true && p1.ss >= 10) {
			System.out
					.println("|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t    | " + ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |");
		} else if (p1.ssBl == true) {
			System.out
					.println("|10. S. Straight | " + blue + ssCal(p1.dices, p1.ss, p1.ssBl) + exit + "\t     | " + ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |");
		} else if (p2.ssBl == true) {
			System.out
					.println("|10. S. Straight | " + ssCal(p1.dices, p1.ss, p1.ssBl) + "\t  | " + blue + ssCal(p2.dices, p2.ss, p2.ssBl) + exit + "\t\t   |");
		} else {
			System.out.println("|10. S. Straight | " + ssCal(p1.dices, p1.ss, p1.ssBl) + "\t  | " + ssCal(p2.dices, p2.ss, p2.ssBl) + "\t   |");
		}

		if (p1.lsBl == true && p2.lsBl == true && p1.ls >= 10 && p2.ls >= 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t    | " + blue + lsCal(p2.dices, p2.ls, p2.lsBl)
					+ exit + "\t    |");
		} else if (p1.lsBl == true && p2.lsBl == true && p1.ls >= 10 && p2.ls < 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t    | " + blue + lsCal(p2.dices, p2.ls, p2.lsBl)
					+ exit + "\t     |");
		} else if (p1.lsBl == true && p2.lsBl == true && p1.ls < 10 && p2.ls >= 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t     | " + blue + lsCal(p2.dices, p2.ls, p2.lsBl)
					+ exit + "\t    |");
		} else if (p1.lsBl == true && p2.lsBl == true) {
			System.out.println("|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t     | " + blue + lsCal(p2.dices, p2.ls, p2.lsBl)
					+ exit + "\t     |");
		} else if (p1.lsBl == true && p1.ls >= 10) {
			System.out
					.println("|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t    | " + lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |");
		} else if (p1.lsBl == true) {
			System.out
					.println("|11. L. Straight | " + blue + lsCal(p1.dices, p1.ls, p1.lsBl) + exit + "\t     | " + lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |");
		} else if (p2.lsBl == true) {
			System.out
					.println("|11. L. Straight | " + lsCal(p1.dices, p1.ls, p1.lsBl) + "\t  | " + blue + lsCal(p2.dices, p2.ls, p2.lsBl) + exit + "\t\t   |");
		} else {
			System.out.println("|11. L. Straight | " + lsCal(p1.dices, p1.ls, p1.lsBl) + "\t  | " + lsCal(p2.dices, p2.ls, p2.lsBl) + "\t   |");
		}

		if (p1.yaBl == true && p2.yaBl == true && p1.ya >= 10 && p2.ya >= 10) {
			System.out.println(
					"|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t    | " + blue + yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t    |");
		} else if (p1.yaBl == true && p2.yaBl == true && p1.ya >= 10 && p2.ya < 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t    | " + blue + yaCal(p2.dices, p2.ya, p2.yaBl)
					+ exit + "\t     |");
		} else if (p1.yaBl == true && p2.yaBl == true && p1.ya < 10 && p2.ya >= 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t     | " + blue + yaCal(p2.dices, p2.ya, p2.yaBl)
					+ exit + "\t    |");
		} else if (p1.yaBl == true && p2.yaBl == true) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t     | " + blue + yaCal(p2.dices, p2.ya, p2.yaBl)
					+ exit + "\t     |");
		} else if (p1.yaBl == true && p1.ya >= 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t    | " + yaCal(p2.dices, p2.ya, p2.yaBl) + "\t   |");
		} else if (p1.yaBl == true) {
			System.out
					.println("|12. Yacht\t | " + blue + yaCal(p1.dices, p1.ya, p1.yaBl) + exit + "\t     | " + yaCal(p2.dices, p2.ya, p2.yaBl) + "\t   |");
		} else if (p2.yaBl == true) {
			System.out.println("|12. Yacht\t | " + yaCal(p1.dices, p1.ya, p1.yaBl) + "\t  | " + blue + yaCal(p2.dices, p2.ya, p2.yaBl) + exit + "\t\t   |");
		} else {
			System.out.println("|12. Yacht\t | " + yaCal(p1.dices, p1.ya, p1.yaBl) + "\t  | " + yaCal(p2.dices, p2.ya, p2.yaBl) + "\t   |");
		}

		// 합계 출력
		System.out.println("|==================================|");
		System.out.println("| Total\t\t | " + p1.total + "\t  | " + p2.total + "\t   |");
		System.out.println(" ================================== ");
	}

	private static void insertDice(YachtVariable yv) {
		// while문 조건용 변수
		boolean validInput = false;

		// 족보에 무사히 값이 들어가면 while문 종료
		while (validInput == false) {
			String menu = "0";

			System.out.print("1~12 중 어디에 넣을 건지를 선택하세요 : ");
			menu = scanner.next();
			scanner.nextLine();

			switch (menu) {
				case "1": {
					if (yv.oneBl == true) { // 족보에 이미 값이 들어가 있으면
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue; // whlie문 다시 시작
					}
					yv.one = oneCal(yv.dices, yv.one, yv.oneBl);
					yv.oneBl = true;
					validInput = true; // while문 빠져나오는 용
					break;
				}
				case "2": {
					if (yv.twoBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.two = twoCal(yv.dices, yv.two, yv.twoBl);
					yv.twoBl = true;
					validInput = true;
					break;
				}
				case "3": {
					if (yv.threeBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.three = threeCal(yv.dices, yv.three, yv.threeBl);
					yv.threeBl = true;
					validInput = true;
					break;
				}
				case "4": {
					if (yv.fourBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.four = fourCal(yv.dices, yv.four, yv.fourBl);
					yv.fourBl = true;
					validInput = true;
					break;
				}
				case "5": {
					if (yv.fiveBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.five = fiveCal(yv.dices, yv.five, yv.fiveBl);
					yv.fiveBl = true;
					validInput = true;
					break;
				}
				case "6": {
					if (yv.sixBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.six = sixCal(yv.dices, yv.six, yv.sixBl);
					yv.sixBl = true;
					validInput = true;
					break;
				}
				case "7": {
					if (yv.chBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ch = chCal(yv.dices, yv.ch, yv.chBl);
					yv.chBl = true;
					validInput = true;
					break;
				}
				case "8": {
					if (yv.fkBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.fk = fkCal(yv.dices, yv.fk, yv.fkBl);
					yv.fkBl = true;
					validInput = true;
					break;
				}
				case "9": {
					if (yv.fhBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.fh = fhCal(yv.dices, yv.fh, yv.fhBl);
					yv.fhBl = true;
					validInput = true;
					break;
				}
				case "10": {
					if (yv.ssBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ss = ssCal(yv.dices, yv.ss, yv.ssBl);
					yv.ssBl = true;
					validInput = true;
					break;
				}
				case "11": {
					if (yv.lsBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ls = lsCal(yv.dices, yv.ls, yv.lsBl);
					yv.lsBl = true;
					validInput = true;
					break;
				}
				case "12": {
					if (yv.yaBl == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yv.ya = yaCal(yv.dices, yv.ya, yv.yaBl);
					yv.yaBl = true;
					validInput = true;
					break;
				}
			}
		}
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
			if (uniqueNumbers[i] + 1 == uniqueNumbers[i + 1] &&
					uniqueNumbers[i] + 2 == uniqueNumbers[i + 2] &&
					uniqueNumbers[i] + 3 == uniqueNumbers[i + 3]) {
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
			if (straight[i] + 1 == straight[i + 1] &&
					straight[i] + 2 == straight[i + 2] &&
					straight[i] + 3 == straight[i + 3] &&
					straight[i] + 4 == straight[i + 4]) {
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
		int total = yv.one + yv.two + yv.three + yv.four + yv.five + yv.six + yv.bonus
				+ yv.ch + yv.fk + yv.fh + yv.ss + yv.ls + yv.ya;

		return total;
	}

	private static void printRule() {
		System.out.println("1. 주사위 5개를 던진다.\n");
		System.out.println("2. 이 중 원하는 주사위들은 남겨두고, 나머지 주사위들을 다시 던진다. \n다시 던지기는 한 라운드에 2번까지 가능하며, 앞에서 던지지 않았던 주사위도 원한다면 다시 던질 수 있다.\n");
		System.out.println("3. 주사위 던지기가 끝난 후 나온 최종 조합으로, 아래 제시된 족보 중 아직까지 기록되지 않은 하나를 반드시 선택하여, 점수판에 기록한다.\n");
		System.out.println("4. 만약 조건에 만족하지 않는 족보를 선택하는 경우, 선택한 족보의 점수칸에 0점으로 기록된다.\n");
		System.out.println("5. 모든 플레이어가 점수판을 모두 채우면 게임이 종료되고, 점수 총합이 가장 높은 플레이어가 승리한다.\n\n");

		System.out.println(" ======================================================================= ");
		System.out.println("|\t\t\t\t족보\t\t\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| 이름\t\t| 설명\t\t\t\t| 예시\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| Aces\t\t| " + "1이 나온 주사위 눈의 총합. 최대 5점.\t" + "|[1][1][1][5][6] => 3점\t|");
		System.out.println("| Deuces\t| " + "2가 나온 주사위 눈의 총합. 최대 10점.\t" + "|[2][2][2][5][6] => 6점\t|");
		System.out.println("| Threes\t| " + "3이 나온 주사위 눈의 총합. 최대 15점.\t" + "|[3][3][3][5][6] => 9점\t|");
		System.out.println("| Fours\t\t| " + "4가 나온 주사위 눈의 총합. 최대 20점.\t" + "|[1][2][4][4][4] => 12점\t|");
		System.out.println("| Fives\t\t| " + "5가 나온 주사위 눈의 총합. 최대 25점.\t" + "|[1][2][5][5][5] => 15점\t|");
		System.out.println("| Sixes\t\t| " + "6이 나온 주사위 눈의 총합. 최대 30점.\t" + "|[1][2][6][6][6] => 18점\t|");
		System.out.println(" ======================================================================= ");
		System.out.println("상단 항목의 점수 합계가 63점 이상일 때, 보너스 점수 35점을 추가로 얻는다.\n");

		System.out.println(" ======================================================================= ");
		System.out.println("|\t\t\t\t족보\t\t\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| 이름\t\t| 설명\t\t\t\t| 예시\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| Choice\t| " + "주사위 눈 5개의 총합. 최대 30점.\t" + "|[3][4][5][6][6] => 24점\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| 4 of a Kind\t| " + "동일한 주사위 눈이 4개 이상일 때,\t" + "|[5][6][6][6][6] => 29점\t|");
		System.out.println("|\t\t| " + "주사위 눈 5개의 총합. 최대 30점.\t" + "|\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("|\t\t| " + "주사위를 3개, 2개로 묶었을 때 각각의\t" + "|\t\t\t|");
		System.out.println("| Full House\t| " + "묶음 안에서 주사위 눈이 서로 동일할 때,\t" + "|[5][5][6][6][6] => 28점\t|");
		System.out.println("|\t\t| " + "주사위 눈 5개의 총합. 최대 30점.\t" + "|\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| S. Straight\t| " + "이어지는 주사위 눈이 4개 이상일 때.\t" + "|[1][2][3][4][6] => 15점\t|");
		System.out.println("|\t\t| " + "고정 15점.\t\t\t" + "|\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| L. Straight\t| " + "이어지는 주사위 눈이 5개일 때.\t" + "|[1][2][3][4][5] => 30점\t|");
		System.out.println("|\t\t| " + "고정 30점.\t\t\t" + "|\t\t\t|");
		System.out.println("|=======================================================================|");
		System.out.println("| Yacht\t\t| " + "동일한 주사위 눈이 5개일 때.\t\t" + "|[1][1][1][1][1] => 50점\t|");
		System.out.println("|\t\t| " + "고정 50점.\t\t\t" + "|\t\t\t|");
		System.out.println(" ======================================================================= ");
		System.out.println();
	}

}