/*
 * WareHouse.java
 *
 * Created on 02 Kasým 2007 Cuma, 17:21
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
import java.util.*;

/**
 *
 * @author Kerem
 */
public class WareHouse implements DataType {
    
    public enum USAGE_GOAL { PURCHASE, SALE };
    
    public String
            lgort,
            lgobe;
    
    public boolean 
            usepur,
            usesal;
    
    /** Creates a new instance of WareHouse */
    public WareHouse() {
    }

    public void fillFromSql(ResultSet R) throws Exception {
        lgort = Sql.getString(R, "LGORT");
        lgobe = Sql.getString(R, "LGOBE");
        usepur = Sql.getBoolean(R, "USPUR");
        usesal = Sql.getBoolean(R, "USSAL");
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from sap_warehouse where LGORT = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " deposu mevcut deðil");        
    }

    @Override
    public String getDisplayText() {
        return lgobe + " (" + lgort + ")";
    }
    
    public static ArrayList getAll(USAGE_GOAL Goal) throws Exception
    {
        ArrayList ret = new ArrayList();
        
        String command = "";
        command = "select * from sap_warehouse WHERE ";
        command += (Goal == USAGE_GOAL.PURCHASE ? "USPUR = 'X'" : "USSAL = 'X'");
        command += " order by LGOBE";
        
        ResultSet r = Main.sql.selectData(command);
        while (r.next())
        {
            WareHouse w = new WareHouse();
            w.fillFromSql(r);
            ret.add(w);
        }
        
        return ret;
    }
    
    public void insert(Sql S) throws Exception
    {
        String command = "";
        command += "INSERT INTO sap_warehouse (LGORT, LGOBE, USPUR, USSAL) values (";
        command += "'" + lgort + "', ";
        command += "'" + Sql.sqlFormatText(lgobe, 16) + "', ";
        command += "'" + (usepur ? "X" : "") + "', ";
        command += "'" + (usesal ? "X" : "") + "'";
        command += ")";
        
        S.executeQuery(command);
    }    
    
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_warehouse");
    }    
    
    public static ArrayList getAll(Sap S) throws Exception
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_WAREHOUSES");
        JCO.Function function = ftemp.getFunction();
        function.getImportParameterList().setValue(Main.config.steelyardParam.werks, "I_WERKS");
        S.client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_WAR");   
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                WareHouse d = new WareHouse();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("LGORT")) d.lgort = field.getString();
                    if (field.getName().equals("LGOBE")) d.lgobe = field.getString();
                    if (field.getName().equals("USPUR")) d.usepur = field.getString().equals("X");
                    if (field.getName().equals("USSAL")) d.usesal = field.getString().equals("X");
                }

                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }       
    
}
