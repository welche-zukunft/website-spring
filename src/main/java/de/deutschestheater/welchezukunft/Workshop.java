package de.deutschestheater.welchezukunft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Workshop {
	
	@Id
	private Long id;

	private String leiterIn = "";

	private String titel = "";
	
	private String logline = "";
	
	@Column(length = 3000) 
	private String kurzinfo = "";
	
	private String intro = "";
	
	private String cvWorkshopleiterIn = "";
	
	private String moderatorIn = "";
	
	private String cvModeratorIn = "";
	
	private Integer max = 10;

	private Integer blockiert = 0;
	
	private Integer warteliste = 0;
	
	private Integer belegt = 0;

	
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getLogline() {
		return logline;
	}

	public void setLogline(String logline) {
		this.logline = logline;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCvWorkshopleiterIn() {
		return cvWorkshopleiterIn;
	}

	public void setCvWorkshopleiterIn(String cvWorkshopleiterIn) {
		this.cvWorkshopleiterIn = cvWorkshopleiterIn;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeiterIn() {
		return leiterIn;
	}

	public void setLeiterIn(String leiterIn) {
		this.leiterIn = leiterIn;
	}

	public String getKurzinfo() {
		return kurzinfo;
	}

	public void setKurzinfo(String kurzinfo) {
		this.kurzinfo = kurzinfo;
	}

	public String getModeratorIn() {
		return moderatorIn;
	}

	public void setModeratorIn(String moderatorin) {
		this.moderatorIn = moderatorin;
	}

	public String getCvModeratorIn() {
		return cvModeratorIn;
	}

	public void setCvModeratorIn(String cvModeratorin) {
		this.cvModeratorIn = cvModeratorin;
	}
	
	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getBlockiert() {
		return blockiert;
	}

	public void setBlockiert(Integer blockiert) {
		this.blockiert = blockiert;
	}
	
	public Integer getWarteliste() {
		return warteliste;
	}

	public void setWarteliste(Integer warteliste) {
		this.warteliste = warteliste;
	}

	public Integer getBelegt() {
		return belegt;
	}

	public void setBelegt(Integer belegt) {
		this.belegt = belegt;
	}


	@Override
	public boolean equals(Object obj) {
	    if (this.id == ((Workshop)obj).id) {
	        return true;
	    } else {
	    	return false;
	    }
	}

	@Override
	public int hashCode() {
	    int hash = 3;
	    hash = 53 * hash + (this.titel != null ? this.titel.hashCode() : 0);
	    return hash;
	}

	
	@Override
	public String toString(){
		return "Workshop : ID " + this.id 
				+ " / LeiterIn " + this.leiterIn  + "\n"
				+ " / titel " + this.titel + "\n"
				+ " / logline " + this.logline + "\n"
				+ " / kurzinfo " + this.kurzinfo + "\n"
				+ " / intro " + this.intro + "\n"
				+ " / cvWorkshopleiterIn " + this.cvWorkshopleiterIn + "\n"
				+ " / moderatorin " + this.moderatorIn + "\n"
				+ " / cvModeratorIn " + this.cvModeratorIn;

	}

	
	

}
