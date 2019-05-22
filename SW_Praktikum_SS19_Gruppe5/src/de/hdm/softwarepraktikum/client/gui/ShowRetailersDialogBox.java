package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.DialogBox;

import de.hdm.softwarepraktikum.shared.bo.Group;

/**
 * Klasse zur Darstellung einer Dialogbox, wenn alle Retailer angezeigt
 * werden sollen
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ShowRetailersDialogBox extends DialogBox{
	
	private Group selectedGroup;

	public Group getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(Group selectedGroup) {
		this.selectedGroup = selectedGroup;
	}
	
	
	

}
