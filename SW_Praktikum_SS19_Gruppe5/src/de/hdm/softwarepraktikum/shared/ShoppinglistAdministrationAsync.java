package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.client.gui.RegistrationForm.SaveUserCallback;
import de.hdm.softwarepraktikum.shared.bo.User;
import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;
import de.hdm.softwarepraktikum.shared.dummydata.UserDD;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ShoppinglistAdministrationAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void getGroupById(int id, AsyncCallback<GroupDD> callback);

	void getShoppinglistsOf(GroupDD value, AsyncCallback<ArrayList<ShoppinglistDD>> asyncCallback);

	void getAllGroups(AsyncCallback<ArrayList<GroupDD>> asyncCallback);

	// Methode fuer Dummydata (fuer RegistrationForm in client.gui)
	void saveUser(User u, AsyncCallback<Void> callback) throws IllegalArgumentException;; 
}
