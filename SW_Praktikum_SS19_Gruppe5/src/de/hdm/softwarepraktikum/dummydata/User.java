package de.hdm.softwarepraktikum.dummydata;

public class User {
	private String userName;
	private int id;
	private int creationDate;
	private String gMail;

	public User(String userName, int id, int creationDate, String gMail) {
		this.userName = userName;
		this.id = id;
		this.creationDate = creationDate;
		this.gMail=gMail;
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

	public int getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(int creationDate) {
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
