package team;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Yacht {
	
 	static Scanner scanner = new Scanner(System.in);
 	public static final String blue     = "\u001B[34m" ;
 	public static final String exit     = "\u001B[0m" ;

	public static void main(String[] args) {
		
		//주사위 눈금 배열
		int[] dicesP1 = new int[5];
		int[] dicesP2 = new int[5];
		
		//족보 저장 변수
		int oneP1, twoP1, threeP1, fourP1, fiveP1, sixP1;
		int oneP2, twoP2, threeP2, fourP2, fiveP2, sixP2;
		
		//족보 1~6 합
		int oneToSixP1 = 0;
		int oneToSixP2 = 0;
		
		//보너스 점수 변수
		int bonusP1;
		int bonusP2;
		
		//족보 저장 변수
		int chP1, fkP1, fhP1, ssP1, lsP1, yaP1;
		int chP2, fkP2, fhP2, ssP2, lsP2, yaP2;
		
		int totalP1, totalP2;
		
		//족보 저장 여부 판별용
		boolean oneBlP1 = false;
		boolean twoBlP1 = false;
		boolean threeBlP1 = false;
		boolean fourBlP1 = false;
		boolean fiveBlP1 = false;
		boolean sixBlP1 = false;
		boolean chBlP1 = false;
		boolean fkBlP1 = false;
		boolean fhBlP1 = false;
		boolean ssBlP1 = false;
		boolean lsBlP1 = false;
		boolean yaBlP1 = false;
		
		boolean oneBlP2 = false;
		boolean twoBlP2 = false;
		boolean threeBlP2 = false;
		boolean fourBlP2 = false;
		boolean fiveBlP2 = false;
		boolean sixBlP2 = false;
		boolean chBlP2 = false;
		boolean fkBlP2 = false;
		boolean fhBlP2 = false;
		boolean ssBlP2 = false;
		boolean lsBlP2 = false;
		boolean yaBlP2 = false;
		
		boolean bonusBlP1 = false;
		boolean bonusBlP2 = false;
		
		//게임의 턴 저장 변수
		int turn = 1;
		
		//변수 초기화
		{
			oneP1 = 0;
			twoP1 = 0;
			threeP1 = 0;
			fourP1 = 0;
			fiveP1 = 0;
			sixP1 = 0;
			bonusP1 = 0;
			chP1 = 0;
			fkP1 = 0;
			fhP1 = 0;
			ssP1 = 0;
			lsP1 = 0;
			yaP1 = 0;
			
			oneP2 = 0;
			twoP2 = 0;
			threeP2 = 0;
			fourP2 = 0;
			fiveP2 = 0;
			sixP2 = 0;
			bonusP2 = 0;
			chP2 = 0;
			fkP2 = 0;
			fhP2 = 0;
			ssP2 = 0;
			lsP2 = 0;
			yaP2 = 0;
			
			totalP1 = 0;
			totalP2 = 0;
		}
		
		//12턴 동안 반복
		while(turn <= 12) {

			//주사위 굴리기
			diceRoll(dicesP1);
			
			//화면 출력
			print(dicesP1, turn, oneP1, twoP1, threeP1, fourP1, fiveP1, sixP1, bonusP1, chP1, fkP1, fhP1, ssP1, lsP1, yaP1, oneBlP1, twoBlP1, threeBlP1, fourBlP1, fiveBlP1, sixBlP1, bonusBlP1, chBlP1, fkBlP1, fhBlP1, ssBlP1, lsBlP1, yaBlP1, oneToSixP1, totalP1,
					 dicesP2, oneP2, twoP2, threeP2, fourP2, fiveP2, sixP2, bonusP2, chP2, fkP2, fhP2, ssP2, lsP2, yaP2, oneBlP2, twoBlP2, threeBlP2, fourBlP2, fiveBlP2, sixBlP2, bonusBlP2, chBlP2, fkBlP2, fhBlP2, ssBlP2, lsBlP2, yaBlP2, oneToSixP2, totalP2);
			
			//배열의 내용을 출력
			System.out.println("주사위 결과:");
			for (int result : dicesP1) {
				System.out.print("[" + result + "]");
			}
			System.out.println();
			
			//주사위 다시 굴리기(최대 2회)
			for(int r = 2; r > 0;) {
		        // 주사위를 다시 굴리기
		        r = diceReroll(dicesP1, r);
		        
		        //r = n;
		        
				print(dicesP1, turn, oneP1, twoP1, threeP1, fourP1, fiveP1, sixP1, bonusP1, chP1, fkP1, fhP1, ssP1, lsP1, yaP1, oneBlP1, twoBlP1, threeBlP1, fourBlP1, fiveBlP1, sixBlP1, bonusBlP1, chBlP1, fkBlP1, fhBlP1, ssBlP1, lsBlP1, yaBlP1, oneToSixP1, totalP1,
						 dicesP2, oneP2, twoP2, threeP2, fourP2, fiveP2, sixP2, bonusP2, chP2, fkP2, fhP2, ssP2, lsP2, yaP2, oneBlP2, twoBlP2, threeBlP2, fourBlP2, fiveBlP2, sixBlP2, bonusBlP2, chBlP2, fkBlP2, fhBlP2, ssBlP2, lsBlP2, yaBlP2, oneToSixP2, totalP2);
				
		        // 배열의 내용을 출력
		        System.out.println("다시 굴린 주사위 결과:");
		        for (int result : dicesP1) {
		            System.out.print("[" + result + "]");
		        }
		        System.out.println();
		        
		        r--;
			}
			
			//insertDice(dicesP1, oneP1, twoP1, threeP1, fourP1, fiveP1, sixP1, chP1, fkP1, fhP1, ssP1, lsP1, yaP1);
			
			//while문 조건용 변수
			boolean validInput = false;
			
			//족보에 무사히 값이 들어가면 while문 종료
			while(validInput == false) {
				String menu = "0";
				
				System.out.print("1~12 중 어디에 넣을 건지를 선택하세요 : ");
				menu = scanner.next();
				scanner.nextLine();
				
				switch (menu) {
				case "1": {
					if(oneBlP1 == true) { //족보에 이미 값이 들어가 있으면
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue; //whlie문 다시 시작
					}
					oneP1 = oneCal(dicesP1, oneP1, oneBlP1);
					oneBlP1 = true;
					validInput = true; //while문 빠져나오는 용
					break;
				}
				case "2": {
					if(twoBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					twoP1 = twoCal(dicesP1, twoP1, twoBlP1);
					twoBlP1 = true;
					validInput = true;
					break;
				}
				case "3": {
					if(threeBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					threeP1 = threeCal(dicesP1, threeP1, threeBlP1);
					threeBlP1 = true;
					validInput = true;
					break;
				}
				case "4": {
					if(fourBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					fourP1 = fourCal(dicesP1, fourP1, fourBlP1);
					fourBlP1 = true;
					validInput = true;
					break;
				}
				case "5": {
					if(fiveBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					fiveP1 = fiveCal(dicesP1, fiveP1, fiveBlP1);
					fiveBlP1 = true;
					validInput = true;
					break;
				}
				case "6": {
					if(sixBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					sixP1 = sixCal(dicesP1, sixP1, sixBlP1);
					sixBlP1 = true;
					validInput = true;
					break;
				}
				case "7": {
					if(chBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					chP1 = chCal(dicesP1, chP1, chBlP1);
					chBlP1 = true;
					validInput = true;
					break;
				}
				case "8": {
					if(fkBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					fkP1 = fkCal(dicesP1, fkP1, fkBlP1);
					fkBlP1 = true;
					validInput = true;
					break;
				}
				case "9": {
					if(fhBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					fhP1 = fhCal(dicesP1, fhP1, fhBlP1);
					fhBlP1 = true;
					validInput = true;
					break;
				}
				case "10": {
					if(ssBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					ssP1 = ssCal(dicesP1, ssP1, ssBlP1);
					ssBlP1 = true;
					validInput = true;
					break;
				}
				case "11": {
					if(lsBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					lsP1 = lsCal(dicesP1, lsP1, lsBlP1);
					lsBlP1 = true;
					validInput = true;
					break;
				}
				case "12": {
					if(yaBlP1 == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					yaP1 = yaCal(dicesP1, yaP1, yaBlP1);
					yaBlP1 = true;
					validInput = true;
					break;
				}
			}
				//보너스 점수 획득 가능한지 계산
				oneToSixP1 = oneP1 + twoP1 + threeP1 + fourP1 + fiveP1 + sixP1;
				
				if(oneToSixP1 >= 63) {
					bonusBlP1 = true;
					bonusP1 = 35;
				}
				
				totalP1 = totalCal(oneP1, twoP1, threeP1, fourP1, fiveP1, oneToSixP1, bonusP1, chP1, fkP1, fhP1, ssP1, lsP1, yaP1);
				totalP2 = totalCal(oneP2, twoP2, threeP2, fourP2, fiveP2, oneToSixP2, bonusP2, chP2, fkP2, fhP2, ssP2, lsP2, yaP2);
			
				System.out.println("-------------------------------------------------------------------\n");
				turn++;
			}
		}
		turn = 12;
		
		print(dicesP1, turn, oneP1, twoP1, threeP1, fourP1, fiveP1, sixP1, bonusP1, chP1, fkP1, fhP1, ssP1, lsP1, yaP1, oneBlP1, twoBlP1, threeBlP1, fourBlP1, fiveBlP1, sixBlP1, bonusBlP1, chBlP1, fkBlP1, fhBlP1, ssBlP1, lsBlP1, yaBlP1, oneToSixP1, totalP1,
				 dicesP2, oneP2, twoP2, threeP2, fourP2, fiveP2, sixP2, bonusP2, chP2, fkP2, fhP2, ssP2, lsP2, yaP2, oneBlP2, twoBlP2, threeBlP2, fourBlP2, fiveBlP2, sixBlP2, bonusBlP2, chBlP2, fkBlP2, fhBlP2, ssBlP2, lsBlP2, yaBlP2, oneToSixP2, totalP2);
		
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
        
        while(true) {
            // 사용자 입력 받기
        	System.out.print("다시 굴릴 주사위 번호를 입력하세요 (예: 1 2 4, 종료는 0, 남은 기회 : "+ r + "번): ");
            String input = scanner.nextLine();
            
            //0을 입력하면 종료
            if(input.equals("0")) {
            	//scanner.close();
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
            // 결과 배열 출력
            System.out.print("결과 배열: [");
            for (int i = 0; i < RerollDices.length; i++) {
                System.out.print(RerollDices[i]);
                if (i < RerollDices.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
            */
            
            for (int i = 0; i < RerollDices.length; i++) {
            	if(RerollDices[i] != 0) {
            		System.out.print(RerollDices[i] + "번 ");
            	}
            }
            System.out.println("주사위를 다시 돌립니다.");
            
            for(int i = 0; i < 5; i++) {
            	if(RerollDices[i] == 0) {
            		break;
            	}
            	dices[RerollDices[i] - 1] = random.nextInt(6) + 1; // 1부터 6까지의 랜덤 숫자 생성
            }
            return r;
        }
	}
	
	private static void print(int[] dicesP1, int turn, int oneP1, int twoP1, int threeP1, int fourP1, int fiveP1, int sixP1, int bonusP1, int chP1, int fkP1, int fhP1, int ssP1, int lsP1, int yaP1, boolean oneBlP1, Boolean twoBlP1, Boolean threeBlP1, Boolean fourBlP1, Boolean fiveBlP1, Boolean sixBlP1, Boolean bonusBlP1, Boolean chBlP1, Boolean fkBlP1, Boolean fhBlP1, Boolean ssBlP1, Boolean lsBlP1, Boolean yaBlP1, int oneToSixP1, int totalP1,
			int[] dicesP2, int oneP2, int twoP2, int threeP2, int fourP2, int fiveP2, int sixP2, int bonusP2, int chP2, int fkP2, int fhP2, int ssP2, int lsP2, int yaP2, boolean oneBlP2, Boolean twoBlP2, Boolean threeBlP2, Boolean fourBlP2, Boolean fiveBlP2, Boolean sixBlP2, Boolean bonusBlP2, Boolean chBlP2, Boolean fkBlP2, Boolean fhBlP2, Boolean ssBlP2, Boolean lsBlP2, Boolean yaBlP2, int oneToSixP2, int totalP2) {
		System.out.println(" ================================== ");
		System.out.println("|\t   Turn " + turn + "/12  \t\t   |");
		System.out.println("|==================================|");
		System.out.println("| Categorries\t |player1 |player2 |");
		System.out.println("|==================================|");
		
		//1 ~ 6
		if(oneBlP1 == true && oneBlP2 == true) {
			System.out.println("|1. Aces\t | " + blue + oneCal(dicesP1, oneP1, oneBlP1) + exit + "\t     | " + blue + oneCal(dicesP2, oneP2, oneBlP2) + exit + "\t\t   |");
		}else if(oneBlP1 == true) {
			System.out.println("|1. Aces\t | " + blue + oneCal(dicesP1, oneP1, oneBlP1) + exit + "\t     | " + oneCal(dicesP2, oneP2, oneBlP2) + "\t   |");
		}else if(oneBlP2 == true) {
			System.out.println("|1. Aces\t | " + oneCal(dicesP1, oneP1, oneBlP1) + "\t  | " + blue + oneCal(dicesP2, oneP2, oneBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|1. Aces\t | " + oneCal(dicesP1, oneP1, oneBlP1) + "\t  | " + oneCal(dicesP2, oneP2, oneBlP2) + "\t   |");
		}
		
		if(twoBlP1 == true && twoBlP2 == true && twoP1 >= 10 && twoP2 >= 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(dicesP1, twoP1, twoBlP1) + exit + "\t    | " + blue + twoCal(dicesP2, twoP2, twoBlP2) + exit + "\t    |");
		}else if(twoBlP1 == true && twoBlP2 == true && twoP1 >= 10 && twoP2 < 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(dicesP1, twoP1, twoBlP1) + exit + "\t    | " + blue + twoCal(dicesP2, twoP2, twoBlP2) + exit + "\t     |");
		}else if(twoBlP1 == true && twoBlP2 == true && twoP1 < 10 && twoP2 >= 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(dicesP1, twoP1, twoBlP1) + exit + "\t     | " + blue + twoCal(dicesP2, twoP2, twoBlP2) + exit + "\t    |");
		}else if(twoBlP1 == true && twoBlP2 == true) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(dicesP1, twoP1, twoBlP1) + exit + "\t     | " + blue + twoCal(dicesP2, twoP2, twoBlP2) + exit + "\t     |");
		}else if(twoBlP1 == true && twoP1 >= 10) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(dicesP1, twoP1, twoBlP1) + exit + "\t    | " + twoCal(dicesP2, twoP2, twoBlP2) + "\t   |");
		}else if(twoBlP1 == true) {
			System.out.println("|2. Deuces\t | " + blue + twoCal(dicesP1, twoP1, twoBlP1) + exit + "\t     | " + twoCal(dicesP2, twoP2, twoBlP2) + "\t   |");
		}else if(twoBlP2 == true) {
			System.out.println("|2. Deuces\t | " + twoCal(dicesP1, twoP1, twoBlP1) + "\t  | " + blue + twoCal(dicesP2, twoP2, twoBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|2. Deuces\t | " + twoCal(dicesP1, twoP1, twoBlP1) + "\t  | " + twoCal(dicesP2, twoP2, twoBlP2) + "\t   |");
		}
		
		if(threeBlP1 == true && threeBlP2 == true && threeP1 >= 10 && threeP2 >= 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(dicesP1, threeP1, threeBlP1) + exit + "\t    | " + blue + threeCal(dicesP2, threeP2, threeBlP2) + exit + "\t    |");
		}else if(threeBlP1 == true && threeBlP2 == true && threeP1 >= 10 && threeP2 < 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(dicesP1, threeP1, threeBlP1) + exit + "\t    | " + blue + threeCal(dicesP2, threeP2, threeBlP2) + exit + "\t     |");
		}else if(threeBlP1 == true && threeBlP2 == true && threeP1 < 10 && threeP2 >= 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(dicesP1, threeP1, threeBlP1) + exit + "\t     | " + blue + threeCal(dicesP2, threeP2, threeBlP2) + exit + "\t    |");
		}else if(threeBlP1 == true && threeBlP2 == true) {
			System.out.println("|3. Threes\t | " + blue + threeCal(dicesP1, threeP1, threeBlP1) + exit + "\t     | " + blue + threeCal(dicesP2, threeP2, threeBlP2) + exit + "\t     |");
		}else if(threeBlP1 == true && threeP1 >= 10) {
			System.out.println("|3. Threes\t | " + blue + threeCal(dicesP1, threeP1, threeBlP1) + exit + "\t    | " + threeCal(dicesP2, threeP2, threeBlP2) + "\t   |");
		}else if(threeBlP1 == true) {
			System.out.println("|3. Threes\t | " + blue + threeCal(dicesP1, threeP1, threeBlP1) + exit + "\t     | " + threeCal(dicesP2, threeP2, threeBlP2) + "\t   |");
		}else if(threeBlP2 == true) {
			System.out.println("|3. Threes\t | " + threeCal(dicesP1, threeP1, threeBlP1) + "\t  | " + blue + threeCal(dicesP2, threeP2, threeBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|3. Threes\t | " + threeCal(dicesP1, threeP1, threeBlP1) + "\t  | " + threeCal(dicesP2, threeP2, threeBlP2) + "\t   |");
		}
		
		if(fourBlP1 == true && fourBlP2 == true && fourP1 >= 10 && fourP2 >= 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(dicesP1, fourP1, fourBlP1) + exit + "\t    | " + blue + fourCal(dicesP2, fourP2, fourBlP2) + exit + "\t    |");
		}else if(fourBlP1 == true && fourBlP2 == true && fourP1 >= 10 && fourP2 < 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(dicesP1, fourP1, fourBlP1) + exit + "\t    | " + blue + fourCal(dicesP2, fourP2, fourBlP2) + exit + "\t     |");
		}else if(fourBlP1 == true && fourBlP2 == true && fourP1 < 10 && fourP2 >= 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(dicesP1, fourP1, fourBlP1) + exit + "\t     | " + blue + fourCal(dicesP2, fourP2, fourBlP2) + exit + "\t    |");
		}else if(fourBlP1 == true && fourBlP2 == true) {
			System.out.println("|4. Fours\t | " + blue + fourCal(dicesP1, fourP1, fourBlP1) + exit + "\t     | " + blue + fourCal(dicesP2, fourP2, fourBlP2) + exit + "\t     |");
		}else if(fourBlP1 == true && fourP1 >= 10) {
			System.out.println("|4. Fours\t | " + blue + fourCal(dicesP1, fourP1, fourBlP1) + exit + "\t    | " + fourCal(dicesP2, fourP2, fourBlP2) + "\t   |");
		}else if(fourBlP1 == true) {
			System.out.println("|4. Fours\t | " + blue + fourCal(dicesP1, fourP1, fourBlP1) + exit + "\t     | " + fourCal(dicesP2, fourP2, fourBlP2) + "\t   |");
		}else if(fourBlP2 == true) {
			System.out.println("|4. Fours\t | " + fourCal(dicesP1, fourP1, fourBlP1) + "\t  | " + blue + fourCal(dicesP2, fourP2, fourBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|4. Fours\t | " + fourCal(dicesP1, fourP1, fourBlP1) + "\t  | " + fourCal(dicesP2, fourP2, fourBlP2) + "\t   |");
		}
		
		if(fiveBlP1 == true && fiveBlP2 == true && fiveP1 >= 10 && fiveP2 >= 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(dicesP1, fiveP1, fiveBlP1) + exit + "\t    | " + blue + fiveCal(dicesP2, fiveP2, fiveBlP2) + exit + "\t    |");
		}else if(fiveBlP1 == true && fiveBlP2 == true && fiveP1 >= 10 && fiveP2 < 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(dicesP1, fiveP1, fiveBlP1) + exit + "\t    | " + blue + fiveCal(dicesP2, fiveP2, fiveBlP2) + exit + "\t     |");
		}else if(fiveBlP1 == true && fiveBlP2 == true && fiveP1 < 10 && fiveP2 >= 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(dicesP1, fiveP1, fiveBlP1) + exit + "\t     | " + blue + fiveCal(dicesP2, fiveP2, fiveBlP2) + exit + "\t    |");
		}else if(fiveBlP1 == true && fiveBlP2 == true) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(dicesP1, fiveP1, fiveBlP1) + exit + "\t     | " + blue + fiveCal(dicesP2, fiveP2, fiveBlP2) + exit + "\t     |");
		}else if(fiveBlP1 == true && fiveP1 >= 10) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(dicesP1, fiveP1, fiveBlP1) + exit + "\t    | " + fiveCal(dicesP2, fiveP2, fiveBlP2) + "\t   |");
		}else if(fiveBlP1 == true) {
			System.out.println("|5. Fives\t | " + blue + fiveCal(dicesP1, fiveP1, fiveBlP1) + exit + "\t     | " + fiveCal(dicesP2, fiveP2, fiveBlP2) + "\t   |");
		}else if(fiveBlP2 == true) {
			System.out.println("|5. Fives\t | " + fiveCal(dicesP1, fiveP1, fiveBlP1) + "\t  | " + blue + fiveCal(dicesP2, fiveP2, fiveBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|5. Fives\t | " + fiveCal(dicesP1, fiveP1, fiveBlP1) + "\t  | " + fiveCal(dicesP2, fiveP2, fiveBlP2) + "\t   |");
		}
		
		if(sixBlP1 == true && sixBlP2 == true && sixP1 >= 10 && sixP2 >= 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(dicesP1, sixP1, sixBlP1) + exit + "\t    | " + blue + sixCal(dicesP2, sixP2, sixBlP2) + exit + "\t    |");
		}else if(sixBlP1 == true && sixBlP2 == true && sixP1 >= 10 && sixP2 < 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(dicesP1, sixP1, sixBlP1) + exit + "\t    | " + blue + sixCal(dicesP2, sixP2, sixBlP2) + exit + "\t     |");
		}else if(sixBlP1 == true && sixBlP2 == true && sixP1 < 10 && sixP2 >= 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(dicesP1, sixP1, sixBlP1) + exit + "\t     | " + blue + sixCal(dicesP2, sixP2, sixBlP2) + exit + "\t    |");
		}else if(sixBlP1 == true && sixBlP2 == true) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(dicesP1, sixP1, sixBlP1) + exit + "\t     | " + blue + sixCal(dicesP2, sixP2, sixBlP2) + exit + "\t     |");
		}else if(sixBlP1 == true && sixP1 >= 10) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(dicesP1, sixP1, sixBlP1) + exit + "\t    | " + sixCal(dicesP2, sixP2, sixBlP2) + "\t   |");
		}else if(sixBlP1 == true) {
			System.out.println("|6. Sixes\t | " + blue + sixCal(dicesP1, sixP1, sixBlP1) + exit + "\t     | " + sixCal(dicesP2, sixP2, sixBlP2) + "\t   |");
		}else if(sixBlP2 == true) {
			System.out.println("|6. Sixes\t | " + sixCal(dicesP1, sixP1, sixBlP1) + "\t  | " + blue + sixCal(dicesP2, sixP2, sixBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|6. Sixes\t | " + sixCal(dicesP1, sixP1, sixBlP1) + "\t  | " + sixCal(dicesP2, sixP2, sixBlP2) + "\t   |");
		}
		System.out.println("|==================================|");
		
		//보너스
		if(bonusBlP1 == true && bonusBlP2 == true) {
			System.out.println("|Subtotal\t | " + blue + oneToSixP1 + "/63" + exit + "\t | " + blue + oneToSixP2 + "/63" + exit + "\t |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + blue + "+" + bonusCal(bonusBlP1) + exit + "\t\t  | " + blue + "+" + bonusCal(bonusBlP2) + exit + "\t   |");
		}else if(bonusBlP1 == true && oneToSixP2 >= 10) {
			System.out.println("|Subtotal\t | " + blue + oneToSixP1 + "/63" + exit + "\t | " +  oneToSixP2 + "/63" + "  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + blue + "+" + bonusCal(bonusBlP1) + exit + "\t\t  | " + "+" + bonusCal(bonusBlP2) + "\t   |");
		}else if(bonusBlP1 == true && oneToSixP2 < 10) {
			System.out.println("|Subtotal\t | " + blue + oneToSixP1 + "/63" + exit + "\t | " +  oneToSixP2 + "/63" + "   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + blue + "+" + bonusCal(bonusBlP1) + exit + "\t\t  | " + "+" + bonusCal(bonusBlP2) + "\t   |");
		}else if(bonusBlP2 == true && oneToSixP1 >= 10) {
			System.out.println("|Subtotal\t | " + oneToSixP1 + "/63" + "  | " + blue + oneToSixP2 + "/63" + exit + "  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusBlP1) + "\t  | " + blue + "+" + bonusCal(bonusBlP2) + exit + "\t   |");
		}else if(bonusBlP2 == true && oneToSixP1 < 10) {
			System.out.println("|Subtotal\t | " + oneToSixP1 + "/63" + "\t  | " + blue + oneToSixP2 + "/63" + exit + "  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusBlP1) + "\t  | " + blue + "+" + bonusCal(bonusBlP2) + exit + "\t   |");
		}else if(oneToSixP1 >= 10 && oneToSixP2 >= 10) {
			System.out.println("|Subtotal\t | " + oneToSixP1 + "/63  | " + oneToSixP2 + "/63  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusBlP1) + "\t  | " + "+" + bonusCal(bonusBlP2) + "\t   |");
		}else if(oneToSixP1 >= 10 && oneToSixP2 < 10) {
			System.out.println("|Subtotal\t | " + oneToSixP1 + "/63  | " + oneToSixP2 + "/63   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusBlP1) + "\t  | " + "+" + bonusCal(bonusBlP2) + "\t   |");
		}else if(oneToSixP1 < 10 && oneToSixP2 >= 10) {
			System.out.println("|Subtotal\t | " + oneToSixP1 + "/63   | " + oneToSixP2 + "/63  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusBlP1) + "\t  | " + "+" + bonusCal(bonusBlP2) + "\t   |");
		}else {
			System.out.println("|Subtotal\t | " + oneToSixP1 + "/63" + "\t  | " + oneToSixP2 + "/63   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusBlP1) + "\t  | " + "+" + bonusCal(bonusBlP2) + "\t   |");
		}
		
		System.out.println("|==================================|");

		//choice
		if(chBlP1 == true && chBlP2 == true && chP1 >= 10 && chP2 >= 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(dicesP1, chP1, chBlP1) + exit + "\t    | " + blue + chCal(dicesP2, chP2, chBlP2) + exit + "\t    |");
		}else if(chBlP1 == true && chBlP2 == true && chP1 >= 10 && chP2 < 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(dicesP1, chP1, chBlP1) + exit + "\t    | " + blue + chCal(dicesP2, chP2, chBlP2) + exit + "\t     |");
		}else if(chBlP1 == true && chBlP2 == true && chP1 < 10 && chP2 >= 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(dicesP1, chP1, chBlP1) + exit + "\t     | " + blue + chCal(dicesP2, chP2, chBlP2) + exit + "\t    |");
		}else if(chBlP1 == true && chBlP2 == true) {
			System.out.println("|7. Choice\t | " + blue + chCal(dicesP1, chP1, chBlP1) + exit + "\t     | " + blue + chCal(dicesP2, chP2, chBlP2) + exit + "\t     |");
		}else if(chBlP1 == true && chP1 >= 10) {
			System.out.println("|7. Choice\t | " + blue + chCal(dicesP1, chP1, chBlP1) + exit + "\t    | " + chCal(dicesP2, chP2, chBlP2) + "\t   |");
		}else if(chBlP1 == true) {
			System.out.println("|7. Choice\t | " + blue + chCal(dicesP1, chP1, chBlP1) + exit + "\t     | " + chCal(dicesP2, chP2, chBlP2) + "\t   |");
		}else if(chBlP2 == true) {
			System.out.println("|7. Choice\t | " + chCal(dicesP1, chP1, chBlP1) + "\t  | " + blue + chCal(dicesP2, chP2, chBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|7. Choice\t | " + chCal(dicesP1, chP1, chBlP1) + "\t  | " + chCal(dicesP2, chP2, chBlP2) + "\t   |");
		}
		System.out.println("|==================================|");
		
		//특수 족보
		if(fkBlP1 == true && fkBlP2 == true && fkP1 >= 10 && fkP2 >= 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(dicesP1, fkP1, fkBlP1) + exit + "\t    | " + blue + fkCal(dicesP2, fkP2, fkBlP2) + exit + "\t    |");
		}else if(fkBlP1 == true && fkBlP2 == true && fkP1 >= 10 && fkP2 < 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(dicesP1, fkP1, fkBlP1) + exit + "\t    | " + blue + fkCal(dicesP2, fkP2, fkBlP2) + exit + "\t     |");
		}else if(fkBlP1 == true && fkBlP2 == true && fkP1 < 10 && fkP2 >= 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(dicesP1, fkP1, fkBlP1) + exit + "\t     | " + blue + fkCal(dicesP2, fkP2, fkBlP2) + exit + "\t    |");
		}else if(fkBlP1 == true && fkBlP2 == true) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(dicesP1, fkP1, fkBlP1) + exit + "\t     | " + blue + fkCal(dicesP2, fkP2, fkBlP2) + exit + "\t     |");
		}else if(fkBlP1 == true && fkP1 >= 10) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(dicesP1, fkP1, fkBlP1) + exit + "\t    | " + fkCal(dicesP2, fkP2, fkBlP2) + "\t   |");
		}else if(fkBlP1 == true) {
			System.out.println("|8. 4 of a Kind  | " + blue + fkCal(dicesP1, fkP1, fkBlP1) + exit + "\t     | " + fkCal(dicesP2, fkP2, fkBlP2) + "\t   |");
		}else if(fkBlP2 == true) {
			System.out.println("|8. 4 of a Kind  | " + fkCal(dicesP1, fkP1, fkBlP1) + "\t  | " + blue + fkCal(dicesP2, fkP2, fkBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|8. 4 of a Kind  | " + fkCal(dicesP1, fkP1, fkBlP1) + "\t  | " + fkCal(dicesP2, fkP2, fkBlP2) + "\t   |");
		}
		
		if(fhBlP1 == true && fhBlP2 == true && fhP1 >= 10 && fhP2 >= 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(dicesP1, fhP1, fhBlP1) + exit + "\t    | " + blue + fhCal(dicesP2, fhP2, fhBlP2) + exit + "\t    |");
		}else if(fhBlP1 == true && fhBlP2 == true && fhP1 >= 10 && fhP2 < 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(dicesP1, fhP1, fhBlP1) + exit + "\t    | " + blue + fhCal(dicesP2, fhP2, fhBlP2) + exit + "\t     |");
		}else if(fhBlP1 == true && fhBlP2 == true && fhP1 < 10 && fhP2 >= 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(dicesP1, fhP1, fhBlP1) + exit + "\t     | " + blue + fhCal(dicesP2, fhP2, fhBlP2) + exit + "\t    |");
		}else if(fhBlP1 == true && fhBlP2 == true) {
			System.out.println("|9. Full House   | " + blue + fhCal(dicesP1, fhP1, fhBlP1) + exit + "\t     | " + blue + fhCal(dicesP2, fhP2, fhBlP2) + exit + "\t     |");
		}else if(fhBlP1 == true && fhP1 >= 10) {
			System.out.println("|9. Full House   | " + blue + fhCal(dicesP1, fhP1, fhBlP1) + exit + "\t    | " + fhCal(dicesP2, fhP2, fhBlP2) + "\t   |");
		}else if(fhBlP1 == true) {
			System.out.println("|9. Full House   | " + blue + fhCal(dicesP1, fhP1, fhBlP1) + exit + "\t     | " + fhCal(dicesP2, fhP2, fhBlP2) + "\t   |");
		}else if(fhBlP2 == true) {
			System.out.println("|9. Full House   | " + fhCal(dicesP1, fhP1, fhBlP1) + "\t  | " + blue + fhCal(dicesP2, fhP2, fhBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|9. Full House   | " + fhCal(dicesP1, fhP1, fhBlP1) + "\t  | " + fhCal(dicesP2, fhP2, fhBlP2) + "\t   |");
		}
		
		if(ssBlP1 == true && ssBlP2 == true && ssP1 >= 10 && ssP2 >= 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(dicesP1, ssP1, ssBlP1) + exit + "\t    | " + blue + ssCal(dicesP2, ssP2, ssBlP2) + exit + "\t    |");
		}else if(ssBlP1 == true && ssBlP2 == true && ssP1 >= 10 && ssP2 < 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(dicesP1, ssP1, ssBlP1) + exit + "\t    | " + blue + ssCal(dicesP2, ssP2, ssBlP2) + exit + "\t     |");
		}else if(ssBlP1 == true && ssBlP2 == true && ssP1 < 10 && ssP2 >= 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(dicesP1, ssP1, ssBlP1) + exit + "\t     | " + blue + ssCal(dicesP2, ssP2, ssBlP2) + exit + "\t    |");
		}else if(ssBlP1 == true && ssBlP2 == true) {
			System.out.println("|10. S. Straight | " + blue + ssCal(dicesP1, ssP1, ssBlP1) + exit + "\t     | " + blue + ssCal(dicesP2, ssP2, ssBlP2) + exit + "\t     |");
		}else if(ssBlP1 == true && ssP1 >= 10) {
			System.out.println("|10. S. Straight | " + blue + ssCal(dicesP1, ssP1, ssBlP1) + exit + "\t    | " + ssCal(dicesP2, ssP2, ssBlP2) + "\t   |");
		}else if(ssBlP1 == true) {
			System.out.println("|10. S. Straight | " + blue + ssCal(dicesP1, ssP1, ssBlP1) + exit + "\t     | " + ssCal(dicesP2, ssP2, ssBlP2) + "\t   |");
		}else if(ssBlP2 == true) {
			System.out.println("|10. S. Straight | " + ssCal(dicesP1, ssP1, ssBlP1) + "\t  | " + blue + ssCal(dicesP2, ssP2, ssBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|10. S. Straight | " + ssCal(dicesP1, ssP1, ssBlP1) + "\t  | " + ssCal(dicesP2, ssP2, ssBlP2) + "\t   |");
		}
		
		if(lsBlP1 == true && lsBlP2 == true && lsP1 >= 10 && lsP2 >= 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(dicesP1, lsP1, lsBlP1) + exit + "\t    | " + blue + lsCal(dicesP2, lsP2, lsBlP2) + exit + "\t    |");
		}else if(lsBlP1 == true && lsBlP2 == true && lsP1 >= 10 && lsP2 < 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(dicesP1, lsP1, lsBlP1) + exit + "\t    | " + blue + lsCal(dicesP2, lsP2, lsBlP2) + exit + "\t     |");
		}else if(lsBlP1 == true && lsBlP2 == true && lsP1 < 10 && lsP2 >= 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(dicesP1, lsP1, lsBlP1) + exit + "\t     | " + blue + lsCal(dicesP2, lsP2, lsBlP2) + exit + "\t    |");
		}else if(lsBlP1 == true && lsBlP2 == true) {
			System.out.println("|11. L. Straight | " + blue + lsCal(dicesP1, lsP1, lsBlP1) + exit + "\t     | " + blue + lsCal(dicesP2, lsP2, lsBlP2) + exit + "\t     |");
		}else if(lsBlP1 == true && lsP1 >= 10) {
			System.out.println("|11. L. Straight | " + blue + lsCal(dicesP1, lsP1, lsBlP1) + exit + "\t    | " + lsCal(dicesP2, lsP2, lsBlP2) + "\t   |");
		}else if(lsBlP1 == true) {
			System.out.println("|11. L. Straight | " + blue + lsCal(dicesP1, lsP1, lsBlP1) + exit + "\t     | " + lsCal(dicesP2, lsP2, lsBlP2) + "\t   |");
		}else if(lsBlP2 == true) {
			System.out.println("|11. L. Straight | " + lsCal(dicesP1, lsP1, lsBlP1) + "\t  | " + blue + lsCal(dicesP2, lsP2, lsBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|11. L. Straight | " + lsCal(dicesP1, lsP1, lsBlP1) + "\t  | " + lsCal(dicesP2, lsP2, lsBlP2) + "\t   |");
		}
		
		if(yaBlP1 == true && yaBlP2 == true && yaP1 >= 10 && yaP2 >= 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(dicesP1, yaP1, yaBlP1) + exit + "\t    | " + blue + yaCal(dicesP2, yaP2, yaBlP2) + exit + "\t    |");
		}else if(yaBlP1 == true && yaBlP2 == true && yaP1 >= 10 && yaP2 < 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(dicesP1, yaP1, yaBlP1) + exit + "\t    | " + blue + yaCal(dicesP2, yaP2, yaBlP2) + exit + "\t     |");
		}else if(yaBlP1 == true && yaBlP2 == true && yaP1 < 10 && yaP2 >= 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(dicesP1, yaP1, yaBlP1) + exit + "\t     | " + blue + yaCal(dicesP2, yaP2, yaBlP2) + exit + "\t    |");
		}else if(yaBlP1 == true && yaBlP2 == true) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(dicesP1, yaP1, yaBlP1) + exit + "\t     | " + blue + yaCal(dicesP2, yaP2, yaBlP2) + exit + "\t     |");
		}else if(yaBlP1 == true && yaP1 >= 10) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(dicesP1, yaP1, yaBlP1) + exit + "\t    | " + yaCal(dicesP2, yaP2, yaBlP2) + "\t   |");
		}else if(yaBlP1 == true) {
			System.out.println("|12. Yacht\t | " + blue + yaCal(dicesP1, yaP1, yaBlP1) + exit + "\t     | " + yaCal(dicesP2, yaP2, yaBlP2) + "\t   |");
		}else if(yaBlP2 == true) {
			System.out.println("|12. Yacht\t | " + yaCal(dicesP1, yaP1, yaBlP1) + "\t  | " + blue + yaCal(dicesP2, yaP2, yaBlP2) + exit + "\t\t   |");
		}else {
			System.out.println("|12. Yacht\t | " + yaCal(dicesP1, yaP1, yaBlP1) + "\t  | " + yaCal(dicesP2, yaP2, yaBlP2) + "\t   |");
		}
		
		//합계 출력
		System.out.println("|==================================|");
		System.out.println("| Total\t\t | " + totalP1 + "\t  | " + totalP2 + "\t   |");
		System.out.println(" ================================== ");
	}
	
	
	private static void insertDice(int[] dices, int one, int two, int three, int four, int five, int six, int ch, int fk, int fh, int ss, int ls, int ya, boolean oneBl, boolean twoBl, boolean threeBl, boolean fourBl, boolean fiveBl, boolean sixBl, boolean chBl, boolean fkBl, boolean fhBl, boolean ssBl, boolean lsBl, boolean yaBl) {
		//while문 조건용 변수
		boolean validInput = false;
		
		//족보에 무사히 값이 들어가면 while문 종료
		while(validInput == false) {
			String menu = "0";
			
			System.out.print("1~12 중 어디에 넣을 건지를 선택하세요 : ");
			menu = scanner.next();
			scanner.nextLine();
			
			switch (menu) {
			case "1": {
				if(oneBl == true) { //족보에 이미 값이 들어가 있으면
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue; //whlie문 다시 시작
				}
				one = oneCal(dices, one, oneBl);
				oneBl = true;
				validInput = true; //while문 빠져나오는 용
				break;
			}
			case "2": {
				if(twoBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				two = twoCal(dices, two, twoBl);
				twoBl = true;
				validInput = true;
				break;
			}
			case "3": {
				if(threeBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				three = threeCal(dices, three, threeBl);
				threeBl = true;
				validInput = true;
				break;
			}
			case "4": {
				if(fourBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				four = fourCal(dices, four, fourBl);
				fourBl = true;
				validInput = true;
				break;
			}
			case "5": {
				if(fiveBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				five = fiveCal(dices, five, fiveBl);
				fiveBl = true;
				validInput = true;
				break;
			}
			case "6": {
				if(sixBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				six = sixCal(dices, six, sixBl);
				sixBl = true;
				validInput = true;
				break;
			}
			case "7": {
				if(chBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				ch = chCal(dices, ch, chBl);
				chBl = true;
				validInput = true;
				break;
			}
			case "8": {
				if(fkBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				fk = fkCal(dices, fk, fkBl);
				fkBl = true;
				validInput = true;
				break;
			}
			case "9": {
				if(fhBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				fh = fhCal(dices, fh, fhBl);
				fhBl = true;
				validInput = true;
				break;
			}
			case "10": {
				if(ssBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				ss = ssCal(dices, ss, ssBl);
				ssBl = true;
				validInput = true;
				break;
			}
			case "11": {
				if(lsBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				ls = lsCal(dices, ls, lsBl);
				lsBl = true;
				validInput = true;
				break;
			}
			case "12": {
				if(yaBl == true) {
					System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
					continue;
				}
				ya = yaCal(dices, ya, yaBl);
				yaBl = true;
				validInput = true;
				break;
			}
		}
		}
	}
	
	private static int oneCal(int[] dices, int one, boolean oneB) {
        // 숫자 1의 개수를 세기 위한 변수 선언
        int count = 0;
        
        if(oneB == true) {
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
	
	private static int twoCal(int[] dices, int two, boolean twoB) {
        // 숫자 2의 개수를 세기 위한 변수 선언
        int count = 0;
        
        if(twoB == true) {
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
	
	private static int threeCal(int[] dices, int three, boolean threeB) {
        // 숫자 3의 개수를 세기 위한 변수 선언
        int count = 0;
        
        if(threeB == true) {
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
	
	private static int fourCal(int[] dices, int four, boolean fourB) {
        // 숫자 4의 개수를 세기 위한 변수 선언
        int count = 0;
        
        if(fourB == true) {
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
	
	private static int fiveCal(int[] dices, int five, boolean fiveB) {
        // 숫자 5의 개수를 세기 위한 변수 선언
        int count = 0;
        
        if(fiveB == true) {
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
	
	private static int sixCal(int[] dices, int six, boolean sixB) {
        // 숫자 6의 개수를 세기 위한 변수 선언
        int count = 0;
        
        if(sixB == true) {
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
	
	private static int bonusCal(boolean bonusB) {
		if(bonusB == true) {
			return 35;
		}
		
		return 0;
	}
	
	private static int chCal(int[] dices, int ch, boolean chB) {
		int sum = 0;
		
        if(chB == true) {
        	return ch;
        }
		
		for(int i = 0; i < dices.length; i++) {
			sum += dices[i];
		}
		
		return sum;
	}
	
	private static int fkCal(int[] dices, int fk, boolean fkB) {
        // 각 숫자의 빈도를 저장할 배열 (1부터 6까지이므로 크기는 6)
        int[] count = new int[6];
        int sum = 0;
        
        if(fkB == true) {
        	return fk;
        }
        
        if(dices[0] == 0) {
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
        
        if(hasFourOrMore == true) {
        	for(int i = 0; i < dices.length; i++) {
        		sum += dices[i];
        	}
        }
		
		return sum;
	}
	
	private static int fhCal(int[] dices, int fh, boolean fhB) {
        // 각 숫자의 빈도를 저장할 배열 (1부터 6까지이므로 크기는 6)
        int[] count = new int[6];
        int sum = 0;
        
        if(fhB == true) {
        	return fh;
        }
        
        if(dices[0] == 0) {
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
        
        if(isFullHouse == true) {
        	for(int i = 0; i < dices.length; i++) {
            	sum += dices[i];
            }	
        }
		
		return sum;
	}
	
	private static int ssCal(int[] dices, int ss, boolean ssB) {
		int[] straight = new int[5];
		int value = 0;
		
        if(ssB == true) {
        	return ss;
        }
		
		for(int i = 0; i < dices.length; i++) {
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
        
        if(hasFourInARow == true) {
        	value = 15;
        }
        
		return value;
	}
	
	private static int lsCal(int[] dices, int ls, boolean lsB) {
		int[] straight = new int[5];
		int value = 0;
		
        if(lsB == true) {
        	return ls;
        }
		
		for(int i = 0; i < dices.length; i++) {
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
        
        if(hasFiveInARow == true) {
        	value = 30;
        }
        
		return value;
	}
	
	private static int yaCal(int[] dices, int ya, boolean yaB) {
        // 각 숫자의 빈도를 저장할 배열 (1부터 6까지이므로 크기는 6)
        int[] count = new int[6];
        int value = 0;
        
        if(yaB == true) {
        	return ya;
        }
        
        if(dices[0] == 0) {
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
        
        if(hasFiveOrMore == true) {
        	value = 50;
        }
		
		return value;
	}
	
	private static int totalCal(int one, int two, int three, int four, int five, int six, int bonus, int ch, int fk, int fh, int ss, int ls, int ya) {
		int total = one + two + three + four + five + six + bonus
					+ ch + fk + fh + ss + ls + ya;
		
		return total;
	}
}