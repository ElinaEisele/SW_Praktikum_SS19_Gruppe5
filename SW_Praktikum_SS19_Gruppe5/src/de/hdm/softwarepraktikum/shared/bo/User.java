package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class User extends NamedBusinessObject implements IsSerializable{

	private String gmailAdress;
	
	public User(String name) {
		super(name);
	}
}
