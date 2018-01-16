/*
 * Division.java
 *
 * Created on 06 Kasým 2007 Salý, 10:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import java.sql.*;
import java.io.*;
import com.sap.mw.jco.*;  
import java.util.*;

/**
 *
 * @author Kerem
 */
public class Division implements DataType {
    
    public String
            spart,
            vtext;
    
    public double tolerance;
    
    public Material.SD_WEIGHT_CALC_ALGO sdWeightCalcAlgo;
    
    /** Creates a new instance of Division */
    public Division() {
    }

    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        spart = Sql.getString(R, "SPART");
        vtext = Sql.getString(R, "VTEXT");
        tolerance = R.getDouble("Tolerance");
        sdWeightCalcAlgo = Material.getSDWeightAlgo(Sql.getString(R, "Algor"));
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from sap_division where SPART = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " bölümü mevcut deðil");        
    }

    @Override
    public String getDisplayText() {
        return vtext + " (" + spart + ")";
    }
    
    @Override
    public void insert(Sql S) throws Exception
    {
        String command = "";
        command += "INSERT INTO sap_division (SPART, Algor, Tolerance, VTEXT) VALUES (";
        command += "'" + spart + "', ";
        command += "'" + Material.getSDWeightAlgo(sdWeightCalcAlgo) + "', ";
        command += String.valueOf(tolerance) + ", ";
        command += "'" + Sql.sqlFormatText(vtext, 20) + "'";
        command += ")";
        
        S.executeQuery(command);
    }    
        
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("delete from sap_division");
    }    
    
    public static ArrayList getAll(Sap S) throws Exception
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_DIVISIONS");
        JCO.Function function = ftemp.getFunction();
        S.client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_DIV");   
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                Division d = new Division();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("SPART")) d.spart = field.getString();
                    if (field.getName().equals("VTEXT")) d.vtext = field.getString();
                    if (field.getName().equals("ALGOR")) d.sdWeightCalcAlgo = Material.getSDWeightAlgo(field.getString());
                    if (field.getName().equals("TOLER")) d.tolerance = field.getDouble();
                }

                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;          
    }
    
}
