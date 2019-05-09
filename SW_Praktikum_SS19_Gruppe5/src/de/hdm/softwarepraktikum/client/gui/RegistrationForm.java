package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.dummydata.UserDD;

/**
 * Diese Klasse stellt ein Formular zur Registrierung des Nutzers dar.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class RegistrationForm extends VerticalPanel{
	
	private User user;
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private HorizontalPanel registrationFormHeader = new HorizontalPanel();
	private HorizontalPanel buttons = new HorizontalPanel();
	
	private Label welcomeLabel = new Label("Herzlich Willkommen zum Einkaufserlebnis mit Maul Tasche!");
	private Label registrationInfoLabel = new Label("Bitte registrieren Sie sich hier:");
	
	private Label firstNameLabel = new Label("Vorname:");
	private Label lastNameLabel = new Label("Nachname:");
	
	private DynamicTextBox firstNameTextBox = new DynamicTextBox();
	private DynamicTextBox lastNameTextBox = new DynamicTextBox();
	
	private Grid registrationGrid = new Grid(2, 2);
	
	private Button registerButton = new Button("Registrieren");
	private Button cancelButton = new Button("Abbrechen");
	
	private Anchor startFormUrl = new Anchor();
	
	public RegistrationForm(Anchor startFormUrl, User u) {
		this.startFormUrl = startFormUrl;
		this.user = u;
		
		registerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String userName= lastNameTextBox.getText() +" "+ firstNameTextBox.getText();
				u.setName(userName);
				shoppinglistAdministration.saveUser(u, new SaveUserCallback());
			}
			
		});
		
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("Container").clear();
//				Window.open(u.getLogoutUrl(), "_self", ""); 
			}
		});
		
		buttons.add(registerButton);
		buttons.add(cancelButton);
		
	}
	
	public void onLoad() {
		
		this.setWidth("100%");
		registrationFormHeader.setHeight("8vh");
		registrationFormHeader.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		registerButton.setPixelSize(130, 40);
		
		registrationFormHeader.add(welcomeLabel);
		registrationFormHeader.setCellVerticalAlignment(welcomeLabel, ALIGN_BOTTOM);
		
//		buttons.setSpacing(20);
		
		firstNameTextBox.setMaxLength(30);
		lastNameTextBox.setMaxLength(30);
		
		firstNameTextBox.setText("Vorname");
		lastNameTextBox.setText("Nachname");
		
//		registrationGrid.setCellSpacing(10);
		
		registrationGrid.setWidget(0, 0, firstNameLabel);
		registrationGrid.setWidget(0, 1, firstNameTextBox);
		registrationGrid.setWidget(1, 0, lastNameLabel);
		registrationGrid.setWidget(1, 1, lastNameTextBox);
		
		this.add(registrationFormHeader);
		this.add(welcomeLabel);
		this.add(registrationGrid);
		this.add(buttons);
		
	}
	
	private class DynamicTextBox extends TextBox{
		
		boolean seavable = true;
		String text;
		
		public boolean isSeavable() {
			return seavable;
		}
		
		public void setSeavable(boolean seavable) {
			this.seavable = seavable;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
		
		
	}
	
	private class SaveUserCallback implements AsyncCallback<Void>{
		
		@Override
		public void onFailure(Throwable caught) {
//			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(Void u) {
		Window.open(startFormUrl.getHref(), "_self", "");

	}			
}

}
