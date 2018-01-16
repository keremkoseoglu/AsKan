/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package askan.systems;

import askan.*;
import askan.printing.*;
import java.awt.print.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import com.sap.mw.jco.*;  

/**
 *
 * @author Kerem
 */
public class CompanyProcess implements DataType {

    public enum WEIGHT_STYLE {SINGLE, DOUBLE};
    
    public double id;
    public boolean canceled;
    
    private String
            trmtyp,
            trmtyp2;
    
    public Weight
            emptyWeight,
            fullWeight,
            calcWeight;
    
    public Calendar dateWeight;
    
    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insert(Sql S) throws Exception {
        String command = "";
        
        command += "INSERT INTO ask_company (";
        command += "  [id_kantar]";
        command += " ,[TRMTYP]";
        command += " ,[TRMTYP2]";
        command += " ,[fullWeight]";
        command += " ,[fullUOM]";
        command += " ,[emptyWeight]";
        command += " ,[emptyUOM]";
        command += " ,[calcWeight]";
        command += " ,[calcUOM]";
        command += " ,[dateWeight]";
        command += " ,[Operator]";
        command += " ) VALUES (";
        command += "  '" + Main.config.intParam.kantarID + "'";
        command += " ,'" + Util.formatPlate(trmtyp) + "'";
        command += " ,'" + Util.formatPlate(trmtyp2) + "'";
        command += " ," + String.valueOf(fullWeight.getWeight());
        command += " ,'" + fullWeight.getUOM() + "'";
        command += " ," + String.valueOf(emptyWeight.getWeight());
        command += " ,'" + emptyWeight.getUOM() + "'";
        command += " ," + String.valueOf(calcWeight.getWeight());
        command += " ,'" + calcWeight.getUOM() + "'";
        command += " ,'" + Sql.sqlFormatDate(dateWeight, Sql.DATE_FORMAT.TURKISH, true) + "'";
        command += " ,'" + Main.operatorName + "'";
        command += ")";
        
        Main.sql.executeQuery(command);        
        
        if (getWeightStyle() == WEIGHT_STYLE.SINGLE)
        {
            Vehicle.setWeight(trmtyp, emptyWeight);
        }
        else
        {
            Vehicle.setWeight(trmtyp2, calcWeight);
        }
    }

    @Override
    public String getDisplayText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void setVehicles(String Plate1, String Plate2) throws Exception
    {
        trmtyp = Util.formatPlate(Plate1);
        trmtyp2 = Util.formatPlate(Plate2);
        
        emptyWeight = Vehicle.getWeight(trmtyp);
        if (emptyWeight.getWeight() == 0 && getWeightStyle() == WEIGHT_STYLE.DOUBLE) throw new Exception(Plate1 + " aracý daha önce tartýlmamýþ");
    }
    
    public WEIGHT_STYLE getWeightStyle()
    {
        return (trmtyp2.length() > 0 ? WEIGHT_STYLE.DOUBLE : WEIGHT_STYLE.SINGLE);
    }
    
    public void calculateCalcWeight()
    {
        if (fullWeight == null) 
        { 
            fullWeight = new Weight(); 
            calcWeight = new Weight(); 
        }
        else
        {
            calcWeight = new Weight();
            calcWeight.setWeight(fullWeight.toKG() - emptyWeight.toKG(), Material.UNIT_OF_MEASURE.KG);
        }
    }

}
