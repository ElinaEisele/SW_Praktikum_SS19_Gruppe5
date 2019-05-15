package de.hdm.softwarepraktikum.client.gui;

import java.util.Set;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Product;

/**
 * Klasse zur Darstellung von Shoppinglist-Objekte. Erweiterungen von
 * <code>AbstractCell<T></code> dienen zur Erzeugung von HTML-Code fuer
 * benutzerdefinierte Objekte.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
//public class ShoppinglistCell extends AbstractCell<Shoppinglist> {
//
//	@Override
//	public void render(Context context, Shoppinglist value, SafeHtmlBuilder sb) {
//		if (value == null) {
//			return;
//		}
//		
//		sb.appendHtmlConstant("<div>");
//		sb.appendEscaped(value.getName());
//		sb.appendHtmlConstant("</div>");
//	}
//
//}

public class ShoppinglistCell extends VerticalPanel implements Cell<Group> {
Product productToDisplay = null;
	VerticalPanel v1;
	ShoppinglistCellTable shoppinglistCellTable;
	ListDataProvider<Group> provider;
	
	TextBox productNameTextBox = new TextBox();
	TextBox amountTextBox = new TextBox();
	TextBox unitNameTextBox = new TextBox();
	TextBox retailerNameTextBox = new TextBox();
	
	public void onLoad() {
		super.onLoad();

		
		/*
		 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
		 */
		
		
		shoppinglistCellTable = new ShoppinglistCellTable();
		v1 = new VerticalPanel();
		this.add(v1);

		
		/*
		 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
		 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
		 * bestimmt.
		 */

		Grid shoppinglistGrid = new Grid(5, 3);
		v1.add(shoppinglistGrid);

		

		//newButton.addClickHandler(new NewClickHandler());
		

		Label productNameLabel = new Label("Produkt");
		shoppinglistGrid.setWidget(0, 0, productNameLabel);
		shoppinglistGrid.setWidget(0, 1, productNameTextBox);
		productNameTextBox.setText("Test1");
		
		Label amountLabel = new Label("Menge");
		shoppinglistGrid.setWidget(1, 0, amountLabel);
		shoppinglistGrid.setWidget(1, 1, amountTextBox);
		amountTextBox.setText("Test");
		
		Label unitNameLabel = new Label("Einheit");
		shoppinglistGrid.setWidget(2, 0, unitNameLabel);
		shoppinglistGrid.setWidget(2, 1, unitNameTextBox);	
		unitNameTextBox.setText("Test");
		
		Label retailerNameLabel = new Label("Händler");
		shoppinglistGrid.setWidget(3, 0, retailerNameLabel);
		shoppinglistGrid.setWidget(3, 1, retailerNameTextBox);
		retailerNameTextBox.setText("Test");
		

		HorizontalPanel customerButtonsPanel = new HorizontalPanel();
		shoppinglistGrid.setWidget(4, 1, customerButtonsPanel);

		Button saveButton = new Button("Speichern");
		//saveButton.addClickHandler(new ChangeClickHandler());
		Button discardButton = new Button("verwerfen");
		discardButton.addClickHandler(new DiscardClickHandler());
		
		customerButtonsPanel.add(saveButton);
		customerButtonsPanel.add(discardButton);
//				Button searchButton = new Button("Suchen");
//				customerButtonsPanel.add(searchButton);

	}
	private class DiscardClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			v1.clear();
			v1.add(shoppinglistCellTable);
		}
	}
	@Override
	public boolean dependsOnSelection() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set<String> getConsumedEvents() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean handlesSelection() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEditing(Context context, Element parent, Group value) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onBrowserEvent(Context context, Element parent, Group value, NativeEvent event,
			ValueUpdater<Group> valueUpdater) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void render(Context context, Group value, SafeHtmlBuilder sb) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean resetFocus(Context context, Element parent, Group value) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setValue(Context context, Element parent, Group value) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
			

			
			


