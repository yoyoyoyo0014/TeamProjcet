package minigame.db.dao;

import org.apache.ibatis.annotations.Param;

// 인터페이스로 선언.
// Data Access Object : DB와의 연결, 조작을 도와주는 기능
public interface GameDAO {

	// 테스트용 코드
	boolean insertGame(@Param("title")String gameTitle);

	int selectGameKey(@Param("title")String gameTitle);

	int selectVPoint(@Param("title")String gameTitle);

	int selectLPoint(@Param("title")String gameTitle);

	int selectDPoint(@Param("title")String gameTitle);

	String selectGameTitle(@Param("key")int sc_gm_key);

}
