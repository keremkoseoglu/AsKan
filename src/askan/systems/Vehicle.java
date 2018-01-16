/*
 * Vehicle.java
 *
 * Created on 01 Kasým 2007 Perþembe, 12:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import com.sap.mw.jco.*;  

/**
 *
 * @author Kerem
 */
public class Vehicle {
    
    public String trmtyp;
    public Weight weight;
    
    /** Creates a new instance of Vehicle */
    public Vehicle() {
    }
    
    public static Weight getWeight(String Plate) throws Exception
    {
        Weight ret = new Weight();
        
        ResultSet r = Main.sql.selectData("select weight from ask_vehicle_weight where TRMTYP = '" + Util.formatPlate(Plate) + "'");
        if (r != null && r.next()) 
        {
            ret.setWeight(r.getDouble("weight"), Sql.getString(r, "uom"));
        }
        
        return ret;
    }
    
    public static void setWeight(Sql S, String Plate, Weight W) throws Exception
    {
        if (W == null) return;
        if (W.getWeight() == 0) return;
        Main.sql.executeQuery("delete from ask_vehicle_weight where TRMTYP = '" + Util.formatPlate(Plate) + "'");
        
        String command = "";
        command += "INSERT INTO ask_vehicle_weight (";
        command += "  [TRMTYP]";
        command += " ,[weight]";
        command += " ,[uom]";
        command += ") VALUES (";
        command += "  '" + Util.formatPlate(Plate) + "'";
        command += " , " + String.valueOf(W.getWeight());
        command += " ,'" + W.getUOM() + "'";
        command += ")";
        S.executeQuery(command);
    }
    
    public static boolean isVehicleInPlant(String Plate) throws Exception
    {
        return (SalesProcess.getActiveSalesProcesses(Plate).size() > 0
                ||
                PurchProcess.getActivePurchProcesses(Plate).size() > 0);
    }
    
    public static void setWeight(String Plate, Weight W) throws Exception
    {
        setWeight(Main.sql, Plate, W);
    }
    
    public static ArrayList getWeights(Sql S) throws Exception
    {
        ArrayList ret = new ArrayList();
        
        ResultSet r = S.selectData("SELECT * FROM ask_vehicle_weight");
        if (r == null) return ret;
        
        while (r.next())
        {
            Vehicle v = new Vehicle();
            v.trmtyp = r.getString("TRMTYP");
            v.weight = new Weight();
            v.weight.setWeight(r.getDouble("weight"), r.getString("uom"));
            ret.add(v);
        }
        
        return ret;
    }
    
    public static void setWeights(Sap S, ArrayList al) throws Exception
    {
        boolean b = true;
        
        IFunctionTemplate ftemp = S.repository.getFunctionTemplate("ZCZMKAN_SET_VEHICLE_WEIGHTS");
        JCO.Function function = ftemp.getFunction();
        JCO.ParameterList pl = function.getTableParameterList();
        JCO.Table t = pl.getTable("I_VEHICLE");
        
        for (int n = 0; n < al.size(); n++)
        {
            Vehicle v = (Vehicle) al.get(n);
            t.appendRow();
            t.setValue(v.trmtyp, "TRMTYP");
            t.setValue(v.weight.getWeight(), "NTGEW");
            t.setValue(v.weight.getUOM(), "GEWEI");
        }
        
        pl.setValue(t, "I_VEHICLE");
        function.setTableParameterList(pl);
        
        S.client.execute(function);
        
    }
    
}
