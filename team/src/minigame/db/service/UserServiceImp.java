package minigame.db.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import minigame.db.dao.UserDAO;
import minigame.db.model.vo.UserVO;

// Service Implement 
public class UserServiceImp implements UserService {

	private UserDAO userDao;

	public UserServiceImp() {
		String resource = "minigame/db/config/mybatis-config.xml";
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			userDao = session.getMapper(UserDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//로그인 메소드
	//잘못된 로그인 = 반환값 null
	public UserVO Login(String id, String pw) {
		return userDao.LoginUser(id, pw);
	}
	//유저가 존재하는지
	public boolean ExistUser(String id) {
		 UserVO user = userDao.ExistLogin(id);
		 return user ==null ? false : true;
	}
	//비밀번호 찾기 아이디가 존재하지 않거나 이메일과 아이디가 맞지 않을 때 null 값
	public String FindPassWord(String id, String email) {
		
		if(ExistUser(id)) {
			return null;
		}
		return userDao.findPassWord(id,email);
	}
	//회원가입 아이디가 존재하면 false
	public boolean JoinMembership(String id, String email, String pw) {
		if(ExistUser(id)) {
			return false;
		}
		UserVO user = new UserVO(id,pw,email);
		return userDao.JoinMembership(user);
	}

}
