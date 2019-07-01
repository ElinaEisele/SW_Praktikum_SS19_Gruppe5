package de.hdm.softwarepraktikum.client.gui.report;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Die Klasse <code>Notification</code>, instanziiert neue Objekte einer Notification, welche während der 
 * Laufzeit im Header angezeigt werden. Diese Klasse kann störende Window-alerts ersetzen.
 * 
 * @author CarlaHofmann
 *
 */
public class NotificationReport {
	
	private static Label notificationLabel = new Label();
	private static boolean isActive = false;
	
	public NotificationReport() {
		
	}
	
	public static void clear() {
		if (isActive == true) {
			RootPanel.get("reportHeader").remove(RootPanel.get("reportHeader").getWidgetIndex(notificationLabel));
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
		RootPanel.get("reportHeader").add(notificationLabel);
		
		final Timer timer = new Timer() {

			@Override
			public void run() {
				RootPanel.get("reportHeader").remove(RootPanel.get("reportHeader").getWidgetIndex(notificationLabel));
			}
			
		};
		
		timer.schedule(5000);
	}

}
