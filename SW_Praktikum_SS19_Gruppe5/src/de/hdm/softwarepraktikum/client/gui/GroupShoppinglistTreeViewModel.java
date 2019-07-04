package de.hdm.softwarepraktikum.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.hdm.softwarepraktikum.client.ClientsideSettings;
import de.hdm.softwarepraktikum.client.ShoppinglistEditorEntryLogin.CurrentUser;
import de.hdm.softwarepraktikum.shared.ShoppinglistAdministrationAsync;
import de.hdm.softwarepraktikum.shared.bo.BusinessObject;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.bo.Shoppinglist;
import de.hdm.softwarepraktikum.shared.bo.User;

/**
 * Diese Implementierung des TreeViewModels sorgt für die Verwaltung des
 * Gruppen- und Shoppinglistenbaums.
 * 
 * @author Elina Eisele, JonasWagenknecht
 * 
 */

public class GroupShoppinglistTreeViewModel implements TreeViewModel {

	private User u = CurrentUser.getUser();
	private ArrayList<Group> userGroups = new ArrayList<Group>();

	private GroupShowForm groupShowForm;
	private ShoppinglistShowForm shoppinglistShowForm;
	private NavigatorPanel navigatorPanel = new NavigatorPanel();

	private Group selectedGroup = null;
	private Shoppinglist selectedShoppinglist = null;
	private ListDataProvider<Group> groupDataProvider = new ListDataProvider<Group>();
	private ShoppinglistAdministrationAsync shoppinglistAdministration = null;

	/*
	 * In dieser Map werden die ListDataProviders fuer die Shoppinglisten der im
	 * Gruppen- und Shoppinglistbaum expandierten Gruppenknoten gemerkt. In einer
	 * Gruppe kann es mehrere Shoppinglisten geben.
	 */
	private Map<Group, ListDataProvider<Shoppinglist>> shoppinglistDataProviders = null;

	/**
	 * Nested Class, welche BusinessObjects auf eindeutige Zahlenobjekte abbildet,
	 * die als Schlüssel für Baumknoten dienen.
	 *
	 */
	private class BusinessObjectKeyProvider implements ProvidesKey<BusinessObject> {

		@Override
		public Integer getKey(BusinessObject bo) {
			if (bo == null) {
				return null;
			} else {
				if (bo instanceof Group) {
					return new Integer(bo.getId());
				} else {
					return new Integer(-bo.getId());
				}
			}
		}

	};

	private BusinessObjectKeyProvider boKeyProvider = null;
	private SingleSelectionModel<BusinessObject> selectionModel = null;

	/**
	 * Nested Class für die Reaktion auf Selektionsereignisse. Als Folge einer
	 * Baumknotenauswahl wird je nach Typ des Business-Objects die "selectedGroup"
	 * bzw. die "selectedShoppinglist" gesetzt.
	 *
	 */
	private class SelectionChangeEventHandler implements SelectionChangeEvent.Handler {

		@Override
		public void onSelectionChange(SelectionChangeEvent event) {
			BusinessObject selection = selectionModel.getSelectedObject();
			if (selection instanceof Group) {
				setSelectedGroup((Group) selection);
			} else if (selection instanceof Shoppinglist) {
				setSelectedShoppinglist((Shoppinglist) selection);

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
	
	

	public SingleSelectionModel<BusinessObject> getSelectionModel() {
		return selectionModel;
	}

	public void setSelectionModel(SingleSelectionModel<BusinessObject> selectionModel) {
		this.selectionModel = selectionModel;
	}

	public ArrayList<Group> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(ArrayList<Group> userGroups) {
		this.userGroups = userGroups;
	}

	void setGroupShowForm(GroupShowForm gsf) {
		groupShowForm = gsf;
	}

	void setShoppinglistShowForm(ShoppinglistShowForm ssf) {
		shoppinglistShowForm = ssf;
	}

	Group getSelectedGroup() {
		return selectedGroup;
	}

	void setSelectedGroup(Group g) {
		groupShowForm = new GroupShowForm();
		RootPanel.get("main").clear();
		selectedGroup = g;
		groupShowForm.setSelected(g);
		groupShowForm.setGstvm(this);
		navigatorPanel.setSelectedGroup(selectedGroup);

		selectedShoppinglist = null;
		shoppinglistShowForm.setSelected(null);
		RootPanel.get("main").add(groupShowForm);

	}

	Shoppinglist getSelectedShoppinglist() {
		return selectedShoppinglist;
	}

	void setSelectedShoppinglist(Shoppinglist s) {
		shoppinglistShowForm = new ShoppinglistShowForm();
		RootPanel.get("main").clear();
		selectedShoppinglist = s;
		shoppinglistShowForm.setSelected(s);
		shoppinglistShowForm.setGstvm(this);
		navigatorPanel.setSelectedShoppinglist(s);

		if (s != null) {

			shoppinglistAdministration.getGroupOf(selectedShoppinglist, new AsyncCallback<Group>() {

				@Override
				public void onFailure(Throwable caught) {
					Notification.show("Keine Gruppe gefunden.");
				}

				@Override
				public void onSuccess(Group result) {
					selectedGroup = result;
					shoppinglistShowForm.setSelectedGroup(selectedGroup);
					RootPanel.get("main").add(shoppinglistShowForm);
				}

			});

		}

	}

	/**
	 * Dient dem Hinzufügen eines neuen <code>Group</code> Objekts zum Tree
	 * 
	 * @param g <code>Group</code> welche hinzugefügt werden soll
	 */
	void addGroup(Group g) {
		List<Group> allGroups = groupDataProvider.getList();
		allGroups.add(g);
		this.getUserGroups().add(g);
		groupDataProvider.refresh();
		selectionModel.setSelected(g, true);
	}

	/**
	 * Diese Methode durchlaeuft eine Liste mit allen Gruppen. Hat eine Gruppe in
	 * der Liste die gleiche Id wie die upzudatende Gruppe, wird die Gruppe an
	 * dieser Stelle neu gesetzt.
	 */
	void updateGroup(Group group) {
		List<Group> groupList = groupDataProvider.getList();
		int i = 0;
		for (Group g : groupList) {
			if (g.getId() == group.getId()) {
				groupList.set(i, group);
				break;
			} else {
				i++;
			}
		}
		groupDataProvider.refresh();
	}

	void removeGroup(Group group) {
		groupDataProvider.getList().remove(group);
		shoppinglistDataProviders.remove(group);
	}

	/**
	 * Dient dem Hinzufügen eines <code>Shoppinglist</code> Objekts zu einer
	 * <code>Group</code> innerhalb der Baumstruktur.
	 * 
	 * @param shoppinglist welche der <code>Group</code> hinzugefügt werden soll
	 * @param group        zu welcher die <code>Shoppinglist</code> hinzugefügt
	 *                     werden soll
	 */
	void addShoppinglistToGroup(Shoppinglist shoppinglist, Group group) {
		/**
		 * Wurde noch kein Shoppinglist Provider für diese <code>Group</code> erstellt,
		 * so muss diese auch nicht bearbeitet werden.
		 */
		selectionModel.setSelected(shoppinglist, true);

		ListDataProvider<Shoppinglist> shoppinglistsProvider = shoppinglistDataProviders.get(group);

		if (!shoppinglistsProvider.getList().contains(shoppinglist)) {
			shoppinglistsProvider.getList().add(shoppinglist);
		}
		

	}

	void removeShoppinglistOfGroup(Shoppinglist shoppinglist, Group group) {
		if (!shoppinglistDataProviders.containsKey(group)) {
			return;
		}
		shoppinglistDataProviders.get(group).getList().remove(shoppinglist);
		selectionModel.setSelected(group, true);
	}

	/**
	 * Eine Shoppinglist in der Baumstruktur soll ersetzt werden durch eine
	 * Shoppinglist mit der selben Id, wenn sich die Eigenschaften einer
	 * Shoppinglist geändert haben und in der Baumstruktur noch ein veraltetes
	 * Shoppinglistobjekt vorhanden ist.
	 * 
	 * Dient dem Aktualisieren eines <code>Shoppinglist</code> Objekts im
	 * ListDataProvider und somit aus der Baumstruktur.
	 * 
	 */
	void updateShoppinglist(Shoppinglist s) {
		shoppinglistAdministration.getGroupById(s.getGroupId(), new UpdateShoppinglistCallback(s));
	}

	private class UpdateShoppinglistCallback implements AsyncCallback<Group> {
		Shoppinglist shoppinglist = null;

		UpdateShoppinglistCallback(Shoppinglist s) {
			shoppinglist = s;
		}

		@Override
		public void onFailure(Throwable t) {
			Notification.show(t.toString());
		}

		@Override
		public void onSuccess(Group group) {			
			
			List<Shoppinglist> shoppinglistList = shoppinglistDataProviders.get(group).getList();

			for (int i = 0; i < shoppinglistList.size(); i++) {
				if (shoppinglist.getId() == shoppinglistList.get(i).getId()) {
					shoppinglistList.set(i, shoppinglist);
					break;
				}
			}
		}

	}
	

	/**
	 * Zurueckgeben der NodeInfo, die die Kinder des jeweiligen Werts enthaelt.
	 */
	@Override
	public <T> NodeInfo<?> getNodeInfo(T value) {

		if (value.equals("Root")) {

			shoppinglistAdministration.getGroupsOf(u, new AsyncCallback<ArrayList<Group>>() {

				@Override
				public void onFailure(Throwable t) {
					Notification.show(t.toString());
				}

				@Override
				public void onSuccess(ArrayList<Group> groups) {
					for (Group g : groups) {
						GroupShoppinglistTreeViewModel.this.getUserGroups().add(g);
						groupDataProvider.getList().add(g);
					}
				}

			});

			return new DefaultNodeInfo<Group>(groupDataProvider, new GroupCell(), selectionModel, null);
		}

		if (value instanceof Group) {
			// Erzeugen eines ListDataProviders für Shoppinglist-Daten
			final ListDataProvider<Shoppinglist> shoppinglistsProvider = new ListDataProvider<Shoppinglist>();
			
			shoppinglistDataProviders.put((Group) value, shoppinglistsProvider);
						
			shoppinglistAdministration.getShoppinglistsOf((Group) value, new AsyncCallback<ArrayList<Shoppinglist>>() {

				@Override
				public void onFailure(Throwable caught) {
					Notification.show(caught.toString());
				}

				@Override
				public void onSuccess(ArrayList<Shoppinglist> shoppinglists) {
					for (Shoppinglist s : shoppinglists) {
						shoppinglistsProvider.getList().add(s);
					}
				}
			});
			// Zurueckgeben der Node Info, die die Daten in einer Zelle gruppiert.
			return new DefaultNodeInfo<Shoppinglist>(shoppinglistsProvider, new ShoppinglistCell(), selectionModel,
					null);
		}
		return null;
	}

	/**
	 * Es wird geprueft, ob der Wert einen Blattknoten repraesentiert. Ein
	 * Blattknoten kann nicht geoeffnet werden.
	 */
	@Override
	public boolean isLeaf(Object value) {
		return (value instanceof Shoppinglist);
	}

}
