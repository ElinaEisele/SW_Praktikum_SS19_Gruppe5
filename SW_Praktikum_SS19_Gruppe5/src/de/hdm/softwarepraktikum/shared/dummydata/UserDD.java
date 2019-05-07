package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.ArrayList;
import java.util.Date;

/**
 * User object class for testing purposes
 * 
 * @author JonasWagenknecht, ElinaEisele
 */

public class UserDD extends NamedBusinessObjectDD {

	private String gMail;
	private ArrayList<GroupDD> groups = new ArrayList<GroupDD>();

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
	
	public void addGroup(GroupDD g) {
		this.groups.add(g);
	}
	
	public ArrayList<GroupDD> getGroups(){
		return groups;
	}
}
