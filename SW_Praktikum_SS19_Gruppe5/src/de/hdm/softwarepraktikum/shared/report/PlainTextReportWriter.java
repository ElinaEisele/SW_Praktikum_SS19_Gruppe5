package de.hdm.softwarepraktikum.shared.report;

import java.util.Vector;

import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.AllShoppinglistsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.ReportWriter;
import de.hdm.softwarepraktikum.shared.report.Row;

/**
 * Der ReportWriter formatiert den Report mittels Plain Text. Das Ergebnis wird 
 * im richtigen Format in der Variable reportText abgelegt und kann nachdem der Report 
 * erzeugt wurde mit der Methode getReportText() ausgelesen werden.
 * 
 * 
 * @author FelixRapp
 */
public class PlainTextReportWriter extends ReportWriter {

	  /**
	   * Textfeld zum speichern des Ergebnisses
	   */
  private String reportText = "";

  /**
   * Zuruecksetzen der Variable reportText.
   */
  public void resetReportText() {
    this.reportText = "";
  }

  /**
   * Text des Headers erstellen.
   * 
   * @return Text
   */
  public String getHeader() {
    // Hier sollte der Header erzeugt werden.
    return "";
  }

  /**
   * Text des Trailers erstellen
   * 
   * @return Text
   */
  public String getTrailer() {
    // Hier sollte der Trailer erzeugt werden.
    return "";
  }

  /**
   * Erstellen des uebergebenen Reports und speichern im richtigen Format.
   * 
   * @param r der zu erstellende Report
   */
public void process(AllListitemsOfGroupReport r) {

    // Zuerst wird das Ergebniss des vorherigen Durchlaufs geloescht.
    this.resetReportText();

    //In diesem StringBuffer werden die Ergebnisse der Methode abgelegt.
    StringBuffer result = new StringBuffer();

    /*
     * Die einzelnen Bestandteile des Reports werden nacheinander ausgelesen
     * und dem StringBuffer in HTML-Form angehängt.
     */
    result.append("*** " + r.getTitle() + " ***\n\n");
    result.append(r.getHeaderData() + "\n");
    result.append("Erstellt am: " + r.getCreated().toString() + "\n\n");
    Vector<Row> rows = r.getRows();

    for (Row row : rows) {
      for (int k = 0; k < row.getNumColumns(); k++) {
        result.append(row.getColumnAt(k) + "\t ; \t");
      }

      result.append("\n");
    }

    result.append("\n");
    result.append(r.getImprint() + "\n");

    /*
     * Der String Buffer wird in einen String umgewandelt, damit es anschließend
     * moeglich ist das Ergebnis mit der Methode getReportText() aufzurufen.
     */
    this.reportText = result.toString();
  }

/**
 * Erstellen des uebergebenen Reports und speichern im richtigen Format.
 * 
 * @param r der zu erstellende Report
 */
  @Override
public void process(AllShoppinglistsOfGroupReport r) {

  }

  /**
   * Auslesen des zuletzt erstellen Report.
   * 
   * @return String im HTML-Format
   */
  public String getReportText() {
    return this.getHeader() + this.reportText + this.getTrailer();
  }
}
