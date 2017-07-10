package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AbstractDao {
	private static final String PERSISTENCE_UNIT = "RWA_projekat";

	public EntityManager createEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		return emf.createEntityManager();
	}
}
