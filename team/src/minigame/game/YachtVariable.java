package minigame.game;

public class YachtVariable {
	
		//주사위 눈금 배열
		int[] dices = new int[5];
		
		//족보 저장 변수
		int one, two, three, four, five, six;
		
		//족보 1~6 합
		int oneToSix;
		
		//보너스 점수 변수
		int bonus;
		
		//족보 저장 변수
		int ch, fk, fh, ss, ls, ya;
		
		int total;
		
		//족보 저장 여부 판별용
		boolean oneBl = false;
		boolean twoBl = false;
		boolean threeBl = false;
		boolean fourBl = false;
		boolean fiveBl = false;
		boolean sixBl = false;
		boolean chBl = false;
		boolean fkBl = false;
		boolean fhBl = false;
		boolean ssBl = false;
		boolean lsBl = false;
		boolean yaBl = false;
		
		int reRollCount = 0;
		int duplicateInput = 0;
		
		boolean bonusBl = false;
		boolean validInput = false;

		//변수 초기화
		public YachtVariable() {
			
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
			
			oneToSix = 0;
			
			total = 0;
		}
}