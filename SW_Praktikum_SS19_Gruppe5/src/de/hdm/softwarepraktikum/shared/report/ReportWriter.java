package de.hdm.softwarepraktikum.shared.report;

import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;
import de.hdm.softwarepraktikum.shared.report.AllShoppinglistsOfGroupReport;

/**
 * Die Klasse dient dazu die von Server �bergebenen Report-Objekte in 
 * ein lesbares Format zu �berf�hren.
 * 
 * Es kann verschieden Zielformate in den verschiedenen Subklassen geben.
 * In dieser Klasse werden lediglich die Methoden deklariert, welche von
 * allen Subklassen implementiert werden muss.
 * 
 * @author FelixRapp
 */
public abstract class ReportWriter {

  /**
   * Erstellen des �bergebenen Reports und speichern im richtigen Format.
   * 
   * @param r der zu erstellende Report
   */
  public abstract void process(AllListitemsOfGroupReport r);

  /**
   * Erstellen des �bergebenen Reports und speichern im richtigen Format.
   * 
   * @param r der zu erstellende Report
   */
  public abstract void process(AllShoppinglistsOfGroupReport r);

}