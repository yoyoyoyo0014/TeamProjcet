package minigame.utils;

public class Type {

	// 태그와 메세지 구분자
	// 기본적으로 클라이언트에 메세지를 전송할 때
	// Type::Message로 전송한다.

	// 로그인 관련 태그
	public final static String login = "LOGIN"; // 로그인
	public final static String loginSuccess = "LOGINSUCCESS"; // 로그인 성공
	public final static String loginFAIL = "LOGINFAIL"; // 로그인 실패
	public final static String join = "JOIN"; // 회원가입
	public final static String menu = "MENU"; // 메뉴

	// 방관련 태그
	public final static String createRoom = "CREATEROOM"; // 방생성
	public final static String roomList = "ROOMLIST"; // 방입장
	public final static String rExit = "ROOMEXIT"; // 방퇴장

	// 게임을 구분하기 위한 태그
	public final static String baseball = "BASEBALL"; // 야구게임
	public final static String omok = "OMOK"; // 야구게임
	public final static String typing = "TYPING"; // 타자 게임
	public final static String yacht = "YACHT"; // 요트 게임
	public final static String speedQuiz = "SPEEDQUIZ";

	// 게임 관련 태그
	public final static String full = "FULL"; // 방 인원이 꽉 찼음
	public final static String start = "START"; // 게임 시작
	public final static String playing = "PLAYING"; // 게임 진행중
	public final static String end = "END"; // 게임 끝.
	public final static String Turn = "TURN"; // 내 차례
	public final static String allTurn = "ALLTURN"; // 모두가 입력을 받음.

	// 기타 태그
	public final static String blank = ""; // 공백
	public final static String alert = "ALERT"; // 알람, 메세지만 출력

	// 기타 (미구현)
	public final static String view = "VIEW"; // 회원정보조회
	public final static String update = "UPDATE"; // 회원정보변경
	public final static String rank = "RANK"; // 전적조회(전체회원)
	public final static String search = "SEARCH"; // 전적조회(개인랭크)
	public final static String cuser = "CUSER"; // 접속유저 리스트

	// 종료
	public final static String exit = "EXIT"; // 종료
	public final static String logout = "LOGOUT"; // 로그아웃
	public final static String reset = "PASSWORDRESET";
	public final static String update_pwd = "PASSWORDUPDATE";
	
	public final static String success = "SUCCESS";
	public final static String fail = "FAIL";

	public final static String drawEnd = "DRAWEND";
	
	public final static String personal = "PERSONALRANK";
	public final static String topPlayer = "TOPPLAYER";
	
	public static String kor_tag(String gameTitle) {
		switch (gameTitle) {
			case baseball:
				return "야구";
			case omok:
				return "오목";
			case typing:
				return "타자";
			case yacht:
				return "요트";
			case speedQuiz:
				return "스피드";
			default:
				return "";
		}
	}

}
