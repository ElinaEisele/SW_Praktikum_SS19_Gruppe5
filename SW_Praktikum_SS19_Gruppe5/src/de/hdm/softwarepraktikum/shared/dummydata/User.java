package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * 
 * @author JonasWagenknecht
 * User object for testing purposes
 * 
 */

public class User extends BusinessObject{
	private String userName;
	private int id;
	private Date creationDate;
	private String gMail;

	public User(String userName, int id, Date creationDate, String gMail) {
		this.userName = userName;
		this.id = id;
		this.creationDate = creationDate;
		this.gMail = gMail;
	}

	public String getgMail() {
		return gMail;
	}

	public void setgMail(String gMail) {
		this.gMail = gMail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
