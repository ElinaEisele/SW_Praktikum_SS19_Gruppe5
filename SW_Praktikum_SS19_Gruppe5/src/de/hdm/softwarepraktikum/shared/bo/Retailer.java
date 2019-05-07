package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Retailer extends NamedBusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	public Retailer(String retailername) {
		this.setName(retailername);
	}
}
