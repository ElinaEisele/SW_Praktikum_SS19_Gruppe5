package de.hdm.softwarepraktikum.shared;

import java.util.Date;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;

public interface ReportGenerator {
	
    /**
     * Initialsierungsmethode.
     */
    public void init() throws IllegalArgumentException;
    
    /**
     * Methode zum erstellen eines AllListitemsOfGroupReport
     * 
     * @param g Gruppe, für welche der Report erstellt werden soll
     * @return AllListitemsOfGroupReport der vollständige Report
     * @throws IllegalArgumentException
     */
    public AllListitemsOfGroupReport createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate) throws IllegalArgumentException;

}
