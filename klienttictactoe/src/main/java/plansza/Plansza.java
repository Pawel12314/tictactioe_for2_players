/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plansza;

import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import pionek.PionekFactory.IPionekFactory;
import visitor.Visitor;
import visitor.WstawPole;

/**
 *
 * @author Geoff
 */
public class Plansza implements IPlansza{
    
    private ArrayList<WstawPole> lista;
    
    
    public Plansza()
    {
        lista = new ArrayList<WstawPole>();
    }
    @Override
    public Object wykonaj(Visitor v) {
        v.wykonaj(this);
        return null;
    }

    @Override
    public void wybierzGrafiki(IPionekFactory factory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<WstawPole> pobierzPlansze() {
        return lista;
    }
    
}
