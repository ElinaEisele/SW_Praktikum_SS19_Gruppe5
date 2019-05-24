package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Diese Klasse stellt in der Tabelle eines <code>SimpleReport</code> -Objektes eine Zeile dar.
 * Die Klasse <code>Row</code> implemtentiert das Interface <code>Serializable</code>, da diese vom Server zum 
 * Client gesendet werden soll.
 * Eine Zeile besteht aus mehreren Spalten.
 *  
 * @author TimBeutelspacher
 */
public class Row implements Serializable{


	private static final long serialVersionUID = 1L;
	
	/**
	 * Zwischenspeicher fuer die Spalten der Zeile.
	 */
	private ArrayList<Column> columns = new ArrayList<Column>();
	
	/**
	 * Hinzufuegen einer Spalte.
	 * @param column ist die Spalte, welche hinzugefuegt werden soll.
	 */
	public void addColumn(Column column){
		this.columns.add(column);
	}

	/**
	 * Entfernen einer Spalte.
	 * @param column ist die Spalte, welche entfernt werden soll.
	 */
	public void removeColumn(Column column) {
		this.columns.remove(this.columns.indexOf(column));
	}
	
	/**
	 * Auslesen saemtlicher Spalten.
	 * @return ArrayList mit Column-Objekten.
	 */
	public ArrayList<Column> getColumns(){
		return this.columns;
	}
	
	/**
	 * Ausgeben der Anzahl der saemtlichen Spalten.
	 * @return int Anzahl der Spalten.
	 */
	public int getSizeOfColumns() {
		return this.columns.size();
	}
	
	/**
	 * Auslesen eines einzelnen Spalten-Objekts.
	 * @param i Index der gesuchten Spalte.
	 * @return Column-Obejekt an dem gesuchten Index.
	 */
	public Column getColumnAt(int i) {
		return this.columns.get(i);
	}
}
