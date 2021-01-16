/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plansza;

import java.awt.Graphics;
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
public interface IPlansza {
    
    //public void paintComponent(Graphics g);
    public Object wykonaj(Visitor v);
    //public void zaladujTlo();
    public void wybierzGrafiki(IPionekFactory factory);
    public ArrayList<WstawPole> pobierzPlansze();
        
        
}
