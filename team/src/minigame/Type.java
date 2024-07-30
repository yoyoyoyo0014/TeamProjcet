package minigame;

public class Type {

	// 태그와 메세지 구분자
	// 기본적으로 클라이언트에 메세지를 전송할 때
	// Type::Message로 전송한다.

	// 로그인 관련 태그
	final static String login = "LOGIN"; // 로그인
	final static String loginSuccess = "LOGINSUCCESS"; // 로그인 성공
	final static String loginFAIL = "LOGINFAIL"; // 로그인 실패
	final static String join = "JOIN"; // 회원가입
	final static String menu = "MENU"; // 메뉴

	// 방관련 태그
	final static String createRoom = "CREATEROOM"; // 방생성
	final static String roomList = "ROOMLIST"; // 방입장
	final static String rExit = "ROOMEXIT"; // 방퇴장

	// 게임을 구분하기 위한 태그
	final static String baseBall = "BASEBALL"; // 야구게임
	final static String omok = "OMOK"; // 야구게임
	final static String typing = "TYPING"; // 타자 게임
	final static String yacht = "YACHT"; // 요트 게임
	final static String speedGame = "SPEEDGAME";

	// 게임 관련 태그
	final static String full = "FULL"; // 방 인원이 꽉 찼음
	final static String start = "START"; // 게임 시작
	final static String playing = "PLAYING"; // 게임 진행중
	final static String end = "END"; // 게임 끝.
	final static String Turn = "TURN"; // 내 차례
	final static String allTurn = "ALLTURN"; // 모두가 입력을 받음.

	// 기타 태그
	final static String blank = ""; // 공백
	final static String alert = "ALERT"; // 알람, 메세지만 출력

	// 기타 (미구현)
	final static String view = "VIEW"; // 회원정보조회
	final static String update = "UPDATE"; // 회원정보변경
	final static String rank = "RANK"; // 전적조회(전체회원)
	final static String search = "SEARCH"; // 전적조회(개인랭크)
	final static String cuser = "CUSER"; // 접속유저 리스트

	// 종료
	final static String exit = "EXIT"; // 종료

	public static String kor_tag(String gameTitle) {
		switch (gameTitle) {
			case baseBall:
				return "야구";
			case omok:
				return "오목";
			case typing:
				return "타자";
			case yacht:
				return "요트";
			case speedGame:
				return "스피드";
			default:
				return "";
		}
	}

}
