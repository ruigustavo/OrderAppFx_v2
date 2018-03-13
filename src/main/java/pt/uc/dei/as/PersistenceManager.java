/*
 * Copyright 2017 (C) <University of Coimbra>
 * 
 * Created on : 15-02-2017
 * Author     : Bruno Cabral 
 */
package pt.uc.dei.as;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


// TODO: Auto-generated Javadoc
/**
 * The Class PersistenceManager.
 */
public class PersistenceManager {
	
	/** The em factory. */
	private EntityManagerFactory emFactory;

	/**
	 * Instantiates a new persistence manager.
	 */
	public PersistenceManager() {
		emFactory = Persistence.createEntityManagerFactory("OrderAppFX");
	}
	
	/**
	 * Instantiates a new persistence manager.
	 *
	 * @param ip the ip
	 */
	public  PersistenceManager(String ip) {
		Map<String, String> persistenceMap = new HashMap<String, String>();
		String url = "jdbc:mysql://" + ip + ":3306/ete_db?useSSL=true&amp;verifyServerCertificate=false";
		persistenceMap.put("javax.persistence.jdbc.url", url);
		persistenceMap.put("javax.persistence.jdbc.user", "eteuser");
		persistenceMap.put("javax.persistence.jdbc.password", "123456");
		persistenceMap.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");

		if (emFactory != null && emFactory.isOpen())
			emFactory.close();
		emFactory = Persistence.createEntityManagerFactory("OrderAppFX", persistenceMap);
	}
	
	/**
	 * Instantiates a new persistence manager.
	 *
	 * @param driver the driver
	 * @param url the url
	 * @param username the username
	 * @param password the password
	 */
	public  PersistenceManager(String driver, String url, String username, String password) {
		Map<String, String> persistenceMap = new HashMap<String, String>();

		persistenceMap.put("javax.persistence.jdbc.url", url);
		persistenceMap.put("javax.persistence.jdbc.user", username);
		persistenceMap.put("javax.persistence.jdbc.password", password);
		persistenceMap.put("javax.persistence.jdbc.driver", driver);

		if (emFactory != null && emFactory.isOpen())
			emFactory.close();
		emFactory = Persistence.createEntityManagerFactory("OrderAppFX", persistenceMap);
	}

	/**
	 * Gets the entity manager.
	 *
	 * @return the entity manager
	 */
	public EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}
	
	/**
	 * Close.
	 */
	public void close() {
		emFactory.close();
	}
}
