/*
 * IntTimer.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 11:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.sync;

/**
 *
 * @author Kerem
 */
import askan.*;
import askan.systems.*;
import java.io.*;
import java.util.*;
import javax.swing.event.*;

public class IntTimer extends TimerTask {
    
    /**
     * Creates a new instance of IntTimer
     */

    
    public IntTimer() {
        
    }
    
    
    @Override
    public void run()
    {
        Main.syncMaster.doSync();
    }
    

    
}
