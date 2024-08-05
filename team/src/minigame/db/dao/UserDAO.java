package minigame.db.dao;

import org.apache.ibatis.annotations.Param;

import minigame.db.model.vo.UserVO;

// 인터페이스로 선언.
// Data Access Object : DB와의 연결, 조작을 도와주는 기능
public interface UserDAO {

	UserVO login(@Param("id") String id, @Param("pw") String pw);

	UserVO existUser(@Param("id") String id);

	String findPassword(@Param("id") String id, @Param("email") String email);

	boolean join(@Param("us") UserVO user);

	boolean updatePassword(@Param("id") String id, @Param("pwd") String pwd, @Param("newPwd") String newPwd);

	int selectUserKey(@Param("id") String playerID);

	String selectUserId(@Param("key")int us_key);

}
