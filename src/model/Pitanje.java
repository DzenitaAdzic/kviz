package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.HashMap;
import java.util.List;


/**
 * The persistent class for the pitanje database table.
 * 
 */
@Entity
@NamedQuery(name="Pitanje.findAll", query="SELECT p FROM Pitanje p")
public class Pitanje implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int bodovi;

	private String pitanje;

	private int vrijeme;

	//bi-directional many-to-many association to Kviz
	@ManyToMany(mappedBy="pitanja")
	private List<Kviz> kvizovi;

	//bi-directional many-to-many association to Odgovor
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
		name="pitanje_odgovor"
		, joinColumns={
			@JoinColumn(name="pitanje_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="odgovor_id")
			}
		)
	private List<Odgovor> odgovori;

	public Pitanje() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBodovi() {
		return this.bodovi;
	}

	public void setBodovi(int bodovi) {
		this.bodovi = bodovi;
	}

	public String getPitanje() {
		return this.pitanje;
	}

	public void setPitanje(String pitanje) {
		this.pitanje = pitanje;
	}

	public int getVrijeme() {
		return this.vrijeme;
	}

	public void setVrijeme(int vrijeme) {
		this.vrijeme = vrijeme;
	}

	public List<Kviz> getKvizovi() {
		return this.kvizovi;
	}

	public void setKvizovi(List<Kviz> kvizovi) {
		this.kvizovi = kvizovi;
	}

	public List<Odgovor> getOdgovori() {
		return this.odgovori;
	}

	public void setOdgovori(List<Odgovor> odgovori) {
		this.odgovori = odgovori;
	}

	public HashMap<String, Object> toMap() {
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("id", new Integer(id).toString());
		mapa.put("pitanje", pitanje);
		mapa.put("vrijeme", new Integer(vrijeme).toString());
		mapa.put("bodovi", new Integer(bodovi).toString());
		mapa.put("odgovori", odgovori);
		return mapa;
	}	
}