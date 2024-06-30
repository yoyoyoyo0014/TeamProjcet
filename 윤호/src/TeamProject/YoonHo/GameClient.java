package TeamProject.YoonHo;



import java.util.Scanner;

import TeamProject.ClientBuilder;


public class GameClient extends ClientBuilder<String> {

	Scanner scan = new Scanner(System.in);
	boolean a =true;
	@Override
	public void GameStart() {
		while(true) {
			if(a) {
				String a = ClientInputStreamThread();
				System.out.println(a);
			}else{
				String str = Test();
				ClientOutStreamThread(str);
			}
			a = !a;
		}
	}
	
	
	public String Test() {
		System.out.println("입력 : ");
		String str = (String)scan.next();
		return "asdas";
	}
}
