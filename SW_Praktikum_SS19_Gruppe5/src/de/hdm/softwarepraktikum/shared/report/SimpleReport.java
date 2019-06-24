package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

/**
 * <p>
 * Diese Klasse stellt einen Report da, welcher neben den Attributen der
 * Superklasse <code>Report</code> eine Tabelle mit Positionsdaten aufweist.
 * Die Tabelle besteht aus den beiden Hilfsklassen <code>Column</code> und <code>Row</code> besteht.
 * </p>
 * <p>
 * Die erwaehnten Positionsdaten sind Positionen, wie man sie aus Bestellungen kennt.
 * Jede Bestellposition besitzt Attribute, welche in Objekten der Klasse <code>Column</code> dargestellt werden.
 * </p>
 * @author TimBeutelspacher
 */
public class SimpleReport extends Report{

	private static final long serialVersionUID = 1L;
	
	/**
     * Default Konstruktor.
     */
    public SimpleReport() {
        // TODO implement here
    }

	/**
	 * Default Konnstruktor 
	 */
	public SimpleReport() {
		
	}
	
	/**
	 * Tabelle mit den Positionsdaten als <code>ArrayList</code> zwischengespeichert.
	 */
	private ArrayList<Row> table = new ArrayList<Row>();
	
	/**
	   * Hinzufuegen einer Zeile in der Tabelle.
	   * 
	   * @param r die hinzuzufügende Zeile
	   */
	  public void addRow(Row r) {
	    this.table.add(r);
	  }

	  /**
	   * Entfernen einer Zeile aus der Tabelle.
	   * 
	   * @param r die zu entfernende Zeile
	   */
	  public void removeRow(Row r) {
	    this.table.remove(r);
	  }

	  /**
	   * Auslesen saemtlicher Positionsdaten der Tabelle.
	   * 
	   * @return die Tabelle der Positionsdaten
	   */
	  public ArrayList<Row> getRows() {
	    return this.table;
	  }
}
