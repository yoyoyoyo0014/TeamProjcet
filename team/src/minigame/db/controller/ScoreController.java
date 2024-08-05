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

	// 승자 1, 패자 2, 무승부 0
	public boolean updateScore(String gameTitle, String playerID, int isWin) {

		int gmKey = gameService.getGameKey(gameTitle);
		int usKey = userService.getUserKey(playerID);

		int win = 0;
		int lose = 0;
		int draw = 0;

		int point = 0;

		switch (isWin) {
			case 1:
				win++;
				point = gameService.getGameVPoint(gameTitle);
				break;
			case 2:
				lose++;
				point = gameService.getGameLPoint(gameTitle);
				break;
			case 0:
				draw++;
				point = gameService.getGameDPoint(gameTitle);
				break;
		}
		
		return scoreService.updateScore(gmKey, usKey, win, draw, lose, point);
	}

}
