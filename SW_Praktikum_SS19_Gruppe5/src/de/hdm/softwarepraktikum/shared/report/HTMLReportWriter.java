package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;
import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Der HTMLReportWriter formatiert die Reports mittels HTML. Das im richtigen
 * Format vorliegende Ergebnis wird anschlie�end in der String Variable 
 * reportText abgespeichert.
 * 
 * @author FelixRapp, TimBeutelspacher
 */
public class HTMLReportWriter extends ReportWriter {
	
	private static final long serialVersionUID = 1L;
	
	public HTMLReportWriter() {
		
	}

  /**
   * Textfeld zum speichern des Ergebnisses
   */
  private String reportText = "";
  
  private String reportTextHeader = "";

  /**
   * Zur�cksetzen der Variable reportText.
   */
  public void resetReportText() {
    this.reportText = "";
  }

  /**
   * Paragraph-Objekt als HTML-Text ausgeben.
   * 
   * @param p der Paragraph
   * @return HTML-Text
   */
  public String paragraph2HTML(Paragraph p) {
    if (p instanceof CompositeParagraph) {
      return this.paragraph2HTML((CompositeParagraph) p);
    }
    else {
      return this.paragraph2HTML((SimpleParagraph) p);
    }
  }

  /**
   * CompositeParagraph-Objekt als HTML-Text ausgeben.
   * 
   * @param p der CompositeParagraph
   * @return HTML-Text
   */
  public String paragraph2HTML(CompositeParagraph p) {
    StringBuffer result = new StringBuffer();

    for (int i = 0; i < p.getNumParagraphs(); i++) {
      result.append("<p>" + p.getParagraphAt(i) + "</p>");
    }
    return result.toString();
  }

  /**
   * SimpleParagraph-Objekt als HTML-Text ausgeben.
   * 
   * @param p der SimpleParagraph
   * @return HTML-Text
   */
  public String paragraph2HTML(SimpleParagraph p) {
    return "<p>" + p.toString() + "</p>";
  }

  /**
   * HTML-Header-Text erstellen.
   * 
   * @return HTML-Text
   */
  public String getHeader() {
    StringBuffer result = new StringBuffer();

    result.append("<html><head><title></title></head><body>");
    return result.toString();
  }

  /**
   * HTML-Trailer-Text produzieren.
   * 
   * @return HTML-Text
   */
  public String getTrailer() {
    return "</body></html>";
  }
  
  /**
   * Erstellen des uebergebenen Reports und speichern im richtigen Format.
   * 
   * @param r der zu erstellende Report
   */
  @Override
public void process(AllListitemsOfGroupReport r) {
    // Zuerst wird das Ergebniss des vorherigen Durchlaufs gel�scht.
    this.resetReportText();
    
  //In diesem StringBuffer werden die Ergebnisse der Methode abgelegt.
    StringBuffer result = new StringBuffer();

	//    result.append("<head>");
	//    result.append("<meta charset='utf-8'>"); 
	//    result.append("<link href=\'https://fonts.googleapis.com/css?family=Barriecito&display=swap\'>");
	//    result.append("</head>");
    
    /*
     * Die einzelnen Bestandteile des Reports werden nacheinander ausgelesen
     * und dem StringBuffer in HTML-Form angehängt.
     */
   result.append("<H1>" + r.getTitle() + "</H1>");
   result.append("<H3>" + r.getCreationDateString() + "</H3>");
    
//    result.append("<H1 style = font-family: 'Barriecito', cursive; font-weight: bold; font-size: 120%;>" + r.getTitle() + "</H1>");
//    result.append("<H3 style = font-family: 'Open Sans', sans-serif; font-weight: bold; font-size: 100%;>" + r.getCreationDateString()+ "</H3>");
    
    this.reportTextHeader = result.toString();

//
//    /*
//     * Die einzelnen Bestandteile des Reports werden nacheinander ausgelesen
//     * und dem StringBuffer in HTML-Form angeh�ngt.
//     */
//    result.append("<H1>" + r.getTitle() + "</H1>");
//    result.append("<H3>" + r.getCreationDateString()+ "</H3>");
//    result.append("<table style=\"width:400px;border:1px solid silver\"><tr></table>");
//    result.append("<td valign=\"top\"><b>" + paragraph2HTML(r.getHeaderData())+ "</b></td>");
//    result.append("</tr><tr><td></td><td>" + r.getCreationDate().toString() + "</td></tr></table>");

    ArrayList<Row> rows = r.getRows();
    result.append("<table style=\"width:400px\">");

    //Reihe wird erstellt
    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.get(i);
      result.append("<tr>");
      
      //Spalten werden erstellt
      for (int k = 0; k < row.getSizeOfColumns(); k++) {
    	  
    	//Erste Reihe soll fett geschreiben werden und der Hintergrund soll grau sein.
        if (i == 0) {
          result.append("<td style=\\\"background:silver;font-weight:bold\\>" +row.getColumnAt(k).toString() + "</td>");
        }
        
        //Andere Reihen sollen einfach dargestellt werden
        else {
          result.append("<td>" + row.getColumnAt(k) + "</td>");
        }
      }
      //Reihenende wird gekennzeichnet.
      result.append("</tr>");
    }

    //Tabellenende wird gekennzeichnet.
    result.append("</table>");

    /*
     * Der String Buffer wird in einen String umgewandelt, damit es anschlie�end
     * moeglich ist das Ergebnis mit der Methode getReportText() aufzurufen.
     */
    this.reportText = result.toString();
  }

  /**
   * Erstellen des �bergebenen Reports und speichern im richtigen Format.
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