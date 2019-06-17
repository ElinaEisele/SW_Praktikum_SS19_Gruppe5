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
import de.hdm.softwarepraktikum.shared.LoginInfo;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Diese Klasse stellt ein Formular zur Registrierung des Nutzers dar.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class RegistrationForm extends VerticalPanel{
	
	private User user;
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
	
	private HorizontalPanel registrationFormHeaderPanel = new HorizontalPanel();
	private HorizontalPanel buttonsPanel = new HorizontalPanel();
	
	private Label welcomeLabel = new Label("Herzlich Willkommen zum Einkaufserlebnis mit Maul Tasche!");
	private Label registrationInfoLabel = new Label("Bitte registrieren Sie sich hier:");
	
	private Label firstNameLabel = new Label("Vorname:");
	private Label lastNameLabel = new Label("Nachname:");
	
	private DynamicTextBox firstNameTextBox = new DynamicTextBox();
	private DynamicTextBox lastNameTextBox = new DynamicTextBox();
	
	private Grid registrationGrid = new Grid(2, 2);
	
	private Button registerButton = new Button("Registrieren");
	private Button cancelButton = new Button("Abbrechen");
	
	private Anchor destinationUrl = new Anchor();
	

// statt LoginInfo eigentlich User	
	public RegistrationForm(Anchor destinationUrl, User u) {
		this.destinationUrl = destinationUrl;
		this.user = u;
		
		registerButton.addClickHandler(new RegistrationClickHandler());
		cancelButton.addClickHandler(new CancelClickHandler());

		buttonsPanel.add(registerButton);
		buttonsPanel.add(cancelButton);
		
	}
	
	private class RegistrationClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			String userName = lastNameTextBox.getText()+" "+firstNameTextBox.getText();
			user.setName(userName);
			shoppinglistAdministration.createUser(userName, user.getGmailAddress(), new SaveUserCallback());
			Window.alert(user.getGmailAddress());
		}
		
	}
	
	private class CancelClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("main").clear();
			Window.open(user.getLogoutUrl(), "_self", "");
		}
		
	}
	
	public void onLoad() {
		
		this.setWidth("100%");
		registrationFormHeaderPanel.setHeight("8vh");
		registrationFormHeaderPanel.setWidth("100%");
		cancelButton.setPixelSize(130, 40);
		registerButton.setPixelSize(130, 40);
		
		registrationFormHeaderPanel.add(welcomeLabel);
		registrationFormHeaderPanel.setCellVerticalAlignment(welcomeLabel, ALIGN_BOTTOM);
		
//		buttons.setSpacing(20);
		
		firstNameTextBox.setMaxLength(30);
		lastNameTextBox.setMaxLength(30);
		
		firstNameTextBox.setLabelText("Vorname");
		lastNameTextBox.setLabelText("Nachname");
		
//		registrationGrid.setCellSpacing(10);
		
		registrationGrid.setWidget(0, 0, firstNameLabel);
		registrationGrid.setWidget(0, 1, firstNameTextBox);
		registrationGrid.setWidget(1, 0, lastNameLabel);
		registrationGrid.setWidget(1, 1, lastNameTextBox);
		
		this.add(registrationFormHeaderPanel);
		this.add(welcomeLabel);
		this.add(registrationGrid);
		this.add(buttonsPanel);
		
	}
	
	private class DynamicTextBox extends TextBox{
		
		String labelText;
		

		public String getLabelText() {
			return labelText;
		}

		public void setLabelText(String text) {
			this.labelText = text;
		}
		
	}
	
	private class SaveUserCallback implements AsyncCallback<User>{
		
		@Override
		public void onFailure(Throwable caught) {
			Notification.show(caught.toString());
		}

		@Override
		public void onSuccess(User u) {
		Window.open(destinationUrl.getHref(), "_self", "");

		}			
	}


}
