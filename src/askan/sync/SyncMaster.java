/*
 * SyncMaster.java
 *
 * Created on 26 Ekim 2007 Cuma, 10:52
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.sync;
import askan.*;
import askan.systems.*;
import java.io.*;
import java.util.*;
import javax.swing.event.*;

/**
 *
 * @author Kerem
 */
public class SyncMaster {
    
    private Sql sql;
    private Sap sap;   
    
    private boolean syncActive;
    
    protected EventListenerList listenerList;
    public static String SYNC_OVER = "Senkronizasyon bitti";    
    
    /** Creates a new instance of SyncMaster */
    public SyncMaster() {
        listenerList = new EventListenerList();
    }
    
    public void startSync()
    {
        IntThread it = new IntThread();
        it.addIntEventListener(
                new IntEventListener()
                {
                    public void intEventOccured(IntEvent Evt)
                    {
                        Main.appendLog(Evt.text);
                    }
                }
        );        
        it.start();   
    }
    
    public void doSync() 
    {
        // Start
        if (syncActive)
        {
            fireIntEvent("Senkronizasyon zaten aktif, tekrar baþlatýlmadý");
            return;
        }
        
        syncActive = true;
        
        try
        {
            sap = new Sap();
            sap.connect();
            sql = new Sql();
            sql.connect();
            Main.sql.connect(); // Ana veriler için lazým
            fireIntEvent("Senkronizasyon baþladý");
        }
        catch(Exception ex)
        {
            fireIntEvent("Senkronizasyon baþlatýlamadý: " + ex.toString());
            syncActive = false;
            return;
        }
        
        // Kantar ayarlarýný aktaralým
        if (Main.config.syncParam.syncSteelyardConfig)
        {
            try
            {
                fireIntEvent("Kantar ayarlarý aktarýlýyor");
                sql.startTransaction();
                Main.config.steelyardParam.read(sap);
                Main.config.steelyardParam.write(sql);
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Kantar ayarý aktarým hatasý: " + ex.toString());
            }
        }        
        
        // Bölümleri aktaralým
        if (Main.config.syncParam.syncDivisions)
        {
            try
            {
                fireIntEvent("Bölümler aktarýlýyor");
                sql.startTransaction();
                Division.deleteAll(sql);
                ArrayList al = Division.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    Division d = (Division) al.get(n);
                    d.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Bölüm aktarým hatasý: " + ex.toString());
            }
        }
        
        // Malzemeleri aktaralým
        if (Main.config.syncParam.syncMaterials)
        {
            try
            {
                fireIntEvent("Malzemeler aktarýlýyor");
                sql.startTransaction();
                Material.deleteAll(sql);
                ArrayList al = Material.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    Material m = (Material) al.get(n);
                    m.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Malzeme aktarým hatasý: " + ex.toString());
            }   
        }
        
        // Depolarý aktaralým
        if (Main.config.syncParam.syncWarehouses)
        {
            try
            {
                fireIntEvent("Depolar aktarýlýyor");
                sql.startTransaction();
                WareHouse.deleteAll(sql);
                ArrayList al = WareHouse.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    WareHouse m = (WareHouse) al.get(n);
                    m.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Depo yeri aktarým hatasý: " + ex.toString());
            }   
        }    
        
        // Müþterileri aktaralým
        if (Main.config.syncParam.syncCustomers)
        {
            try
            {
                fireIntEvent("Müþteriler aktarýlýyor");
                sql.startTransaction();
                Customer.deleteAll(sql);
                ArrayList al = Customer.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    Customer m = (Customer) al.get(n);
                    m.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Müþteri aktarým hatasý: " + ex.toString());
            }   
        }    
        
        // Þöförleri aktaralým
        if (Main.config.syncParam.syncDrivers)
        {
            try
            {
                fireIntEvent("Þöförler aktarýlýyor");
                sql.startTransaction();
                Driver.deleteAll(sql);
                ArrayList al = Driver.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    Driver m = (Driver) al.get(n);
                    m.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Þöför aktarým hatasý: " + ex.toString());
            }   
        }    
        
        // Satýcýlarý aktaralým
        if (Main.config.syncParam.syncVendors)
        {
            try
            {
                fireIntEvent("Satýcýlar aktarýlýyor");
                sql.startTransaction();
                Vendor.deleteAll(sql);
                ArrayList al = Vendor.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    Vendor m = (Vendor) al.get(n);
                    m.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Satýcý aktarým hatasý: " + ex.toString());
            }   
        }   
        
        // IncoTerms'i aktaralým
        if (Main.config.syncParam.syncIncoTerms)
        {
            try
            {
                fireIntEvent("Teslimat türleri aktarýlýyor");
                sql.startTransaction();
                IncoTerms.deleteAll(sql);
                ArrayList al = IncoTerms.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    IncoTerms m = (IncoTerms) al.get(n);
                    m.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Teslim türü aktarým hatasý: " + ex.toString());
            }   
        }           
        
        // Dökümanlarý
        if (Main.config.syncParam.syncDocuments)
        {
            try
            {
                fireIntEvent("Belgeler aktarýlýyor");
                sql.startTransaction();
                SalesDocument.deleteAll(sql);
                ArrayList al = SalesDocument.getAll(sap);
                for (int n = 0; n < al.size(); n++)
                {
                    SalesDocument d = (SalesDocument) al.get(n);
                    d.insert(sql);
                }
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Belge aktarým hatasý: " + ex.toString());
            }   
        }  
        
        // Araç Aðýrlýklarý
        if (Main.config.syncParam.syncVehicleWeight)
        {
            try
            {
                fireIntEvent("Araç aðýrlýklarý aktarýlýyor");
                ArrayList al = Vehicle.getWeights(sql);
                Vehicle.setWeights(sap, al);
            }
            catch(Exception ex)
            {
                fireIntEvent("Araç aðýrlýðý aktarým hatasý: " + ex.toString());
            }   
        }        
        
        // Kantar giriþlerini aktaralým
        if (Main.config.syncParam.syncSalesProcess)
        {
            try
            {
                fireIntEvent("Satýþ kayýtlarý aktarýlýyor");
                SalesProcess.transferToSap(sap, sql);
            }
            catch(Exception ex)
            {
                fireIntEvent("Satýþ kayýtlarý aktarým hatasý: " + ex.toString());
            }              
        }
        
        // Satýnalma giriþlerini aktaralým
        if (Main.config.syncParam.syncPurchProcess)
        {
            try
            {
                fireIntEvent("Satýnalma kayýtlarý aktarýlýyor");
                PurchProcess.transferToSap(sap, sql);
            }
            catch(Exception ex)
            {
                fireIntEvent("Satýnalma kayýtlarý aktarým hatasý: " + ex.toString());
            }              
        }    
        
        // MM malzemelerini güncelleyelim (performans)
        fireIntEvent("MM malzemeleri güncelleniyor");
        Main.mmPurchaseables = Material.getPurchaseables();
        
        // End
        syncActive = false;
        sql.disconnect();
        sap.disconnect();
        fireIntEvent(SYNC_OVER);        
    }

    /////////////////////////////////
    // EVENTS
    /////////////////////////////////
    
    public void addIntEventListener(IntEventListener Listener) 
    {
        listenerList.add(IntEventListener.class, Listener);
    }

    private void fireIntEvent(IntEvent Evt)
    {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i+=2) {
            if (listeners[i] == IntEventListener.class) {
                ((IntEventListener)listeners[i+1]).intEventOccured(Evt);
            }
        }
    }    
    
    private void fireIntEvent(String S)
    {
        IntEvent ie = new IntEvent(this);
        ie.text = S;
        fireIntEvent(ie);        
    }    
    
}
