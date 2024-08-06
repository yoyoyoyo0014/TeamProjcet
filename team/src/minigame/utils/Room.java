package minigame.utils;

import lombok.Data;
import minigame.game.Baseball;
import minigame.game.Omok;
import minigame.game.SpeedQuiz;
import minigame.game.Typing;
import minigame.game.Yacht;

@Data
public class Room {

	// 두 명의 사용자 관리
	private ConnectedUser roomManager; // 방장
	private ConnectedUser player; // 참여한 플레이어

	// 게임 객체 생성
	private Baseball baseball;
	private Omok omok;
	private Typing typing;
	private Yacht yacht;
	private SpeedQuiz speedQuiz;
	// .. private otherGame otherGame

	private String isPlaying = Type.start;
	private String gameTitle;
	private String roomTitle;

	public Room(ConnectedUser roomManager, String roomTitle, String gameTitle) {

		this.roomManager = roomManager;
		this.gameTitle = gameTitle;
		this.roomTitle = roomTitle;
	}

	public boolean isDraw() {
		switch (gameTitle) {
		case Type.baseball:
			return baseball.isDraw();
		case Type.omok:
			return omok.isDraw();
		case Type.typing:
			return typing.isDraw();
		case Type.yacht:
			return yacht.isDraw();
		case Type.speedQuiz:
			return speedQuiz.isDraw();
		}
		return false;
	}

	public String getCurrentTurn() {
		String msg = "";

		switch (gameTitle) {
		case Type.baseball:
			msg = baseball.getCurrentTurn();
			break;
		case Type.omok:
			msg = omok.getCurrentTurn();
			break;
		case Type.typing:
			msg = typing.getCurrentTurn();
			break;
		case Type.yacht:
			msg = yacht.getCurrentTurn();
			break;
		case Type.speedQuiz:
			msg = speedQuiz.getCurrentTurn();
			break;
		}
		return msg;
	}

	public String getLoser() {
		String msg = null;

		switch (gameTitle) {
		case Type.baseball:
			msg = baseball.getLoser();
			break;
		case Type.omok:
			msg = omok.getLoser();
			break;
		case Type.typing:
			msg = typing.getLoser();
			break;
		case Type.yacht:
			msg = yacht.getLoser();
			break;
		case Type.speedQuiz:
			msg = speedQuiz.getLoser();
			break;
		}
		return msg;
	}

	public String getWinner() {
		String msg = null;

		switch (gameTitle) {
		case Type.baseball:
			msg = baseball.getWinner();
			break;
		case Type.omok:
			msg = omok.getWinner();
			break;
		case Type.typing:
			msg = typing.getWinner();
			break;
		case Type.yacht:
			msg = yacht.getWinner();
			break;
		case Type.speedQuiz:
			msg = speedQuiz.getWinner();
			break;
		}
		return msg;
	}

	// 게임을 통괄하는 메소드
	// start, playing, end로 나눠서
	// 처음 시작시, 중간 플레이 과정, 승패 결과로 나뉨
	public Message gameRun(Message message) {

		Message tmp = new Message();

		switch (gameTitle) {
		case Type.baseball:

			if (isPlaying.equals(Type.start)) {
				// 게임 첫 시작.
				isPlaying = Type.playing;
			} else {
				baseball.run(message);
			}
			tmp.setMsg(baseball.getResult());
			break;
		case Type.omok:
			if (isPlaying.equals(Type.start)) {
				// 게임 첫 시작.
				isPlaying = Type.playing;
			} else {
				omok.run(message);
			}
			tmp.setMsg(omok.getResult());
			break;
		case Type.typing:
			if (isPlaying.equals(Type.start)) {
				// 게임 첫 시작.
				isPlaying = Type.playing;
			} else {
				typing.run(message);
			}
			// word list를 객체로 넘겨줌.
			tmp.setStrList(typing.getWords());
			tmp.setMsg(typing.getResult());
			break;
		case Type.yacht:
			if (isPlaying.equals(Type.start)) {
				// 게임 첫 시작.
				isPlaying = Type.playing;
			} else {
				yacht.run(message);
			}
			tmp.setMsg(yacht.getResult());
			break;
		case Type.speedQuiz:
			if (isPlaying.equals(Type.start)) {
				// 게임 첫 시작.
				isPlaying = Type.playing;
			} else 
				speedQuiz.run(message);

			String result = speedQuiz.getResult();

			if (result == null) 
				result = "";


			tmp.setMsg(result);
			if (speedQuiz.end) 
				tmp.setEnd(true);


			break;

		}
		return tmp;
	}

	public void gameInit() {
		// 게임 객체 생성

		switch (gameTitle) {
		case Type.baseball:
			baseball = new Baseball(roomManager.getUser().getId(), player.getUser().getId());
			break;
		case Type.omok:
			omok = new Omok(roomManager.getUser().getId(), player.getUser().getId());
			break;
		case Type.typing:
			typing = new Typing(roomManager.getUser().getId(), player.getUser().getId());
			break;
		case Type.yacht:
			yacht = new Yacht(roomManager.getUser().getId(), player.getUser().getId());
			break;

		case Type.speedQuiz:
			speedQuiz = new SpeedQuiz(roomManager.getUser().getId(), player.getUser().getId());
			break;

		}

	}

	@Override
	public String toString() {
		return "[" + Type.kor_tag(getGameTitle()) + "(" + getGameTitle() + ")]"
				+ "[" + getRoomTitle() + "]"
				+ "[방장:" + getRoomManager().getUser().getId() + "]";
	}

}
