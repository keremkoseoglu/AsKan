/*
 * To change this template, choose Tools | Templates
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
public class IncoTerms implements DataType {

    public String inco1, bezei;
    
    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        inco1 = Sql.getString(R, "INCO1");
        bezei = Sql.getString(R, "BEZEI");
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from sap_inco where INCO1 = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " IncoTerms'i mevcut deðil");

    }

    @Override
    public void insert(Sql S) throws Exception {
        String command = "";
        command += "INSERT INTO sap_inco (INCO1, BEZEI) VALUES (";
        command += " '" + Sql.sqlFormatText(inco1, 3) + "'";
        command += ",'" + Sql.sqlFormatText(bezei, 30) + "'";
        command += ")";
        
        S.executeQuery(command);
    }

    @Override
    public String getDisplayText() {
        return bezei + " (" + inco1 + ")";
    }
    
    public static void deleteAll(Sql S) throws Exception
    {
        S.executeQuery("DELETE FROM sap_inco");
    }
    
    public static ArrayList getAll(Sap S) throws Exception
    {
        ArrayList ret = new ArrayList();
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_GET_INCO");
        JCO.Function function = ftemp.getFunction();
        S.client.execute(function);
        JCO.Table t = function.getTableParameterList().getTable("E_INCO");   
        
        if (t.getNumRows() > 0)
        {
            t.firstRow();
            while (b)
            {
                IncoTerms d = new IncoTerms();
                for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
                {
                    JCO.Field field = e.nextField();
                    if (field.getName().equals("INCO1")) d.inco1 = field.getString();
                    if (field.getName().equals("BEZEI")) d.bezei = field.getString();
                }

                ret.add(d);
                b = t.nextRow();
            }    
        }
        
        return ret;        
    }

}
