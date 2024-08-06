package minigame.db.controller;

import java.util.Scanner;

import lombok.NoArgsConstructor;
import minigame.db.service.GameServiceImp;
import minigame.db.service.ScoreServiceImp;
import minigame.db.service.UserServiceImp;

@NoArgsConstructor
public class GameController { // 인터페이스로 선언.

	private UserServiceImp userService = new UserServiceImp();
	private ScoreServiceImp scoreService = new ScoreServiceImp();
	private GameServiceImp gameService = new GameServiceImp();

	private Scanner scan;

	public GameController(Scanner scan) {
		this.scan = scan;
	}

	public void insertGame() {

		System.out.print("<테스트> 게임 이름 : ");
		String gameTitle = scan.nextLine();
		
		if(gameService.insertGame(gameTitle)) {
			System.out.println("게임 저장 완료");
		}
		else {
			System.out.println("게임 저장 실패");
		}

		
	}
}
