package de.hdm.softwarepraktikum.shared.report;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Report, welcher alle Listitems einer Gruppe darstellt.
 * In der Klasse <code>AllListitemsOfGroupReport</code> werden keine weiteren Attribute und Methoden
 * formuliert, da dies bereits in den Superklassen geschrieben wurde.
 * Man benötigt diese Klassen dennoch um die Typen der Reports zu kennzeichnen.
 * 
 * @author TimBeutelspacher
 */
public class AllListitemsOfGroupReport extends SimpleReport{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Default Konstruktor
	 */
	public AllListitemsOfGroupReport() {
		
	}

	public String getCreationDateConvertToString() {
		return this.getCreationDate().toString();
	}

}
