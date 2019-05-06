package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

public class NamedBusinessObject extends BusinessObjectDD {
	private String name;

	public NamedBusinessObject(String name, int id, Date creationDate) {
		super(id, creationDate);
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
