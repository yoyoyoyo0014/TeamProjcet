package minigame;

import java.io.ObjectOutputStream;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConnectedUser {
	// 소켓으로 접속한 유저의 소켓정보(oos)와 유저닉네임(userId)를 저장하는 객체
	
	private String userId;
	@NonNull
	private ObjectOutputStream oos;
}
