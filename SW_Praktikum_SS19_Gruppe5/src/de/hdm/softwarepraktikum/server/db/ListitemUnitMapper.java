package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse fuer </code>ListitemUnit</code> Objekte. Diese umfasst Methoden um ListitemUnit
 * Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das Mapping
 * funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen und
 * DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann & LeoniFriedrich
 */

public class ListitemUnitMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse
	 */

	private static ListitemUnitMapper listitemUnitMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von UnitMapper
	 */
	protected ListitemUnitMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse
	 *
	 * @return Listitemunitmapper
	 */
	public static ListitemUnitMapper listitemUnitMapper() {
		if (listitemUnitMapper == null) {
			listitemUnitMapper = new ListitemUnitMapper();
		}

		return listitemUnitMapper;
	}
	
	/**
	 * Ausgabe einer Liste aller Unit-Objekte
	 * 
	 * @return ArrayList<ListitemUnit>
	 */

	public ArrayList<ListitemUnit> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<ListitemUnit> units = new ArrayList<ListitemUnit>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM units ORDER BY id");

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
	 * Unit mithilfe der id finden
	 * 
	 * @param id
	 * @return Listitemunit-Objekt
	 */
	public ListitemUnit findById(int id) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM units WHERE id = " + id);

			if (rs.next()) {
				ListitemUnit liu = new ListitemUnit();
				liu.setId(rs.getInt("id"));
				liu.setCreationDate(rs.getDate("creationDate"));
				liu.setName(rs.getString("name"));
				return liu;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * Unit mithilfe des Namens finden
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
	 *Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen
	 * 
	 * @param unit
	 * @return Listitemunit-Objekt
	 */
	public ListitemUnit insert(ListitemUnit unit) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM units ");

			if (rs.next()) {
				unit.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement("INSERT INTO units (id, creationDate, name) VALUES (?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, unit.getId());
			pstmt.setDate(2, (Date) unit.getCreationDate());
			pstmt.setString(3, unit.getName());
			pstmt.executeUpdate();
			return unit;

		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank
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

			return unit;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Loeschen eines Units aus der Datenbank
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
	 * 
	 * Unit eines Eintrags finden
	 * 
	 * @param listitem
	 * @return Listitemunit-Objekt
	 */
	
	public ListitemUnit getUnitOf(Listitem listitem) {
		
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT listitems.id, listitems.unit_id, units.creationDate, units.name "
					+ "FROM listitems INNER JOIN units "
					+ "ON listitems.unit_id = units.id "
					+ "WHERE listitems.id = " + listitem.getId());
			
			while(rs.next()) {
				ListitemUnit liu = new ListitemUnit();
				liu.setId(rs.getInt("unit_id"));
				liu.setCreationDate(rs.getDate("creationDate"));
				liu.setName(rs.getString("name"));
				return liu;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
