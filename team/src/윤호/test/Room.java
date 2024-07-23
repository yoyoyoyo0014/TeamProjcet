package 윤호.test;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room implements Serializable{
	private static final long serialVersionUID = -7143458069655409085L;
	public static int maxUserCount=3;
	
	public List<User> userList =new ArrayList<User>();
	
	public int currentQuizNum=0;//현재 문제 번호
	
	String currentQuizProblem;//현재 문제
	String[] currentQuizAnswer;//현재 문제 정답
	
	List<Integer> correctAnswerRecord = new ArrayList<Integer>();
	public boolean InsertRoom(User user) {
		if(userList.size()> maxUserCount)
			return false;
		
		userList.add(user);
		//게임 정답지 세팅
		if(userList.size()>=2) 
			GetRandomQuizNum();
		
		return true;
	}
	//문제 맞는 확인하는 방법
	public boolean GetCurrentQuizAnser(String answer) {
		answer.trim();{
			for(int j=0;j<SpeedQuizList.sqList[ currentQuizNum].answer.length;j++) {
				//정답이면 새로운 문제 선택 후 true 반환
				if(answer.equals(SpeedQuizList.sqList[currentQuizNum].answer[j])) {
					//새로운 문제
					GetRandomQuizNum();
					return true;
				}
			}
		}
		return false;
	}
	//세로운 문제 
	void GetRandomQuizNum() {
		//게임 정답지 세팅
		if(correctAnswerRecord.size()==0) {
			for(int i=0;i<SpeedQuizList.sqList.length;i++) 
				correctAnswerRecord.add(i);
		}
		Integer ran=new Random().nextInt(correctAnswerRecord.size());
		currentQuizNum =  correctAnswerRecord.get(ran);
		correctAnswerRecord.remove(correctAnswerRecord.get(ran));
	}
	public String GetCurrentQuizProblem() {
		return SpeedQuizList.sqList[currentQuizNum].problem;
	}
}
class User{
	public String id;
	public ObjectOutputStream oos;
	
	public User(String id, ObjectOutputStream oos) {
		this.id = id;
		this.oos = oos;
	}
}
