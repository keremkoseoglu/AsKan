/*
 * Util.java
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

import java.io.*;
import java.util.*;

public class Util {
    
    /** Creates a new instance of Util */
    public Util() {
    }
    
    public static long dateDiff(Calendar C1, Calendar C2)
    {
        return ( C1.getTimeInMillis() - C2.getTimeInMillis() ) / 1000;
    }

    public static ArrayList readTextFile(String path)
    {
        File aFile = new File(path);
        ArrayList contents = new ArrayList();
        BufferedReader input = null;
        
        try
        {
            input = new BufferedReader( new FileReader(aFile));
            String line = null;
            while (( line = input.readLine()) != null)
            {
                contents.add(line);
            }
        }
        catch(Exception ex)
        {
        }
        
        return contents;
    }   
    
}

