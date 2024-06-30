package TeamProject.YoonHo;

import java.util.Scanner;

import TeamProject.ServerBuilder;


public class GameServer extends ServerBuilder<String>{
	
	Scanner scan = new Scanner(System.in);
	boolean a = true;
	@Override
	public void GameStart() {
		while(true) {
			if(a) {
				String str = Test();
				ServerOutStreamThread(str);
			}else{
				String a =ServerInputStreamThread();
				System.out.println(a);
			}
			a = !a;
		}
	}
	

	public String Test() {
		System.out.println("입력 : ");
		String str = (String)scan.next();
		return str;
	}
}
