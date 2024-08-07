package minigame.game;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import minigame.utils.Message;
import minigame.utils.Type;

@Data
public class SpeedQuiz {

	public SpeedQuizList[] sqList = SpeedQuizQuesionList.sqList;

	public boolean turnEnd = false;

	private String player1;
	private String player2;
	// 게임에 관한 모든 출력 값을 gameResult 변수로 받음.
	private String gameResult = "";

	private String winner;
	private String loser;

	private String currentTurn = Type.allTurn;

	private int player1WinCount = 0;
	private int player2WinCount = 0;

	private String quiz;
	private String[] answer;

	private boolean isEnd = false;

	private boolean isDraw = false;

	List<Integer> correctAnswerRecord = new ArrayList<Integer>();
	public List<Integer> currentQuizNum = new ArrayList<Integer>();// 현재 문제 번호

	public boolean end = false;

	final int winCount = 3;

	String[] player1Result;
	String[] player2Result;

	// 새로운 문제
	public void GetRandomQuizNum() {
		// 게임 정답지 세팅
		if (correctAnswerRecord.size() == 0) {
			for (int i = 0; i < sqList.length; i++)
				correctAnswerRecord.add(i);
		}
		Integer ran = new java.util.Random().nextInt(correctAnswerRecord.size());
		currentQuizNum.add(correctAnswerRecord.get(ran));
		correctAnswerRecord.remove(correctAnswerRecord.get(ran));
	}

	public SpeedQuiz(String p1, String p2) {
		player1 = p1;
		player2 = p2;

		// 랜덤으로 문제를 던져준다.
		GetRandomQuizNum();
		int i = currentQuizNum.get(0);
		quiz = sqList[i].problem;

		gameResult += quiz;

		answer = sqList[i].answer;
		System.out.println("스피드 퀴즈 정답: answer");

		player1Result = answer;
		player2Result = answer;
		gameResult += "정답 입력 >> ";
	}

	public void run(Message msg) {
		if (msg.equals(""))
			return;

		String[] realAnser;

		if (msg.getPName().equals(player1))
			realAnser = player1Result;
		else
			realAnser = player2Result;

		for (String tmp : realAnser) {
			if (tmp.equals(msg.getMsg())) {
				if (winner == null) {

					String winnerName = msg.getPName();
					gameResult += "정답!";

					if (winnerName.equals(player1)) {
						player1WinCount++;
						// 3번 이겼을 때
						if (player1WinCount >= winCount) {
							SuccessSpeedQuiz(msg);
							return;
						}
					} else {
						player2WinCount++;
						// 3번 이겼을 때
						if (player2WinCount >= winCount) {
							SuccessSpeedQuiz(msg);
							return;
						}
					}
					MakeQuiz(msg);
					return;
				}
			}
		}

		gameResult += "떙!\n";
		gameResult += "정답 입력 >> ";

	}

	// 문제 만든 후 유저에게 정보 전달
	public void MakeQuiz(Message msg) {
		gameResult += Score() + "\n";

		if (msg.getPName().equals(player1)) {
			GetRandomQuizNum();
			int i = currentQuizNum.get(player1WinCount);
			quiz = sqList[i].problem;

			gameResult += quiz;
			player1Result = sqList[i].answer;
		}

		else {
			GetRandomQuizNum();
			int i = currentQuizNum.get(player2WinCount);
			quiz = sqList[i].problem;

			gameResult += quiz;
			player2Result = sqList[i].answer;
		}

		gameResult += "정답 입력 >> ";
	}

	// 모든 문제를 클리어
	public void SuccessSpeedQuiz(Message msg) {
		winner = msg.getPName();
		loser = winner.equals(player1) ? player2 : player1;
		gameResult += "끝!\n";
		end = true;
		return;
	}

	// 스코어 정보 전달
	public String Score() {
		return player1 + " : " + player1WinCount + ",  " + player2 + " : " + player2WinCount;
	}

	public String getResult() {
		String tmp = gameResult;
		gameResult = "";
		return tmp;
	}

}

class SpeedQuizList {

	public String problem;
	public String[] answer;

	public SpeedQuizList(String problem, String... answer) {
		this.problem = problem;
		this.answer = answer;
	}
}
