package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Stellt mehrere Absätze (SimpleParagraph-Objekte) dar. 
 * 
 * @author FelixRapp
 */
public class CompositeParagraph extends Paragraph implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Hier werden die Unterabschnitte abgespeichert.
   */
  private ArrayList<SimpleParagraph> subParagraphs = new ArrayList<SimpleParagraph>();

  /**
   * Hinzufügen eines Abschnitts.
   * 
   * @param p der hinzuzufügende Unterabschnitt.
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
   * @return ArrayList, welche alle Unterabschnitte enthält.
   */
  public ArrayList<SimpleParagraph> getSubParagraphs() {
    return this.subParagraphs;
  }

  /**
   * Gibt die Anzahl der Unterabschnitte zurück.
   * 
   * @return Anzahl der Unterabschnitte
   */
  public int getNumParagraphs() {
    return this.subParagraphs.size();
  }

  /**
   * Ausgeben eines bestimmten Unterabschnitts.
   * 
   * @param i der Index des gewünschten Unterabschnitts
   * 
   * @return der gewünschte Unterabschnitt
   */
  public SimpleParagraph getParagraphAt(int i) {
    return this.subParagraphs.get(i);
  }

  /**
   * Zurückgeben eines CompositeParagraph-Objekts als String.
   */
  @Override
public String toString() {
    /*
     * Neues StringBuffer-Objekt, in welchem die Ergebnisse angehängt werden.
     */
    StringBuffer result = new StringBuffer();

    // Schleife über alle Unterabschnitte
    for (int i = 0; i < this.subParagraphs.size(); i++) {
      SimpleParagraph p = this.subParagraphs.get(i);

      /*
       * Der Unterabschnitt wird in einer String umgewandelt und anschließend dem 
       * StringBuffer angehängt.
       */
      result.append(p.toString() + "\n");
    }

    /*
     * Der StringBuffer wird in einen String umgewandelt und ausgegeben.
     */
    return result.toString();
  }
}
