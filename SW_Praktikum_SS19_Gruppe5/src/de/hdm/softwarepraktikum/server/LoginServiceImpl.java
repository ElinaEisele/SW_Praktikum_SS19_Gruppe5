package de.hdm.softwarepraktikum.server;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import de.hdm.softwarepraktikum.server.db.UserMapper;
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
		UserService userService= UserServiceFactory.getUserService(); 
		com.google.appengine.api.users.User googleUser= userService.getCurrentUser(); 
		User user = new User();
		
		if (googleUser != null) {
			
			User existingUser = UserMapper.userMapper().findByGmail(googleUser.getEmail);
			
			if (existingUser != null) {
				existingUser.setLoggedIn(true);
				existingUser.setLogoutUrl(userService.createLogoutURL(requestUri));
				
				return existingUser;
				
			}
			
			user.setLoggedIn(true);
			user.setLogoutUrl(userService.getLogoutURL(requestUri));
			user.setGmailAdress(googleUser.getEmail);
			UserMapper.userMapper().insert(user);
		}
		
		user.setLoginUrl(userService.createLoginURL(requestUri));
		
		return user;
			

	}

}
