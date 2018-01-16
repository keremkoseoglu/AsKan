/*
 * IntEventListener.java
 *
 * Created on 26 Þubat 2007 Pazartesi, 13:15
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

public interface IntEventListener extends EventListener {
    
    /** Creates a new instance of IntEventListener */
    public void intEventOccured(IntEvent I);
}
