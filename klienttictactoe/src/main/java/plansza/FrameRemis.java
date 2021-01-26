/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plansza;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Geoff
 */
public class FrameRemis extends IFrame{
      int width;
    int height;
    Graphics2D graphic;
    public FrameRemis(Graphics g,int width,int height)
    {
        
        this.graphic=(Graphics2D)g;
        this.width=width;
        this.height = height;
        /*linie = new ArrayList<Line2D>();
        Line2D s1 = new Line2D.Double(new Point(width/3,0),new Point(width/3,height));
        linie.add(s1);
        Line2D s2 = new Line2D.Double(new Point((width/3)*2,0),new Point((width/3)*2,height));
        linie.add(s2);
        
        Line2D x1 = new Line2D.Double(new Point(0,(height/3)),new Point(width,(height/3)));
        linie.add(x1);
        Line2D x2 = new Line2D.Double(new Point(0,(height/3)*2),new Point(width,(height/3)*2));
        linie.add(x2);*/
    }
    public void addVertical(int denominator,int counter)
    {
        graphic.setStroke(new BasicStroke(5));
        graphic.setColor(Color.YELLOW);
         graphic.drawLine( (int)(width/denominator)*counter,0, (int)(width/denominator)*counter,height);
    }
    public void addHorizontal(int denominator,int counter)
    {
        graphic.setStroke(new BasicStroke(5));
        graphic.setColor(Color.YELLOW);
        graphic.drawLine( 0,(int)(height/denominator)*counter ,width, (int)(height/denominator)*counter);
    }
    public Graphics2D getLinie()
    {
        return graphic;
    }
}
