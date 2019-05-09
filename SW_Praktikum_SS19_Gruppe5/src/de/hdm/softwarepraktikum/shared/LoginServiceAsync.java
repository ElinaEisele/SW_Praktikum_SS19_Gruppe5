package de.hdm.softwarepraktikum.shared;


import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.User;


public interface LoginServiceAsync{
	 void login(String requestUri, AsyncCallback<User> asyncCallback);
}
