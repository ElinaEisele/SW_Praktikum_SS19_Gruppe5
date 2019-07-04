package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * @author CarlaHofmann & LeoniFriedrich
 */

public class DBConnection {
	
	/**
     * Die Klasse DBConnection wird nur einmal instantiiert. Man spricht hierbei
     * von einem sogenannten <b>Singleton</b>.
     * <p>
     * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal
     * für saemtliche eventuellen Instanzen dieser Klasse vorhanden. Sie
     * speichert die einzige Instanz dieser Klasse.
     * 
     */
	
    private static Connection con = null;
    
    /**
     * Die URL, mit deren Hilfe die Datenbank angesprochen wird.
     */
    private static String googleUrl = "jdbc:google:mysql://main-mechanism-242607:europe-west3:swpraktikum/swpraktikum?user=root&password=swpraktikum";
    private static String localUrl = "jdbc:mysql://localhost:3306/swpraktikum?user=root&password=&serverTimezone=UTC";
    
    /**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>DBConnection.connection()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafuer sorgt, dass nur eine
     * einzige Instanz von <code>DBConnection</code> existiert.
     * <p>
     * 
     * @return DAS <code>DBConncetion</code>-Objekt.
     * @see con
     */
    
    public static Connection connection() {
        
        String url = null;
     
		if (con == null) {
			try {
                
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                	
					/**
					 * Klasse laden, die das neue 
					 * "jdbc:google:mysql://"-Praefix bereitstellt.
					 */
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
               
                } else {
                	
                	/**
                	 * Klasse laden, die eine lokale MySQL-Instanz
                	 * zur Nutzung während der Entwicklung bereitstellt.
                	 */
                	Class.forName("com.mysql.jdbc.Driver");
                    url  = localUrl;

                }
                
                /*
                 * DriverManager kann nun eine Verbindung mit den oben in der 
                 * Variable url angegebenen Verbindungsinformationen
                 * aufbauen.
                 * 
                 * Diese Verbindung wird dann in der statischen Variable con
                 * abgespeichert und fortan verwendet.
                 */
                
                con = DriverManager.getConnection(url);
                
            } catch (Exception e) {
            	
                con = null;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
                
            }
        }
		/**
		 * Zurueckgeben der Verbindung
		 */
        return con;
    }

}
