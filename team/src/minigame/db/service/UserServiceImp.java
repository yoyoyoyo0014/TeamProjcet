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

	// 로그인 메소드
	// 잘못된 로그인
	public boolean login(String id, String pw) {
		if (userDao.login(id, pw) != null)
			return true;
		else {
			return false;
		}
	}

	// 유저가 존재하는지
	public boolean existUser(String id) {
		UserVO user = userDao.existUser(id);
		return user == null ? false : true;
	}

	// 비밀번호 찾기 아이디가 존재하지 않거나 이메일과 아이디가 맞지 않을 때 null 값
	public String findPassword(String id, String email) {
		return userDao.findPassword(id, email);
	}

	// 회원가입 아이디가 존재하면 false
	public boolean join(String id, String pw, String email) {
		if (existUser(id)) {
			return false;
		}
		UserVO user = new UserVO(id, pw, email);
		return userDao.join(user);
	}

	@Override
	public boolean updatePassword(String id, String pwd, String newPwd) {

		return userDao.updatePassword(id, pwd, newPwd);
	}
	
	@Override
	public int getUserKey(String playerID) {
	
		return userDao.selectUserKey(playerID);
	}
	
	@Override
	public String getUserId(int us_key) {
		
		return userDao.selectUserId(us_key);
	}



}
