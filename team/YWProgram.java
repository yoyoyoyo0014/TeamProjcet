package team;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	
	private List<Score> list = new ArrayList<Score>();
	private List<String> words = new ArrayList<String>();
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
			list = (List<Score>)ois.readObject();
		} 
		catch (Exception e) {
		} 
	}
	
	@Override
	public void runMenu(int menu) throws Exception {
		
		switch(menu) {
		
		case 1:
			System.out.println("==========게임을 설명합니다==========");
			break;
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
	
	private void start() {
		
		System.out.println("Enter를 입력하면 게임이 시작됩니다");
		String str = scan.nextLine();
		
		if(str.isBlank()) {
			System.out.println("게임 시작!");
			LocalTime now = LocalTime.now();
			int hour = now.getHour();
			int minute = now.getMinute();
			int second = now.getSecond();
			for(int i = 0; i<words.size(); i++) {
				System.out.println(words.get(i));
				String answer = scan.nextLine();
			}
			LocalTime after = LocalTime.now();
			
			int ahour = now.getHour();
			int aminute = now.getMinute();
			int asecond = now.getSecond();
			
			int resulth = ahour - hour;
			int resultm = aminute - minute;
			int results = asecond - second;
			
			if(hour > ahour || minute > aminute || second > asecond) {
				
			}
			
			
			
			
		}
		
	}
	
	private void centens() {
		
		words.add("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세");
		words.add("간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 장 공장장이다.");
		words.add("네가 떨어뜨린 도끼가 이 쇠도끼냐 아니면 금도끼냐 아니면 은도끼냐");
		words.add("토끼와 거북이가 경주를 하는테 토끼가 중간에 낮잠을 자고 말았어요");
		
	}
}

@Data
class Score implements Serializable {

	private static final long serialVersionUID = 12345L;
	
	private String ID;
	private String PW;
	private int score;
	
}

@Data
class Centens {
	private String words;
}