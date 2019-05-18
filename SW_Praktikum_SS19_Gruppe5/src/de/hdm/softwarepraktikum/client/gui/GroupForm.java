package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;


import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Product;

public class GroupForm extends VerticalPanel {
	
		Group groupToDisplay = null;
		GroupShoppinglistTreeViewModel gstvm = null;	
		
			
		/*
		 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
		 */
			TextBox productNameTextBox = new TextBox();
			TextBox amountTextBox = new TextBox();
			TextBox unitNameTextBox = new TextBox();
			TextBox retailerNameTextBox = new TextBox();
			
			
			/*
			 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem Raster
			 * angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen Widgets
			 * bestimmt.
			 */
	
			public void onLoad() {
				super.onLoad();
				
				Grid shoppinglistGrid = new Grid(5, 3);
				this.add(shoppinglistGrid);

				

				//newButton.addClickHandler(new NewClickHandler());
				

				Label productNameLabel = new Label("Produkt");
				shoppinglistGrid.setWidget(0, 0, productNameLabel);
				shoppinglistGrid.setWidget(0, 1, productNameTextBox);
				productNameTextBox.setText("");
				
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
//						Button searchButton = new Button("Suchen");
//						customerButtonsPanel.add(searchButton);

			}
			private class DiscardClickHandler implements ClickHandler {
				@Override
				public void onClick(ClickEvent event) {
					
				}
			}
}
