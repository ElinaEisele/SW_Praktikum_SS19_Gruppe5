package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * Superclass Business Object
 * 
 * @author ElinaEisele
 *
 */

public class BusinessObject{
	
	private int id;
	private Date creationDate;
	
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
