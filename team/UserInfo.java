package team;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class UserInfo {

	private String id;
	private List<Games> list = new ArrayList<>();

}

// id를 입력하면
// A게임에서 몇승몇패
// B게임에서 몇승몇패
// C게임에서 몇승몇패
// D게임에서 몇승몇패
// E게임에서 몇승몇패


// ID마다 전적?