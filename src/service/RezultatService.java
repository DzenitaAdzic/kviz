package service;

import java.util.List;

import dao.KorisnikDao;
import dao.RezultatDao;
import model.Korisnik;
import model.Rezultat;

public class RezultatService {
	
	private RezultatDao rezultatDao;
	
	public RezultatService(RezultatDao rezultatDao) {
		this.rezultatDao = rezultatDao;
	}
	
	public boolean create(Rezultat rez) {		
		return rezultatDao.save(rez);	
	}
	
	public List<Rezultat> findAll() {
		return rezultatDao.findAll();
	}
		
}
