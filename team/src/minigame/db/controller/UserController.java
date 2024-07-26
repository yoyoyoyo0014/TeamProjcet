package minigame.db.controller;

import java.util.Scanner;

import lombok.NoArgsConstructor;
import minigame.db.service.GameServiceImp;
import minigame.db.service.ScoreServiceImp;
import minigame.db.service.UserServiceImp;

@NoArgsConstructor
public class UserController { // 인터페이스로 선언.

	private UserServiceImp userService = new UserServiceImp();
	private ScoreServiceImp scoreService = new ScoreServiceImp();
	private GameServiceImp gameService = new GameServiceImp();

	private Scanner scan;

	public UserController(Scanner scan) {
		this.scan = scan;
	}

	

}
