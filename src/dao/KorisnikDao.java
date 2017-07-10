package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Korisnik;

public class KorisnikDao extends AbstractDao {
	
	public void KorisnikDao() {
		
	}
	
	public List<Korisnik> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT u FROM Korisnik u");
		List<Korisnik> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public Korisnik findByUsername(String username) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Korisnik u WHERE u.username = :username").setParameter("username", username);
			Korisnik user = (Korisnik) q.getSingleResult();
			return user;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public boolean deleteById(int userId) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Korisnik u WHERE u.id = :userId").setParameter("userId", userId);
			Korisnik user = (Korisnik) q.getSingleResult();
			if(user.getSuperadmin()==1) {
				return false;
			}
			System.out.println("DELETING: " + user.getUsername() + " - " + user.getId());
			em.getTransaction().begin();
			em.remove(user);			
			em.getTransaction().commit();
			return true;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return false;
	}
	
	public boolean save(Korisnik user) {
		EntityManager em = createEntityManager();		
		try {
			Query q = em.createQuery("SELECT u FROM Korisnik u WHERE u.username = :username").setParameter("username", user.getUsername());
			q.getSingleResult();
			return false;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();			
		} finally {		
			if (em!= null) {
				em.close();
			}
		} 
		return true;
	}
}
