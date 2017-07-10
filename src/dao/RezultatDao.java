package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Korisnik;
import model.Rezultat;

public class RezultatDao extends AbstractDao {
	
	public void RezultatDao() {
		
	}
	
	public List<Rezultat> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT u FROM Rezultat u ORDER BY u.bodovi DESC");
		List<Rezultat> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public boolean save(Rezultat rez) {
		EntityManager em = createEntityManager();		
			em.getTransaction().begin();
			em.persist(rez);
			em.getTransaction().commit();			
			em.close();
		return true;
	}
}
