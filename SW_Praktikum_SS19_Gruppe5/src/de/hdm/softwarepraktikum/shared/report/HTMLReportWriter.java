package de.hdm.softwarepraktikum.shared.report;

import java.util.Vector;

/**
 * Der HTMLReportWriter formatiert die Reports mittels HTML. Das im richtigen
 * Format vorliegende Ergebnis wird anschließend in der String Variable 
 * reportText abgespeichert.
 * 
 * @author FelixRapp
 */
public class HTMLReportWriter extends ReportWriter {

  /**
   * Textfeld zum speichern des Ergebnisses
   */
  private String reportText = "";

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
    StringBuffer result = new StringBuffer();

    /*
     * Die einzelnen Bestandteile des Reports werden nacheinander ausgelesen
     * und dem StringBuffer in HTML-Form angehängt.
     */
    result.append("<H1>" + r.getTitle() + "</H1>");
    result.append("<table style=\"width:400px;border:1px solid silver\"><tr>");
    result.append("<td valign=\"top\"><b>" + paragraph2HTML(r.getHeaderData())
        + "</b></td>");
    result.append("<td valign=\"top\">" + paragraph2HTML(r.getImprint())
        + "</td>");
    result.append("</tr><tr><td></td><td>" + r.getCreated().toString()
        + "</td></tr></table>");

    Vector<Row> rows = r.getRows();
    result.append("<table style=\"width:400px\">");

    for (int i = 0; i < rows.size(); i++) {
      Row row = rows.elementAt(i);
      result.append("<tr>");
      for (int k = 0; k < row.getNumColumns(); k++) {
        if (i == 0) {
          result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k)
              + "</td>");
        }
        else {
          if (i > 1) {
            result.append("<td style=\"border-top:1px solid silver\">"
                + row.getColumnAt(k) + "</td>");
          }
          else {
            result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
          }
        }
      }
      result.append("</tr>");
    }

    result.append("</table>");

    /*
     * Der String Buffer wird in einen String umgewandelt, damit es anschließend
     * moeglich ist das Ergebnis mit der Methode getReportText() aufzurufen.
     */
    this.reportText = result.toString();
  }

  /**
   * Erstellen des übergebenen Reports und speichern im richtigen Format.
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