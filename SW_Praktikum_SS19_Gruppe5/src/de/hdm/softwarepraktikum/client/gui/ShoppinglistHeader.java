package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;

/**
 * Klasse fuer die Anordnung der Buttons im <code>ShoppinglistHeader</code>, der
 * in der <code>ShoppinglistShowForm</code> angezeigt wird.
 * 
 * @author ElinaEisele, JonasWagenknecht
 */

public class ShoppinglistHeader extends HorizontalPanel {

//	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings
//			.getShoppinglistAdministration();
//	private GroupShoppinglistTreeViewModel gstvm = null;
//
//	private Shoppinglist shoppinglistToDisplay = null;
//
//	private Label shoppinglistHeaderLabel;
//
//	private Button newRetailer;
//	private Button newListitem;
//	private Button deleteShoppinglist;
//	private Button assignUserToRetailer;
//	private Button editShoppinglistName;
//
//	public ShoppinglistHeader() {
//
//		shoppinglistHeaderLabel = new Label("getListName");
//
//		newRetailer = new Button("Neuer Einzelh�ndler");
//		newListitem = new Button("Eintrag hinzufuegen");
//		deleteShoppinglist = new Button("Einkaufsliste loeschen");
//		assignUserToRetailer = new Button("Nutzer zuordnen");
//
//		editShoppinglist = new Button("Editieren");
//		
//		newListitemForm = new NewListitemForm();
//	
//		listLabel.setStyleName("ListLabel");
//		
//		addListitem.addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//				shoppinglistShowForm.clear();
////				shoppinglistShowForm.add(newListitemForm);
//			}
//			
//		});
//				
//		Image addListitemImg = new Image();
//		addListitemImg.setUrl("images/shopping-cart.png");
//		addListitemImg.setSize("16px", "16px");
//		addListitem.getElement().appendChild(addListitemImg.getElement());
//		addListitem.setStyleName("ShoppinglistHeaderButton");
//		
//		Image deleteShoppinglistImg = new Image();
//		deleteShoppinglistImg.setUrl("images/delete.png");
//		deleteShoppinglistImg.setSize("16px", "16px");
//		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
//		deleteShoppinglist.setStyleName("ShoppinglistHeaderButton");
//		
//		Image assignUserToRetailerImg = new Image();
//		assignUserToRetailerImg.setUrl("images/man-pushing-a-shopping-cart.png");
//		assignUserToRetailerImg.setSize("16px", "16px");
//		assignUserToRetailer.getElement().appendChild(assignUserToRetailerImg.getElement());
//		assignUserToRetailer.setStyleName("ShoppinglistHeaderButton");
//		assignUserToRetailer.addClickHandler(new AssignUserToRetailerClickHandler());
//
//		Image editShoppinglistNameImg = new Image();
//		editShoppinglistNameImg.setUrl("images/edit.png");
//		editShoppinglistNameImg.setSize("16px", "16px");
//		editShoppinglistName.getElement().appendChild(editShoppinglistNameImg.getElement());
//		editShoppinglistName.setStyleName("ShoppinglistHeaderButton");
//		editShoppinglistName.addClickHandler(new EditShoppinglistNameClickHandler());
//
//		Image deleteShoppinglistImg = new Image();
//		deleteShoppinglistImg.setUrl("images/delete.png");
//		deleteShoppinglistImg.setSize("16px", "16px");
//		deleteShoppinglist.getElement().appendChild(deleteShoppinglistImg.getElement());
//		deleteShoppinglist.setStyleName("ShoppinglistHeaderButton");
//		deleteShoppinglist.addClickHandler(new DeleteShoppinglistClickHandler());
//
//	}
//
//	public void onLoad() {
//
//		this.add(shoppinglistHeaderLabel);
//
//		this.add(newRetailer);
//		this.add(newListitem);
//		this.add(assignUserToRetailer);
//		this.add(editShoppinglistName);
//		this.add(deleteShoppinglist);
//
//	}
//
//	/**
//	 * Sobald eine <code>Shoppinglist</code> ausgewaehlt wird das Label mit den
//	 * entsprechenden Informationen bef�llt.
//	 * 
//	 * @param s, das zu setzende <code>Shoppinglist</code> Objekt.
//	 */
//	public void setShoppinglistToDisplay(Shoppinglist s) {
//		if (s != null) {
//			shoppinglistToDisplay = s;
//			shoppinglistHeaderLabel.setText(shoppinglistToDisplay.getName());
//
//		} else {
//			this.clear();
//		}
//	}
//
//	/**
//	 * ***************************************************************************
//	 * Abschnitt der ClickHandler
//	 * ***************************************************************************
//	 */
//
//	/**
//	 * ClickHandler dient dem Erzeugen einer <code>NewRetailerDialogBox</code>
//	 * Instanz.
//	 */
//	private class NewRetailerClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (shoppinglistToDisplay != null) {
//				NewRetailerDialogBox nrdb = new NewRetailerDialogBox();
//				nrdb.setGstvm(ShoppinglistHeader.this.gstvm);
//				nrdb.show();
//			} else {
//				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");
//			}
//		}
//
//	}
//
//	/**
//	 * ClickHandler dient dem Erzeugen einer <code>NewListitemForm</code> Instanz.
//	 */
//	private class NewListitemClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (shoppinglistToDisplay != null) {
//				NewListitemForm nlf = new NewListitemForm(shoppinglistToDisplay);
//				nlsf.setGstvm(ShoppinglistHeader.this.gstvm);
//				RootPanel.get("main").clear();
////				RootPanel.get("main").add(nlfheader);
//				RootPanel.get("main").add(nlf);
//			} else {
//				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");			}
//		}
//
//	}
//
//	/**
//	 * ClickHandler dient dem Erzeugen einer
//	 * <code>AssignUserToRetailerDialogBox</code> Instanz.
//	 */
//	private class AssignUserToRetailerClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (shoppinglistToDisplay != null) {
//				AssignUserToRetailerDialogBox autrdb = new AssignUserToRetailerDialogBox();
//			autrdb.setGstvm(ShoppinglistHeader.this.gstvm);
//			autrdb.show();
//			} else {
//				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");			}
//		}
//
//	}
//
//	/**
//	 * ClickHandler dient dem Erzeugen einer
//	 * <code>EditShoppinglistNameDialogBox</code> Instanz.
//	 */
//	private class EditShoppinglistNameClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (shoppinglistToDisplay != null) {
//				EditShoppinglistNameDialogBox esndb = new EditShoppinglistNameDialogBox();
//				esndb.setGstvm(ShoppinglistHeader.this.gstvm);
//				esndb.show();
//			} else {
//				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");			}
//		}
//
//	}
//
//	/**
//	 * ClickHandler dient dem Erzeugen einer
//	 * <code>DeleteShoppinglistDialogBox</code> Instanz.
//	 */
//	private class DeleteShoppinglistClickHandler implements ClickHandler {
//
//		@Override
//		public void onClick(ClickEvent event) {
//			if (shoppinglistToDisplay != null) {
//				DeleteShoppinglistDialogBox dsdb = new DeleteShoppinglistDialogBox();
//				dsdb.setGstvm(ShoppinglistHeader.this.gstvm);
//				dsdb.show();
//			} else {
//				Notification.show("Es wurde keine Shoppinglist ausgewaehlt.");			}
//		}
//
//	}

}
