package minigame.utils;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message implements Serializable {

	private static final long serialVersionUID = 4312574510781508428L;

	private String Type;
	private String msg;

	private String pName;

	private String opt1, opt2; // , ... 필요시 더 생성
	
	private List<String> strList;
	
	private boolean turnEnd = false;
	private boolean notPost = false;
	
	private boolean end = false;
}
