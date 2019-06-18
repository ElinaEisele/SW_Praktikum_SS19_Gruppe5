	
package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.ListitemUnit;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Das asynchrone Gegenstueck zu <code>ShoppinglistAdministration</code>.Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation.
 * 
 * @author TimBeutelspacher, FelixRapp
 */
public interface ShoppinglistAdministrationAsync {
	
	void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getAllGroups(AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void createUser(String name, String mail, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void createGroupFor(User user, String name, AsyncCallback<Group> callback) throws IllegalArgumentException;
	
	void createShoppinglistFor(Group group, String name, AsyncCallback<Shoppinglist> callback) throws IllegalArgumentException;
	
	void createProduct(String name, AsyncCallback<Product> callback) throws IllegalArgumentException;
	
	void getGroupById(int id, AsyncCallback<Group> callback) throws IllegalArgumentException;

	void getShoppinglistsOf(Group value, AsyncCallback<ArrayList<Shoppinglist>> asyncCallback) throws IllegalArgumentException;

	void getUsersOf(Group group, AsyncCallback<ArrayList<User>> callback) throws IllegalArgumentException;
	
	void getUsersOf(int groupId, AsyncCallback<ArrayList<User>> callback ) throws IllegalArgumentException;
	
	void getUserById(int userId, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void getUsersByName(String name, AsyncCallback<ArrayList<User>>callback) throws IllegalArgumentException;
  
	void createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit, Retailer retailer, AsyncCallback<Listitem> callback) throws IllegalArgumentException;
	
	void createRetailer(String name, AsyncCallback<Retailer> callback) throws IllegalArgumentException; 
	
	void save(User user, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Shoppinglist shoppinglist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Product product, AsyncCallback<Void> callback) throws IllegalArgumentException;

	void delete(User user, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(Shoppinglist shoppinglist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getGroupsOf(User user, AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void getGroupsOf(int userId, AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void getUserByMail(String mail, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void getShoppinglistsByName(String name, AsyncCallback<ArrayList<Shoppinglist>> callback) throws IllegalArgumentException;
	
	void getShoppinglistById(int shoppinglistId, AsyncCallback<Shoppinglist> callback) throws IllegalArgumentException;
	
	void getListitemsByNameOf(Shoppinglist shoppinglist, String productname, AsyncCallback<ArrayList<Listitem>>callback) throws IllegalArgumentException;

	void getListitemsOf(Shoppinglist shoppinglist, AsyncCallback<ArrayList<Listitem>> callback);
	
	void getListitemData(Shoppinglist shoppinglist, AsyncCallback<Map<Listitem, ArrayList<String>>> callback) throws IllegalArgumentException;

	void getAllRetailers(AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getRetailersByName(String name, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getRetailerById(int retailerId, AsyncCallback<Retailer> callback) throws IllegalArgumentException;
	
	void getRetailersOf(Shoppinglist shoppinglist, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getRetailersOf(Shoppinglist shoppinglist, User user, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getUserRetailerAllocation(Shoppinglist shoppinglist, AsyncCallback<Map<String, String>> callback) throws IllegalArgumentException;
	
	void getAssigndUserOf(Shoppinglist shoppinglist, Retailer retailer, AsyncCallback<User> callback);
	
	void getAssigndRetailersOf(Shoppinglist shoppinglist, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void assignRetailer(Retailer retailer, Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void assignUser(User user, Retailer retailer, Shoppinglist shoppinglist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void setStandardListitem(Listitem listitem, Group group, boolean value, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getProductOf(Listitem listitem, AsyncCallback<Product> callback) throws IllegalArgumentException;
	
	void getStandardListitemsOf(Group group, AsyncCallback<ArrayList<Listitem>> callback) throws IllegalArgumentException;
	
	void filterShoppinglistsByUser(Shoppinglist shoppinglist, User user, AsyncCallback<ArrayList<Listitem>> callback)throws IllegalArgumentException;
	
	void filterShoppinglistsByRetailer(Shoppinglist shoppinglist, Retailer retailer, AsyncCallback<ArrayList<Listitem>>callback) throws IllegalArgumentException;
	
	void addUserToGroup(User user, Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void removeUserFromGroup(User user, Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getListitemUnitOf(Listitem listitem, AsyncCallback<ListitemUnit> callback) throws IllegalArgumentException;
	
	void getAmountOf(Listitem listitem, AsyncCallback<Float> callback) throws IllegalArgumentException;
	
	void createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit, AsyncCallback<Listitem> callback) throws IllegalArgumentException;

	void createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit, boolean standard, AsyncCallback<Listitem> callback) throws IllegalArgumentException;
	
	void createListitem(Group group, Shoppinglist shoppinglist, String productname, float amount, ListitemUnit listitemUnit, Retailer retailer, boolean standard, AsyncCallback<Listitem> callback) throws IllegalArgumentException;
	
	void refreshData(ArrayList<Group> groups, User u, AsyncCallback<Boolean> callback) throws IllegalArgumentException;

	void getProductnameOf(Listitem listitem, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void getAllListitemUnits(AsyncCallback<ArrayList<ListitemUnit>> callback) throws IllegalArgumentException;
	
	void getListitemUnitById(int id, AsyncCallback<ListitemUnit> callback) throws IllegalArgumentException;
	
	void getListitemMapBy(String searchString, Shoppinglist shoppinglist, AsyncCallback<Map<Shoppinglist, ArrayList<Listitem>>> callback) throws IllegalArgumentException;
	
	void getListitemsNameMapBy(Shoppinglist shoppinglist, AsyncCallback<Map<Listitem, String>> callback) throws IllegalArgumentException;
	
	void getRetailerOf(Listitem listitem, AsyncCallback<Retailer> callback) throws IllegalArgumentException;

	void getGroupOf(Shoppinglist shoppinglist, AsyncCallback<Group> callback) throws IllegalArgumentException;
	
	void changeNameOf(Group group, String name, AsyncCallback<Group> callback) throws IllegalArgumentException;
	
	void archiveListitems(ArrayList<Listitem> listitems, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void deleteAssignment(Retailer retailer, Shoppinglist shoppinglist, AsyncCallback<Void> callback) throws IllegalArgumentException;
}
