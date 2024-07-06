package team;

import java.util.Scanner;

import program.Program;

public class GameManager implements Program {

	Scanner scan = new Scanner(System.in);

	@Override
	public void printMenu() {
		System.out.println("메뉴");
		System.out.println("1.연우씨게임");
		System.out.println(" 2.유노씨게임");
		System.out.println("3.섭건씨 게임");
		System.out.println("4.병두 게임");
		System.out.print("게임 선택 : ");
		// TODO Auto-generated method stub
		// 메뉴
		// 1.연우씨게임
		// 2.유노씨게임
		// 3.섭건씨 게임
		// 4.병두 게임
		// 게임 선택 :

	}

	@Override
	public void runMenu(int menu) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int menu = -1;
		try {
			printMenu();
			menu = scan.nextInt();

			runMenu(menu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
