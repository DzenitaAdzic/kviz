package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the korisnik database table.
 * 
 */
@Entity
@NamedQuery(name="Korisnik.findAll", query="SELECT k FROM Korisnik k")
public class Korisnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	private String password;

	private int superadmin;

	private String username;

	//bi-directional many-to-one association to Kviz
	@OneToMany(mappedBy="korisnik", cascade = CascadeType.ALL)
	private List<Kviz> kvizovi;

	public Korisnik() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSuperadmin() {
		return this.superadmin;
	}

	public void setSuperadmin(int superadmin) {
		this.superadmin = superadmin;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Kviz> getKvizovi() {
		return this.kvizovi;
	}

	public void setKvizovi(List<Kviz> kvizovi) {
		this.kvizovi = kvizovi;
	}

	public Kviz addKvizovi(Kviz kvizovi) {
		getKvizovi().add(kvizovi);
		kvizovi.setKorisnik(this);

		return kvizovi;
	}

	public Kviz removeKvizovi(Kviz kvizovi) {
		getKvizovi().remove(kvizovi);
		kvizovi.setKorisnik(null);

		return kvizovi;
	}

}