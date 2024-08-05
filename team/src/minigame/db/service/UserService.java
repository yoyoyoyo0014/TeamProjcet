package minigame.db.service;

public interface UserService { // 인터페이스로 선언.

	boolean login(String id, String pw);

	boolean existUser(String id);

	String findPassword(String id, String email);

	boolean join(String id, String email, String pw);

	boolean updatePassword(String id, String pwd, String newPwd);

	int getUserKey(String playerID);

	String getUserId(int us_key);

}