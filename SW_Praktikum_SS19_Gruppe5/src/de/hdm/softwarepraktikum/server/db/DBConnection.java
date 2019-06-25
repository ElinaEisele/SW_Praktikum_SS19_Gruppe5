package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

	/**
	 * Verwalten einer Verbindung zur Datenbank.
	 * 
	 * @author CarlaHofmann
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
    
    private static String googleUrl = "jdbc:google:mysql://main-mechanism-242607:europe-west3:swpraktikum/swpraktikum?user=root&password=swpraktikum";
    //private static String googleUrl = "jdbc:google:mysql://bankproject-154007:bankproject/bankproject?user=demo&password=demo";
    
    //private static String googleUrl = "jdbc:mysql://google/swpraktikum?cloudSqlInstance=main-mechanism-242607:europe-west3:swpraktikum&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=swpraktikum&useSSL=false";
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
     
        // Wenn es bisher keine Conncetion zur DB gab, ...
		if (con == null) {
			try {
                
				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
                	
                    //Klasse laden, welche das neue Pr�fix "jdbc:google:mysql: //" bereitstellt.
                	
                    Class.forName("com.mysql.jdbc.GoogleDriver");
                    url = googleUrl;
               
                } else {
                   
                    // Lokale MySQL Instanz zur Nutzung w�hrend der Entwicklung.
                	
                	Class.forName("com.mysql.jdbc.Driver");
                    url  = localUrl;

                }
                
                /*
                 * Dann erst kann uns der DriverManager eine Verbindung mit den
                 * oben in der Variable url angegebenen Verbindungsinformationen
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

        // Zurueckgeben der Verbindung
        return con;
    }

}
