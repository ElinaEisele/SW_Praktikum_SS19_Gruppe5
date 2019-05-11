package de.hdm.softwarepraktikum.server;


import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


import de.hdm.softwarepraktikum.shared.LoginInfo;
import de.hdm.softwarepraktikum.shared.LoginService;


public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{
	
	 private static final long serialVersionUID = 1L;
	 private UserService userService = null;
	 User user = null;

	/**
	 * Login-Methode pruef, ob der User dem System bekannt ist. Wenn dies der Fall ist,
	 * werden die Attribute fuer dieses Objekt gesetzte. Ansonsten wird ein neuer Datensatz
	 * in die Datenbank geschrieben und der User ist eingeloggt.
	 */
	@Override
	public LoginInfo login(String continuationURL) {
		UserService userService= UserServiceFactory.getUserService(); 
		User user = userService.getCurrentUser(); 
		LoginInfo loginInfo = new LoginInfo();
		
		if (user != null) {
			
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(continuationURL));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(continuationURL));
		}
		return loginInfo;
			
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


