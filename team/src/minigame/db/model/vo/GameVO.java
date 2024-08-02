package minigame.db.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// VO: Value Object의 약자로 값 자체를 표현하는 객체
public class GameVO {

	private int gm_key; // 게임 번호
	private String gm_title; // 게임 명
	private int gm_vPoint; // 이길 때 얻는 점수 
	private int gm_lPoint; // 졌을 때 잃는 점수
	
	public GameVO(String gm_title) {
		this.gm_title = gm_title;
	}

}