package de.hdm.softwarepraktikum.shared.bo;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung der Klasse User, welche einen Nutzer des Programms darestellt. Dieser kann Mitglied von
 * verschiedenen Gruppen sein.
 * 
 * @author FelixRapp, TimBeutelspacher
 */

public class User extends NamedBusinessObject implements IsSerializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Google Mail Adresse des Nutzers.
	 */
	private String gmailAdress;
	
	/**
	 * Login Status
	 */
	private boolean isLoggedIn = false;
	
	/**
	 * Login URL
	 */
	private String loginUrl;
	
	/**
	 * Logout URL
	 */
	private String logoutUrl;
	
	/*
	 * Default-Konstruktor, welcher den Konstruktor in NamedBusinessObject aufruft
	 */
	public User() {
		super();
	}
	
	/**
	 * Konstruktor zum Setzen des Namens.
	 */
	public User(String name) {
		super(name);
	}
	
	/**
	 * Auslesen der Gmail Adresse des Nutzers.
	 */
	public String getGmailAdress() {
		return gmailAdress;
	}
	
	
	/**
	 * Setzen der Google Mail Adresse des Nutzers.
	 */
	public void setGmailAdress(String gmailAdress) {
		this.gmailAdress = gmailAdress;
	}
	
	/**
	 * Auslesen des LogIn Status.
	 */
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	/**
	 * Setzen des LogIn Status.
	 */
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	/**
	 * Auslesen der Login URL.
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Setzen der Login URL.
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Auslesen der Logout URL.
	 */
	public String getLogoutUrl() {
		return logoutUrl;
	}

	/**
	 * Setzen der Logout URL.
	 */
	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
}
