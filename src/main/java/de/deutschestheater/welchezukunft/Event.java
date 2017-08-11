package de.deutschestheater.welchezukunft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Event {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private Long workshopId;
		
	private String ueberschrift;
	
	// Todo: Validation 
	private Integer jahr;
	
	private String text;
	
	private String embedcode;
	
	private String filename;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}
	
	


}
