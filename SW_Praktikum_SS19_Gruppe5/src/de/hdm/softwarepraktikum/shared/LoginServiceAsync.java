package de.hdm.softwarepraktikum.shared;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Asynchrones Interface zur Realisierung des Logins.
 * 
 * @author ElinaEisele
 *
 */
public interface LoginServiceAsync{
	 void login(String requestUri, AsyncCallback<User> callback);
}
