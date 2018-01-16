/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package askan.printing;

import askan.*;
import java.awt.*;
import java.awt.print.*;
import javax.print.*;

/**
 *
 * @author Kerem
 */
public class PrintMaster {

    public enum TABLE_COLUMN { LEFT, RIGHT };
    
    public static int paperWidth = 480;
    public static int paperHeight = 300;
    
    //public static int paperFrameWidth = 478;
    //public static int paperFrameHeight = 298;
    
    private static int lineSize = 10;
    private static int tab1 = 10;
    private static int tab2 = 70;
    private static int tab3 = 75;
    private static int tab4 = 250;
    private static int tab5 = 310;
    private static int tab6 = 315;
    
    public int linePos;
    
    public PrintMaster()
    {
        linePos = 10;
    }
    
    public void newLine()
    {
        newLine(1);
    }
    
    public void newLine(int LineCount)
    {
        for (int n = 0; n < LineCount; n++) linePos += lineSize;
    }
    
    public void paintTitle(Graphics G, String Title)
    {
      //G.drawRect(1, 1, PrintMaster.paperFrameWidth, PrintMaster.paperFrameHeight);
      
      G.setFont(new Font("Monospaced", Font.PLAIN, 8));
      G.drawString(Main.config.steelyardParam.name1, tab1, linePos);
      newLine();
      G.drawString(Main.config.steelyardParam.addre, tab1, linePos);
      newLine();
      G.drawString("TEL: " + Main.config.steelyardParam.telno + " FAX: " + Main.config.steelyardParam.faxno, tab1, linePos);
      
      newLine(2);
      G.setFont(new Font("Monospaced", Font.BOLD, 10));
      G.drawString(Title, 100, linePos);
      newLine(2);
      
      G.setFont(new Font("Monospaced", Font.PLAIN, 8));
    }
    
    public void paintTitledString(Graphics G, TABLE_COLUMN T, String Title, String Value)
    {
        int pos1, pos2, pos3;
        
        if (T == TABLE_COLUMN.LEFT)
        {
            pos1 = tab1;
            pos2 = tab2;
            pos3 = tab3;
        }
        else
        {
            pos1 = tab4;
            pos2 = tab5;
            pos3 = tab6;
        }
        
        G.drawString(Title, pos1, linePos);
        G.drawString(":", pos2, linePos);      
        G.drawString((Value == null ? "" : Value), pos3, linePos);
    }
    
}
