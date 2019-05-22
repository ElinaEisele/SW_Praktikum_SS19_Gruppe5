package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;

/**
 * Stellt einen einzelnen Absatz dar, dessen Inhalt als String abgespeichert wird.
 * 
 * @author FelixRapp
 */
public class SimpleParagraph extends Paragraph implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * Inhalt des Absatzes.
   */
  private String text = "";

  /**
   * Es wird ein No-Argument Konstruktor benötigt. Da es einen Konstruktor mit Argumenten
   * gibt muss der No-Argument Konstruktor somit auch angegeben werden.
   */
  public SimpleParagraph() {
  }

  /**
   * Konstruktur, der die Möglichkeit bietet bei Instanziierung, einen Wert festzulegen.
   * 
   * @param value Der Inhalt des Absatzes
   */
  public SimpleParagraph(String value) {
    this.text = value;
  }

  /**
   * Auslesen des Inhalts.
   * 
   * @return Inhalt als String
   */
  public String getText() {
    return this.text;
  }

  /**
   * Ändern des Inhalts.
   * 
   * @param text der neue Inhalt des Absatzes.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Das Simple-Paragraph-Objekt wird als String ausgegeben.
   */
  @Override
  public String toString() {
    return this.text;
  }
}