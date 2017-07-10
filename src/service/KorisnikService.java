package service;

import java.util.List;

import dao.KorisnikDao;
import model.Korisnik;

public class KorisnikService {
	
	private KorisnikDao korisnikDao;
	
	public KorisnikService(KorisnikDao korisnikDao) {
		this.korisnikDao = korisnikDao;
	}
	
	public boolean create(Korisnik korisnik) {		
		return korisnikDao.save(korisnik);	
	}
	
	public boolean remove(int userId) {
		return korisnikDao.deleteById(userId);
	}
	
	public List<Korisnik> findAll() {
		return korisnikDao.findAll();
	}
	
	public Korisnik findByUsername(String username) {
		return korisnikDao.findByUsername(username);
	}
	
	public Korisnik authenticate(String username, String password) {
		
		Korisnik user = findByUsername(username);
		
		if (user == null) {
			return null;
		}
		
		if (user.getPassword().equals(password)) {
			return user;
		}
		
		return null;
	}
}
