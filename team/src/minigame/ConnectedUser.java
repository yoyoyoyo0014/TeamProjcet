package minigame;

import java.io.ObjectOutputStream;
import java.util.Objects;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConnectedUser {
	// 소켓으로 접속한 유저의 소켓정보(oos)와 유저닉네임(userId)를 저장하는 객체

	private User user;
	@NonNull
	private ObjectOutputStream oos;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectedUser other = (ConnectedUser) obj;
		return Objects.equals(user, other.user);
	}

	@Override
	public int hashCode() {
		return Objects.hash(user);
	}

}
