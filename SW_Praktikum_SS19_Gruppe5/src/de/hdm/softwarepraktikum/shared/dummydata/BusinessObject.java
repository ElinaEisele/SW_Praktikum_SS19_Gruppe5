package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;
/**
 * Named Business object class for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class BusinessObject {

	private int id;
	private Date creationDate;

	public BusinessObject(int id, Date creationDate) {
		this.id = id;
		this.creationDate = creationDate;
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
