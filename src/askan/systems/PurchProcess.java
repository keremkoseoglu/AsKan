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
public class PurchProcess implements DataType {

    public double id;
    
    public String
            id_kantar,
            lifnrManual,
            senderManual,
            matnrManual,
            sofidManual,
            irsal,
            trmtyp,
            trmtyp2,
            emptyNote,
            fullNote;
    
    public boolean
            canceled,
            passedFull,
            passedEmpty,
            transferredToSap,
            manualEntries,
            inspect;
    
    public Driver driver;
    
    public Vendor
            vendor,
            sender;
    
    public Material
            material;
    
    public Weight 
            lfimg,
            emptyWeight,
            fullWeight,
            calcWeight;
    
    public Calendar
            dateEmpty,
            dateFull;
    
    public WareHouse
            wareHouse;
    
    public PurchProcess()
    {
        id_kantar = "";
        lifnrManual = "";
        senderManual = "";
        matnrManual = "";
        sofidManual = "";
        irsal = "";
        trmtyp = "";
        trmtyp2 = "";    
        emptyNote = "";
        fullNote = "";
        
        canceled = false;
        passedFull = false;
        passedEmpty = false;
        transferredToSap = false;
        manualEntries = false;
        inspect = false;
    }
    
    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        calcWeight = new Weight();
        calcWeight.setWeight(R.getDouble("calcWeight"), Sql.getString(R, "calcUOM"));

        emptyWeight = new Weight();
        emptyWeight.setWeight(R.getDouble("emptyWeight"), Sql.getString(R, "emptyUOM"));
        
        fullWeight = new Weight();
        fullWeight.setWeight(R.getDouble("fullWeight"), Sql.getString(R, "fullUOM"));
        
        lfimg = new Weight();
        lfimg.setWeight(R.getDouble("LFIMG"), Sql.getString(R, "VRKME"));
        
        id = R.getDouble("id");
        id_kantar = Sql.getString(R, "id_kantar");
        irsal = Sql.getString(R, "irsal");
        lifnrManual = Sql.getString(R, "lifnrManual");
        senderManual = Sql.getString(R, "SenderManual");
        canceled = Sql.getBoolean(R, "canceled");
        inspect = Sql.getBoolean(R, "INSPE");
        passedFull = Sql.getBoolean(R, "passedFull");
        passedEmpty = Sql.getBoolean(R, "passedEmpty");
        transferredToSap = Sql.getBoolean(R, "transferredToSap");
        manualEntries = Sql.getBoolean(R, "manualEntries");
        matnrManual = Sql.getString(R, "matnrManual");
        trmtyp = Sql.getString(R, "trmtyp");
        trmtyp2 = Sql.getString(R, "trmtyp2");
        emptyNote = Sql.getString(R, "emptyNote");
        fullNote = Sql.getString(R, "fullNote");
        
        dateEmpty = Sql.getCalendar(R, "dateEmpty");
        dateFull = Sql.getCalendar(R, "dateFull");        
        
        try
        {
            driver = new Driver();
            driver.fillFromID(Sql.getString(R, "SOFID"));
        }
        catch(Exception ex)
        {
            driver = null;
            sofidManual = Sql.getString(R, "sofidManual");
        }
        
        try
        {
            material = new Material();
            material.fillFromID(Sql.getString(R, "MATNR"));
        }
        catch(Exception ex)
        {
            material = null;
        }
        
        try
        {
            sender = new Vendor();
            sender.fillFromID(Sql.getString(R, "Sender"));
        }
        catch(Exception ex)
        {
            sender = null;
        }
        
        try
        {
            vendor = new Vendor();
            vendor.fillFromID(Sql.getString(R, "LIFNR"));
        }
        catch(Exception ex)
        {
            vendor = null;
        }     
        
        try
        {
            wareHouse = new WareHouse();
            wareHouse.fillFromID(Sql.getString(R, "LGORT"));
        }
        catch(Exception ex)
        {
            wareHouse = null;
        }        
        
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from ask_purch where id = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " giriþi mevcut deðil");  
    }

    @Override
    public void insert(Sql S) throws Exception {
        
        id_kantar = Main.config.intParam.kantarID;
        dateFull = Calendar.getInstance();
        
        String command = "";
        command += "INSERT INTO ask_purch (";
        command += " [id_kantar]";
        command += " ,[TRMTYP]";
        command += " ,[TRMTYP2]";
        command += " ,[LIFNR]";
        command += " ,[lifnrManual]";
        command += " ,[Sender]";
        command += " ,[SenderManual]";
        command += " ,[MATNR]";
        command += " ,[matnrManual]";
        command += " ,[fullWeight]";
        command += " ,[fullUOM]";
        command += " ,[passedFull]";
        command += " ,[dateFull]";
        command += " ,[manualEntries]";
        command += " ,[Operator]";
        command += " ,[fullNote]";
        command += " ,[INSPE]";
        command += ") VALUES (";
        command += " '" + id_kantar + "'";
        command += " ,'" + Sql.sqlFormatText(trmtyp, 18) + "'";
        command += " ,'" + Sql.sqlFormatText(trmtyp2, 18) + "'";
        command += " ,'" + (vendor == null ? "" : vendor.lifnr) + "'";
        command += " ,'" + Sql.sqlFormatText(lifnrManual, 35) + "'";
        command += " ,'" + (sender == null ? "" : sender.lifnr) + "'";
        command += " ,'" + Sql.sqlFormatText(senderManual, 35) + "'";
        command += " ,'" + (material == null ? "" : material.matnr) + "'";
        command += " ,'" + Sql.sqlFormatText(matnrManual, 40) + "'";
        command += " ," + String.valueOf(fullWeight.getWeight());
        command += " ,'" + fullWeight.getUOM() + "'";
        command += " ,'" + (passedFull ? "X" : "") + "'";
        command += " ,'" + Sql.sqlFormatDate(dateFull, Sql.DATE_FORMAT.TURKISH, true) + "' ";
        command += " ,'" + (manualEntries ? "X" : "") + "' ";
        command += " ,'" + Sql.sqlFormatText(Main.operatorName, 50) + "' ";
        command += " ,'" + Sql.sqlFormatText(fullNote, 50) + "' ";
        command += " ,'" + (inspect ? "X" : "") + "'";
        command += ")";
        
        Main.sql.executeQuery(command);
        id = getActivePurchProcess(trmtyp).id;
    }
    
    public void setPlate(String Plate)
    {
        trmtyp = Util.formatPlate(Plate);
    }
    
    public String getPlate()
    {
        return trmtyp;
    }
    
    public void setPlate2(String Plate)
    {
        trmtyp2 = Util.formatPlate(Plate);
    }
    
    public String getPlate2()
    {
        return trmtyp2;
    }
    
    public String getVendorDisplayText()
    {
        return ((vendor == null || vendor.lifnr.equals("")) ? lifnrManual : vendor.getDisplayText());
    }
    
    public String getSenderDisplayText()
    {
        return ((sender == null || sender.lifnr.equals("")) ? senderManual : sender.getDisplayText());
    }
    
    public String getMaterialDisplayText()
    {
        return ((material == null || material.matnr.equals("")) ? matnrManual : material.getDisplayText());
    }
    
    public static ArrayList getActivePurchProcesses(String Plate) throws Exception
    {
        ArrayList ret = new ArrayList();

        ResultSet rs = Main.sql.selectData("select * from ask_purch where id_kantar = '" + Main.config.intParam.kantarID + "' and (canceled is null or canceled <> 'X') and TRMTYP = '" + Plate + "' and (passedEmpty is null or passedEmpty = '')");
        while (rs.next()) 
        {
            PurchProcess sp = new PurchProcess();
            sp.fillFromSql(rs);
            ret.add(sp);
        }
        
        return ret;
    }    
    
    public static PurchProcess getActivePurchProcess(String Plate) 
    {
        PurchProcess ret = new PurchProcess();
        
        try
        {
            ArrayList al = getActivePurchProcesses(Plate);
            ret = ((PurchProcess) al.get(al.size() - 1));
        }
        catch(Exception ex)
        {
            Main.appendLog(Plate + " plakasýnýn IDsi alýnýrken bir hata oluþtu: " + ex.toString());
        }        
        
        return ret;
    }     

    @Override
    public String getDisplayText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void printFull()
    {
        //askan.printing.PrintObject po = new askan.printing.PrintObject();
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintMMFull(this));
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
    
    public void printEmpty()
    {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintMMEmpty(this));
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
    
    public boolean fillFromPlate(String Plate) throws Exception
    {
        ResultSet r = Main.sql.selectData("select * from ask_purch where TRMTYP = '" + Util.formatPlate(Plate) + "' and (canceled is null or canceled <> 'X') and (passedEmpty is null or passedEmpty <> 'X')");
        if (r != null && r.next()) 
        {
            fillFromSql(r); 
            return true;
        }
        else
        {
            return false;
        }
    }       
    
    public void calculateCalcWeight()
    {
        calcWeight.setWeight(Material.toKG(fullWeight) - Material.toKG(emptyWeight), Material.UNIT_OF_MEASURE.KG);
    }    
    
    public static void cancelActivePurchProcesses(String Plate) throws Exception
    {
        String command = "";
        command += "UPDATE ask_purch ";
        command += " SET canceled = 'X'";
        command += " WHERE id_kantar = '" + Main.config.intParam.kantarID + "'";
        command += " AND TRMTYP = '" + Util.formatPlate(Plate) + "'";
        command += " AND passedFull = 'X'";
        command += " AND (passedEmpty is null OR passedEmpty <> 'X')";
        
        Main.sql.executeQuery(command);
    }    
    
    public void saveEmptyWeight() throws Exception
    {   
        String command = "";
        command += " UPDATE ask_purch SET";
        command += " passedEmpty = 'X',";
        command += " dateEmpty = '" + Sql.sqlFormatDate(dateEmpty, Sql.DATE_FORMAT.TURKISH, true) + "',";
        command += " emptyWeight = " + String.valueOf(emptyWeight.getWeight()) + ", ";
        command += " emptyUOM = '" + Material.getUOM(emptyWeight.uom) + "', ";
        command += " calcWeight = " + String.valueOf(calcWeight.getWeight()) + ", ";
        command += " calcUOM = '" + Material.getUOM(calcWeight.uom) + "', ";           
        command += wareHouse == null ? "" : " LGORT = '" + wareHouse.lgort + "', ";
        command += " emptyNote = '" + Sql.sqlFormatText(emptyNote, 50) + "', ";
        command += " [SOFID] = '" + (driver == null ? "" : driver.sofid) + "', ";
        command += " [sofidManual] = '" + Sql.sqlFormatText(sofidManual, 50) + "', ";
        command += " [irsal] = '" + Sql.sqlFormatText(irsal, 50) + "', ";
        command += " [LFIMG] = " + String.valueOf(lfimg.getWeight()) + ", ";
        command += " [VRKME] = '" + lfimg.getUOM() + "'";
        if (inspect) command += ", INSPE = 'X'";
        command += " WHERE id = " + id;
        
        Main.sql.executeQuery(command);
        passedEmpty = true;
    }    
    
    public void setTransferred(Sql S, boolean Transferred) throws Exception
    {
        transferredToSap = Transferred;
        
        String command = "";
        command += " UPDATE ask_purch SET transferredToSap = '" + (transferredToSap ? "X" : "") + "' where id = " + String.valueOf(id);
        S.executeQuery(command);
    }    
    
    private static ArrayList getUntransferredEntries(Sql S)
    {
        ArrayList ret = new ArrayList();
        
        try
        {
            ResultSet r = S.selectData("select * from ask_purch where (canceled is null or canceled <> 'X') and passedEmpty = 'X' and (transferredToSap is null or transferredToSap <> 'X')");
            if (r == null) return ret;
            
            while (r.next())
            {
                PurchProcess c = new PurchProcess();
                c.fillFromSql(r);
                ret.add(c);
            }
        }
        catch(Exception ex)
        {
            Main.appendLog("Aktarýlacak kayýtlar okunurken hata oluþtu: " + ex.toString());
        }
        
        return ret;          
    }       
    
    public static void transferToSap(Sap SapInstance, Sql SqlInstance) throws Exception
    {
        ArrayList al = getUntransferredEntries(SqlInstance);
        
        IFunctionTemplate ftemp = SapInstance.repository.getFunctionTemplate("ZCZMKAN_ADD_PURCH");
        JCO.Function function = ftemp.getFunction();
        JCO.Table t = function.getTableParameterList().getTable("I_PURCH");   
        
        for (int n = 0; n < al.size(); n++)
        {
            PurchProcess sp = (PurchProcess) al.get(n);
            
            t.appendRow();
            for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
            {
                JCO.Field field = e.nextField();
                if (field.getName().equals("RECID")) field.setValue(Sap.getNumc(sp.id));
                if (field.getName().equals("KANID")) field.setValue(sp.id_kantar);
                if (field.getName().equals("TRMTYP")) field.setValue(Sql.sqlFormatText(sp.trmtyp, 18));
                if (field.getName().equals("TRMTY2")) field.setValue(Sql.sqlFormatText(sp.trmtyp2, 18));
                if (field.getName().equals("LIFNR")) field.setValue(sp.vendor == null ? "" : sp.vendor.lifnr);
                if (field.getName().equals("LIFMA")) field.setValue(Sql.sqlFormatText(sp.lifnrManual, 35));
                if (field.getName().equals("SENDE")) field.setValue(sp.sender == null ? "" : sp.sender.lifnr);
                if (field.getName().equals("SENMA")) field.setValue(Sql.sqlFormatText(sp.senderManual, 35));
                if (field.getName().equals("MATNR")) field.setValue(sp.material == null ? "" : sp.material.matnr);
                if (field.getName().equals("MATMA")) field.setValue(Sql.sqlFormatText(sp.matnrManual, 18));
                if (field.getName().equals("SOFID")) field.setValue(sp.driver == null ? "" : sp.driver.sofid);
                if (field.getName().equals("SOFMA")) field.setValue(Sql.sqlFormatText(sp.sofidManual, 50));
                if (field.getName().equals("IRSAL")) field.setValue(Sql.sqlFormatText(sp.irsal, 50));
                if (field.getName().equals("LFIMG")) field.setValue(sp.lfimg.getWeight());
                if (field.getName().equals("VRKME")) field.setValue(sp.lfimg.getUOM());  
                if (field.getName().equals("WEIEM")) field.setValue(sp.emptyWeight.getWeight());
                if (field.getName().equals("MEIEM")) field.setValue(sp.emptyWeight.getUOM());
                if (field.getName().equals("WEIFU")) field.setValue(sp.fullWeight.getWeight());
                if (field.getName().equals("MEIFU")) field.setValue(sp.fullWeight.getUOM());
                if (field.getName().equals("WEICA")) field.setValue(sp.calcWeight.getWeight());
                if (field.getName().equals("MEICA")) field.setValue(sp.calcWeight.getUOM());
                if (field.getName().equals("DATEM")) field.setValue(Sap.getDate(sp.dateEmpty));
                if (field.getName().equals("TIMEM")) field.setValue(Sap.getTime(sp.dateEmpty));
                if (field.getName().equals("DATFU")) field.setValue(Sap.getDate(sp.dateFull));
                if (field.getName().equals("TIMFU")) field.setValue(Sap.getTime(sp.dateFull));
                if (field.getName().equals("LGORT")) field.setValue(sp.wareHouse == null ? "" : sp.wareHouse.lgort);
                if (field.getName().equals("MANUE")) field.setValue(sp.manualEntries ? "X" : "");
                if (field.getName().equals("INSPE")) field.setValue(sp.inspect ? "X" : "");
                if (field.getName().equals("OPERA")) field.setValue(Sql.sqlFormatText(Main.operatorName, 20));
                if (field.getName().equals("ENOTE")) field.setValue(Sql.sqlFormatText(sp.emptyNote, 50));
                if (field.getName().equals("FNOTE")) field.setValue(Sql.sqlFormatText(sp.fullNote, 50));
            }            
        }       
        
        SapInstance.client.execute(function);
        
        for (int n = 0; n < al.size(); n++)
        {
            PurchProcess s = (PurchProcess) al.get(n);
            s.setTransferred(SqlInstance, true);
        }
    } 

}
