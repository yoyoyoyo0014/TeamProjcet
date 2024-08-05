package minigame;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SpeedQuiz {

	public SpeedQuizList[] sqList = {
			new SpeedQuizList("1982년생 가수이며 안동 장씨이다. 2008년 밴드로 데뷔했으며 \n 현재까지도 꾸준히 앨범을 출시하고있다. 해당 가수의 이름은?\n", "장기하"),
			new SpeedQuizList("뜻이 맞는 사람들이 모여 목적을 달성하기 위해 맴세하는 뜻으로\n" + "복숭아 나무 밑에서 유비,관우, 장비가 의형제를 맺은것에 유래된 고사성어는 무엇일까\n",
					"도원결의"),
			new SpeedQuizList("인재를 맞아들이기 위해 노력하거나 마음을 쓴다는 뜻으로\n" + " 유비가 제갈량을 얻기위해 초가집을 세번이나 찾아간 일화에서 유래된 고사성어는 무엇일까\n",
					"삼고초려"),
			new SpeedQuizList("1천원에 나오는 인물은 누구일까 무엇일까\n",
					"율곡이이", "이이", "율곡 이이"),
			new SpeedQuizList("1999년에 태어난 친구들은 토끼띠이다. 그럼 2001년 생은 무슨 띠일까? \n",
					"뱀", "뱀띠"),
			new SpeedQuizList("몇 명씩 무리를 지어 다니거나 함께 일을 하는 모양.을 뜻하는 사자성어 초성 : ㅅㅅㅇㅇ\n",
					"삼삼오오"),
			new SpeedQuizList("열 가운데 여덟이나 아홉.을 뜻하는 사자성어  초성 : ㅅㅈㅍㄱ\n",
					"십중팔구"),
			new SpeedQuizList("장기전을 피하고 속전으로 전국을 판가름하는 것.을 뜻하는 사자성어 초성 : ㅅㅈㅅㄱ \n",
					"속전속결"),
			new SpeedQuizList("묻는 말에 대하여 아주 딴판의 소리로 대답함.을 뜻하는 사자성어 초성 : ㄷㅁㅅㄷ \n",
					"동문서답"),
			new SpeedQuizList("곤란할 때는 주저하지 말고 도망가는 것이 가장 좋다.을 뜻하는 사자성어 초성 : ㅅㅅㅇㄱ \n",
					"삼십육계"),
			new SpeedQuizList("여러 가지 사물이 모두 차이가 있고 구별이 있음.을 뜻하는 사자성어 초성 : ㅊㅊㅁㅂ \n",
					"천차만별"),
			new SpeedQuizList("봄, 여름, 가을, 겨울의 네 계절.을 뜻하는 사자성어 초성 : ㅊㅎㅊㄷ \n",
					"춘하추동"),
			new SpeedQuizList("꼭 죽을 고비에서 살아남.을 뜻하는 사자성어 초성 : ㄱㅅㅇㅅ\n",
					"구사일생"),
			new SpeedQuizList("큰 소리로 떠들고 마구 노래 부름.을 뜻하는 사자성어 초성 : ㄱㅅㅂㄱ\n",
					"고성방가"),
			new SpeedQuizList("한 번에 천금을 얻다. 즉 단 한 번에 큰 재산이나 이익을 얻는 것.을 뜻하는 사자성어 초성 : ㅇㅎㅊㄱ\n",
					"일확천금"),
			new SpeedQuizList("하늘이나 땅에서 일어나는 재난이나 변사.을 뜻하는 사자성어 초성 : ㅊㅈㅈㅂ\n",
					"천재지변"), };

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

	private String firstSelectPlayer = "";

	private boolean isDraw = false;

	List<Integer> correctAnswerRecord = new ArrayList<Integer>();
	public int currentQuizNum = 0;// 현재 문제 번호

	public boolean sucP1 = false;
	boolean sucP2 = false;

	boolean notPost = false;
	boolean end = false;
	int winCount = 3;

	String[] player1Result;
	String[] player2Result;

	// 세로운 문제
	public void GetRandomQuizNum() {
		// 게임 정답지 세팅
		if (correctAnswerRecord.size() == 0) {
			for (int i = 0; i < sqList.length; i++)
				correctAnswerRecord.add(i);
		}
		Integer ran = new java.util.Random().nextInt(correctAnswerRecord.size());
		currentQuizNum = correctAnswerRecord.get(ran);
		correctAnswerRecord.remove(correctAnswerRecord.get(ran));

		winner = null;
		loser = null;
	}

	public String GetCurrentQuizProblem() {
		return sqList[currentQuizNum].problem;
	}

	public SpeedQuiz(String p1, String p2) {
		player1 = p1;
		player2 = p2;

		// 랜덤으로 문제를 던져준다.
		GetRandomQuizNum();
		int i = currentQuizNum;
		quiz = sqList[i].problem;

		gameResult += quiz;

		answer = sqList[i].answer;

		player1Result = answer;
		player2Result = answer;
		gameResult += "정답 입력 >> ";
	}

	public void run(Message msg) {
		boolean success = false;
		if (msg.equals("")) {
			return;
		}
		boolean isPlayer1;
		String[] realAnser;

		if (msg.getPName().equals(player1)) {
			isPlayer1 = true;
			realAnser = player1Result;
		} else {
			isPlayer1 = false;
			realAnser = player2Result;
			;
		}

		for (String tmp : realAnser) {
			if (tmp.equals(msg.getMsg())) {
				if (winner == null) {
					success = true;
					// 처음으로 성공했을 때
					firstSelectPlayer = msg.getPName();
					String winnerName = msg.getPName();
					gameResult += "정답!";

					if (winnerName.equals(player1)) {
						player1WinCount++;
						// 3번 이겼을 때
						if (player1WinCount >= winCount) {
							System.out.println("우승");
							winner = msg.getPName();
							loser = winner.equals(player1) ? player2 : player1;
							gameResult += "끝!";
							notPost = false;
							end = true;
							msg.setOpt1(winner);
							return;
						}
					} else {
						player2WinCount++;
						// 3번 이겼을 때
						if (player2WinCount >= winCount) {
							System.out.println("우승");
							winner = msg.getPName();
							loser = winner.equals(player1) ? player2 : player1;
							gameResult += "끝!";
							notPost = false;
							end = true;
							msg.setOpt1(winner);
							return;
						}
					}
					gameResult += Score() + "\n";

					GetRandomQuizNum();
					int i = currentQuizNum;
					quiz = sqList[i].problem;

					gameResult += quiz;
					if (msg.getPName().equals(player1))
						player1Result = sqList[i].answer;

					else
						player2Result = sqList[i].answer;

					gameResult += "정답 입력 >> ";
					break;
				}
			}
		}

		if (!success && winner == null)
			gameResult += "떙!";
	}

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
