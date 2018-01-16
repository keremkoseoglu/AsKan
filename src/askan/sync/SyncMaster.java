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
            fireIntEvent("Senkronizasyon zaten aktif, tekrar ba�lat�lmad�");
            return;
        }
        
        syncActive = true;
        
        try
        {
            sap = new Sap();
            sap.connect();
            sql = new Sql();
            sql.connect();
            Main.sql.connect(); // Ana veriler i�in laz�m
            fireIntEvent("Senkronizasyon ba�lad�");
        }
        catch(Exception ex)
        {
            fireIntEvent("Senkronizasyon ba�lat�lamad�: " + ex.toString());
            syncActive = false;
            return;
        }
        
        // Kantar ayarlar�n� aktaral�m
        if (Main.config.syncParam.syncSteelyardConfig)
        {
            try
            {
                fireIntEvent("Kantar ayarlar� aktar�l�yor");
                sql.startTransaction();
                Main.config.steelyardParam.read(sap);
                Main.config.steelyardParam.write(sql);
                sql.endTransaction();
            }
            catch(Exception ex)
            {
                sql.cancelTransaction();
                fireIntEvent("Kantar ayar� aktar�m hatas�: " + ex.toString());
            }
        }        
        
        // B�l�mleri aktaral�m
        if (Main.config.syncParam.syncDivisions)
        {
            try
            {
                fireIntEvent("B�l�mler aktar�l�yor");
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
                fireIntEvent("B�l�m aktar�m hatas�: " + ex.toString());
            }
        }
        
        // Malzemeleri aktaral�m
        if (Main.config.syncParam.syncMaterials)
        {
            try
            {
                fireIntEvent("Malzemeler aktar�l�yor");
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
                fireIntEvent("Malzeme aktar�m hatas�: " + ex.toString());
            }   
        }
        
        // Depolar� aktaral�m
        if (Main.config.syncParam.syncWarehouses)
        {
            try
            {
                fireIntEvent("Depolar aktar�l�yor");
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
                fireIntEvent("Depo yeri aktar�m hatas�: " + ex.toString());
            }   
        }    
        
        // M��terileri aktaral�m
        if (Main.config.syncParam.syncCustomers)
        {
            try
            {
                fireIntEvent("M��teriler aktar�l�yor");
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
                fireIntEvent("M��teri aktar�m hatas�: " + ex.toString());
            }   
        }    
        
        // ��f�rleri aktaral�m
        if (Main.config.syncParam.syncDrivers)
        {
            try
            {
                fireIntEvent("��f�rler aktar�l�yor");
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
                fireIntEvent("��f�r aktar�m hatas�: " + ex.toString());
            }   
        }    
        
        // Sat�c�lar� aktaral�m
        if (Main.config.syncParam.syncVendors)
        {
            try
            {
                fireIntEvent("Sat�c�lar aktar�l�yor");
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
                fireIntEvent("Sat�c� aktar�m hatas�: " + ex.toString());
            }   
        }   
        
        // IncoTerms'i aktaral�m
        if (Main.config.syncParam.syncIncoTerms)
        {
            try
            {
                fireIntEvent("Teslimat t�rleri aktar�l�yor");
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
                fireIntEvent("Teslim t�r� aktar�m hatas�: " + ex.toString());
            }   
        }           
        
        // D�k�manlar�
        if (Main.config.syncParam.syncDocuments)
        {
            try
            {
                fireIntEvent("Belgeler aktar�l�yor");
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
                fireIntEvent("Belge aktar�m hatas�: " + ex.toString());
            }   
        }  
        
        // Ara� A��rl�klar�
        if (Main.config.syncParam.syncVehicleWeight)
        {
            try
            {
                fireIntEvent("Ara� a��rl�klar� aktar�l�yor");
                ArrayList al = Vehicle.getWeights(sql);
                Vehicle.setWeights(sap, al);
            }
            catch(Exception ex)
            {
                fireIntEvent("Ara� a��rl��� aktar�m hatas�: " + ex.toString());
            }   
        }        
        
        // Kantar giri�lerini aktaral�m
        if (Main.config.syncParam.syncSalesProcess)
        {
            try
            {
                fireIntEvent("Sat�� kay�tlar� aktar�l�yor");
                SalesProcess.transferToSap(sap, sql);
            }
            catch(Exception ex)
            {
                fireIntEvent("Sat�� kay�tlar� aktar�m hatas�: " + ex.toString());
            }              
        }
        
        // Sat�nalma giri�lerini aktaral�m
        if (Main.config.syncParam.syncPurchProcess)
        {
            try
            {
                fireIntEvent("Sat�nalma kay�tlar� aktar�l�yor");
                PurchProcess.transferToSap(sap, sql);
            }
            catch(Exception ex)
            {
                fireIntEvent("Sat�nalma kay�tlar� aktar�m hatas�: " + ex.toString());
            }              
        }    
        
        // MM malzemelerini g�ncelleyelim (performans)
        fireIntEvent("MM malzemeleri g�ncelleniyor");
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
