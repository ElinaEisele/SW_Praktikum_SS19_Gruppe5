package de.hdm.softwarepraktikum.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Interface zur Realisierung des Logins.
 * 
 * @author ElinaEisele
 *
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService{
	 public LoginInfo login(String requestUri);

}
