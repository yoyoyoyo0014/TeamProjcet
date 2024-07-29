package minigame.db;

import java.util.Scanner;

import minigame.db.controller.GameController;
import minigame.db.controller.ScoreController;
import minigame.db.controller.UserController;

public class db_test {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		UserController userController = new UserController(scan);
		ScoreController scoreController = new ScoreController(scan);
		GameController gameController = new GameController(scan);

		gameController.insertGame();

	}

}
