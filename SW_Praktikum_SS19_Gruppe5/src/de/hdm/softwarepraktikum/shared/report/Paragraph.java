package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;

/**
 * Die Klasse Paragraph dient zum speichern von Text. Mithilfe des ReportWriters l�sst 
 * dieser Text sich sp�ter in verschiedene Zielformate konvertieren.
 * 
 * Paragraph ist Serializable, so das Objekte dieser
 * Klasse durch das Netzwerk �bertragbar sind.
 * 
 * @author FelixRapp
 */
public abstract class Paragraph implements Serializable {

  private static final long serialVersionUID = 1L;

}

