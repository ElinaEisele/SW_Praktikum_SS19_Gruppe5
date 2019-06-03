package de.hdm.softwarepraktikum.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.softwarepraktikum.shared.bo.Group;
import de.hdm.softwarepraktikum.shared.report.AllListitemsOfGroupReport;

/**
 * Das asynchrone Gegenstueck zu <code>GreetingService</code>.Es wird
 * semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher erfolgt
 * hier keine weitere Dokumentation.
 * 
 * @author FelixRapp, TimBeutelspacher
 */

public interface ReportGeneratorAsync {
	
    public void init(AsyncCallback<Void> callback) throws IllegalArgumentException;
    
    public void createAllListitemsOfGroupReport(Group g, Date startdate, Date enddate, AsyncCallback<AllListitemsOfGroupReport> callback) throws IllegalArgumentException;

}
