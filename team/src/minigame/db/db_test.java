package minigame.db;

import java.util.Scanner;

import minigame.db.controller.GameController;
import minigame.db.controller.ScoreController;
import minigame.db.controller.UserController;
import minigame.db.model.vo.UserVO;

public class db_test {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		UserController userController = new UserController(scan);
		ScoreController scoreController = new ScoreController(scan);
		GameController gameController = new GameController(scan);
		
		//userController.JoinMembership("qweqwe", "qweqwe@naver.com", "qweqwe");
		//System.out.println(userController.FindPassWord("qweqwe", "qweqwe@naver.com"));
		// user = userController.Login("qweqwe", "qweqwe");
		//System.out.println(user.getUs_email());
		gameController.insertGame();
		
		
		

	}

}
