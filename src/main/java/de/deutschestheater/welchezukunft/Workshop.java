package de.deutschestheater.welchezukunft;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Workshop {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String leiterIn;

	private String kurzinfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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



}
