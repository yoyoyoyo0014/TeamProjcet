package minigame.db.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import minigame.db.dao.GameDAO;

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

}
