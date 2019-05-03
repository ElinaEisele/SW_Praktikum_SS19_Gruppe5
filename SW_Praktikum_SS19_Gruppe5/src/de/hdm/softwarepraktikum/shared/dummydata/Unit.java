package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

public class Unit {
	private String unitName;
	private int id;
	private Date creationDate;

	public Unit(String unitName, int id, Date creationDate) {
		this.unitName = unitName;
		this.id = id;
		this.creationDate = creationDate;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
