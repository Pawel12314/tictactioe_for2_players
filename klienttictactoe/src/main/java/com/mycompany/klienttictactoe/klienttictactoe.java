/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klienttictactoe;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    public static String EXIT_MSG_KOLEJKA = "QUIT_QUEUE";
    public static String NIE_TWOJA_TURA = "NOT_TURN";
    public static String EXIT_MSG_GRA = "BREAK";
    public static String ZLA_INSTRUKCJA = "BAD_INSTR";
    public static String BAD_MOVE="BAD_MOVE";
    public static String CANT_MOVE="CANT_MOVE";
    public static String OPPONENT_WIN="OPPONENT_WIN";
    public static String YOU_WIN="WON";
    public static String REMIS="REMIS";
    public static String RUCH="MOVE";
    public static String PUT="PUT";
    
    
    public static String GAME_EXIT_MSG_KOLEJKA = "wyszedles z kolejki";
    public static String GAME_NIE_TWOJA_TURA = "nie twoja tura";
    public static String GAME_EXIT_MSG_GRA = "zakonczono gre";
    public static String GAME_ZLA_INSTRUKCJA = "niepoprawna instrukcja";
    public static String GAME_BAD_MOVE="zly ruch";
    public static String GAME_CANT_MOVE="nie mozesz wykonac ruchu";
    public static String GAME_OPPONENT_WIN="przeciwnik wygral";
    public static String GAME_YOU_WIN="Wygrales";
    public static String GAME_REMIS="REMIS";
    //public static String GAME_RUCH="MOVE";
    
    Socket socket;
    Scanner in;
    PrintWriter out;
    StandardFactory pionekFactory;
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
    
    

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l); //To change body of generated methods, choose Tools | Templates.
    }
    
    public klienttictactoe(String address)
    {
        this.setPreferredSize(new Dimension(300, 300));
        try
        {
            socket = new Socket(address, 58901);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
           background = new BackgroundFactory(); 
        }
        catch(Exception e)
        {
            e.printStackTrace();
            
        }
        plansza = new Plansza();
        pionekFactory = new StandardFactory();
        
        
        fabryka = new PionekPylekFactory();
        this.addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent ev) {
                int x = (ev.getX()-ev.getX()%100)/100;
                int y = (ev.getY()-ev.getY()%100)/100;
                
                
                out.println("move"+" "+String.valueOf(x)+" "+String.valueOf(y));
                //JOptionPane.showMessageDialog(null, "click: x>"+x+" y>"+y,"action",JOptionPane.INFORMATION_MESSAGE);
		}

            public void mouseReleased(MouseEvent ev) {

            }
        });
        
        
             
        
    }
    public void graj()
    {
        //this.setBackground(Color.black);
        //this.setLayout(new GridLayout(3, 3, 2, 2));
        while(in.hasNextLine())
        {
            //String command= in.nextLine();
            
            String[] command = in.nextLine().replace("\n","").replace("\r","").split(" ");
            if(command[0].equals(BAD_MOVE)&&command.length==1)
            {
                JOptionPane.showMessageDialog(null, GAME_BAD_MOVE,"action",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(command[0].equals(CANT_MOVE)&& command.length==1)
            {
                JOptionPane.showMessageDialog(null, GAME_CANT_MOVE,"action",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(command[0].equals(EXIT_MSG_GRA)&&command.length==1)
            {
                JOptionPane.showMessageDialog(null, GAME_EXIT_MSG_GRA,"action",JOptionPane.INFORMATION_MESSAGE);
                out.close();
                in.close();
            }
            else if(command[0].equals(EXIT_MSG_KOLEJKA)&&command.length==1)
            {
                out.close();
                in.close();
            }
            else if(command[0].equals(NIE_TWOJA_TURA)&&command.length==1)
            {
                JOptionPane.showMessageDialog(null, GAME_NIE_TWOJA_TURA,"action",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(command[0].equals(OPPONENT_WIN)&&command.length==1)
            {
                JOptionPane.showMessageDialog(null, GAME_OPPONENT_WIN,"action",JOptionPane.INFORMATION_MESSAGE);
            }
            else if(command[0].equals(REMIS)&&command.length==1)
            {
              JOptionPane.showMessageDialog(null, GAME_REMIS,"action",JOptionPane.INFORMATION_MESSAGE);  
              
            }
            else if(command[0].equals(YOU_WIN)&& command.length==1)
            {
                JOptionPane.showMessageDialog(null, GAME_YOU_WIN,"action",JOptionPane.INFORMATION_MESSAGE); 
            }
            else if(command[0].equals(PUT)&&command.length==4)
            {
                //JOptionPane.showMessageDialog(null, "otrzymano polecenie ruchu","action",JOptionPane.INFORMATION_MESSAGE);  
                
                try
                {
                    //JOptionPane.showMessageDialog(null, "ruszyl pionek : ->"+command[1],"action",JOptionPane.INFORMATION_MESSAGE); 
                    int x = Integer.parseInt(command[2]);
                    int y = Integer.parseInt(command[3]);
                     if(command[1].equals("x"))
                    {
                        WstawPole pole = new WstawX(new Point(x,y),pionekFactory);
                        plansza.wykonaj(pole);
                    }
                     else if(command[1].equals("o"))
                     {
                         WstawPole pole = new WstawO(new Point(x,y),pionekFactory);
                        plansza.wykonaj(pole);
                     }
                    repaint();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                     JOptionPane.showMessageDialog(null, "BLAD PRZY WSTAWIANIU POLA","action",JOptionPane.INFORMATION_MESSAGE);  
                }
               
            }
            //JOptionPane.showMessageDialog(null, in.nextLine(),"action",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public static void main(String[] args) {
        
        
        
        klienttictactoe board = new klienttictactoe("127.0.0.1");
        JFrame frame = new JFrame("Chess");
        frame.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
          board.out.println("exit");
            
        }
      });
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(board);
                
		frame.pack();
		frame.setVisible(true);
        board.graj();
    }
}
