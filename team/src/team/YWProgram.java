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

import lombok.AllArgsConstructor;
import lombok.Data;

public class YWProgram {

	public static void main(String[] args) {
		Program p = new Program();
		p.run();
	}

}

class Program implements program.Program {
	
	private List<User> list = new ArrayList<User>();
	private List<String> words = new ArrayList<String>();
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
				e.printStackTrace();
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
			System.out.println("======점수를 출력합니다======");
			Collections.sort(list);
			for(int i = 0; i< 5; i++) {
				System.out.println(i+1 + "위. " + list.get(i));
			}
			break;
		case 4:
			System.out.println("=====프로그램을 종료합니다=====");
			break;
		default:
			System.out.println("===잘못된 메뉴를 선택했습니다===");
		}
		
	}
	
	private void start() throws ParseException {
		
		int totalScore = 0;
		int score = 0;
		
		System.out.println("점수 : " + totalScore);
		System.out.println("스테이지를 시작하려면 Enter를 눌러주세요");
		scan.nextLine();
		String str1 = scan.nextLine();
		
		if(!str1.isBlank()) {
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		System.out.println("=====STAGE 1=====");
		score = stage();
		totalScore += score;
		
		if(totalScore < 14000) {
			saveScore(totalScore);
			return;
		}
		
		System.out.println("점수 : " + totalScore);
		System.out.println("다음 스테이지로 넘어가려면 Enter를 눌러주세요");
		String str2 = scan.nextLine();
		
		if(!str2.isBlank()) {
			saveScore(totalScore);
			System.out.println();
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		
		System.out.println("=====STAGE 2=====");
		score = stage();
		totalScore += score;
		
		if(totalScore < 28000) {
			saveScore(totalScore);
			return;
		}
		
		System.out.println("점수 : " + totalScore);
		System.out.println("다음 스테이지로 넘어가려면 Enter를 눌러주세요");
		String str3 = scan.nextLine();
		
		if(!str3.isBlank()) {
			saveScore(totalScore);
			System.out.println();
			System.out.println("메뉴로 돌아갑니다.");
			return;
		}
		
		System.out.println("=====STAGE 3=====");
		score = stage();
		totalScore += score;
		
		saveScore(totalScore);
		return;
		
	}
	
	private void saveScore(int totalScore) {
		
		System.out.println();
		System.out.print("ID를 입력해주세요 : ");
		String ID = scan.nextLine();
		list.add(new User(ID, totalScore));
		System.out.println();
		Collections.sort(list);
		System.out.println("=====기록이 저장되었습니다=====");
		save(fileName);
		
	}
	
	private int stage() throws ParseException {
		
		centens();
		Collections.shuffle(words);
		
		int score = 0;
		int totalScore = 0;
		
		System.out.println("Enter를 입력하면 게임이 시작됩니다");
		scan.nextLine();
		
		System.out.println("게임 시작!");
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedNow = now.format(formatter);
		
		for(int i = 0; i<5; i++) {
			System.out.println(words.get(i));
			answer.add(scan.nextLine());
		}
		
		LocalDateTime after = LocalDateTime.now();
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedAfter = after.format(formatter2);
		
		long diff = timeOper(formatedNow, formatedAfter);
		
		score = (int)Math.max(15000 - diff, 0);
		
		for(int i = 0; i<5; i++) {
			words.get(i).contains(answer.get(i));
			if(words.get(i) != answer.get(i)) {
				score -= 100;
			}
		}
		
		totalScore += score;
		
		return totalScore;
	}
	
	private void centens() {
		
		words.add("동해물과 백두산이 마르고 닳도록 하느님이 보우하사 우리 나라 만세");
		words.add("간장 공장 공장장은 강 공장장이고 된장 공장 공장장은 장 공장장이다.");
		words.add("네가 떨어뜨린 도끼가 이 쇠도끼냐 아니면 금도끼냐 아니면 은도끼냐");
		words.add("토끼와 거북이가 경주를 하는데 토끼가 중간에 낮잠을 자고 말았어요.");
		words.add("조금 가다가 강가에 다다르니, 장마로 인해 물이 많아진 강물에 한 아이가 빠져 허우적거리고 있었지요.");
		words.add("어머니는 늘 두 아들 때문에 마음이 편할 날이 없었어요.");
		words.add("언제부턴가 갈대는 속으로 조용히 울고 있었다. "
				+ "그런 어느 밤이었을 것이다. 그의 온몸이 흔들리고 있는 것을 알았다. "
				+ "바람도 달빛도 아닌 것. 갈대는 저를 흔드는 것이 제 조용한 울음인 것을 까맣게 몰랐다. "
				+ "......산다는 것은 속으로 이렇게 조용히 울고 있는 것이라는 것을 그는 몰랐다.");
		
		words.add("호랑이 그리려다 고양이 그린다.");
		words.add("종로에서 뺨 맞고 행랑 뒤에서 눈 흘긴다");
		words.add("배고픈 여우 한 마리가 포도밭 옆을 지나가게 되었어요.");
		words.add("옛날 어느 나라에 임금님이 살고 계셨어요.");
		words.add("죽는 날까지 하늘을 우러러 한 점 부끄럼이 없기를, 잎새에 이는 바람에도 나는 괴로워했다.");
		words.add("자세히 보아야 예쁘다. 오래 보아야 사랑스럽다. 너도 그렇다.");
		
		words.add("망건 쓰자 파장");
		words.add("아이고, 망했다. 망했어!");
		words.add("여름비는 잠비고, 가을비는 떡비");
		words.add("단단한 땅에 물이 괸다.");
		words.add("물이 깊어야 고기가 모인다.");
		words.add("첫술에 배부를까");
		words.add("가는 날이 장날");
		
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
@AllArgsConstructor
class User implements Serializable, Comparable<User> {

	private static final long serialVersionUID = 12345L;
	
	private String ID;
	private int totalScore;
	
	@Override
	public int compareTo(User o) {
		User other = (User) o;
		return other.totalScore - this.totalScore;
	}

	@Override
	public String toString() {
		return "ID : " + ID + ", 점수 : " + totalScore;
	}
	
	
	
}
