package minigame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Game implements Serializable {

	// 추후 DB 대체 데이터
	private static final long serialVersionUID = -3639078028209149053L;

	@NonNull
	private String name;
	private int win = 0, lose = 0; // win, lose;
	private List<String> matchRecord = new ArrayList<>();
	// date : win(lose) : player(me) : player(other)

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public void updateWin() {
		win++;
	}

	public void updateLose() {
		lose++;
	}

}
