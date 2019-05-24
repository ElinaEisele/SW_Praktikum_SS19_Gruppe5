package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;

/**
 * Ein Objekt der Klasse <code>CompositeReport</code> stellt einen zusammengesetzten Report dar.
 * Dieser Report besteht aus ein Menge von Teil-Reports (siehe Attribut <code>subReports</code>.
 * 
 * @author TimBeutelspacher
 */
public class CompositeReport extends Report {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Menge der Teil-Reports.
	 */
	private ArrayList<Report> subReports = new ArrayList<Report>();
	
	/**
	 * Hinzufuegen eines Teil-Reports
	 * @param report ist der Report, welcher dem CompositeReport hinzugefuegt wird.
	 */
	public void addSubReport(Report report) {
		this.subReports.add(report);
	}

	/**
	 * Loeschen eines Teil-Reports
	 * @param report ist das zu entfernende Report-Objekt
	 */
	public void removeSubReport(Report report) {
		this.subReports.remove(this.subReports.indexOf(report));
	}
	
	/**
	 * Ausgeben der Menge der SubReports
	 * @return int Laenge der ArrayList "SubReports"
	 */
	public int getSizeOfSubReports() {
		return this.subReports.size();
	}
	
	/**
	 * Auslesen des einzelnen Teil-Reports.
	 * @param index ist der Index, welcher ausgegeben werden soll.
	 * @return Report-Objekt, welches sich an dem bestimmten Index befindet.
	 */
	public Report getSubReportAt(int index) {
		return this.subReports.get(index);
	}
	
}
