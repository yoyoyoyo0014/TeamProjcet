package minigame.db.controller;

import java.util.Scanner;

import lombok.NoArgsConstructor;
import minigame.db.service.GameServiceImp;
import minigame.db.service.UserServiceImp;
import minigame.db.service.ScoreServiceImp;

@NoArgsConstructor
public class ScoreController { // 인터페이스로 선언.

	private UserServiceImp userService = new UserServiceImp();
	private ScoreServiceImp scoreService = new ScoreServiceImp();
	private GameServiceImp gameService = new GameServiceImp();

	private Scanner scan;

	public ScoreController(Scanner scan) {
		this.scan = scan;
	}
}
