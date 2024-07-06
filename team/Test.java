package team;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Test {

	static Scanner scan = new Scanner(System.in);
	
	static List<String> eWords = new ArrayList<String>();
	static List<String> answer = new ArrayList<String>();
	
	
    public static void main(String[] args) throws ParseException {
    	
    	eWords.add("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세");
    	eWords.add("간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 장 공장장이다.");
    	eWords.add("네가 떨어뜨린 도끼가 이 쇠도끼냐 아니면 금도끼냐 아니면 은도끼냐");
    	eWords.add("토끼와 거북이가 경주를 하는테 토끼가 중간에 낮잠을 자고 말았어요.");
		
    	int totalScore = stage1();
    	
    	if(totalScore > 15000) {
    		System.out.println("승리!");
    	}
    	else {
    		System.out.println(totalScore);
    	}
	}
    
	private static int stage1() throws ParseException {
			
		Collections.shuffle(eWords);
		
		int score = 0;
		int totalScore = 0;
		
		System.out.println("Enter를 입력하면 게임이 시작됩니다");
		scan.nextLine();
		scan.nextLine();
		
		System.out.println("게임 시작!");
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedNow = now.format(formatter);
		
		for(int i = 0; i<eWords.size(); i++) {
			System.out.println(eWords.get(i));
			answer.add(scan.nextLine());
		}
		
		LocalDateTime after = LocalDateTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedAfter = after.format(formatter2);
		
		long diff = timeOper(formatedNow, formatedAfter);
		
		score = (int)Math.max(15000 - diff, 0);
		
		for(int i = 0; i<eWords.size(); i++) {
			eWords.get(i).contains(answer.get(i));
			if(eWords.get(i) != answer.get(i)) {
				score -= 100;
			}
		}
		
		totalScore += score;
		
		return totalScore;
    }
    
    private static long timeOper(String str1, String str2) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		String time1 = str1;
		String time2 = str2;
		
		Date date1 = sdf.parse(time1);
		Date date2 = sdf.parse(time2);
		
		long diff = date2.getTime() - date1.getTime();
		
		return diff / 1000;
		
	}

}