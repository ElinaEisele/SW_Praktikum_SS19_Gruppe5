package de.hdm.softwarepraktikum.shared.dummydata;

import java.util.Date;

public class Group {
	private String groupName;
	private int id;
	private Date creationDate;

	public Group(String groupName, int id, Date creationDate) {
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
