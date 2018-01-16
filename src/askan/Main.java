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
        
        // Config'i okuyalým
        config = null;
        try
        {
            config = new Config();
        }
        catch(Exception ex)
        {
            appendLog("config.txt dosyasýnda bir hata var: " + ex.toString());
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
        
        // Gerekiyorsa adamýn adýný alalým
        if (config.intParam.askName)
        {
            try
            {
                operatorName = askan.systems.Util.getPopupValue("Lütfen adýnýzý girin:");
            }
            catch(Exception ex)
            {
                appendLog("Operatör adýný alýrken bir hata oluþtu: " + ex.toString());
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
            appendLog("Teknik SQL Hatasý: " + ex.toString());
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
            appendLog("Kantar baðlantý hatasý: " + ex.toString());
            fm.setSteelYardState(false);
        }       
        
        // Gerekiyorsa, senkronizasyonu baþlatalým
        if (config.syncParam.syncActive) syncMaster.startSync();
        
        // Formu açalým
        fm.setVisible(true);        
    }
    
    public static void appendLog(String Entry)
    {   
        cal = Calendar.getInstance();
        String logText = "[" + sdf.format(cal.getTime()) + "] " + Entry;
        logText = logText.replaceAll("ý", "i");
        logText = logText.replaceAll("Ý", "I");
        logText = logText.replaceAll("ð", "g");
        logText = logText.replaceAll("Ð", "G");
        logText = logText.replaceAll("ü", "u");
        logText = logText.replaceAll("Ü", "U");
        logText = logText.replaceAll("þ", "s");
        logText = logText.replaceAll("Þ", "S");
        logText = logText.replaceAll("ö", "o");
        logText = logText.replaceAll("Ö", "O");
        logText = logText.replaceAll("ç", "c");
        logText = logText.replaceAll("Ç", "C");
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
