/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klienttictactoe;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pionek.IPionek;
import pionek.PionekFactory.BackgroundFactory;
import pionek.PionekFactory.IBackgroundFactory;
import pionek.PionekFactory.IPionekFactory;
import pionek.PionekFactory.StandardFactory;
import pionek.dekorator.DekoratorPionek;
import pionek.pylek.PionekPylekFactory;
import plansza.Frame;
import plansza.IPlansza;
import plansza.Plansza;
import visitor.WstawO;
import visitor.WstawPole;
import visitor.WstawX;

/**
 *
 * @author Geoff
 */
public class klienttictactoe extends JPanel{
    private static IPlansza plansza;
    private static PionekPylekFactory fabryka;
    private static IBackgroundFactory background;
    public static final int ZEROX = 23;
    public static final int ZEROY = 7;
    @Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
                Frame frame= new Frame(300,300);
                for(Line2D line : frame.getLinie())
                {
                    g.drawLine((int)line.getP1().getX(), (int)line.getP1().getY(),(int)line.getP2().getX(), (int)line.getP2().getY());
                }
                
		//g.drawImage(background.getBackgorund(), 0, 0, null);
		Graphics2D g2d = (Graphics2D) g;
                
		for (WstawPole pole : plansza.pobierzPlansze()) {//
                    try
                    {
                        IPionek pionek = fabryka.getPionek(pole );
                        //IPionek pionek = new DekoratorPionek(fabryka.getPionek(pole));
                         pionek.draw(g2d,pole.getPunkt());
                         //pionek = pionek.getDecoree();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                       
                }
		
	}
    
    public klienttictactoe()
    {
        try
        {
           background = new BackgroundFactory(); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
        plansza = new Plansza();
        StandardFactory pionekFactory = new StandardFactory();
        WstawPole w = new WstawO(new Point(0,0),pionekFactory);
        plansza.wykonaj(w);
        WstawPole w1 = new WstawO(new Point(1,0),pionekFactory);
        plansza.wykonaj(w1);
        
        WstawPole w2 = new WstawX(new Point(2,2),pionekFactory);
        plansza.wykonaj(w2);
        
        fabryka = new PionekPylekFactory();
        
             
        
    }
    
    public static void main(String[] args) {
        
        klienttictactoe board = new klienttictactoe();
        JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(board);
                
		frame.pack();
		frame.setVisible(true);
        
    }
}
