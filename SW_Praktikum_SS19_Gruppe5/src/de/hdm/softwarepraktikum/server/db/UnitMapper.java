package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>Unit</code> Objekte. Diese umfasst Methoden um Unit
 * Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das Mapping
 * funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen und
 * DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann & LeoniFriedrich
 */

public class UnitMapper {

	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static UnitMapper unitMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von UnitMapper.
	 */
	protected UnitMapper() {
	}

	/**
	 * Sicherstellung der Singleton-Eigenschaft der Mapperklasse.
	 *
	 * @return Unitmapper
	 */
	public static UnitMapper unitMapper() {
		if (unitMapper == null) {
			unitMapper = new UnitMapper();
		}

		return unitMapper;
	}
	
	/**
	 * Ausgabe einer Liste aller Unit-Objekte.
	 * 
	 * @return Unitliste
	 * 
	 */

	public ArrayList<Unit> findAll() {

		Connection con = DBConnection.connection();
		ArrayList<Unit> units = new ArrayList<Unit>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM units ORDER BY id");

			while (rs.next()) {
				Unit u = new Unit();
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
	public Unit findById(int id) {
		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM units WHERE id = " + id);

			if (rs.next()) {
				Unit u = new Unit();
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
	public ArrayList<Unit> findByName(String name) {

		Connection con = DBConnection.connection();
		ArrayList<Unit> units = new ArrayList<Unit>();

		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, creationDate, name FROM units WHERE name = " + name);

			while (rs.next()) {
				Unit u = new Unit();
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
	public Unit insert(Unit unit) {

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
	public Unit update(Unit unit) {

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
	public void delete(Unit unit) {

		Connection con = DBConnection.connection();

		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM units WHERE id =" + unit.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
