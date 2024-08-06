package minigame.db.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.NoArgsConstructor;
import minigame.db.model.vo.ScoreVO;
import minigame.db.service.GameServiceImp;
import minigame.db.service.UserServiceImp;
import minigame.utils.Message;
import minigame.utils.Type;
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

	public Message getScore(String id) {

		int us_key = userService.getUserKey(id);

		List<ScoreVO> list = scoreService.getScore(us_key);
		Message msg = new Message();

		if (list.size() == 0) {
			msg.setMsg("<등록된 전적이 없습니다.>");
			return msg;
		}

		String str = "====================================\n";
		str += "<개인 전적 조회>";
		for (ScoreVO tmp : list) {
			str += "\n";
			String gameTitle = gameService.getGameTitle(tmp.getSc_gm_key());
			str += Type.kor_tag(gameTitle) + " : " + tmp.getSc_point() + "점 (" + tmp.getSc_win() + "승" + tmp.getSc_draw() + "무" + tmp.getSc_lose() + "패)";
		}
		str += "\n";
		msg.setMsg(str);
		return msg;

	}

	public Message getTopScore() {

		List<Integer> gameKeyList = scoreService.getScoreGameList();

		Message msg = new Message();

		if (gameKeyList == null) {
			msg.setMsg("<등록된 전적이 없습니다.>");
			return msg;
		}

		String str = "<상위 전적 조회>\n";
		for (int gm_key : gameKeyList) {

			List<ScoreVO> list = scoreService.getScoreFromGmKey(gm_key);
			String gameTitle = gameService.getGameTitle(gm_key);

			str += "====================================\n";
			str += "|"+Type.kor_tag(gameTitle) + "|\n";

			for (int i = 0; i < 3; i++) {

				if (list.size() == i) {
					break;
				}
				ScoreVO tmp = list.get(i);
				String id = userService.getUserId(tmp.getSc_us_key());
				str += "|"+(i + 1) + "등| |id:" + id + "| |" + tmp.getSc_point() + "점(" + tmp.getSc_win() + "승" + tmp.getSc_draw() + "무" + tmp.getSc_lose() + "패)|\n";

			}

		}

		msg.setMsg(str);

		return msg;

	}

}
