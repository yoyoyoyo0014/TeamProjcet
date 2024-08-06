package minigame.db.controller;

import lombok.NoArgsConstructor;
import minigame.db.service.GameServiceImp;
import minigame.db.service.ScoreServiceImp;
import minigame.db.service.UserServiceImp;

@NoArgsConstructor
public class UserController { // 인터페이스로 선언.

	private UserServiceImp userService = new UserServiceImp();
	private ScoreServiceImp scoreService = new ScoreServiceImp();
	private GameServiceImp gameService = new GameServiceImp();

	// 로그인 메소드
	// 잘못된 로그인 = 반환값 null
	public boolean login(String id, String pw) {
		return userService.login(id, pw);
	}

	// 유저가 존재하는지
	public boolean existUser(String id) {
		return userService.existUser(id);
	}

	// 비밀번호 찾기 아이디가 존재하지 않거나 이메일과 아이디가 맞지 않을 때 null 값
	public String findPassword(String id, String email) {
		return userService.findPassword(id, email);
	}

	// 회원가입 아이디가 존재하면 false
	public boolean join(String id, String pw, String email) {
		return userService.join(id, pw, email);
	}

	public boolean updatePassword(String id, String pwd, String newPwd) {
		return userService.updatePassword(id, pwd, newPwd);
	}

}
