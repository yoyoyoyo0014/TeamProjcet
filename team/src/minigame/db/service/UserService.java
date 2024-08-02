package minigame.db.service;

import minigame.db.model.vo.UserVO;

public interface UserService { // 인터페이스로 선언.
	public UserVO Login(String id, String pw);
	
	 boolean ExistUser(String id);
	 
	 String FindPassWord(String id, String email);
	 
	 boolean JoinMembership(String id, String email, String pw);
}