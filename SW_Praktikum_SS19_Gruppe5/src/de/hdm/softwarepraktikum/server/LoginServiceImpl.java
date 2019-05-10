package de.hdm.softwarepraktikum.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import com.google.appengine.api.users.User; 
import com.google.appengine.api.users.UserService; 
import com.google.appengine.api.users.UserServiceFactory; 

import de.hdm.softwarepraktikum.shared.LoginService;
import de.hdm.softwarepraktikum.shared.bo.User;


public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{
	
	 private static final long serialVersionUID = 1L;

	/**
	 * Login-Methode pruef, ob der User dem System bekannt ist. Wenn dies der Fall ist,
	 * werden die Attribute fuer dieses Objekt gesetzte. Ansonsten wird ein neuer Datensatz
	 * in die Datenbank geschrieben und der User ist eingeloggt.
	 */
	@Override
	public User login(String requestUri) {
		UserServiceuserService= UserServiceFactory.getUserService(); 
		com.google.appengine.api.users.User googleUser= userService.getCurrentUser(); 
		LoginInfologinInfo= new User();
		
		if (googleUser != null) {
			// setter
		} else {
			// setter
		}
		
		return user;

	}
	
	

}
