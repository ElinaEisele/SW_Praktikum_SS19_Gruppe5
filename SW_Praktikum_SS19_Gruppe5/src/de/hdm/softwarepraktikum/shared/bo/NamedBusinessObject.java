package de.hdm.softwarepraktikum.shared.bo;

public class NamedBusinessObject extends BusinessObject{

	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public NamedBusinessObject(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
