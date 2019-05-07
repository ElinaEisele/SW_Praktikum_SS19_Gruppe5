package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.dummydata.GroupDD;
import de.hdm.softwarepraktikum.shared.dummydata.ShoppinglistDD;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ShoppinglistAdministrationAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;

	void getGroupById(int id, AsyncCallback<GroupDD> callback);

	void getShoppinglistsOf(GroupDD value, AsyncCallback<ArrayList<ShoppinglistDD>> asyncCallback);

	void getAllGroups(AsyncCallback<ArrayList<GroupDD>> asyncCallback);
}
