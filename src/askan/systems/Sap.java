/*
 * Sap.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 10:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

/**
 *
 * @author Kerem
 */

import askan.*;
import java.io.*;
import com.sap.mw.jco.*;  
import java.util.*;

public class Sap {
    
    public JCO.Client client;
    public IRepository repository;      
    
    /** Creates a new instance of Sap */
    public Sap() {
    }
    
    public void connect()
    {
        client = JCO.createClient
                (Main.config.sapParam.client,
                Main.config.sapParam.user,
                Main.config.sapParam.pass,
                Main.config.sapParam.lang,
                Main.config.sapParam.ip,
                Main.config.sapParam.system
                );

        client.connect(); 
        repository = JCO.createRepository("rep", client);
    }    
    
    public boolean isAlive()
    {
        return client.isAlive();
    }
    
    public void disconnect()
    {
        client.disconnect();
    }     
    
    public static String getDate(Calendar C)
    {
        if (C == null) return "00000000";
        
        int idx1 = 0;
        int idx2 = 0;
        
        String s = C.toString();
        
        idx1 = s.indexOf("YEAR=") + 5;
        idx2 = idx1 + 4;
        String y = s.substring(idx1, idx2);

        idx1 = s.indexOf("MONTH=") + 6;
        idx2 = idx1 + 2;        
        String m = s.substring(idx1, idx2);
        if (m.substring(1, 2).equals(",")) m = "0" + m.substring(0, 1);        
        
        idx1 = s.indexOf("DAY_OF_MONTH=") + 13;
        idx2 = idx1 + 2;            
        String d = s.substring(idx1, idx2);
        if (d.substring(1, 2).equals(",")) d = "0" + d.substring(0, 1);
        
        return y + m + d;
    }
    
    public static String getTime(Calendar C)
    {
        if (C == null) return "000000";        
        
        String h = unpackDatePart(String.valueOf(C.get(Calendar.HOUR_OF_DAY)));
        String m = unpackDatePart(String.valueOf(C.get(Calendar.MINUTE)));
        String ss = unpackDatePart(String.valueOf(C.get(Calendar.SECOND)));
        
        return h + m + ss;
    }
    
    private static String unpackDatePart(String DayOrMonth)
    {
        String ret = DayOrMonth;
        if (ret.length() == 1) ret = "0" + DayOrMonth;
        return ret;
    }
    
    public static String getNumc(Double D)
    {
        String full = String.valueOf(D);
        int pos = full.indexOf(".");
        String half = (pos > 0 ? full.substring(0, pos) : full);
        return half;
    }
    
}
