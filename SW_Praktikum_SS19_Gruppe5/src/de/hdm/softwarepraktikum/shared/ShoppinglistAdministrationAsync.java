package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.dummydata.Group;
import de.hdm.softwarepraktikum.shared.dummydata.Shoppinglist;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ShoppinglistAdministrationAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void getGroupById(int id, AsyncCallback<Group> callback);

	void getShoppinglistsOf(Group value, AsyncCallback<ArrayList<Shoppinglist>> asyncCallback);

	void getAllGroups(AsyncCallback<ArrayList<Group>> asyncCallback);
}
