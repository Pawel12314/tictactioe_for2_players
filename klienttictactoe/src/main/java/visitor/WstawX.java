/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visitor;

import java.awt.Point;
import pionek.PionekFactory.IPionekFactory;
import plansza.Plansza;

/**
 *
 * @author Geoff
 */
public class WstawX extends WstawPole{

    private Point punkt;
    public WstawX(Point p,IPionekFactory factory)
    {
        super(factory.getBitmap_X());
        punkt=p;
    }
    
    public Point getPunkt()
    {
        return punkt;
    }
    @Override
    public Object wykonajNaPlanszy(Plansza p) {
        p.pobierzPlansze().add(this);
        return null;
    }

    @Override
    public Object wykonajNaPowtorce(Plansza p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPath() {
        return path;
    }
    
}
