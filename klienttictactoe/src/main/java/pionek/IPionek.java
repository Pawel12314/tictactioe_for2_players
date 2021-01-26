/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Geoff
 */
public interface IPionek {
    
    public abstract void draw(Graphics2D g ,Point p);
   public abstract IPionek getDecoree();
}
