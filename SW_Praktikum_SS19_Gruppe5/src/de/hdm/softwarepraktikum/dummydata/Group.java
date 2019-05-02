package de.hdm.softwarepraktikum.dummydata;

public class Group {
	private String groupName;
	private int id;
	private int creationDate;

	public Group(String groupName, int id, int creationDate) {
		this.groupName = groupName;
		this.id = id;
		this.creationDate = creationDate;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(int creationDate) {
		this.creationDate = creationDate;
	}

}
