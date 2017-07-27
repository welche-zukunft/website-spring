package de.deutschestheater.welchezukunft;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import enumutils.AGB;
import enumutils.Status;

@Entity
public class User {
	
	
	@Id
	private Long id;
	
	private Integer workshopId;
	
	private String motivation;
	
	private String mail;
	
	private String mailConfirm;

	private String vorname;
	
	private String nachname;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private AGB agb;

	
	

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

	public Integer getWorkshopId() {
		return workshopId;
	}

	public void setWorkshopId(Integer workshopId) {
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