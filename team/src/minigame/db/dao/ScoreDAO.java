package minigame.db.dao;

import org.apache.ibatis.annotations.Param;

// 인터페이스로 선언.
// Data Access Object : DB와의 연결, 조작을 도와주는 기능
public interface ScoreDAO {

	boolean updateScore(@Param("gm_key")int gmKey, @Param("us_key")int usKey, @Param("win")int win, @Param("draw")int draw, @Param("lose")int lose, @Param("point")int point);

}
