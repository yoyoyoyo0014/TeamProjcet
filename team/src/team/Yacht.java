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
		int[] dices = new int[5];
		
		//족보 저장 변수
		int one, two, three, four, five, six;
		
		//족보 1~6 합
		int oneToSix = 0;
		
		//보너스 점수 변수
		int bonus;
		
		//족보 저장 변수
		int ch, fk, fh, ss, ls, ya;
		
		//족보 저장 여부 판별용
		boolean oneB = false;
		boolean twoB = false;
		boolean threeB = false;
		boolean fourB = false;
		boolean fiveB = false;
		boolean sixB = false;
		boolean bonusB = false;
		boolean chB = false;
		boolean fkB = false;
		boolean fhB = false;
		boolean ssB = false;
		boolean lsB = false;
		boolean yaB = false;
		
		//게임의 턴 저장 변수
		int turn = 1;
		
		//변수 초기화
		{
			one = 0;
			two = 0;
			three = 0;
			four = 0;
			five = 0;
			six = 0;
			bonus = 0;
			ch = 0;
			fk = 0;
			fh = 0;
			ss = 0;
			ls = 0;
			ya = 0;
		}
		
		//12턴 동안 반복
		while(turn <= 12) {
			
			//주사위 굴리기
			diceRoll(dices);
			
			//화면 출력
			print(dices, turn, one, two, three, four, five, six, bonus, ch, fk, fh, ss, ls, ya, oneB, twoB, threeB, fourB, fiveB, sixB, bonusB, chB, fkB, fhB, ssB, lsB, yaB, oneToSix);
			
			//배열의 내용을 출력
			System.out.println("주사위 결과:");
			for (int result : dices) {
				System.out.print("[" + result + "]");
			}
			System.out.println();
			
			//주사위 다시 굴리기(최대 2회)
			for(int r = 2; r > 0;) {
		        // 주사위를 다시 굴리기
		        r = diceReroll(dices, r);
		        
		        //r = n;
		        
		        print(dices, turn, one, two, three, four, five, six, bonus, ch, fk, fh, ss, ls, ya, oneB, twoB, threeB, fourB, fiveB, sixB, bonusB, chB, fkB, fhB, ssB, lsB, yaB, oneToSix);

		        // 배열의 내용을 출력
		        System.out.println("다시 굴린 주사위 결과:");
		        for (int result : dices) {
		            System.out.print("[" + result + "]");
		        }
		        System.out.println();
		        
		        r--;
			}
			
			//insertDice(dices, one, two, three, four, five, six, ch, fk, fh, ss, ls, ya);
			
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
					if(oneB == true) { //족보에 이미 값이 들어가 있으면
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue; //whlie문 다시 시작
					}
					one = oneCal(dices, one, oneB);
					oneB = true;
					validInput = true; //while문 빠져나오는 용
					break;
				}
				case "2": {
					if(twoB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					two = twoCal(dices, two, twoB);
					twoB = true;
					validInput = true;
					break;
				}
				case "3": {
					if(threeB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					three = threeCal(dices, three, threeB);
					threeB = true;
					validInput = true;
					break;
				}
				case "4": {
					if(fourB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					four = fourCal(dices, four, fourB);
					fourB = true;
					validInput = true;
					break;
				}
				case "5": {
					if(fiveB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					five = fiveCal(dices, five, fiveB);
					fiveB = true;
					validInput = true;
					break;
				}
				case "6": {
					if(sixB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					six = sixCal(dices, six, sixB);
					sixB = true;
					validInput = true;
					break;
				}
				case "7": {
					if(chB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					ch = chCal(dices, ch, chB);
					chB = true;
					validInput = true;
					break;
				}
				case "8": {
					if(fkB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					fk = fkCal(dices, fk, fkB);
					fkB = true;
					validInput = true;
					break;
				}
				case "9": {
					if(fhB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					fh = fhCal(dices, fh, fhB);
					fhB = true;
					validInput = true;
					break;
				}
				case "10": {
					if(ssB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					ss = ssCal(dices, ss, ssB);
					ssB = true;
					validInput = true;
					break;
				}
				case "11": {
					if(lsB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					ls = lsCal(dices, ls, lsB);
					lsB = true;
					validInput = true;
					break;
				}
				case "12": {
					if(yaB == true) {
						System.out.println("이미 등록되어 있습니다. 다른 족보를 선택해주세요.");
						continue;
					}
					ya = yaCal(dices, ya, yaB);
					yaB = true;
					validInput = true;
					break;
				}
			}
				//보너스 점수 획득 가능한지 계산
				oneToSix = one + two + three + four + five + six;
				
				if(oneToSix >= 63) {
					bonusB = true;
					bonus = 35;
				}
			
			
				System.out.println("-------------------------------------------------------------------\n");
				turn++;
			}
		}
		turn = 12;
		
		print(dices, turn, one, two, three, four, five, six, bonus, ch, fk, fh, ss, ls, ya, oneB, twoB, threeB, fourB, fiveB, sixB, bonusB, chB, fkB, fhB, ssB, lsB, yaB, oneToSix);
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
	
	private static void print(int[] dices, int turn, int one, int two, int three, int four, int five, int six, int bonus, int ch, int fk, int fh, int ss, int ls, int ya, boolean oneB, Boolean twoB, Boolean threeB, Boolean fourB, Boolean fiveB, Boolean sixB, Boolean bonusB, Boolean chB, Boolean fkB, Boolean fhB, Boolean ssB, Boolean lsB, Boolean yaB, int oneToSix) {
		System.out.println(" ================================== ");
		System.out.println("|\t   Turn " + turn + "/12  \t\t   |");
		System.out.println("|==================================|");
		System.out.println("| Categorries\t |player1 |player2 |");
		System.out.println("|==================================|");
		
		//1 ~ 6
		if(oneB == true) {
			System.out.println("|1. Aces\t | " + blue + oneCal(dices, one, oneB) + exit + "\t     | " + blue + oneCal(dices, one, oneB) + exit + "\t\t   |");
		} else {
			System.out.println("|1. Aces\t | " + oneCal(dices, one, oneB) + "\t  | " + oneCal(dices, one, oneB) + "\t   |");
		}
		if(twoB== true) {
			System.out.println("|2. Deuces\t | " +  blue + twoCal(dices, two, twoB) + exit + "\t\t  | " +  blue + twoCal(dices, two, twoB) + exit + "\t\t   |");
		}else {
			System.out.println("|2. Deuces\t | " + twoCal(dices, two, twoB) + "\t  | " + twoCal(dices, two, twoB) + "\t   |");
		}
		if(threeB== true) {
			System.out.println("|3. Threes\t | " +  blue + threeCal(dices, three, threeB) + exit + "\t\t  | " +  blue + threeCal(dices, three, threeB) + exit + "\t\t   |");
		}else {
			System.out.println("|3. Threes\t | " + threeCal(dices, three, threeB) + "\t  | " + threeCal(dices, three, threeB) + "\t   |");
		}
		if(fourB == true) {
			System.out.println("|4. Fours\t | " +  blue + fourCal(dices, four, fourB) + exit + "\t\t  | " +  blue + fourCal(dices, four, fourB) + exit + "\t\t   |");
		}else {
			System.out.println("|4. Fours\t | " + fourCal(dices, four, fourB) + "\t  | " + fourCal(dices, four, fourB) + "\t   |");
		}
		if(fiveB == true) {
			System.out.println("|5. Fives\t | " +  blue + fiveCal(dices, five, fiveB) + exit + "\t\t  | " +  blue + fiveCal(dices, five, fiveB) + exit + "\t\t   |");
		}else {
			System.out.println("|5. Fives\t | " + fiveCal(dices, five, fiveB) + "\t  | " + fiveCal(dices, five, fiveB) + "\t   |");
		}
		if(sixB == true) {
			System.out.println("|6. Sixes\t | " +  blue + sixCal(dices, six, sixB) + exit + "\t\t  | " +  blue + sixCal(dices, six, sixB) + exit + "\t\t   |");
		}else {
			System.out.println("|6. Sixes\t | " + sixCal(dices, six, sixB) + "\t  | " + sixCal(dices, six, sixB) + "\t   |");
		}
		System.out.println("|==================================|");
		
		//보너스
		if(bonusB == true) {
			System.out.println("|Subtotal\t | " +  blue + oneToSix + "/63" + exit + "\t | " +  blue + oneToSix + "/63" + exit + "\t |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " +  blue + "+" + bonusCal(bonusB) + exit + "\t\t  | " +  blue + "+" + bonusCal(bonusB) + exit + "\t   |");
		}else if(oneToSix >= 10) {
			System.out.println("|Subtotal\t | " + oneToSix + "/63  | " + oneToSix + "/63  |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusB) + "\t  | " + "+" + bonusCal(bonusB) + "\t   |");
		}else {
			System.out.println("|Subtotal\t | " + oneToSix + "/63" + "\t  | " + oneToSix + "/63   |");
			System.out.println("|----------------------------------|");
			System.out.println("|+35 Bonus\t | " + "+" + bonusCal(bonusB) + "\t  | " + "+" + bonusCal(bonusB) + "\t   |");
		}
		
		System.out.println("|==================================|");

		//choice
		if(chB == true) {
			System.out.println("|7. Choice\t | " +  blue + chCal(dices, ch, chB) + exit + "\t\t  | " +  blue + chCal(dices, ch, chB) + exit + "\t\t   |");
		}else {
			System.out.println("|7. Choice\t | " + chCal(dices, ch, chB) + "\t  | " + chCal(dices, ch, chB) + "\t   |");
		}
		System.out.println("|==================================|");
		
		//특수 족보
		if(fkB == true) {
			System.out.println("|8. 4 of a Kind  | " +  blue + fkCal(dices, fk, fkB) + exit + "\t\t  | " +  blue + fkCal(dices, fk, fkB) + exit + "\t\t   |");
		}else {
			System.out.println("|8. 4 of a Kind  | " + fkCal(dices, fk, fkB) + "\t  | " + fkCal(dices, fk, fkB) + "\t   |");
		}
		if(fhB == true) {
			System.out.println("|9. Full House   | " +  blue + fhCal(dices, fh, fhB) + exit + "\t\t  | " +  blue + fhCal(dices, fh, fhB) + exit + "\t\t   |");
		}else {
			System.out.println("|9. Full House   | " + fhCal(dices, fh, fhB) + "\t  | " + fhCal(dices, fh, fhB) + "\t   |");
		}
		if(ssB == true) {
			System.out.println("|10. S. Straight | " +  blue + ssCal(dices, ss, ssB) + exit + "\t\t  | " +  blue + ssCal(dices, ss, ssB) + exit + "\t\t   |");
		}else {
			System.out.println("|10. S. Straight | " + ssCal(dices, ss, ssB) + "\t  | " + ssCal(dices, ss, ssB) + "\t   |");
		}
		if(lsB == true) {
			System.out.println("|11. L. Straight | " +  blue + lsCal(dices, ls, lsB) + exit + "\t\t  | " +  blue + lsCal(dices, ls, lsB) + exit + "\t\t   |");
		}else {
			System.out.println("|11. L. Straight | " + lsCal(dices, ls, lsB) + "\t  | " + lsCal(dices, ls, lsB) + "\t   |");
		}
		if(yaB == true) {
			System.out.println("|12. Yacht\t | " +  blue + yaCal(dices, ya, yaB) + exit + "\t\t  | " +  blue + yaCal(dices, ya, yaB) + exit + "\t\t   |");
		}else {
			System.out.println("|12. Yacht\t | " + yaCal(dices, ya, yaB) + "\t  | " + yaCal(dices, ya, yaB) + "\t   |");
		}
		
		//합계 출력
		System.out.println("|==================================|");
		System.out.println("| Total\t\t | " + totalCal(one, two, three, four, five, six, bonus, ch, fk, fh, ss, ls, ya) + "\t  | " + totalCal(one, two, three, four, five, six, bonus, ch, fk, fh, ss, ls, ya) + "\t   |");
		System.out.println(" ================================== ");
	}
	
	
	/*
	private static void insertDice(int[] dices, int one, int two, int three, int four, int five, int six, int ch, int fk, int fh, int ss, int ls, int ya) {
		int menu = 0;
		
		System.out.print("1~12 중 어디에 넣을 건지를 선택하세요 : ");
		menu = scanner.nextInt();
		
		switch (menu) {
		case 1: {
			one = oneCal(dices, one);
			break;
		}
		case 2: {
			two = twoCal(dices, two);
			break;
		}
		case 3: {
			threeCal(dices, three);
			break;
		}
		case 4: {
			fourCal(dices, four);
			break;
		}
		case 5: {
			fiveCal(dices, five);
			break;
		}
		case 6: {
			sixCal(dices, six);
			break;
		}
		case 7: {
			chCal(dices, ch);
			break;
		}
		case 8: {
			fkCal(dices, fk);
			break;
		}
		case 9: {
			fhCal(dices, fh);
			break;
		}
		case 10: {
			ssCal(dices, ss);
			break;
		}
		case 11: {
			lsCal(dices, ls);
			break;
		}
		case 12: {
			yaCal(dices, ya);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + menu);
		}
	}
	*/
	
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