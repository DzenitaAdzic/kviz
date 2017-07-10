package model;

import java.io.Serializable;
import javax.persistence.*;

import org.eclipse.persistence.annotations.ReadOnly;

import java.util.HashMap;
import java.util.List;


/**
 * The persistent class for the kviz database table.
 * 
 */
@Entity
@NamedQuery(name="Kviz.findAll", query="SELECT k FROM Kviz k")
public class Kviz implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int isActive;

	private String naziv;

	@Column(name="korisnik_id", nullable=false, length=11,
	        updatable=false, insertable=false)
	private int korisnik_id;
	
	//bi-directional many-to-one association to Korisnik
	@ManyToOne
	private Korisnik korisnik;

	//bi-directional many-to-many association to Pitanje
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="kviz_pitanje"
		, joinColumns={
			@JoinColumn(name="kviz_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="pitanje_id")
			}
		)
	private List<Pitanje> pitanja;

	//bi-directional many-to-one association to Rezultat
	@OneToMany(mappedBy="kviz", cascade = CascadeType.ALL)
	private List<Rezultat> rezultati;

	public Kviz() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Korisnik getKorisnik() {
		return this.korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public List<Pitanje> getPitanja() {
		return this.pitanja;
	}

	public void setPitanja(List<Pitanje> pitanja) {
		this.pitanja = pitanja;
	}

	public List<Rezultat> getRezultati() {
		return this.rezultati;
	}

	public void setRezultati(List<Rezultat> rezultati) {
		this.rezultati = rezultati;
	}

	public Rezultat addRezultati(Rezultat rezultati) {
		getRezultati().add(rezultati);
		rezultati.setKviz(this);

		return rezultati;
	}

	public Rezultat removeRezultati(Rezultat rezultati) {
		getRezultati().remove(rezultati);
		rezultati.setKviz(null);

		return rezultati;
	}
	
	public HashMap<String, String> toMap() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		mapa.put("id", new Integer(id).toString());
		mapa.put("naziv", naziv);
		mapa.put("isActive", new Integer(isActive).toString());
		mapa.put("korisnikId", new Integer(korisnik.getId()).toString());
		return mapa;
	}

}