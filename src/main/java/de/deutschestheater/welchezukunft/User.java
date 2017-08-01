package de.deutschestheater.welchezukunft;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import enumutils.AGB;
import enumutils.Modus;
import enumutils.Sprache;
import enumutils.Stand;
import enumutils.Status;

@Entity
public class User {
	
	
	@Id
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private Date datum;
		
	private Long workshopId = (long)14;
		
	@Column(length = 3000) 
	private String motivation;
	
	private String mail;
	
	private String mailConfirm;

	private String vorname;
	
	private String nachname;
	
	@Column(length = 3000) 
	private String internText;

	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private AGB agb;
	
	@Enumerated(EnumType.STRING)
	private Modus modus;

	@Enumerated(EnumType.STRING)
	private Sprache sprache;
	
	@Enumerated(EnumType.STRING)
	private Stand stand = Stand.TODO;
			


	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public AGB getAgb() {
		return agb;
	}

	public void setAgb(AGB agb) {
		this.agb = agb;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Long workshopId) {
		this.workshopId = workshopId;
	}

	public String getMotivation() {
		return motivation;
	}

	public void setMotivation(String motivation) {
		this.motivation = motivation;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getMailConfirm() {
		return mailConfirm;
	}

	public void setMailConfirm(String mailConfirm) {
		this.mailConfirm = mailConfirm;
	}
	
	public String getInternText() {
		return internText;
	}

	public void setInternText(String internText) {
		this.internText = internText;
	}

	public Modus getModus() {
		return modus;
	}

	public void setModus(Modus modus) {
		this.modus = modus;
	}

	public Sprache getSprache() {
		return sprache;
	}

	public void setSprache(Sprache sprache) {
		this.sprache = sprache;
	}
	
	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}
	
	public Stand getStand() {
		return stand;
	}

	public void setStand(Stand bearbeitet) {
		this.stand = bearbeitet;
	}


	


	@Override
	public boolean equals(Object obj) {
	    if (this.id == ((User)obj).id) {
	        return true;
	    } else {
	    	return false;
	    }
	}

	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.nachname != null ? this.nachname.hashCode() : 0);
	    return hash;
	}
	
	
	
	

}