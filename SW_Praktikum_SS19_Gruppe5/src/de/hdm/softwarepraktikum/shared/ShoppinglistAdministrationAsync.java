
package de.hdm.softwarepraktikum.shared;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Listitem;
import de.hdm.softwarepraktikum.shared.bo.Product;
import de.hdm.softwarepraktikum.shared.bo.Retailer;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.Unit;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Das asnchrone Gegenstueck zu <code>GreetingService</code>.Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation.
 */
public interface ShoppinglistAdministrationAsync {
	
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	
	void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getAllGroups(AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void createUser(String name, String mail, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void createGroupFor(User user, String name, AsyncCallback<Group> callback) throws IllegalArgumentException;
	
	void createShoppinglistFor(Group group, String name, AsyncCallback<Shoppinglist> callback) throws IllegalArgumentException;
	
	void createProductFor(Listitem listitem, String name, AsyncCallback<Product> callback) throws IllegalArgumentException;
	
	void getGroupById(int id, AsyncCallback<Group> callback) throws IllegalArgumentException;

	void getShoppinglistsOf(Group value, AsyncCallback<ArrayList<Shoppinglist>> asyncCallback) throws IllegalArgumentException;

	void getUsersOf(Group group, AsyncCallback<ArrayList<User>> callback) throws IllegalArgumentException;
	
	void getUsersOf(int groupId, AsyncCallback<ArrayList<User>> callback ) throws IllegalArgumentException;
	
	void getUserById(int userId, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void getUsersByName(String name, AsyncCallback<ArrayList<User>>callback) throws IllegalArgumentException;
  
	void createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit, Retailer retailer, AsyncCallback<Listitem> callback) throws IllegalArgumentException;
	
	void createRetailer(String name, AsyncCallback<Retailer> callback) throws IllegalArgumentException; 
	
	void save(User user, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
// statt User
	void save(LoginInfo loginInfo, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Shoppinglist shoppinglist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void save(Retailer retailer, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(User user, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(Shoppinglist shoppinglist, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void delete(Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getGroupsOf(User user, AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void getGroupsOf(int userId, AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void getGroupsOf(String username, AsyncCallback<ArrayList<Group>> callback) throws IllegalArgumentException;
	
	void getUserByMail(String mail, AsyncCallback<User> callback) throws IllegalArgumentException;
	
	void getShoppinglistsByName(String name, AsyncCallback<ArrayList<Shoppinglist>> callback) throws IllegalArgumentException;
	
	void getShoppinglistById(int shoppinglistId, AsyncCallback<Shoppinglist> callback) throws IllegalArgumentException;
	
	void getListitemsByNameOf(Shoppinglist shoppinglist, String productname, AsyncCallback<ArrayList<Listitem>>callback) throws IllegalArgumentException;

	void getAllListitemsOf(Shoppinglist shoppinglist, AsyncCallback<ArrayList<Listitem>> callback) throws IllegalArgumentException;
	
	void getAllRetailers(AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getRetailersByName(String name, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getRetailerById(int retailerId, AsyncCallback<Retailer> callback) throws IllegalArgumentException;
	
	void getRetailersOf(Shoppinglist shoppinglist, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void getRetailersOf(Shoppinglist shoppinglist, User user, AsyncCallback<ArrayList<Retailer>> callback) throws IllegalArgumentException;
	
	void assignRetailer(Retailer retailer, Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void assignUser(User user, Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void setProduct (Product product, Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;

	void setStandardListitem(Listitem listitem, Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getProductOf(Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getStandardListitemsOf(Group group, AsyncCallback<ArrayList<Listitem>> callback) throws IllegalArgumentException;
	
	void filterShoppinglistsByUsername(Shoppinglist shoppinglist, User user, AsyncCallback<ArrayList<Listitem>> callback)throws IllegalArgumentException;
	
	void filterShoppinglistsByRetailer(Shoppinglist shoppinglist, Retailer retailer, AsyncCallback<ArrayList<Listitem>>callback) throws IllegalArgumentException;
	
	void addUserToGroup(User user, Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void removeUserFromGroup(User user, Group group, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void setProductName(String name, Product product, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void setAmount(float amount, Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void setUnit(Unit unit, Listitem listitem, AsyncCallback<Void> callback) throws IllegalArgumentException;
	
	void getUnit(Listitem listitem, AsyncCallback<Unit> callback) throws IllegalArgumentException;
	
	void getAmount(Listitem listitem, AsyncCallback<Float> callback) throws IllegalArgumentException;
	
	void createListitem(Shoppinglist shoppinglist, String productname, float amount, Unit unit, AsyncCallback<Listitem> callback) throws IllegalArgumentException;
}
