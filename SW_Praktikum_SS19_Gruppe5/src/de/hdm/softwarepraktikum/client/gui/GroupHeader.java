package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Klasse fuer die Ueberschrift und Buttons in der Gruppenansicht
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class GroupHeader extends HorizontalPanel {

	Label groupHeaderLabel = new Label("Hier steht der Name der ausgewählten Gruppe");

	Button addUser;
	Button leaveGroup;
	Button deleteGroup;
	Button editGroup;

	public void onLoad() {
		super.onLoad();

		addUser = new Button();
		deleteGroup = new Button();
		leaveGroup = new Button();
		editGroup = new Button();

		Image addUserImg = new Image();
		addUserImg.setUrl("images/add-user.png");
		addUserImg.setSize("32px", "32px");
		addUser.getElement().appendChild(addUserImg.getElement());

		Image leaveGroupImg = new Image();
		leaveGroupImg.setUrl("images/logout.png");
		leaveGroupImg.setSize("32px", "32px");
		leaveGroup.getElement().appendChild(leaveGroupImg.getElement());
		//leaveGroup.setStyleName("leaveGroupButton");

		Image deleteImg = new Image();
		deleteImg.setUrl("images/delete.png");
		deleteImg.setSize("32px", "32px");
		deleteGroup.getElement().appendChild(deleteImg.getElement());

		Image editGroupImg = new Image();
		editGroupImg.setUrl("images/edit.png");
		editGroupImg.setSize("32px", "32px");
		editGroup.getElement().appendChild(editGroupImg.getElement());
		
		this.add(groupHeaderLabel);
		this.add(addUser);
		this.add(leaveGroup);
		this.add(editGroup);
		this.add(deleteGroup);


	}

}
