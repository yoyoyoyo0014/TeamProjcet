package minigame.db.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import minigame.db.dao.ScoreDAO;

// Service Implement 
public class ScoreServiceImp implements UserService {

	private ScoreDAO scoreDao;

	public ScoreServiceImp() {
		String resource = "minigame/db/config/mybatis-config.xml";
		InputStream inputStream;
		SqlSession session;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sessionFactory.openSession(true);
			scoreDao = session.getMapper(ScoreDAO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
