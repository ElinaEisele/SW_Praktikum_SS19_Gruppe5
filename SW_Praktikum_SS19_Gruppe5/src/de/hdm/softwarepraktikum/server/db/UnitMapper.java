package de.hdm.softwarepraktikum.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.softwarepraktikum.shared.bo.*;

/**
 * Mapper Klasse für </code>Unit</code> Objekte. Diese umfasst Methoden um
 * Group Objekte zu erstellen, zu suchen, zu modifizieren und zu loeschen. Das
 * Mapping funktioniert dabei bidirektional. Es koennen Objekte in DB-Strukturen
 * und DB-Stukturen in Objekte umgewandelt werden.
 * 
 * @author CarlaHofmann
 */

public class UnitMapper {
	
	/**
	 * Speicherung der Instanz dieser Mapperklasse.
	 */
	private static UnitMapper unitMapper = null;

	/**
	 * Geschuetzter Konstruktor verhindert weitere Instanzierungen von GroupMapper.
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
	 * Methoden die wir brauchen:
	 * 
	 * ArrayList<Unit> findAll()
	 * Unit findById(int id)
	 * ArrayList<Unit> findByName(String name)
	 * Unit insert(Unit unit)
	 * Unit update(Unit unit)
	 * Unit delete(Unit unit)
	 */

}
