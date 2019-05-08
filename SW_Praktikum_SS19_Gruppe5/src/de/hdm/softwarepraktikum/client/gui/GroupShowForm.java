package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;


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
		
		this.add(groupHeader);
		this.add(groupContent);
	}

	public void setSelected(Group g) {
		// TODO Auto-generated method stub
		
	}

}
