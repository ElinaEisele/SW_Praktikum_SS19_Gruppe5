package de.hdm.softwarepraktikum.shared.report;

import java.util.ArrayList;
import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Der HTMLReportWriter formatiert die Reports mittels HTML. Das im richtigen
 * Format vorliegende Ergebnis wird anschließend in der String Variable 
 * reportText abgespeichert.
 * 
 * @author FelixRapp, TimBeutelspacher
 */
public class HTMLReportWriter extends ReportWriter {
	
	private static final long serialVersionUID = 1L;
	

	/**
	 * Default KonstruKtor
	 */
	public HTMLReportWriter() {
		
	}

  /**
   * Textfeld zum speichern des Ergebnisses
   */
  private String reportText = "";
  
  private String reportTextHeader = "";

  /**
   * Zurücksetzen der Variable reportText.
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
    // Zuerst wird das Ergebniss des vorherigen Durchlaufs gelöscht.
    this.resetReportText();
    
  //In diesem StringBuffer werden die Ergebnisse der Methode abgelegt.
    StringBuffer result1 = new StringBuffer();

    /*
     * Die einzelnen Bestandteile des Reports werden nacheinander ausgelesen
     * und dem StringBuffer in HTML-Form angehängt.
     */
    result1.append("<H1>" + r.getTitle() + "</H1>");
    result1.append("<H3>" + r.getCreationDateString()+ "</H3>");
    
    this.reportTextHeader = result1.toString();

    //In diesem StringBuffer werden die Ergebnisse der Methode abgelegt.
    StringBuffer result = new StringBuffer();
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
     * Der String Buffer wird in einen String umgewandelt, damit es anschließend
     * moeglich ist das Ergebnis mit der Methode getReportText() aufzurufen.
     */
    this.reportText = result.toString();
  }

@Override
public void process(AllShoppinglistsOfGroupReport r) {
	// TODO Auto-generated method stub
	
}

  }
  
  public String getReportTextHeader() {
	  return this.getHeader() + this.reportTextHeader + this.getTrailer();
  }

/**
   * Auslesen des Ergebnisses der zuletzt aufgerufenen process-Methode.
   * 
   * @return Der Reporttext wird als String im HTML-Format
   */
  public String getReportText(){
		  return this.getHeader() + this.reportText + this.getTrailer();
  }


}