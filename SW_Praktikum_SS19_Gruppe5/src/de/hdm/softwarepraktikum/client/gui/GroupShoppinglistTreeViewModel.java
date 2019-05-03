package de.hdm.softwarepraktikum.client.gui;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.dummydata.BusinessObject;
import de.hdm.softwarepraktikum.shared.dummydata.Group;
import de.hdm.softwarepraktikum.shared.dummydata.Shoppinglist;

/**
 * Diese Implementierung des TreeViewModels sorgt für die Verwaltung des Gruppen- 
 * und Shoppinglistenbaums.
 * 
 * @author ElinaEisele
 * 
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel{
	
	private GroupForm groupForm;
	private ShoppinglistForm shoppinglistForm;
	
	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglist = null;
	
	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;
	private ListDataProvider<Group> groupDataProvider = null;
	
	/*
	 * In dieser Map werden die ListDataProviders fuer die Shoppinglisten
	 * der im Gruppen- und Shoppinglistbaum expandierten Gruppenknoten gemerkt.
	 */
	private Map<Group, ListDataProvider<Shoppinglist>> shoppinglistDataProviders = null;
	
	
	/**
	 * Nested Class, welche  BusinessObjects auf eindeutige Zahlenobjekte abbildet, 
	 * die als Schlüssel für Baumknoten dienen.
	 *
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject>{

		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			} else {
				return bo.getId();
			}
		}
		
	};
	
	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;
	
	/**
	 * Nested Class für die Reaktion auf Selektionereignisse. Als Folge
	 * einer Baumknotenauswahl wird je nach Typ des Business-Objects
	 * die "selectedGroup" bzw. die "selectedShoppinglist" gesetzt.
	 *
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler{

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Group) {
				setSelectedGroup((Group) selection);
			} else if (selection instanceof Shoppinglist){
				setSelectedShoppinglsit((Shoppinglist) selection);
			}
		}
		
	}
	
	public GroupShoppinglistTreeViewModel() {
		shoppinglistAdministration = ClientsideSettings.getShoppinglistAdministration();
		boKeyProvider = new BusinessObjectKeyProvider();
		selectionModel = new SingleSelectionModel<BusinessObject>(boKeyProvider);
		selectionModel.addSelectionChangeHandler(new SelectionChangeEventHandler());
		shoppinglistDataProviders = new HashMap<Group, ListDataProvider<Shoppinglist>>();		
	}
	
	void setGroupForm(GroupForm gf) {
		groupForm = gf;
	}
	
	void setShoppinglistForm(ShoppinglistForm sf) {
		shoppinglistForm = sf;
	}
	
	Group getSelectedGroup() {
		return selectedGroup;
	}
	
	void setSelectedGroup(Group g) {
		selectedGroup = g;
		groupForm.setSelected(g);
		selectedShoppinglist = null;
		shoppinglistForm.setSelected(null);
	}
	
	Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}
	
	/**
	 * Wenn eine Shoppinglist ausgewählt wird, wird auch die 
	 * ausgewaehlte Gruppe angepasst.
	 */
	void setSelectedShoppinglsit(Shoppinglist s) {
		selectedShoppinglist = s;
		shoppinglistForm.setSelected(s);
	}


	
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isLeaf(Object value) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
