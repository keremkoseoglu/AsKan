/*
 * SyncParam.java
 *
 * Created on 26 Ekim 2007 Cuma, 11:03
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.configuration;

/**
 *
 * @author Kerem
 */
public class SyncParam {
    
    public long period;
    public boolean 
            syncActive,
            syncAtStartup,
            syncDivisions,
            syncMaterials,
            syncWarehouses,
            syncCustomers,
            syncDrivers,
            syncSalesProcess,
            syncVendors,
            syncPurchProcess,
            syncSteelyardConfig,
            syncDocuments,
            syncVehicleWeight,
            syncIncoTerms;
    
    /** Creates a new instance of SyncParam */
    public SyncParam() {
    }
    
}
