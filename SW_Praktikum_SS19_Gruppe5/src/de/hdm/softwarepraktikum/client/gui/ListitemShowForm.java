package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.shared.bo.Listitem;

/**
 * Klasse zur Darstellung eines Shoppinglist-Listitems.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class ListitemShowForm extends VerticalPanel {
	private ListitemHeader listitemHeader = null;
	private ListitemContent listitemContent = null;
	private NewListitemForm newListitemForm = null;
	private VerticalPanel mainPanel = new VerticalPanel();

	private Listitem selectedListitem;
	private GroupShoppinglistTreeViewModel gstvm = new GroupShoppinglistTreeViewModel();

	public ListitemShowForm(ListitemHeader lh, NewListitemForm nlf) {
		listitemHeader = lh;
		mainPanel.add(nlf);

	}

	public ListitemShowForm() {
		listitemHeader = new ListitemHeader();
		listitemContent = new ListitemContent();

		listitemHeader.setStylePrimaryName("listitemHeader");
		listitemContent.setStylePrimaryName("listitemContent");

		mainPanel.add(listitemContent);
	}

	public void onLoad() {
		listitemHeader.setListitemShowForm(ListitemShowForm.this);

		this.add(listitemHeader);
		this.add(mainPanel);

	}

	public void setSelected(Listitem l) {
		selectedListitem = l;

	}

	public GroupShoppinglistTreeViewModel getGstvm() {
		return gstvm;
	}

	public void setGstvm(GroupShoppinglistTreeViewModel gstvm) {
		this.gstvm = gstvm;
	}
}
