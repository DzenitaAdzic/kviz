package service;

import java.util.List;

import dao.KorisnikDao;
import dao.KvizDao;
import model.Korisnik;
import model.Kviz;
import model.Pitanje;

public class KvizService {
	
	private KvizDao kvizDao;
	
	public KvizService(KvizDao kvizDao) {
		this.kvizDao = kvizDao;
	}
	
	public boolean create(Kviz kviz) {		
		return kvizDao.save(kviz);	
	}
	
	public boolean save(Kviz kviz) {		
		return kvizDao.merge(kviz);	
	}
	
	public boolean removePitanje(int pitanjeId) {		
		return kvizDao.deletePitanjeById(pitanjeId);	
	}
	
	public boolean remove(int kvizId) {
		return kvizDao.deleteById(kvizId);
	}
	
	public List<Kviz> findAll() {
		return kvizDao.findAll();
	}
	
	public List<Kviz> findAllActive() {
		return kvizDao.findAllActive();
	}
	
	public Kviz findById(int kvizId) {
		return kvizDao.findById(kvizId);
	}
	
	public List<Kviz> findByKorisnikId(int userId) {
		return kvizDao.findByKorisnikId(userId);
	}
	
	public Pitanje findPitanjeById(int pitanjeId) {
		return kvizDao.findPitanjeById(pitanjeId);
	}
		
}
