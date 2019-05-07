package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung einer exemplarischen Einkaufliste. Eine Einkaufsliste beseteht
 * aus mehreren Eintr�gen (Listitems).
 * 
 * @author TimBeutelspacher
 *
 */
public class Shoppinglist extends NamedBusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * ArrayList mit allen Eintr�gen (Listitem-Objekten)
	 */
	private ArrayList<Listitem> listitems;
	
	/*
	 * Fremdschl�sselbeziehung zur Gruppe, in welcher sich die Einkaufsliste befindet
	 */
	private int groupId;
	
	/**
	 * Konstruktor, in welchem der Name gesetzt wird. Au�erdem werden
	 * der Shoppinglist alle Standardeintr�fe der Gruppe hinzugef�gt.
	 */
	public Shoppinglist(String name) {
		this.setName(name);
		this.setCreationDate(new Date());
		
		/*
		 * Die hier gesetzte ID fungiert als Platzhalter. In der sp�ter aufgerufenen 
		 * insert-Methode wird ein Wert geliefert, welcher mit der Datenbank konsisten ist.
		 */
		this.setId(1);
		this.setGroupId(1);
	}
	
	/**
	 * Ausgeben einer ArrayList mit allen Eintr�gen.
	 */
	public ArrayList<Listitem> getListitems() {
		return listitems;
	}

	/*
	 * Ausgabe der GruppenID, der Gruppe, in welcher sich die Einkaufsliste befindet.
	 */
	public int getGroupId() {
		return groupId;
	}

	/*
	 * Setzen der GruppenID.
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	

	
	
}
