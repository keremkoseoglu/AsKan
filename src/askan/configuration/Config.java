/*
 * Config.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 09:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.configuration;

/**
 *
 * @author Kerem
 */
import askan.*;
import gnu.io.SerialPort;
import java.util.*;

public class Config {
    
    public SapParam sapParam;
    public SqlParam sqlParam;
    public IntParam intParam;
    public SyncParam syncParam;
    public ComParam comParam;
    public SteelyardParam steelyardParam;
            
    private ArrayList fileCont;
    
    /** Creates a new instance of Config */
    public Config() throws Exception {
        String temp = "";
        
        fileCont = Util.readTextFile("config.txt");
        if (fileCont.size() <= 0) throw new Exception("Konfigürasyon dosyasýnda bir hata var");
        
        sapParam = new SapParam();
        sapParam.ip = getValue("sapIP");
        sapParam.client = getValue("sapClient");
        sapParam.user = getValue("sapUser");
        sapParam.pass = getValue("sapPass");
        sapParam.system = getValue("sapSystem");
        sapParam.lang = getValue("sapLang");
        sapParam.connect = getValue("sapConnect").equals("TRUE");
        
        sqlParam = new SqlParam();
        sqlParam.ip = getValue("sqlIP");
        sqlParam.database = getValue("sqlDatabase");
        sqlParam.user = getValue("sqlUser");
        sqlParam.pass = getValue("sqlPass");
        sqlParam.connect = getValue("sqlConnect").equals("TRUE");
        
        syncParam = new SyncParam();
        syncParam.syncActive = getValue("syncActive").equals("TRUE");
        syncParam.syncAtStartup = getValue("syncAtStartup").equals("TRUE");
        syncParam.period = Long.valueOf(getValue("syncPeriod")) * 1000;
        syncParam.syncDivisions = getValue("syncDivisions").equals("TRUE");
        syncParam.syncMaterials = getValue("syncMaterials").equals("TRUE");
        syncParam.syncWarehouses = getValue("syncWarehouses").equals("TRUE");
        syncParam.syncCustomers = getValue("syncCustomers").equals("TRUE");
        syncParam.syncDrivers = getValue("syncDrivers").equals("TRUE");
        syncParam.syncSalesProcess = getValue("syncSalesProcess").equals("TRUE");
        syncParam.syncVendors = getValue("syncVendors").equals("TRUE");
        syncParam.syncPurchProcess = getValue("syncPurchProcess").equals("TRUE");
        syncParam.syncSteelyardConfig = getValue("syncSteelyardConfig").equals("TRUE");
        syncParam.syncDocuments = getValue("syncDocuments").equals("TRUE");
        syncParam.syncVehicleWeight = getValue("syncVehicleWeight").equals("TRUE");
        syncParam.syncIncoTerms = getValue("syncIncoTerms").equals("TRUE");
        
        intParam = new IntParam();
        intParam.kantarID = getValue("intID");
        intParam.defaultName = getValue("intDefaultName");
        intParam.windowsLookAndFeel = getValue("intWindowsLookAndFeel").equals("TRUE");
        intParam.askName = getValue("intAskName").equals("TRUE");
        intParam.manualWeight = getValue("intManualWeight").equals("TRUE");
        intParam.showSDSevk = getValue("intShowSDSevk").equals("TRUE");
        intParam.showSDEmpty = getValue("intShowSDEmpty").equals("TRUE");
        intParam.showSDFull = getValue("intShowSDFull").equals("TRUE");
        intParam.showMMEmpty = getValue("intShowMMEmpty").equals("TRUE");
        intParam.showMMFull = getValue("intShowMMFull").equals("TRUE");
        intParam.showFreeEmpty = getValue("intShowFreeEmpty").equals("TRUE");
        intParam.showFreeFull = getValue("intShowFreeFull").equals("TRUE");
        intParam.showCompany = getValue("intShowCompany").equals("TRUE");
        
        comParam = new ComParam();
        
        temp = getValue("comDataBits");
        if (temp.equals("DATABITS_5")) comParam.dataBits = SerialPort.DATABITS_5;
        if (temp.equals("DATABITS_6")) comParam.dataBits = SerialPort.DATABITS_6;
        if (temp.equals("DATABITS_7")) comParam.dataBits = SerialPort.DATABITS_7;
        if (temp.equals("DATABITS_8")) comParam.dataBits = SerialPort.DATABITS_8;
        
        comParam.id = Integer.parseInt(getValue("comID"));
        comParam.name = getValue("comName");
        
        temp = getValue("comParity");
        if (temp.equals("PARITY_EVEN")) comParam.parity = SerialPort.PARITY_EVEN;
        if (temp.equals("PARITY_MARK")) comParam.parity = SerialPort.PARITY_MARK;
        if (temp.equals("PARITY_NONE")) comParam.parity = SerialPort.PARITY_NONE;
        if (temp.equals("PARITY_ODD")) comParam.parity = SerialPort.PARITY_ODD;
        if (temp.equals("PARITY_SPACE")) comParam.parity = SerialPort.PARITY_SPACE;
        
        comParam.speed = Integer.parseInt(getValue("comSpeed"));
        
        temp = getValue("comStopBits");
        if (temp.equals("STOPBITS_1")) comParam.parity = SerialPort.STOPBITS_1;
        if (temp.equals("STOPBITS_1_5")) comParam.parity = SerialPort.STOPBITS_1_5;
        if (temp.equals("STOPBITS_2")) comParam.parity = SerialPort.STOPBITS_2;
        
        comParam.testMode = getValue("comTestMode").equals("TRUE");
        comParam.defaultEmptyWeight = Double.parseDouble(getValue("comDefaultEmptyWeight"));
        comParam.defaultFullWeight = Double.parseDouble(getValue("comDefaultFullWeight"));
        try
        {
            comParam.verbose = getValue("comVerbose").equals("TRUE");
        }
        catch(Exception ex)
        {
            comParam.verbose = false;
        }
    }
    
    public void readSteelyardParam()
    {
        steelyardParam = new SteelyardParam();
        try
        {
            steelyardParam.read(intParam.kantarID);
        }
        catch(Exception ex)
        {
            Main.appendLog("Kantar ayarlarý okunurken bir hata oluþtu: " + ex.toString());
        }        
    }
    
    private String getValue(String Key)
    {
        int eqPos = 0;
        String curKey = "";
        String ret = "";
        boolean eqFound = false;
        boolean keyFound = false;
        
        for (int n = 0; n < fileCont.size(); n++)
        {
            String s = (String) fileCont.get(n);
            eqFound = false;
            keyFound = false;
            curKey = "";
            
            for (int m = 0; m < s.length(); m++)
            {
                String letter = s.substring(m, m + 1);
                if (!eqFound)
                {
                    if (letter.equals("=")) eqFound = true; else curKey += letter;
                }
                else
                {
                    if (curKey.equals(Key)) 
                    {
                        keyFound = true;
                        ret += letter;
                    }
                }
            }
            
            if (keyFound) return ret;
        }
        
        return ret;
    }
    
}
