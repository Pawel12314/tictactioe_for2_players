/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.pylek;

import java.awt.Point;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import pionek.IPionek;
import visitor.WstawPole;

/**
 *
 * @author Geoff
 */
public class PionekPylekFactory {
    private Map<WstawPole,IPionek> pionki = new LinkedHashMap<WstawPole, IPionek>();
    
    public IPionek getPionek(WstawPole point) throws IOException
    {
        if(pionki.containsKey(point))
        {
            return pionki.get(point);
        }
        else
        {
            try
            {
                IPionek pionek = ConcretePionek.getByBitmap(point.getPath());
                pionki.put(point, pionek);
                return pionek;
            }
            catch(IOException e)
            {
                throw new IOException("nie mozna utworzyc pionka");
            }
            
        }
    }
}
