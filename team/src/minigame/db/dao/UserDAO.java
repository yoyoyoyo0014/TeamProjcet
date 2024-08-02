package minigame.db.dao;

import org.apache.ibatis.annotations.Param;

import minigame.db.model.vo.UserVO;

// 인터페이스로 선언.
// Data Access Object : DB와의 연결, 조작을 도와주는 기능
public interface UserDAO {

	UserVO LoginUser(@Param("id")String id,@Param("pw") String pw);

	UserVO ExistLogin(@Param("id")String id);
	
}
