package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse f�r </code>ListitemUnit</code> Objekte. Diese umfasst Methoden um ListitemUnit
 * Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das Mapping
 * funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen und
 * DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann & LeoniFriedrich
 */

public class ListitemUnitMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static ListitemUnitMapper listitemUnitMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von UnitMapper.
	 */
	protected ListitemUnitMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
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
	 * Ausgabe einer Liste aller Unit-Objekte.
	 * 
	 * @return Unitliste
	 * 
	 */

	public ArrayList<ListitemUnit> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<ListitemUnit> units = new ArrayList<ListitemUnit>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM units ORDER BY id");

			while (rs.next()) {
				ListitemUnit u = new ListitemUnit();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				units.add(u);
			}

			return units;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Methode um ein Unit mittels seiner Id zu finden.
	 * 
	 * @param id
	 * @return Unit
	 */
	public ListitemUnit findById(int id) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM units WHERE id = " + id);

			if (rs.next()) {
				ListitemUnit u = new ListitemUnit();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				return u;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * Unit durch den Namen finden.
	 * 
	 * @param name
	 * @return Unitliste 
	 */
	public ArrayList<ListitemUnit> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<ListitemUnit> units = new ArrayList<ListitemUnit>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM units WHERE name = " + name);

			while (rs.next()) {
				ListitemUnit u = new ListitemUnit();
				u.setId(rs.getInt("id"));
				u.setCreationDate(rs.getDate("creationDate"));
				u.setName(rs.getString("name"));
				units.add(u);
			}

			return units;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 *Insert Methode um der Datenbank eine neue Entitaet hinzuzufuegen.
	 * 
	 * @param unit
	 * @return Unit
	 */
	public ListitemUnit insert(ListitemUnit unit) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid FROM units ");

			if (rs.next()) {
				unit.setId(rs.getInt("maxid") + 1);
			}

			PreparedStatement pstmt = con.prepareStatement(
					"INSERT INTO units (id, creationDate, name) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, unit.getId());
			pstmt.setDate(2, (Date) unit.getCreationDate());
			pstmt.setString(3, unit.getName());
			pstmt.executeUpdate();

			return unit;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

	}
	/**
	 * Wiederholtes Schreiben eines Objekts in die Datenbank.
	 * 
	 * @param unit
	 * @return Unit 
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
	 * Loeschen eines Units aus der Datenbank.
	 * 
	 * @param unit
	 */
	public void delete(ListitemUnit unit) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM units WHERE id =" + unit.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}