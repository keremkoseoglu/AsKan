/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package askan.systems;

import askan.Main;
import askan.steelyard.*;
import askan.printing.*;

import java.awt.*;
import java.awt.print.*;
import java.sql.ResultSet;
import java.util.*;
import java.io.*;
import com.sap.mw.jco.*; 

/**
 *
 * @author Kerem
 */
public class FreeProcess implements DataType {

    public double id;
    public boolean 
            canceled,
            passedEmpty,
            passedFull;
    public String 
            trmtyp,
            notes,
            company;
    
    public Weight 
            fullWeight,
            emptyWeight,
            calcWeight;
    
    public Calendar 
            dateEmpty,
            dateFull;
    
    public TwoWaySerialComm.TRUCK_STATUS truckStatus;
    
    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        id = R.getInt("id");
        canceled = Sql.getBoolean(R, "canceled");
        trmtyp = Sql.getString(R, "TRMTYP");
        notes = Sql.getString(R, "notes");
        company = Sql.getString(R, "company");
        
        fullWeight = new Weight();
        fullWeight.setWeight(R.getDouble("fullWeight"), Sql.getString(R, "fullUOM"));
        
        emptyWeight = new Weight();
        emptyWeight.setWeight(R.getDouble("emptyWeight"), Sql.getString(R, "emptyUOM"));   
        
        calcWeight = new Weight();
        calcWeight.setWeight(R.getDouble("calcWeight"), Sql.getString(R, "calcUOM"));
        
        passedEmpty = Sql.getBoolean(R, "passedEmpty");
        passedFull = Sql.getBoolean(R, "passedFull");
        
        dateEmpty = Sql.getCalendar(R, "dateEmpty");
        dateFull = Sql.getCalendar(R, "dateFull");  
        
        truckStatus = (Sql.getString(R, "torder").equals("E") ? TwoWaySerialComm.truckStatus.EMPTY : TwoWaySerialComm.truckStatus.FULL);
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from ask_free where id = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " giriþi mevcut deðil"); 
    }

    @Override
    public void insert(Sql S) throws Exception {
        String command = "";
        command += "INSERT INTO ask_free (";
        command += "  [id_kantar]";
        command += " ,[TRMTYP]";
        command += " ,[notes]";
        command += " ,[company]";
        command += " ,[fullWeight]";
        command += " ,[fullUOM]";
        command += " ,[emptyWeight]";
        command += " ,[emptyUOM]";
        command += " ,[passedEmpty]";
        command += " ,[passedFull]";
        if (dateEmpty != null) command += " ,[dateEmpty]";
        if (dateFull != null) command += " ,[dateFull]";
        command += " ,[Operator]";
        command += " ,[torder]";
        command += " ) VALUES (";
        command += "  '" + Main.config.intParam.kantarID + "'";
        command += " ,'" + Util.formatPlate(trmtyp) + "'";
        command += " ,'" + Sql.sqlFormatText(notes, 50) + "'";
        command += " ,'" + Sql.sqlFormatText(company, 50) + "'";
        command += " ," + (fullWeight == null ? "0" : String.valueOf(fullWeight.getWeight()));
        command += " ,'" + (fullWeight == null ? "" : fullWeight.getUOM()) + "'";
        command += " ," + (emptyWeight == null ? "0" : String.valueOf(emptyWeight.getWeight()));
        command += " ,'" + (emptyWeight == null ? "" : emptyWeight.getUOM()) + "'";
        command += " ,'" + (passedEmpty ? "X" : "") + "'";
        command += " ,'" + (passedFull ? "X" : "") + "'";
        if (dateEmpty != null) command += " ,'" + Sql.sqlFormatDate(dateEmpty, Sql.DATE_FORMAT.TURKISH, true) + "'";
        if (dateFull != null) command += " ,'" + Sql.sqlFormatDate(dateFull, Sql.DATE_FORMAT.TURKISH, true) + "'";
        command += " ,'" + Main.operatorName + "'";
        command += " ,'" + (truckStatus == TwoWaySerialComm.truckStatus.EMPTY ? "E" : "F") + "'";
        command += ") ";
        
        S.executeQuery(command);
        id = getActiveFreeProcess(trmtyp).id;        
    }

    public void update() throws Exception
    {
        String command = "";
        
        command += "UPDATE ask_free SET";
        command += "  [fullWeight] = " + String.valueOf(fullWeight.getWeight());
        command += " ,[fullUOM] = '" + fullWeight.getUOM() + "'";
        command += " ,[emptyWeight] = " + String.valueOf(emptyWeight.getWeight());
        command += " ,[emptyUOM] = '" + emptyWeight.getUOM() + "'";
        command += " ,[calcWeight] = " + String.valueOf(calcWeight.getWeight());
        command += " ,[calcUOM] = '" + calcWeight.getUOM() + "'";
        command += " ,[passedEmpty] = 'X'";
        command += " ,[passedFull] = 'X'";
        command += " ,[dateEmpty] = '" + Sql.sqlFormatDate(dateEmpty, Sql.DATE_FORMAT.TURKISH, true) + "'";
        command += " ,[dateFull] = '" + Sql.sqlFormatDate(dateFull, Sql.DATE_FORMAT.TURKISH, true) + "'";
        command += " ,[notes] = '" + Sql.sqlFormatText(notes, 50) + "'";
        command += " ,[company] = '" + Sql.sqlFormatText(company, 50) + "'";
        command += " WHERE id = " + String.valueOf(id);
        
        Main.sql.executeQuery(command);
    }
    
    @Override
    public String getDisplayText() {
        return trmtyp + " (" + String.valueOf(id) + ") ";
    }
    
    public void calculateCalcWeight()
    {
        calcWeight.setWeight(Material.toKG(fullWeight) - Material.toKG(emptyWeight), Material.UNIT_OF_MEASURE.KG);
    }
    
    public void print()
    {   
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintFree(this));
        if (pj.printDialog())
        {
            try
            {
                pj.print();
            }
            catch(Exception ex)
            {
                Main.appendLog(ex.toString());
            }
        }        
    }    
    
    public static ArrayList getActiveFreeProcesses(String Plate) throws Exception
    {
        ArrayList ret = new ArrayList();

        ResultSet rs = Main.sql.selectData("select * from ask_free where id_kantar = '" + Main.config.intParam.kantarID + "' and (canceled is null or canceled <> 'X') and TRMTYP = '" + Plate + "' and (((passedFull is null or passedFull = '') and torder = 'E') or ((passedEmpty is null or passedEmpty = '') and torder = 'F'))");
        while (rs.next()) 
        {
            FreeProcess fp = new FreeProcess();
            fp.fillFromSql(rs);
            ret.add(fp);
        }
        
        return ret;
    }    
    
    public static FreeProcess getActiveFreeProcess(String Plate) 
    {
        FreeProcess ret = new FreeProcess();
        
        try
        {
            ArrayList al = getActiveFreeProcesses(Plate);
            ret = ((FreeProcess) al.get(al.size() - 1));
        }
        catch(Exception ex)
        {
            Main.appendLog(Plate + " plakasýnýn IDsi alýnýrken bir hata oluþtu: " + ex.toString());
        }        
        
        return ret;
    }   
    
    public static void cancelActiveFreeProcesses(String Plate) throws Exception
    {
        Main.sql.executeQuery("update ask_free set canceled = 'X' where TRMTYP = '" + askan.systems.Util.formatPlate(Plate) + "'");
    }

}
