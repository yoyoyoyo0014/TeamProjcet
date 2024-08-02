package minigame.db.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
// VO: Value Object의 약자로 값 자체를 표현하는 객체
public class UserVO {

	private int us_key; // 멤버 번호
	private String us_id;
	private String us_pw;
	private String us_email;
	
	public UserVO(String us_id, String us_pw,String us_email) {
		
		this.us_id = us_id;
		this.us_pw = us_pw;
		this.us_email = us_email;
	}
}