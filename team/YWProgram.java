package team;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import lombok.Data;

public class YWProgram {

	public static void main(String[] args) {
		Program p = new Program();
		p.run();
	}

}

class Program implements program.Program {
	
	private List<User> list = new ArrayList<User>();
	private List<String> eWords = new ArrayList<String>();
	private List<String> nWords = new ArrayList<String>();
	private List<String> hWords = new ArrayList<String>();
	private List<String> answer = new ArrayList<String>();
	private Scanner scan = new Scanner(System.in);
	private String fileName = "src/team/ywprogram.txt";

	@Override
	public void printMenu() {
		System.out.println("==========MENU==========");
		System.out.println("1. 게임 설명");
		System.out.println("2. 게임 시작");
		System.out.println("3. 점수 확인");
		System.out.println("4. 게임 종료");
		System.out.println("==========MENU==========");
		
		System.out.print("메뉴를 선택하세요 : ");
	}
	
	@Override
	public void run() {
		
		int menu;
		load(fileName);
		do {
			printMenu();
			menu = scan.nextInt();
			try {
				runMenu(menu);
			} 
			catch (Exception e) {
				System.out.println("예외 발생");
			}
		}
		while(menu != 4);
		save(fileName);
		
	}
	
	@Override
	public void save(String fileName) {
		try(FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(list);
		} catch (Exception e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void load(String fileName) {
		try(FileInputStream fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis)){
			list = (List<User>)ois.readObject();
		} 
		catch (Exception e) {
		} 
	}
	
	@Override
	public void runMenu(int menu) throws Exception {
		
		switch(menu) {
		
		case 1:
			System.out.println("======게임을 설명합니다======");
			System.out.println();
			System.out.println("게임이 시작되면 문장이 나옵니다");
			System.out.println("문장이 나오는 순간부터 시간이 흐릅니다!");
			System.out.println("빠르고 정확하게 문장을 따라 입력합니다");
			System.out.println("게임이 종료 되었을 때, 스코어가 높다면 다음 스테이지로 넘어갑니다.");
			System.out.println("하지만 스코어가 높지 않다면 게임은 종료됩니다.");
			System.out.println("스테이지는 총 3단계까지 있습니다.");
			System.out.println("최종 스테이지까지 완료하고 자신의 기록을 확인해보세요!");
			System.out.println();
			System.out.println("이전으로 되돌아가고 싶으시다면, Enter를 입력해주세요.");
			scan.nextLine();
			String str = scan.nextLine();
			if(str.isBlank()) {
				break;
			}
		case 2:
			start();
			break;
		case 3:
			System.out.println("==========점수를 출력합니다==========");
			break;
		case 4:
			System.out.println("==========프로그램을 종료합니다==========");
			break;
		default:
			System.out.println("==========잘못된 메뉴를 선택했습니다==========");
		}
		
	}
	
	private void start() throws ParseException {
		
		int totalScore = 0;
		
		System.out.println("=====STAGE 1=====");
		totalScore = stage1();
		
		if(totalScore < 20000) {
			System.out.print("ID를 입력해주세요 : ");
			String ID = scan.nextLine();
			list.add(new User(ID, totalScore));
			System.out.println("=====기록이 저장되었습니다=====");
			save(fileName);
			return;
		}
		
		totalScore = 0;
		
	}
	
	private int stage1() throws ParseException {
		
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
	
	@SuppressWarnings("unused")
	private void centens() {
		
		eWords.add("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세");
		eWords.add("간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 장 공장장이다.");
		eWords.add("네가 떨어뜨린 도끼가 이 쇠도끼냐 아니면 금도끼냐 아니면 은도끼냐");
		eWords.add("토끼와 거북이가 경주를 하는테 토끼가 중간에 낮잠을 자고 말았어요.");
		
	}
	
		
	private long timeOper(String str1, String str2) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		String time1 = str1;
		String time2 = str2;
		
		Date date1 = sdf.parse(time1);
		Date date2 = sdf.parse(time2);
		
		long diff = date2.getTime() - date1.getTime();
		
		return diff / 1000;
		
	}
}

@Data
class User implements Serializable {

	private static final long serialVersionUID = 12345L;
	
	private String ID;
	private int score;
	
	public User(String iD, int score) {
		ID = iD;
		this.score = score;
	}
	
}
