/*
 * Main.java
 *
 * Created on 20 Ekim 2007 Cumartesi, 13:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan;

import askan.configuration.*;
import askan.steelyard.*;
import askan.sync.*;
import askan.systems.*;
import java.awt.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author Kerem
 */
public class Main {
    
    private static Calendar cal;
    private static SimpleDateFormat sdf;
    
    public static ArrayList mmPurchaseables;
    
    public static Config config;
    public static Sql sql;
    public static SyncMaster syncMaster;
    public static TwoWaySerialComm steelYard;
    
    public static String operatorName;
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
        
        // Config'i okuyal�m
        config = null;
        try
        {
            config = new Config();
        }
        catch(Exception ex)
        {
            appendLog("config.txt dosyas�nda bir hata var: " + ex.toString());
            return;
        }         
        
        // Look and Feel
        if (config.intParam.windowsLookAndFeel)
        {
            try
            {
                javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch(Exception ex)
            {

            }     
        }        
        
        // Gerekiyorsa adam�n ad�n� alal�m
        if (config.intParam.askName)
        {
            try
            {
                operatorName = askan.systems.Util.getPopupValue("L�tfen ad�n�z� girin:");
            }
            catch(Exception ex)
            {
                appendLog("Operat�r ad�n� al�rken bir hata olu�tu: " + ex.toString());
                return;
            }
        }
        else
        {
            operatorName = config.intParam.defaultName;
        }
        
        if (operatorName == null || operatorName.length() == 0) return;
        
        // Teknik nesneler
        frmMain fm = new frmMain();
        syncMaster = new SyncMaster();
        
        try
        {
            sql = new Sql();
            sql.connect();
            fm.setSqlState(true);
        }
        catch(Exception ex)
        {
            appendLog("Teknik SQL Hatas�: " + ex.toString());
            fm.setSqlState(false);
        }
        
        config.readSteelyardParam();
        
        try
        {
            steelYard = new TwoWaySerialComm();
            steelYard.connect();
        }
        catch(Exception ex)
        {
            appendLog("Kantar ba�lant� hatas�: " + ex.toString());
            fm.setSteelYardState(false);
        }       
        
        // Gerekiyorsa, senkronizasyonu ba�latal�m
        if (config.syncParam.syncActive) syncMaster.startSync();
        
        // Formu a�al�m
        fm.setVisible(true);        
    }
    
    public static void appendLog(String Entry)
    {   
        cal = Calendar.getInstance();
        String logText = "[" + sdf.format(cal.getTime()) + "] " + Entry;
        logText = logText.replaceAll("�", "i");
        logText = logText.replaceAll("�", "I");
        logText = logText.replaceAll("�", "g");
        logText = logText.replaceAll("�", "G");
        logText = logText.replaceAll("�", "u");
        logText = logText.replaceAll("�", "U");
        logText = logText.replaceAll("�", "s");
        logText = logText.replaceAll("�", "S");
        logText = logText.replaceAll("�", "o");
        logText = logText.replaceAll("�", "O");
        logText = logText.replaceAll("�", "c");
        logText = logText.replaceAll("�", "C");
        System.out.println(logText);
        
        /*if (sql != null)
        {
            try
            {
                sql.insertLog(config.intParam.logID, Entry);
            }
            catch(Exception ex) {}
        }*/
    }    
    
}
