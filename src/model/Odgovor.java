package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the odgovor database table.
 * 
 */
@Entity
@NamedQuery(name="Odgovor.findAll", query="SELECT o FROM Odgovor o")
public class Odgovor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private int tacan;

	private String tekst;
	
	public Odgovor() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTacan() {
		return this.tacan;
	}

	public void setTacan(int tacan) {
		this.tacan = tacan;
	}

	public String getTekst() {
		return this.tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

}