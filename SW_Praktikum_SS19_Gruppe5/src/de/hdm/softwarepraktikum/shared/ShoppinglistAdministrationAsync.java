package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Das asnchrone Gegenstück zu <code>GreetingService</code>.Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation.
 */
public interface ShoppinglistAdministrationAsync {
	
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	
	void getAllGroups(AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void createUser(String mail, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void createGroup(String name, AsyncCallback<Group> callback) throws IllegalArgumentException;
	
	void createShoppinglist(Group group, String name, AsyncCallback<Shoppinglist> callback) throws IllegalArgumentException;
	
	void createListitem(Shoppinglist shoppinglist, )
	
	
	void getGroupById(int id, AsyncCallback<Group> callback);

	void getShoppinglistsOf(Group value, AsyncCallback<ArrayList<Shoppinglist>> asyncCallback);

	
}
