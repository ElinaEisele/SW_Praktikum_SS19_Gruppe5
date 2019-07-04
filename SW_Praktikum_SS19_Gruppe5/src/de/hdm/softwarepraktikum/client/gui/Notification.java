package de.hdm.softwarepraktikum.client.gui;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Die Klasse <code>Notification</code>, instanziiert neue Objekte einer Notification, welche während der 
 * Laufzeit im Header angezeigt werden. Diese Klasse kann störende Window-alerts ersetzen.
 * 
 * @author ElinaEisele, JonasWagenknecht
 *
 */
public class Notification {
	
	private static Label notificationLabel = new Label();
	private static boolean isActive = false;
	private static Header header = new Header();
	
	public Notification() {
		
	}
	
	public static void clear() {
		if (isActive == true) {
			RootPanel.get("header").remove(RootPanel.get("header").getWidgetIndex(notificationLabel));
		}
	}
	
	/**
	 * Die Methode show zeigt die Fehlermeldung im Header an, welche nach 10s entfernt wird.
	 * 
	 * @param message Die Nachricht die den Fehler beschreibt wird übergeben.
	 */
	public static void show (String message) {
		notificationLabel.setText(message);
		notificationLabel.setStyleName("notificationLabel");

		RootPanel.get("header").insert(notificationLabel, 0);
		
		final Timer timer = new Timer() {

			@Override
			public void run() {
				RootPanel.get("header").remove(RootPanel.get("header").getWidgetIndex(notificationLabel));

			}
			
		};
		
		timer.schedule(5000);
	}

}
