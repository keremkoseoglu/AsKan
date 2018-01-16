/*
 * PrintSDSevk.java
 *
 * Created on 27 Ekim 2007 Cumartesi, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.printing;

import askan.Main;
import askan.systems.*;
import java.awt.*;
import java.awt.print.*;
import javax.print.*;

/**
 *
 * @author Kerem
 */
public class PrintSDSevk implements Printable {
    
    private SalesProcess salesProcess;
    
    /** Creates a new instance of PrintSDSevk */
    public PrintSDSevk(SalesProcess SP) {
        salesProcess = SP;
    }
    
    @Override
  public int print (Graphics g, PageFormat f, int pageIndex)
  {
    if (pageIndex == 0)
    {
      PrintMaster pm = new PrintMaster();
        
      Paper pap = new Paper();
      pap.setSize(PrintMaster.paperWidth, PrintMaster.paperHeight);
      f.setPaper(pap);
      
      pm.paintTitle(g, "YÜKLEME EMRÝ " + Main.config.intParam.kantarID + "-" + askan.systems.Sap.getNumc(salesProcess.id));
      
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Plaka", salesProcess.trmtyp);
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Operatör", Main.operatorName);
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Tarih", salesProcess.dateSevk == null ? "" : Sql.sqlFormatDate(salesProcess.dateSevk, Sql.DATE_FORMAT.TURKISH, true));
      pm.newLine();      
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Müþteri", salesProcess.getCustomerDisplayText());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Malzeme", salesProcess.material.getDisplayText());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Ýrs. Miktarý", salesProcess.lfimg.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Boþ Aðýrlýk", salesProcess.emptyWeight.toString());
      pm.newLine();      
      
      double d = salesProcess.emptyWeight.toKG() + salesProcess.lfimg.toKG();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Hedef Aðýrlýk", String.valueOf(d) + " KG");
      pm.newLine();            
      
      return PAGE_EXISTS;
    }
    else return NO_SUCH_PAGE;
  }    
        
    
}
