package minigame.db.controller;

import java.util.Scanner;

import lombok.NoArgsConstructor;
import minigame.db.model.vo.UserVO;
import minigame.db.service.GameServiceImp;
import minigame.db.service.ScoreServiceImp;
import minigame.db.service.UserServiceImp;

@NoArgsConstructor
public class UserController { // 인터페이스로 선언.

	private UserServiceImp userService = new UserServiceImp();
	private ScoreServiceImp scoreService = new ScoreServiceImp();
	private GameServiceImp gameService = new GameServiceImp();

	private Scanner scan;

	public UserController(Scanner scan) {
		this.scan = scan;
	}
	
	//로그인 메소드
		//잘못된 로그인 = 반환값 null
		public UserVO Login(String id, String pw) {
			return userService.Login(id, pw);
		}
		//유저가 존재하는지
		public boolean ExistUser(String id) {
			return userService.ExistUser(id);
		}
		//비밀번호 찾기 아이디가 존재하지 않거나 이메일과 아이디가 맞지 않을 때 null 값
		public String FindPassWord(String id, String email) {
			return userService.FindPassWord(id,email);
		}
		//회원가입 아이디가 존재하면 false
		public boolean JoinMembership(String id, String email, String pw) {
			return userService.JoinMembership(id,email,pw);
		}

}
