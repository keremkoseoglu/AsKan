/*
 * Driver.java
 *
 * Created on 02 Kasým 2007 Cuma, 17:41
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import java.util.*;
import java.sql.*;
import java.io.*;
import com.sap.mw.jco.*;  

/**
 *
 * @author Kerem
 */
public class Driver implements DataType {
    
    public String
            sofid,
            fname,
            lname;
    
    private ArrayList vehicles;
    
    /** Creates a new instance of Driver */
    public Driver() {
    }

    public void fillFromSql(ResultSet R) throws Exception {
        sofid = Sql.getString(R, "SOFID");
        fname = Sql.getString(R, "FNAME");
        lname = Sql.getString(R, "LNAME");
    }

    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from sap_driver where SOFID = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " þöförü mevcut deðil");            
    }

    public String getDisplayText() {
        return fname + " " + lname + " (" + sofid + ")";
    }
    
    public void insert(Sql S) throws Exception
    {
        String command = "";
        command += "INSERT INTO sap_driver (SOFID, FNAME, LNAME) values (";
        command += "'" + sofid + "', ";
        command += "'" + Sql.sqlFormatText(fname, 40) + "', ";
        command += "'" + Sql.sqlFormatText(lname, 40) + "'";
        command += ")";
        S.executeQuery(command);
        
        if (vehicles != null)
        {
            for (int n = 0; n < vehicles.size(); n++)
            {
                String plaka = (String) vehicles.get(n);

                command = "";
                command += "INSERT INTO sap_driver_vehicle (SOFID, TRMTYP) values (";
                command += "'" + sofid + "', ";
                command += "'" + plaka + "' ";
                command += ")";
                S.executeQuery(command);            
            }
        }
    }    
    
    public static ArrayList getAll() throws Exception
    {
        ArrayList ret = new ArrayList();
        
        ResultSet r = Main.sql.selectData("select * from sap_driver order by FNAME LNAME");
        while (r.next())
        {
            Driver d = new Driver();
            d.fillFromSql(r);
            ret.add(d);
        }
        
        return ret;
    }    
    
    public static ArrayList getDriversOfVehicle(String Plate) throws Exception
    {
        ArrayList ret = new ArrayList();
        
        ResultSet r = Main.sql.selectData("select * from sap_driver as s1 where exists (select SOFID from sap_driver_vehicle where SOFID = s1.SOFID and TRMTYP='" + Util.formatPlate(Plate) + "') order by s1.FNAME, s1.LNAME");
        while (r.next())
        {
            Driver d = new Driver();
            d.fillFromSql(r);
            ret.add(d);
        }
        
        return ret;        
    }
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_driver");
        S.executeQuery("delete from sap_driver_vehicle");
    }

    public static ArrayList getAll(Sap S) throws Exception
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_DRIVERS");
        JCO.Function function = ftemp.getFunction();
        S.client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_DRIVER");   
        JCO.Table v = function.getTableParameterList().getTable("E_VEHICLE"); 
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                Driver d = new Driver();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("SOFID")) d.sofid = field.getString();
                    if (field.getName().equals("FNAME")) d.fname = field.getString();
                    if (field.getName().equals("LNAME")) d.lname = field.getString();
                }
                
                d.vehicles = new ArrayList();
                if (v.getNumRows() > 0)
                {
                    v.firstRow();
                    boolean bi = true;
                    while (bi)
                    {
                        String plaka = "";
                        boolean canBeAdded = true;
                        for (JCO.FieldIterator e = v.fields(); e.hasMoreElements();)
                        {
                            JCO.Field field = e.nextField();
                            if (field.getName().equals("SOFID") && !field.getString().equals(d.sofid)) canBeAdded = false;
                            if (canBeAdded)
                            {
                                if (field.getName().equals("TRMTYP")) plaka = field.getString();   
                            }
                        }
                        
                        if (canBeAdded) d.vehicles.add(plaka);
                        bi = v.nextRow();
                    }
                }                

                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }  
    
}
