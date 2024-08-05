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
public class GameServiceImp implements GameService {

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

	public int getGameKey(String gameTitle) {
		
		return gameDao.selectGameKey(gameTitle);
	}


	public int getGameVPoint(String gameTitle) {
		return gameDao.selectVPoint(gameTitle);
	}

	public int getGameLPoint(String gameTitle) {
		return gameDao.selectLPoint(gameTitle);
	}

	public int getGameDPoint(String gameTitle) {
		return gameDao.selectDPoint(gameTitle);
	}

	public String getGameTitle(int sc_gm_key) {
		return gameDao.selectGameTitle(sc_gm_key);
	}

}
