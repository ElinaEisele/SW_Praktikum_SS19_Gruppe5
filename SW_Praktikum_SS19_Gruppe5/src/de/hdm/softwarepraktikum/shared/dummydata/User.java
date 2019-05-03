package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

/**
 * User object class for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class User extends NamedBusinessObject {

	private String gMail;

	public User(String name, int id, Date creationDate, String gMail) {
		super(name, id, creationDate);
		this.gMail = gMail;
	}

	public String getgMail() {
		return gMail;
	}

	public void setgMail(String gMail) {
		this.gMail = gMail;
	}
}
