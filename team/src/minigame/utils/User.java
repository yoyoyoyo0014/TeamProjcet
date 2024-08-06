package minigame.utils;

import java.util.Objects;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class User /* implements Serializable */ {
	// 추후 DB 대체 데이터

//	private static final long serialVersionUID = -1178561973660991852L;

	@NonNull
	private String id;
	@NonNull
	private String password;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isValidPassword(String password) {
		return this.password.equals(password);
	}

	public void updatePwd(String prevPwd, String newPwd) {
		if (this.password.equals(prevPwd)) {
			this.password = newPwd;
		}
	}
	
//	보안상 이유로 password는 getter/setter 미구현

}
