package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.Korisnik;
import model.Kviz;
import model.Pitanje;

public class KvizDao extends AbstractDao {
	
	public void KvizDao() {
		
	}
	
	public List<Kviz> findAll() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT u FROM Kviz u");
		List<Kviz> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public List<Kviz> findAllActive() {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT u FROM Kviz u WHERE u.isActive = :isActive").setParameter("isActive", 1);
		List<Kviz> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public List<Kviz> findByKorisnikId(int userId) {
		EntityManager em = createEntityManager();
		Query q = em.createQuery("SELECT u FROM Kviz u WHERE u.korisnik_id = :userId").setParameter("userId", userId);
		List<Kviz> resultList = q.getResultList();
		em.close();
		return resultList;
	}
	
	public Kviz findById(int kvizId) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Kviz u WHERE u.id = :kvizId").setParameter("kvizId", kvizId);
			Kviz kviz = (Kviz) q.getSingleResult();
			return kviz;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public Pitanje findPitanjeById(int pitanjeId) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Pitanje u WHERE u.id = :pitanjeId").setParameter("pitanjeId", pitanjeId);
			Pitanje p = (Pitanje) q.getSingleResult();
			return p;					
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		} finally {		
			if (em!= null) {
				em.close();
			}
		}		
		return null;
	}
	
	public boolean deleteById(int kvizId) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Kviz u WHERE u.id = :kvizId").setParameter("kvizId", kvizId);
			Kviz kviz = (Kviz) q.getSingleResult();
			System.out.println("DELETING: " + kviz.getNaziv() + " - " + kviz.getId());
			em.getTransaction().begin();
			em.remove(kviz);			
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
	
	public boolean deletePitanjeById(int pitanjeId) {
		EntityManager em = createEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Pitanje u WHERE u.id = :pitanjeId").setParameter("pitanjeId", pitanjeId);
			Pitanje pitanje = (Pitanje) q.getSingleResult();
			System.out.println("DELETING: " + pitanje.getPitanje() + " - " + pitanje.getId());
			em.getTransaction().begin();
			em.remove(pitanje);			
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
	
	public boolean save(Kviz kviz) {
		EntityManager em = createEntityManager();		
		em.getTransaction().begin();
		em.persist(kviz);
		em.getTransaction().commit();			
		em.close();
		return true;
	}
	
	public boolean merge(Kviz kviz) {
		EntityManager em = createEntityManager();		
		em.getTransaction().begin();
		em.merge(kviz);
		em.getTransaction().commit();			
		em.close();
		return true;
	}
}
