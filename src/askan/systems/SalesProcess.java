/*
 * SalesProcess.java
 *
 * Created on 26 Ekim 2007 Cuma, 18:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.systems;

import askan.Main;
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
public class SalesProcess implements DataType {

    public double
            id;
    
    public String
            id_kantar;

    /**
     * Plate number
     */
    public String
            trmtyp,
            trmtyp2,
            sevkNote,
            emptyNote,
            fullNote;
    
    public Customer
            customer,
            receiver;
    public Material material;
    public SalesDocument salesDocument;
    public WareHouse wareHouse;
    public Driver driver;
    
    public String
            kunnrManual,
            vbelnManual;

    public String vrkmeManual;

    public String sofidManual;

    public String kunweManual;
    
    public boolean
            canceled;

    public boolean manualEntries;

    public boolean             
            passedDoor,             
            passedEmpty,             
            passedFull,
            inspect;
    

    public Calendar dateSevk;

    public Calendar dateEmpty;
    
    /**
     * Date & time on which the vehicle has passed the full steelyard
     */
    public Calendar
            dateFull;
    
    public Weight lfimg;
    
    public Weight
            emptyWeight,
            fullWeight,
            calcWeight,
            theoWeight;
    
    private boolean transferredToSap;
            
    /** Creates a new instance of SalesProcess */
    public SalesProcess() {
        id = 0;
        id_kantar = "";
        trmtyp = "";
        trmtyp2 = "";
        
        kunnrManual = "";
        vbelnManual = "";
        vrkmeManual = "";
        kunweManual = "";
        sofidManual = "";
        
        customer = new Customer();
        material = new Material();
        salesDocument = new SalesDocument();
        wareHouse = new WareHouse();
        driver = new Driver();
        receiver = new Customer();
        
        canceled = false;
        manualEntries = false;
        passedDoor = false;
        passedEmpty = false;
        passedFull = false;
        transferredToSap = false;
        inspect = false;
        
        dateSevk = null;
        dateEmpty = null;
        dateFull = null;
        
        lfimg = new Weight();
        emptyWeight = new Weight();
        fullWeight = new Weight();
        calcWeight = new Weight();
        theoWeight = new Weight();
        
        sevkNote = "";
        emptyNote = "";
        fullNote = "";
    }

    @Override
    public void fillFromSql(ResultSet R) throws Exception {
        id = R.getDouble("id");
        id_kantar = Sql.getString(R, "id_kantar");
        trmtyp = Sql.getString(R, "TRMTYP");
        trmtyp2 = Sql.getString(R, "TRMTYP2");
        
        try
        {
            customer.fillFromID(Sql.getString(R, "KUNNR"));
        }
        catch(Exception ex)
        {
            customer = null;
        }
        
        try
        {
            material.fillFromID(Sql.getString(R, "MATNR"));
        }
        catch(Exception ex)
        {
            material = null;
        }
        
        try
        {
            salesDocument.fillFromID(Sql.getString(R, "VBELN"));
        }
        catch(Exception ex)
        {
            salesDocument = null;
        }
        
        try
        {
            wareHouse.fillFromID(Sql.getString(R, "LGORT"));
        }
        catch(Exception ex)
        {
            wareHouse = null;
        }
        
        try
        {
            driver.fillFromID(Sql.getString(R, "SOFID"));
        }
        catch(Exception ex)
        {
            driver = null;
        }        
        
        try
        {
            receiver.fillFromID(Sql.getString(R, "KUNWE"));
        }
        catch(Exception ex)
        {
            receiver = null;
        }
        
        if (receiver == null && salesDocument != null && material != null)
        {
            receiver = salesDocument.getReceiver(material.matnr);
        }
        
        kunnrManual = Sql.getString(R, "kunnrManual");
        vbelnManual = Sql.getString(R, "vbelnManual");
        vrkmeManual = Sql.getString(R, "vrkmeManual");
        sofidManual = Sql.getString(R, "sofidManual");
        kunweManual = Sql.getString(R, "kunweManual");
        
        canceled = Sql.getBoolean(R, "canceled");
        manualEntries = Sql.getBoolean(R, "manualEntries");
        passedDoor = Sql.getBoolean(R, "passedDoor");
        passedEmpty = Sql.getBoolean(R, "passedEmpty");
        passedFull = Sql.getBoolean(R, "passedFull");
        transferredToSap = Sql.getBoolean(R, "transferredToSap");
        inspect = Sql.getBoolean(R, "INSPE");
        
        lfimg.setWeight(R.getDouble("LFIMG"), Sql.getString(R, "VRKME"));
        emptyWeight.setWeight(R.getDouble("emptyWeight"), Sql.getString(R, "emptyUOM"));
        fullWeight.setWeight(R.getDouble("fullWeight"), Sql.getString(R, "fullUOM"));
        calcWeight.setWeight(R.getDouble("calcWeight"), Sql.getString(R, "calcUOM"));
        theoWeight.setWeight(R.getDouble("theoWeight"), Sql.getString(R, "theoUOM"));
        
        dateSevk = Sql.getCalendar(R, "dateSevk");
        dateEmpty = Sql.getCalendar(R, "dateEmpty");
        dateFull = Sql.getCalendar(R, "dateFull");
        
        sevkNote = Sql.getString(R, "sevkNote");
        emptyNote = Sql.getString(R, "emptyNote");
        fullNote = Sql.getString(R, "fullNote");
    }

    @Override
    public void fillFromID(String ID) throws Exception {
        ResultSet r = Main.sql.selectData("select * from ask_sales where id = '" + ID + "'");
        if (r.next()) fillFromSql(r); else throw new Exception(ID + " giriþi mevcut deðil");        
    }
    
    @Override
    public String getDisplayText()
    {
        return trmtyp + " (" + String.valueOf(id) + ") ";
    }    

    
    @Override
    public void insert(Sql S) throws Exception
    {
        dateSevk = Calendar.getInstance();
        
        String command = "";
        command += " INSERT INTO ask_sales";
        command += " ([id_kantar]";
        command += " ,[TRMTYP]";
        command += " ,[KUNNR]";
        command += " ,[kunnrManual]";
        command += " ,[MATNR]";
        command += " ,[dtype]";
        command += " ,[VBELN]";
        command += " ,[vbelnManual]";
        command += " ,[LFIMG]";
        command += " ,[VRKME]";
        command += " ,[vrkmeManual]";
        command += " ,[manualEntries]";
        command += " ,[passedDoor]";
        command += (driver == null ? " ,[sofidManual]" : " ,[SOFID]");
        command += (receiver == null ? ", [kunweManual]" : " ,[KUNWE]");
        command += " ,[dateSevk]";
        command += " ,[Operator]";
        command += " ,[sevkNote]";
        command += " ,[INCO1]";
        command += " ,[INSPE]";
        command += ") VALUES (";
        command += " '" + Main.config.intParam.kantarID + "', ";
        command += " '" + trmtyp + "', ";
        command += " '" + (customer == null ? "" : customer.kunnr) + "', ";
        command += " '" + kunnrManual + "', ";
        command += " '" + (material == null ? "" : material.matnr) + "', ";
        command += " '" + (salesDocument == null ? "" : salesDocument.getDocumentTypeText()) + "', ";
        command += " '" + (salesDocument == null ? "" : salesDocument.vbeln) + "', ";
        command += " '" + vbelnManual + "', ";
        command += " " + String.valueOf(lfimg.getWeight()) + ", ";
        command += " '" + Material.getUOM(lfimg.uom) + "', ";
        command += " '" + vrkmeManual + "', ";
        command += " '" + (manualEntries ? "X" : "") + "', ";
        command += " '" + (passedDoor ? "X" : "") + "', ";
        command += (driver == null ? " '" + sofidManual + "'" : " '" + driver.sofid + "'") + ", ";
        command += (receiver == null ? " '" + kunweManual + "'" : " '" + receiver.kunnr + "'") + ", ";
        command += " '" + Sql.sqlFormatDate(dateSevk, Sql.DATE_FORMAT.TURKISH, true) + "', ";
        command += " '" + Sql.sqlFormatText(Main.operatorName, 50) + "', ";
        command += " '" + Sql.sqlFormatText(sevkNote, 50) + "', ";
        command += " '" + (salesDocument == null || salesDocument.incoTerms == null ? "" : salesDocument.incoTerms.inco1) + "', ";
        command += " '" + (inspect ? "X" : "") + "'";
        command += ") ";
        
        Main.sql.executeQuery(command);
        id = getActiveSalesProcess(trmtyp).id;
    }
    
    public void insert() throws Exception
    {
        insert(Main.sql);
    }
    
    
    public boolean fillFromPlate(String Plate) throws Exception
    {
        ResultSet r = Main.sql.selectData("select * from ask_sales where TRMTYP = '" + Util.formatPlate(Plate) + "' and (canceled is null or canceled <> 'X') and (passedFull is null or passedFull <> 'X')");
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
    
    public void saveEmptyWeight() throws Exception
    {
        dateEmpty = Calendar.getInstance();
        
        
        String command = "";
        command += " UPDATE ask_sales SET";
        command += "  emptyWeight = " + String.valueOf(emptyWeight.getWeight());
        command += ", emptyUOM = '" + Material.getUOM(emptyWeight.uom) + "'";
        command += ", TRMTYP2 = '" + Util.formatPlate(trmtyp2) + "'";
        command += ", dateEmpty = '" + Sql.sqlFormatDate(dateEmpty, Sql.DATE_FORMAT.TURKISH, true) + "'";
        command += ", dtype = '" + (salesDocument == null ? "" : salesDocument.getDocumentTypeText()) + "'";
        command += ", passedEmpty = 'X'";
        command += ", emptyNote = '" + Sql.sqlFormatText(emptyNote, 50) + "'";
        if (inspect) command += ", INSPE = 'X'";
        command += " WHERE id = " + id;
        Main.sql.executeQuery(command);
        
        passedEmpty = true;
    }
    
    public void cancelEmptyWeight() throws Exception
    {
        String command = "";
        command += " UPDATE ask_sales SET";
        command += "  emptyWeight = 0";
        command += ", emptyUOM = ''";
        command += ", TRMTYP2 = ''";
        command += ", dateEmpty = null";
        command += ", dtype = null";
        command += ", passedEmpty = ''";
        command += " WHERE id = " + id;
        Main.sql.executeQuery(command);
        
        passedEmpty = false;
    }
    
    public void saveFullWeight() throws Exception
    {
        String command = "";
        command += " UPDATE ask_sales SET";
        command += manualEntries ? " manualEntries = 'X', " : "";
        command += " passedFull = 'X',";
        command += " dateFull = '" + Sql.sqlFormatDate(dateFull, Sql.DATE_FORMAT.TURKISH, true) + "',";
        command += " fullWeight = " + String.valueOf(fullWeight.getWeight()) + ", ";
        command += " fullUOM = '" + Material.getUOM(fullWeight.uom) + "', ";
        command += " calcWeight = " + String.valueOf(calcWeight.getWeight()) + ", ";
        command += " calcUOM = '" + Material.getUOM(calcWeight.uom) + "', ";        
        command += " theoWeight = " + String.valueOf(theoWeight.getWeight()) + ", ";
        command += " theoUOM = '" + Material.getUOM(theoWeight.uom) + "', ";    
        command += wareHouse == null ? "" : " LGORT = '" + wareHouse.lgort + "', ";
        command += " fullNote = '" + Sql.sqlFormatText(fullNote, 50) + "' ";   
        if (inspect) command += ", INSPE = 'X'";
        command += " WHERE id = " + id;
        
        Main.sql.executeQuery(command);

        if (salesDocument != null)
        {
            try
            {
                command = "UPDATE sap_sales_document_head SET processed = 'X' WHERE";
                command += " VBELN = '" + salesDocument.vbeln + "'";
                command += " and dtype = '" + salesDocument.getDocumentTypeText() + "'";

                Main.sql.executeQuery(command);
            }
            catch(Exception ex)
            {
                Main.appendLog("Uyarý: " + salesDocument.vbeln + " dökümaný iþlenmiþ olarak iþaretlenemedi: " + ex.toString());
            }
        }
        
        passedFull = true;
    }
    
    public void printSevk()
    {
        //askan.printing.PrintObject po = new askan.printing.PrintObject();
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintSDSevk(this));
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
    
    public void printFull()
    {
        //askan.printing.PrintObject po = new askan.printing.PrintObject();
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintSDFull(this));
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
    
    public void printFull2()
    {
        //askan.printing.PrintObject po = new askan.printing.PrintObject();
        
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(new PrintSDFull2(this));
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
    
    public String getCustomerDisplayText()
    {
        return customer == null ? kunnrManual : customer.getDisplayText();
    }
    
    public String getVrkmeDisplayText()
    {
        return lfimg == null ? vrkmeManual : Material.getUOMText(lfimg.uom);
    }
    
    public Weight getEligibleWeight()
    {
        if (material.getSDWeightCalcAlgo() == Material.SD_WEIGHT_CALC_ALGO.DOKME)
        {
            return calcWeight;
        }
        else
        {
            return theoWeight;
        }
    }
    
    public void calculateCalcWeight()
    {
        calcWeight.setWeight(Material.toKG(fullWeight) - Material.toKG(emptyWeight), Material.UNIT_OF_MEASURE.KG);
    }
    
    public void calculateTheoWeight()
    {
        if (lfimg.uom == Material.UNIT_OF_MEASURE.KG || lfimg.uom == Material.UNIT_OF_MEASURE.TON)
        {
            theoWeight = lfimg;
        }
        else
        {
            theoWeight.setWeight(lfimg.getWeight() * Material.toKG(material.weightPerUnit), Material.UNIT_OF_MEASURE.KG);
        }
    }
    
   public boolean checkSDTolerance() throws Exception
   {
       if (material.division == null) throw new Exception("Bu malzemenin bölümü tanýmlanmamýþ, tolerans kontrolü yapýlamadý");
       return (getDifferenceRate() <= material.division.tolerance);
   } 
   
   public double getDifferenceRate()
   {
       double diff = 0;
       double oran = 0;
       
       if (material.getSDWeightCalcAlgo() == Material.SD_WEIGHT_CALC_ALGO.DOKME)
       {
           diff = lfimg.toKG() - calcWeight.toKG();
           if (diff < 0) diff = diff * -1;
           oran = diff * 100 / lfimg.toKG();
       }
       else
       {
           diff = theoWeight.toKG() - calcWeight.toKG();
           if (diff < 0) diff = diff * -1;
           oran = diff * 100 / theoWeight.toKG();           
       }
       
       return oran;
   }
   
    public String getReceiverAddress()
    {
        return (receiver == null ? kunweManual : receiver.address);
    }   
    
    public String getDriverName()
    {
        return (driver == null ? sofidManual : (driver.fname + " " + driver.lname));
    }
    
    public void setTransferred(Sql S, boolean Transferred) throws Exception
    {
        transferredToSap = Transferred;
        
        String command = "";
        command += " UPDATE ask_sales SET transferredToSap = '" + (transferredToSap ? "X" : "") + "' where id = " + String.valueOf(id);
        S.executeQuery(command);
    }
    
    public static ArrayList getActiveSalesProcesses(String Plate) throws Exception
    {
        ArrayList ret = new ArrayList();

        ResultSet rs = Main.sql.selectData("select * from ask_sales where id_kantar = '" + Main.config.intParam.kantarID + "' and (canceled is null or canceled <> 'X') and TRMTYP = '" + Plate + "' and (passedFull is null or passedFull = '')");
        while (rs.next()) 
        {
            SalesProcess sp = new SalesProcess();
            sp.fillFromSql(rs);
            ret.add(sp);
        }
        
        return ret;
    }
    
    public static SalesProcess getActiveSalesProcess(String Plate) 
    {
        SalesProcess ret = new SalesProcess();
        
        try
        {
            ArrayList al = getActiveSalesProcesses(Plate);
            ret = ((SalesProcess) al.get(al.size() - 1));
        }
        catch(Exception ex)
        {
            Main.appendLog(Plate + " plakasýnýn IDsi alýnýrken bir hata oluþtu: " + ex.toString());
        }        
        
        return ret;
    }    
    
    public static void cancelActiveSalesProcesses(String Plate) throws Exception
    {
        String command = "";
        command += "UPDATE ask_sales ";
        command += " SET canceled = 'X'";
        command += " WHERE id_kantar = '" + Main.config.intParam.kantarID + "'";
        command += " AND TRMTYP = '" + Plate + "'";
        command += " AND passedDoor = 'X'";
        command += " AND (passedEmpty is null OR passedEmpty <> 'X' OR passedFull is null OR passedFull <> 'X')";
        
        Main.sql.executeQuery(command);
    }
    
    private static ArrayList getUntransferredEntries(Sql S)
    {
        ArrayList ret = new ArrayList();
        
        try
        {
            ResultSet r = S.selectData("select * from ask_sales where (canceled is null or canceled <> 'X') and passedFull = 'X' and (transferredToSap is null or transferredToSap <> 'X')");
            if (r == null) return ret;
            
            while (r.next())
            {
                SalesProcess c = new SalesProcess();
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
        
        IFunctionTemplate ftemp = SapInstance.repository.getFunctionTemplate("ZCZMKAN_ADD_SALES");
        JCO.Function function = ftemp.getFunction();
        JCO.Table t = function.getTableParameterList().getTable("I_SALES");   
        
        for (int n = 0; n < al.size(); n++)
        {
            SalesProcess sp = (SalesProcess) al.get(n);
            
            t.appendRow();
            for (JCO.FieldIterator e = t.fields(); e.hasMoreElements();)
            {
                JCO.Field field = e.nextField();
                if (field.getName().equals("RECID")) field.setValue(Sap.getNumc(sp.id));
                if (field.getName().equals("KANID")) field.setValue(sp.id_kantar);
                if (field.getName().equals("TRMTYP")) field.setValue(sp.trmtyp);
                if (field.getName().equals("TRMTYP2")) field.setValue(sp.trmtyp2);
                if (field.getName().equals("KUNNR")) field.setValue(sp.customer == null ? "" : sp.customer.kunnr);
                if (field.getName().equals("KUNMA")) field.setValue(sp.kunnrManual);
                if (field.getName().equals("MATNR")) field.setValue(sp.material == null ? "" : sp.material.matnr);
                if (field.getName().equals("DTYPE")) field.setValue(sp.salesDocument == null ? "" : sp.salesDocument.getDocumentTypeText());
                if (field.getName().equals("VBELN")) field.setValue(sp.salesDocument == null ? "" : sp.salesDocument.vbeln);
                if (field.getName().equals("VBEMA")) field.setValue(sp.vbelnManual);
                if (field.getName().equals("LFIMG")) field.setValue(sp.lfimg.getWeight());
                if (field.getName().equals("VRKME")) field.setValue(sp.lfimg.getUOM());
                if (field.getName().equals("VRKMA")) field.setValue(sp.vrkmeManual);
                if (field.getName().equals("MANUE")) field.setValue(sp.manualEntries ? "X" : "");
                if (field.getName().equals("PASDO")) field.setValue(sp.passedDoor ? "X" : "");
                if (field.getName().equals("PASEM")) field.setValue(sp.passedEmpty ? "X" : "");
                if (field.getName().equals("PASFU")) field.setValue(sp.passedFull ? "X" : "");
                if (field.getName().equals("INSPE")) field.setValue(sp.inspect ? "X" : "");
                if (field.getName().equals("DATDO")) field.setValue(Sap.getDate(sp.dateSevk));
                if (field.getName().equals("TIMDO")) field.setValue(Sap.getTime(sp.dateSevk));
                if (field.getName().equals("DATEM")) field.setValue(Sap.getDate(sp.dateEmpty));
                if (field.getName().equals("TIMEM")) field.setValue(Sap.getTime(sp.dateEmpty));
                if (field.getName().equals("DATFU")) field.setValue(Sap.getDate(sp.dateFull));
                if (field.getName().equals("TIMFU")) field.setValue(Sap.getTime(sp.dateFull));
                if (field.getName().equals("WEIEM")) field.setValue(sp.emptyWeight.getWeight());
                if (field.getName().equals("MEIEM")) field.setValue(sp.emptyWeight.getUOM());
                if (field.getName().equals("WEIFU")) field.setValue(sp.fullWeight.getWeight());
                if (field.getName().equals("MEIFU")) field.setValue(sp.fullWeight.getUOM());
                if (field.getName().equals("WEICA")) field.setValue(sp.calcWeight.getWeight());
                if (field.getName().equals("MEICA")) field.setValue(sp.calcWeight.getUOM());
                if (field.getName().equals("WEITH")) field.setValue(sp.theoWeight.getWeight());
                if (field.getName().equals("MEITH")) field.setValue(sp.theoWeight.getUOM());
                if (field.getName().equals("LGORT")) field.setValue(sp.wareHouse == null ? "" : sp.wareHouse.lgort);
                if (field.getName().equals("SOFID")) field.setValue(sp.driver == null ? "" : sp.driver.sofid);
                if (field.getName().equals("SOFMA")) field.setValue(sp.sofidManual);
                if (field.getName().equals("KUNWE")) field.setValue(sp.receiver == null ? "" : sp.receiver.kunnr);
                if (field.getName().equals("KUWMA")) field.setValue(sp.kunweManual);
                if (field.getName().equals("OPERA")) field.setValue(Sql.sqlFormatText(Main.operatorName, 20));
                if (field.getName().equals("SNOTE")) field.setValue(Sql.sqlFormatText(sp.sevkNote, 50));
                if (field.getName().equals("ENOTE")) field.setValue(Sql.sqlFormatText(sp.emptyNote, 50));
                if (field.getName().equals("FNOTE")) field.setValue(Sql.sqlFormatText(sp.fullNote, 50));                
                
            }            
        }       
        
        SapInstance.client.execute(function);
        
        for (int n = 0; n < al.size(); n++)
        {
            SalesProcess s = (SalesProcess) al.get(n);
            s.setTransferred(SqlInstance, true);
        }
    }    
    
}
