package de.hdm.SWPraktikum.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ShoppinglistAdministrationAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
}
