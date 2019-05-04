package de.hdm.softwarepraktikum.shared;


import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.softwarepraktikum.shared.dummydata.Group;
import de.hdm.softwarepraktikum.shared.dummydata.Shoppinglist;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("administration")
public interface ShoppinglistAdministration extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	
	Group getGroupById(int id);

	ArrayList<Shoppinglist> getShoppinglistsOf(Group value);
	
	ArrayList<Group> getAllGroups();

}
