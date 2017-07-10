package model;

import java.io.Serializable;
import java.util.HashMap;

import javax.persistence.*;


/**
 * The persistent class for the rezultat database table.
 * 
 */
@Entity
@NamedQuery(name="Rezultat.findAll", query="SELECT r FROM Rezultat r")
public class Rezultat implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String bodovi;

	private int datum;

	private String email;

	private String ime;

	//bi-directional many-to-one association to Kviz
	@ManyToOne(fetch=FetchType.LAZY)
	private Kviz kviz;

	public Rezultat() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBodovi() {
		return this.bodovi;
	}

	public void setBodovi(String bodovi) {
		this.bodovi = bodovi;
	}

	public int getDatum() {
		return this.datum;
	}

	public void setDatum(int datum) {
		this.datum = datum;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIme() {
		return this.ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public Kviz getKviz() {
		return this.kviz;
	}

	public void setKviz(Kviz kviz) {
		this.kviz = kviz;
	}
	
	public HashMap<String, String> toMap() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		mapa.put("id", new Integer(id).toString());
		mapa.put("kviz", kviz.getNaziv());		
		mapa.put("bodovi", new Integer(bodovi).toString());
		mapa.put("email", email);
		return mapa;
	}

}