package minigame;

import lombok.Data;

@Data
public class Room {

	// 두 명의 사용자 관리
	private ConnectedUser roomManager; // 방장
	private ConnectedUser player; // 참여한 플레이어

	// 게임 객체 생성
	private Baseball baseball;
	// private Othergame othergame;
	// ..

	private String isPlaying = Tag.start;
	private String gameTitle;
	private String roomTitle;

	public Room(ConnectedUser roomManager, String gameTitle, String roomTitle) {

		this.roomManager = roomManager;
		this.gameTitle = gameTitle;
		this.roomTitle = roomTitle;

	}

	// public void runGame() {
	//
	// gameInit();
	//
	// }
	public String getCurrentTurn() {
		String msg = "";

		switch (gameTitle) {
			case Tag.baseBall:
				msg = baseball.getCurrentTurn();
				// case Tag.otherGame :
				break;
		}
		return msg;
	}

	public String gameRun(String message) {

		String msg = "";
		switch (gameTitle) {
			case Tag.baseBall:
			
				if (isPlaying.equals(Tag.start)) {
					// 게임 첫 시작.
					msg = baseball.getResult();
					isPlaying = Tag.playing;
				} else {
					baseball.run(message);
					msg = baseball.getResult();
				}
				break;
				
//			case Tag.otherGame:
//				break;
		}

		return msg;
	}

	public void gameInit() {
		// 게임 객체 생성
		// 객체는 방장, 플레이어 두 객의 닉네임 정도만 간단하게 받음(가안)

		switch (gameTitle) {
			case Tag.baseBall:
				baseball = new Baseball(roomManager.getUserId(),
						player.getUserId());
				break;
		}

	}

	@Override
	public String toString() {
		return "[" + gameTitle + "]" + " " + roomTitle + " <방장:"
				+ roomManager.getUserId() + ">";
	}

}
