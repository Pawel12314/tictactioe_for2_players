/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.dekorator;

import java.awt.Graphics2D;
import java.awt.Point;
import pionek.IPionek;

/**
 *
 * @author Geoff
 */
public abstract class IDekoratorPionek implements IPionek {

    
    private IPionek pionek;
    
    public IDekoratorPionek(IPionek p)
    {
        this.pionek = p;
    }
    
    public void draw(Graphics2D g,Point p) {
        pionek.draw(g,p);
    }
    
}
