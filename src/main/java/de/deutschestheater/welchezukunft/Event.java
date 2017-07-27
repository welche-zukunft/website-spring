package de.deutschestheater.welchezukunft;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {
	
	@Id
	private Integer id;
	
	@ManyToOne
	private Workshop workshop;
	
	private String ueberschrift;
	
	// Todo: Validation 
	private Integer jahr;
	
	private String text;
	
	private String embedcode;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}

	public String getUeberschrift() {
		return ueberschrift;
	}

	public void setUeberschrift(String ueberschrift) {
		this.ueberschrift = ueberschrift;
	}

	public Integer getJahr() {
		return jahr;
	}

	public void setJahr(Integer jahr) {
		this.jahr = jahr;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getEmbedcode() {
		return embedcode;
	}

	public void setEmbedcode(String embedcode) {
		this.embedcode = embedcode;
	}


}
