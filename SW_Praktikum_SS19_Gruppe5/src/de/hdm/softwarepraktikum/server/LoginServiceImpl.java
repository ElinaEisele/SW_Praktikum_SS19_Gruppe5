package de.hdm.softwarepraktikum.server;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.softwarepraktikum.shared.LoginInfo;
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
	public User login(String continuationURL) {
		UserService userService= UserServiceFactory.getUserService(); 
		com.google.appengine.api.users.User googleUser = userService.getCurrentUser(); 
		User user = new User();
		
		if (googleUser != null) { // Google User
			
			user.setLoggedIn(true);
			user.setGmailAddress(googleUser.getEmail());
			user.setName(googleUser.getNickname());
			user.setLogoutUrl(userService.createLogoutURL(continuationURL));
		} else {
			user.setLoggedIn(false);
			user.setLoginUrl(userService.createLoginURL(continuationURL));
		}
		return user;
			
//			User existingUser = UserMapper.userMapper().findByGMail(googleUser.getEmail());
//			if (existingUser != null) {
//				existingUser.setLoggedIn(true);
//				existingUser.setLogoutUrl(userService.createLogoutURL(requestUri));
//				
//				return existingUser;
//				
//			}
//			
//			user.setLoggedIn(true);
//			user.setLogoutUrl(userService.createLogoutURL(requestUri));
//			user.setGmailAddress(googleUser.getEmail());
//			UserMapper.userMapper().insert(user);
//		}
//		
//		user.setLoginUrl(userService.createLoginURL(requestUri));
//		
//		return user;
	}


}


