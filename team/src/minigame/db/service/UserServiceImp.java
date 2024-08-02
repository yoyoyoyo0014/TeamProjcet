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

	public UserVO Login(String id, String pw) {
		return userDao.LoginUser(id, pw);
	}

	public boolean ExistUser(String id) {
		 UserVO user = userDao.ExistLogin(id);
		 return user ==null ? false : true;
	}

}
