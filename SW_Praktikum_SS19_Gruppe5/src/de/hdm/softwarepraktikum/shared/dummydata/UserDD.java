package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.ArrayList;
import java.util.Date;

/**
 * User object class for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class UserDD extends NamedBusinessObject {

	private String gMail;
	private ArrayList<Group> groups = new ArrayList<Group>();

	public UserDD(String name, int id, Date creationDate, String gMail) {
		super(name, id, creationDate);
		this.gMail = gMail;
	}

	public String getgMail() {
		return gMail;
	}

	public void setgMail(String gMail) {
		this.gMail = gMail;
	}
	
	public void addGroup(Group g) {
		this.groups.add(g);
	}
	
	public ArrayList<Group> getGroups(){
		return groups;
	}
}
