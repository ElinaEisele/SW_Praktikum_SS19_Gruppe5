package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse Group, welche eine Gruppe darstellt, welche aus mehreren Mitgliedern (User)
 * und Einkaufslisten (Shoppinglist) bestehen kann.
 * 
 * @author Felix Rapp
 */

public class Group extends NamedBusinessObject implements IsSerializable{

	private ArrayList<User> users;
	private ArrayList<Shoppinglist> shoppinglists;
	private ArrayList<Listitem> standardListitem;
	
	public Group(String groupname) {
		super(1, groupname);
	}
	
	public ArrayList<User> getUsers() {
		return users;
	}
	
	public void setUser(ArrayList<User> users) {
		this.users = users;
	}
	
	public ArrayList<Shoppinglist> getShoppinglists() {
		return shoppinglists;
	}
	
	public void setShoppinglists(ArrayList<Shoppinglist> shoppinglists) {
		this.shoppinglists = shoppinglists;
	}
	
	public ArrayList<Listitem> getStandardListitem() {
		return standardListitem;
	}
	
	public void setStandardListitem(ArrayList<Listitem> standardListitem) {
		this.standardListitem = standardListitem;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
}
