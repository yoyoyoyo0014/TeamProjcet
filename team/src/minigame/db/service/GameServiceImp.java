package minigame.db.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import minigame.db.dao.GameDAO;
import minigame.db.model.vo.UserVO;

// Service Implement 
public class GameServiceImp implements UserService {

	private GameDAO gameDao;

	public GameServiceImp() {
		String resource = "minigame/db/config/mybatis-config.xml";
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			gameDao = session.getMapper(GameDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean insertGame(String gameTitle) {

		return gameDao.insertGame(gameTitle);
	}

	@Override
	public UserVO Login(String id, String pw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ExistUser(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String FindPassWord(String id, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean JoinMembership(String id, String email, String pw) {
		// TODO Auto-generated method stub
		return false;
	}

}
