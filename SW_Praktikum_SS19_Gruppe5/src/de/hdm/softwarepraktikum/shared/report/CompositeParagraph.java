package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Stellt mehrere Abs�tze (SimpleParagraph-Objekte) dar. 
 * 
 * @author FelixRapp
 */
public class CompositeParagraph extends Paragraph implements IsSerializable {

  private static final long serialVersionUID = 1L;

  /**
   * Hier werden die Unterabschnitte abgespeichert.
   */
  private ArrayList<SimpleParagraph> subParagraphs = new ArrayList<SimpleParagraph>();

  /**
   * Hinzuf�gen eines Abschnitts.
   * 
   * @param p der hinzuzuf�gende Unterabschnitt.
   */
  public void addSubParagraph(SimpleParagraph p) {
    this.subParagraphs.add(p);
  }

  /**
   * Entfernen eines Abschnitts.
   * 
   * @param p der zu entfernende Unterabschnitt.
   */
  public void removeSubParagraph(SimpleParagraph p) {
    this.subParagraphs.remove(p);
  }

  /**
   * Auslesen aller Unterabschnitte.
   * 
   * @return ArrayList, welche alle Unterabschnitte enth�lt.
   */
  public ArrayList<SimpleParagraph> getSubParagraphs() {
    return this.subParagraphs;
  }

  /**
   * Gibt die Anzahl der Unterabschnitte zur�ck.
   * 
   * @return Anzahl der Unterabschnitte
   */
  public int getNumParagraphs() {
    return this.subParagraphs.size();
  }

  /**
   * Ausgeben eines bestimmten Unterabschnitts.
   * 
   * @param i der Index des gew�nschten Unterabschnitts
   * 
   * @return der gew�nschte Unterabschnitt
   */
  public SimpleParagraph getParagraphAt(int i) {
    return this.subParagraphs.get(i);
  }

  /**
   * Zur�ckgeben eines CompositeParagraph-Objekts als String.
   */
  @Override
public String toString() {
    /*
     * Neues StringBuffer-Objekt, in welchem die Ergebnisse angeh�ngt werden.
     */
    StringBuffer result = new StringBuffer();

    // Schleife �ber alle Unterabschnitte
    for (int i = 0; i < this.subParagraphs.size(); i++) {
      SimpleParagraph p = this.subParagraphs.get(i);

      /*
       * Der Unterabschnitt wird in einer String umgewandelt und anschlie�end dem 
       * StringBuffer angeh�ngt.
       */
      result.append(p.toString() + "\n");
    }

    /*
     * Der StringBuffer wird in einen String umgewandelt und ausgegeben.
     */
    return result.toString();
  }
}
