package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;


/**
 * Uebersicht der Gruppe mit allen Listen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class GroupShowForm extends VerticalPanel{
	
	GroupHeader groupHeader = null;
	GroupContent groupContent = null;
	
	public void onLoad() {
		super.onLoad();
		
		groupHeader = new GroupHeader();
		groupContent = new GroupContent();
		
		groupHeader.setStylePrimaryName("groupHeader");
		groupContent.setStylePrimaryName("groupContent");
		
		this.add(groupHeader);
		this.add(groupContent);
	}

	public void setSelected(Group g) {
		// TODO Auto-generated method stub
		
	}

}
