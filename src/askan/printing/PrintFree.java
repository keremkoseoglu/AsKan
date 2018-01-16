/*
 * PrintSDSevk.java
 *
 * Created on 27 Ekim 2007 Cumartesi, 13:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.printing;

import askan.systems.*;
import java.awt.*;
import java.awt.print.*;
import java.util.Calendar;
import javax.print.*;
import askan.*;

/**
 *
 * @author Kerem
 */
public class PrintFree implements Printable {
    
    private FreeProcess freeProcess;
    
    /** Creates a new instance of PrintSDSevk */
    public PrintFree(FreeProcess FP) {
        freeProcess = FP;
    }
    
  public int print (Graphics g, PageFormat f, int pageIndex)
  {
    if (pageIndex == 0)
    {
      Paper pap = new Paper();
      pap.setSize(PrintMaster.paperWidth, PrintMaster.paperHeight);
      f.setPaper(pap);
        
      PrintMaster pm = new PrintMaster();
      pm.paintTitle(g, "SERBEST TARTIM - " + Main.config.intParam.kantarID + "-" + askan.systems.Sap.getNumc(freeProcess.id));     
      
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Plaka", freeProcess.trmtyp);
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Tarih", Sql.sqlFormatDate(Calendar.getInstance(), Sql.DATE_FORMAT.TURKISH, true));
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Boþ", freeProcess.emptyWeight == null ? "-" : freeProcess.emptyWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Dolu", freeProcess.fullWeight == null ? "-" : freeProcess.fullWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Net", freeProcess.calcWeight == null ? "-" : freeProcess.calcWeight.toString());
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Firma", freeProcess.company == null ? "-" : freeProcess.company);      
      pm.newLine();
      pm.paintTitledString(g, PrintMaster.TABLE_COLUMN.LEFT, "Notlar", freeProcess.notes == null ? "-" : freeProcess.notes);            
      
      return PAGE_EXISTS;
    }
    else return NO_SUCH_PAGE;
  }    
        
    
}
