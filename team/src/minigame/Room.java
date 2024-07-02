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

	private String isPlaying = Type.start;
	private String gameTitle;
	private String roomTitle;

	public Room(ConnectedUser roomManager, String roomTitle, String gameTitle) {

		this.roomManager = roomManager;
		this.gameTitle = gameTitle;
		this.roomTitle = roomTitle;
	}

	public String getCurrentTurn() {
		String msg = "";

		switch (gameTitle) {
			case Type.baseBall:
				msg = baseball.getCurrentTurn();
				// case Tag.otherGame :
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
			case Type.baseBall:

				if (isPlaying.equals(Type.start)) {
					// 게임 첫 시작.
					isPlaying = Type.playing;
				} else {
					baseball.run(message);
				}
				tmp.setMsg(baseball.getResult());
				break;

//			case Tag.otherGame:
//				break;

		}
		return tmp;
	}

	public void gameInit() {
		// 게임 객체 생성
		// 객체는 방장, 플레이어 두 객의 닉네임 정도만 간단하게 받음(가안)

		switch (gameTitle) {
			case Type.baseBall:
				baseball = new Baseball(roomManager.getUser().getId(),
						player.getUser().getId());
				break;
		}

	}

	@Override
	public String toString() {
		return "[" + gameTitle + "]" + " " + roomTitle + " <방장:"
				+ roomManager.getUser().getId() + ">";
	}

}
