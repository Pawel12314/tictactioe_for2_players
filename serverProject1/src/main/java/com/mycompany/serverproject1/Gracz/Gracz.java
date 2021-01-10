/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz;

import Obserwator.Gra;
import Obserwator.IGra;
import com.mycompany.serverproject1.Gracz.Stany.IStanGracza;
import com.mycompany.serverproject1.Pionek.IPionek;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Geoff
 */
public class Gracz extends IGraczObserwator{
    private IPionek pionek;
    Socket socket;
    IGra gra_mediator;
    Scanner input;
    PrintWriter output;
    IStanGracza stan;
    Object graczMutex;
    //Object serverMutex;
    public Gracz(IPionek p,Socket socket, IGra g)
    {
        graczMutex = new Object();
        this.socket=socket;
        this.pionek = p;
        
        gra_mediator = g;
        try
        {
            output = new PrintWriter(socket.getOutputStream());
            
        }
        catch(IOException ioe)
        {
            gra_mediator.zakonczGre();
        }
        
        
        stan = new StanOczekiwanieNaPrzeciwnika();
    }
    /*public Boolean czyMogePostawićPionka()
    {
        
    }*/
    
    public synchronized void updateRozpocznijGre()
    {
        System.out.println("zaladowano nowy thread");
        stan = new StanTuraGracza();
        
        //synchronized()
        output.println("ROZPOCZETO GRE\n");    
        
       
        
    }
    public  void zakonczGre()
    {
        
        //stan = new StankoniecGry();
        //synchronized(graczMutex)
        //{
            try
            {
                
                socket.close();
                input.close();
                output.close();
            }
            catch(IOException e)
            {

            }
        //}
        
    }
           
    public void run() {
        //Socket socket;
        //stan = OczekiwanieNaPrzeciwnika;
        try
        {
            //socket = new Socket(59090);
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            while( !socket.isClosed() && input.hasNextLine())
            {
                
                /*if(stan.sprawdzCzyKoniec()==true)
                {
                    //socket.close();
                    break;
                }*/
                //synchronized(graczMutex)
                try{
                    System.out.println("running");
                    stan.pobierzStrumienWejsciowy(input.nextLine());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                
             
            }
            //output.close();
            //socket.close();
        }
        catch(IOException ioe)
        {
            
        }
        
        
    }
   
    private class StanOczekiwanieNaPrzeciwnika extends IStanGracza
    {
        public StanOczekiwanieNaPrzeciwnika()
        {
            
        }
        public String pobierzStrumienWejsciowy(String strumien)
        {
            
            if(strumien.startsWith("exit"))
            {
                output.write(EXIT_MSG_KOLEJKA);
                    gra_mediator.zakonczGre();
            }
            else if(strumien.startsWith("move"))
            {
                output.println(CANT_MOVE);
            }
            else
            {
                output.println(ZLA_INSTRUKCJA);
            }
            
        return "";
        }

        @Override
        public Boolean sprawdzCzyKoniec() {
            return false;
        }

        
        
        
        
    }
    
    private class StanTuraGracza extends IStanGracza
    {
        public StanTuraGracza()
        {
            
        }

        @Override
        public String pobierzStrumienWejsciowy(String strumien) {
            output.println(strumien);
            String[] command = strumien.split(" ");
            output.println("----");
            for(String s : command)
            {
                output.println(s);
            }
            if(command[0].equals("exit") && command.length==1)
            {
                output.write(EXIT_MSG_KOLEJKA);
                    gra_mediator.zakonczGre();
            }
            else if(command[0].equals("move") && command.length==3)
            {
                try
                {
                    int x = Integer.parseInt(command[1]);
                    int y = Integer.parseInt(command[2]);
                    if(gra_mediator.czyMogeRuszyc(x, y))
                    {
                        gra_mediator.wstawPionka(x, y, pionek);
                        stan=new StanTuraPrzeciwnika();
                    }
                    else{
                        output.println("nie mozna ruszyc");
                        output.println(BAD_MOVE);
                    }
                    
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                    output.write(BAD_MOVE);
                }
            }
            else
            {
                output.println("zla komenda111");
                output.println(ZLA_INSTRUKCJA);
            }
            
            return "";
        }

        @Override
        public Boolean sprawdzCzyKoniec() {
            return false;
        }
        
    }
    
    private class StanTuraPrzeciwnika extends IStanGracza{
        public StanTuraPrzeciwnika(){
            
        }

        @Override
        public String pobierzStrumienWejsciowy(String strumien) {
             
            
            if(strumien.startsWith("exit"))
            {
                output.write(EXIT_MSG_GRA);
                    gra_mediator.zakonczGre();
            }
            else if(strumien.startsWith("move"))
            {
                output.println(NIE_TWOJA_TURA);
            }
            else
            {
                output.println(ZLA_INSTRUKCJA);
            }
           
        return "";
        
        }

        @Override
        public Boolean sprawdzCzyKoniec() {
            return false;
        }
        
    }
    private class StankoniecGry extends IStanGracza
    {
        public StankoniecGry()
        {
            
        }
       

        @Override
        public String pobierzStrumienWejsciowy(String strumien) {
             if(strumien.startsWith("exit"))
            {
                output.write(EXIT_MSG_GRA);
                    gra_mediator.zakonczGre();
            }
            else if(strumien.startsWith("move"))
            {
                output.println(NIE_TWOJA_TURA);
            }
            else
            {
                output.println(ZLA_INSTRUKCJA);
            }
        return "";
        }

        @Override
        public Boolean sprawdzCzyKoniec() {
            return true;
        }

       
    }
    
    
}
