/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.dekorator;

import static com.mycompany.klienttictactoe.klienttictactoe.ZEROX;
import static com.mycompany.klienttictactoe.klienttictactoe.ZEROY;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import pionek.IPionek;

/**
 *
 * @author Geoff
 */
public class DekoratorPionek extends IDekoratorPionek {
    
    
    private int width;
    private int height;
    public DekoratorPionek(IPionek p,int width,int height) {
        super(p);
        this.width=width/3;
        this.height=height/3;
    }
    
    
    
    public void draw(Graphics2D g,Point p)
    {
         AffineTransform trans = g.getTransform();
        AffineTransform tr = new AffineTransform();
        //tr.translate(0,0);
        tr.scale(this.width, this.height);
        g.transform(tr);
        super.draw(g,p);
        g.setTransform(trans);
    }

    @Override
    public IPionek getDecoree() {
        return super.getDecoree();
    }
}
