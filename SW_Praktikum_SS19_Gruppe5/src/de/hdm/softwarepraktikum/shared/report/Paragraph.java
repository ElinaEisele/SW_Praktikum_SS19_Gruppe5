package de.hdm.softwarepraktikum.shared.report;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Die Klasse Paragraph dient zum speichern von Text. Mithilfe des ReportWriters laesst 
 * dieser Text sich spaeter in verschiedene Zielformate konvertieren.
 * 
 * Paragraph ist Serializable, so das Objekte dieser
 * Klasse durch das Netzwerk uebertragbar sind.
 * 
 * @author FelixRapp
 */
public abstract class Paragraph implements IsSerializable {

  private static final long serialVersionUID = 1L;

}

