/*
 * IntThread.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 11:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package askan.sync;

/**
 *
 * @author Kerem
 */
import java.util.*;
import javax.swing.event.*;
import askan.*;

public class IntThread extends Thread {
    
    public static String SYNC_OVER = "Senkronizasyon bitti";
    protected EventListenerList listenerList;

    
    /**
     * Creates a new instance of IntThread
     */
    public IntThread() {
        listenerList = new EventListenerList();
    }
    
    public void run()
    {
        IntTimer it = new IntTimer();
        Main.syncMaster.addIntEventListener(
                new IntEventListener()
                {
                    public void intEventOccured(IntEvent Evt)
                    {
                        fireIntEvent(Evt);
                    }
                }
        );
        
        Timer t = new Timer();
        long delay = Main.config.syncParam.period;
        if (Main.config.syncParam.syncAtStartup) it.run();
        t.scheduleAtFixedRate(it, delay, Main.config.syncParam.period); 
    }
    
    public void addIntEventListener(IntEventListener Listener) 
    {
        listenerList.add(IntEventListener.class, Listener);
    }   
    
    private void fireIntEvent(IntEvent Evt)
    {
        Object[] listeners = listenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i+=2) {
            if (listeners[i] == IntEventListener.class) {
                ((IntEventListener)listeners[i+1]).intEventOccured(Evt);
            }
        }
    }   
    
    private void fireIntEvent(String S)
    {
        IntEvent ie = new IntEvent(this);
        ie.text = S;
        fireIntEvent(ie);        
    }    
    
}
