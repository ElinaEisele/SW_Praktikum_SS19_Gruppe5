package de.hdm.softwarepraktikum.shared.bo;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Realisierung einer exemplarischen Einkaufliste. Eine Einkaufsliste beseteht
 * aus mehreren Eintrï¿½gen (Listitems).
 * 
 * @author TimBeutelspacher
 *
 */
public class Shoppinglist extends NamedBusinessObject implements IsSerializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * ArrayList mit allen Eintrï¿½gen (Listitem-Objekten)
	 */
	private ArrayList<Listitem> listitems;
	
	/*
	 * Fremdschlï¿½sselbeziehung zur Gruppe, in welcher sich die Einkaufsliste befindet
	 */
	private int groupId = 1;
	
	/*
	 * Default-Konstruktor
	 */
	public Shoppinglist() {
	}
	
	/**
	 * Konstruktor, in welchem der Name gesetzt wird.
	 */
	public Shoppinglist(String name) {
		super(name);
		this.setGroupId(1);
	}
	
	/**
	 * Konstruktor zum befüllen des CellTables
	 */
	public Shoppinglist(String name, ArrayList <Listitem>listitems) {
		super(name);
		this.setGroupId(1);
		this.listitems=listitems;		
	}
	
	/**
	 * Ausgeben einer ArrayList mit allen Eintrï¿½gen.
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
