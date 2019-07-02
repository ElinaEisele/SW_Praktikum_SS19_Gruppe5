package de.hdm.softwarepraktikum.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper-Klasse, die <code>ListitemUnit</code>-Objekte auf eine relationale
 * Datenbank abbildet. Hierzu wird eine Reihe von Methoden zur Verfügung
 * gestellt, mit deren Hilfe z.B. Objekte gesucht, erzeugt, modifiziert und
 * gelöscht werden können. Das Mapping ist bidirektional. D.h., Objekte können
 * in DB-Strukturen und DB-Strukturen in Objekte umgewandelt werden.
 *  
 * @author CarlaHofmann & LeoniFriedrich
 */

public class ListitemUnitMapper {

	/**
	 * Die Klasse ListitemUnitMapper wird nur einmal instantiiert. Man spricht hierbei
	 * von einem sogenannten <b>Singleton</b>.
	 * Diese Variable ist durch den Bezeichner <code>static</code> nur einmal für
	 * sämtliche eventuellen Instanzen dieser Klasse vorhanden. Sie speichert die
	 * einzige Instanz dieser Klasse.
	 * 
	 */
	private static ListitemUnitMapper listitemUnitMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen dieser Klasse.
	 */
	protected ListitemUnitMapper() {
	}

	/**
     * Diese statische Methode kann aufgrufen werden durch
     * <code>ListitemUnitMapper.listitemUnitMapper()</code>. Sie stellt die
     * Singleton-Eigenschaft sicher, indem Sie dafür sorgt, dass nur eine einzige
     * Instanz von <code>ListitemUnitMapper</code> existiert.
	 *
	 * @return listitemUnitMapper
	 */
	public static ListitemUnitMapper listitemUnitMapper() {
		if (listitemUnitMapper == null) {
			listitemUnitMapper = new ListitemUnitMapper();
		}

		return listitemUnitMapper;
	}
	
	/**
	 * Auslesen aller <code>Unit<code>-Objekte.
	 * 
	 * @return ArrayList<ListitemUnit>
	 */

	public ArrayList<ListitemUnit> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<ListitemUnit> units = new ArrayList<ListitemUnit>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM units");

			while (rs.next()) {
				ListitemUnit liu = new ListitemUnit();
				liu.setId(rs.getInt("id"));
				liu.setCreationDate(rs.getDate("creationDate"));
				liu.setName(rs.getString("name"));
				units.add(liu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return units;
	}

	/**
	 * Suchen eines <code>ListitemUnit<code>-Objekts mit vorgegebener Id. 
	 * Da diese eindeutig ist, wird genau ein Objekt zurueckgegeben.
	 * 
	 * @param id
	 * @return Listitemunit-Objekt
	 */
	public ListitemUnit findById(int id) {
		
		Connection con = DBConnection.connection();
		ListitemUnit liu = new ListitemUnit();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM units WHERE id = " + id);

			if (rs.next()) {
				liu.setId(rs.getInt("id"));
				liu.setCreationDate(rs.getDate("creationDate"));
				liu.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return liu;

	}
	
	/**
	 * Suchen aller <code>ListitemUnit<code>-Objekte mit vorgegebenem Namen. 
	 * 
	 * @param name
	 * @return ArrayList<ListitemUnit>
	 */
	public ArrayList<ListitemUnit> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<ListitemUnit> units = new ArrayList<ListitemUnit>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM units WHERE name = '" + name + "'");

			while (rs.next()) {
				ListitemUnit liu = new ListitemUnit();
				liu.setId(rs.getInt("id"));
				liu.setCreationDate(rs.getDate("creationDate"));
				liu.setName(rs.getString("name"));
				units.add(liu);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return units;
	}

	/**
	 * Einfügen eines <code>ListitemUnit</code>-Objekts in die Datenbank. 
	 * Dabei wird auch der Primärschlüssel des übergebenen Objekts 
	 * geprüft und ggf. berichtigt.
	 * 
	 * @param unit
	 * @return Listitemunit-Objekt
	 */
	public ListitemUnit insert(ListitemUnit unit) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM units");

			if (rs.next()) {
				unit.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO units (id, creationDate, name) "
					+ "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, unit.getId());
				pstmt.setDate(2, (Date) unit.getCreationDate());
				pstmt.setString(3, unit.getName());
				pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return unit;
	}
	/**
	 * Wiederholtes Schreiben eines 
	 * <code>ListitemUnit<code>-Objekts in die Datenbank.
	 * 
	 * @param unit
	 * @return Listitemunit -Objekt
	 */
	public ListitemUnit update(ListitemUnit unit) {

		Connection con = DBConnection.connection();

		try {

			PreparedStatement pstmt = con.prepareStatement("UPDATE units SET name = ? WHERE id = ?");

				pstmt.setString(1, unit.getName());
				pstmt.setInt(2, unit.getId());
				pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return unit;
		
	}

	/**
	 * Loeschen der Daten eines 
	 * <code>Listitem</code>-Objekts aus der Datenbank.
	 * 
	 * @param unit
	 */
	public void delete(ListitemUnit unit) {

		Connection con = DBConnection.connection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM units WHERE id = " + unit.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Auslesen des zugehörigen Unit-Wertes eines gegebenen 
	 * <code>Listitem<code>-Objekts.
	 * 
	 * @param listitem
	 * @return Listitemunit-Objekt
	 */
	
	public ListitemUnit getUnitOf(Listitem listitem) {
		
		Connection con = DBConnection.connection();
		ListitemUnit liu = new ListitemUnit();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT listitems.id AS listitem_id, "
					+ "listitems.unit_id AS unit_id, "
					+ "units.creationDate AS unit_creationDate, "
					+ "units.name AS unit_name "
					+ "FROM listitems INNER JOIN units "
					+ "ON listitems.unit_id = units.id "
					+ "WHERE listitems.id = " + listitem.getId());
			
			while(rs.next()) {
				liu.setId(rs.getInt("unit_id"));
				liu.setCreationDate(rs.getDate("unit_creationDate"));
				liu.setName(rs.getString("unit_name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return liu;
	}
}
