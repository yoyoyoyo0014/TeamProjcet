package minigame.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import minigame.db.model.vo.ScoreVO;

// 인터페이스로 선언.
// Data Access Object : DB와의 연결, 조작을 도와주는 기능
public interface ScoreDAO {

	boolean updateScore(@Param("gm_key")int gmKey, @Param("us_key")int usKey, @Param("win")int win, @Param("draw")int draw, @Param("lose")int lose, @Param("point")int point);

	List<ScoreVO> selectUserScore(@Param("key")int us_key);

	List<Integer> selectScoreAllList();

	List<ScoreVO> selectScoreList(@Param("gm_key")int gm_key);

}
