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
	private String sc_id;
	private String sc_gm_ti;

	public ScoreVO(int sc_win, int sc_lose, int sc_draw, String sc_id, String gm_ti) {
		this.sc_win = sc_win;
		this.sc_lose = sc_lose;
		this.sc_draw = sc_draw;
		this.sc_id = sc_id;
		this.sc_gm_ti = gm_ti;
	}

}
