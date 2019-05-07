package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung einer exemplarischen Einkaufliste. Eine Einkaufsliste beseteht
 * aus mehreren Einträgen (Listitems).
 * 
 * @author TimBeutelspacher
 *
 */
public class Shoppinglist extends NamedBusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * ArrayList mit allen Einträgen (Listitem-Objekten)
	 */
	private ArrayList<Listitem> listitems;
	
	/*
	 * Fremdschlüsselbeziehung zur Gruppe, in welcher sich die Einkaufsliste befindet
	 */
	private int groupId = 1;
	
	/**
	 * Konstruktor, in welchem der Name gesetzt wird.
	 */
	public Shoppinglist(String name) {
		super(name);
		this.setGroupId(1);
	}
	
	/**
	 * Ausgeben einer ArrayList mit allen Einträgen.
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
