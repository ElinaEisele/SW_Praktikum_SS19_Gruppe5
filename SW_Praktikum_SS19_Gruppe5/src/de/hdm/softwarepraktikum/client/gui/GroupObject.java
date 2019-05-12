package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.List;

public class GroupObject {
	private String groupName;
	private List<String> shoppinglistObjects = new ArrayList<String>();

	private final List<GroupObject> groupObjects = new ArrayList<>();

	public GroupObject(String groupName) {
		this.groupName = groupName;
	}

	public void addShoppinglistObject(String name) {
		shoppinglistObjects.add(name);
	}

	public String getname() {
		return groupName;
	}

	public List<String> getShoppinglistObjects() {
		return shoppinglistObjects;
	}

	public GroupObject addShoppinglistObject(GroupObject groupObject) {
		groupObjects.add(groupObject);
		return groupObject;
	}

	public List<GroupObject> getGroupObjects() {
		return groupObjects;
	}
}
