package minigame.db.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// VO: Value Object의 약자로 값 자체를 표현하는 객체
public class ScoreVO {

	private int sc_key; // 스코어 기본 키
	private int sc_win; //
	private int sc_draw; //
	private int sc_lose;
	private int sc_point;
	private int sc_us_key;
	private int sc_gm_key;

	public ScoreVO(int sc_win, int sc_lose, int sc_draw, int sc_point, int sc_us_key, int sc_gm_key) {
		this.sc_win = sc_win;
		this.sc_lose = sc_lose;
		this.sc_draw = sc_draw;
		this.sc_point = sc_point;
		this.sc_us_key = sc_us_key;
		this.sc_gm_key = sc_gm_key;
	}

}
