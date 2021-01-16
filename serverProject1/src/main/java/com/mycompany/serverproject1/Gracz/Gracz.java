/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz;

import Obserwator.Gra;
import Obserwator.IGra;
import com.mycompany.serverproject1.Gracz.Stany.IStanFabryka;
import com.mycompany.serverproject1.Gracz.Stany.IStanGracza;
import com.mycompany.serverproject1.Pionek.IPionek;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author Geoff
 */
public class Gracz extends Thread{//
    //implements IGraczObserwator{
    private IPionek pionek;
    Socket  socket;
    IGra gra_mediator;
    Scanner input;
    PrintWriter output;
    IStanGracza stan;
    Object graczMutex;
    
    public BlockingQueue<String> queue;
    //Object serverMutex;
    public Gracz(IPionek p,Socket socket, IGra g)
    {
        graczMutex = new Object();
        this.socket=socket;
        this.pionek = p;
        queue= new LinkedBlockingDeque<String>();
       /* try
        {
            socket.setSoTimeout(5000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }*/
        gra_mediator = g;
        try
        {
            
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream());
            
        }
        catch(IOException ioe)
        {
            //gra_mediator.zakonczGre();
        }
        
        
        stan = new StanOczekiwanieNaPrzeciwnika();
    }
    /*public Boolean czyMogePostawićPionka()
    {
        
    }*/
    
    public void updateRozpocznijGre(IStanGracza stan)
    {
        System.out.println("zaladowano nowy thread");
        this.stan = stan;
        
        //synchronized()
        output.println("ROZPOCZETO GRE\n");    
        
       
        
    }
    public synchronized void przerwijGre()
    {
        
        //stan = new StankoniecGry();
        //synchronized(graczMutex)
        //{
           /* try
            {
                
                //socket.close();
                input.close();
                output.close();
            }
            catch(IOException e)
            {

            }*/
        //}
        
    }
    public String getPlayerStream()
    {
        ArrayList<Byte> ar = new ArrayList<Byte>();
        while(true)
        {
            try
            {
                byte[] x =socket.getInputStream().readNBytes(1);
                
                ar.add(x[0]);
            }
            catch(Exception e)
            {
                //e.printStackTrace();
                break;
            }
        }
        Byte[] Bytes= new Byte[ar.size()];
        ar.toArray(Bytes);
        byte[] bytes = new byte[ar.size()];
        int iter=0;
        for(Byte b: Bytes)
        {
            
            bytes[iter++] = b.byteValue();
            
        }
        String s = new String(bytes, StandardCharsets.UTF_8);
        //if(s.endsWith("\n"));
            s = s.replace("\n", "");
//System.out.println(s);
        return s;
    }
    public void run() {
        //Socket socket;
        //stan = OczekiwanieNaPrzeciwnika;
        int i = 0;
        try
        {
            //socket = new Socket(59090);
            input = new Scanner(socket.getInputStream());
            //socket.
            output = new PrintWriter(socket.getOutputStream(), true);
            
            try
            {
                socket.setSoTimeout(500);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            while( !stan.sprawdzCzyKoniec())
            {
              
              if(stan instanceof StankoniecGry)
              {
                  System.out.println("EXIT!!!!!!!!!!!1");
              }
              String command = getPlayerStream();
              if(!command.isEmpty() && !command.isBlank())
              {
                  queue.add(command);
              }
              if(queue.isEmpty())
              {
                  continue;
              }
              stan.pobierzStrumienWejsciowy(queue.take());
             
            }
            //output.close();
            //socket.close();
        }
        catch(Exception e)
        {
            
        }
        
        
    }

    public void zmienTure() {
        stan.zmienTure();
    }

   
    public void koniecGry() {
        stan.zakonczGre();
    }
   
    private class StanOczekiwanieNaPrzeciwnika extends IStanGracza
    {
        
        public StanOczekiwanieNaPrzeciwnika()
        {
            
        }
        public String pobierzStrumienWejsciowy(String strumien)
        {
             output.println(strumien);
            String[] command = strumien.split(" ");
            
            if(strumien.startsWith("exit"))
            {
                output.write(EXIT_MSG_KOLEJKA);
                    gra_mediator.przerwijGre();
            }
            else if(strumien.startsWith("move"))
            {
                output.println(CANT_MOVE);
            }
            else if(command[0].equals(Gra.ZAKONCZONO_GRE) && command.length==1)
            {
                stan.zakonczGre();
            }
            else if(command[0].equals(Gra.ROZPOCZYNA_O) && command.length==1)
            {
                try
                {
                    output.println(pionek.getPionek());
                }
                catch(Exception e)
                {
                    
                }
                
                      
                     
                try
                {
                    if(pionek.getPionek()=='o')
                    {
                        stan = new StanTuraGracza();
                    }
                    else
                    {
                        stan = new StanTuraPrzeciwnika();
                    }
                }catch(NoSuchFieldException e)
                {
                    e.printStackTrace();
                }
            }
            else if(command[0].equals(Gra.ROZPOCZYNA_X) && command.length==1)
            {
                try
                {
                    output.println(pionek.getPionek());
                }
                catch(Exception e)
                {
                    
                }
                try
                {
                    if(pionek.getPionek()=='x')
                    {
                        stan = new StanTuraGracza();
                    }
                    else
                    {
                        stan = new StanTuraPrzeciwnika();
                    }
                }catch(NoSuchFieldException e)
                {
                    e.printStackTrace();
                }
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

        @Override
        public void zmienTure() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void zakonczGre() {
            output.println(EXIT_MSG_KOLEJKA);
            stan = new StankoniecGry();
        }

        @Override
        public void wygrana() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remis() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                //output.write(EXIT_MSG_KOLEJKA);
                    gra_mediator.przerwijGre();
            }
            
            else if(command[0].equals(Gra.KTOS_WYGRAL)&&command.length==1)
            {
                stan.wygrana();
            }
            else if(command[0].equals(Gra.KONIEC_GRY)&&command.length==1)
            {
                stan.remis();
            }
            else if(command[0].equals(Gra.ZAKONCZONO_GRE) && command.length==1)
            {
                stan.zakonczGre();
            }
            else if(command[0].equals(Gra.ZMIANA_TURY)&&command.length==1)
            {
                stan.zmienTure();
            }
            else if(command[0].equals(Gra.PUT_PIONEK_COMMAND)&&command.length==4)
            {
                output.write(RUCH+" "+command[1]+" "+command[2]+" "+command[3]);
                
                if(gra_mediator.sprawdzCzyWygrana())
                        {
                            gra_mediator.poinformujOWygranej();
                            
                        }
                        
                        else
                        {
                            if(gra_mediator.sprawdzCzyKoniec())
                            {
                                gra_mediator.przerwijGre();
                            }
                            else
                            {
                               gra_mediator.zmienTure(); 
                            }
                            
                        }
                
            }
            else if(command[0].equals("move") && command.length==3)
            {
                output.println(command[1]+" "+command[2]+" endl");
                try
                {
                    int x = Integer.parseInt(command[1]);
                    int y = Integer.parseInt(command[2]);
                    if(gra_mediator.czyMogeRuszyc(x, y))
                    {
                        
                        gra_mediator.wstawPionka(x, y, pionek);
                        
                        
                        
                        
                        
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
                catch(NoSuchFieldException e)
                {
                    e.printStackTrace();
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

        @Override
        public void zmienTure() {
            stan = new StanTuraPrzeciwnika();
        }

        @Override
        public void zakonczGre() {
            output.println(EXIT_MSG_GRA);
            stan = new StankoniecGry();
        }

        @Override
        public void remis() {
            output.println(REMIS);
            stan = new StankoniecGry();
        }

        @Override
        public void wygrana() {
            output.println(YOU_WIN);
            stan = new StankoniecGry();
        }
        
    }
    
    private class StanTuraPrzeciwnika extends IStanGracza{
        public StanTuraPrzeciwnika(){
            
        }

        @Override
        public String pobierzStrumienWejsciowy(String strumien) {
             output.println(strumien);
            String[] command = strumien.split(" ");
            
            if(strumien.startsWith("exit"))
            {
                output.write(EXIT_MSG_GRA);
                    gra_mediator.przerwijGre();
            }
            else if(strumien.startsWith("move"))
            {
                output.println(NIE_TWOJA_TURA);
            }
            else if(command[0].equals(Gra.PUT_PIONEK_COMMAND)&&command.length==4)
            {
               output.write(RUCH+" "+command[1]+" "+command[2]+" "+command[3]);
                
            }
            else if(command[0].equals(Gra.KTOS_WYGRAL)&&command.length==1)
            {
                stan.wygrana();
            }
            
            else if(command[0].equals(Gra.KONIEC_GRY)&&command.length==1)
            {
                stan.remis();
            }
            else if(command[0].equals(Gra.ZAKONCZONO_GRE) && command.length==1)
            {
                stan.zakonczGre();
            }
            else if(command[0].equals(Gra.ZMIANA_TURY)&&command.length==1)
            {
                stan.zmienTure();
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

        @Override
        public void zmienTure() {
           stan = new StanTuraGracza();
        }

        @Override
        public void zakonczGre() {
            output.println(EXIT_MSG_GRA);
            stan = new StankoniecGry();
        }

        @Override
        public void remis() {
            output.println(REMIS);
            stan = new StankoniecGry();
        }

        @Override
        public void wygrana() {
            output.println(OPPONENT_WIN);
            stan = new StankoniecGry();
        }
        
    }
    private class StankoniecGry extends IStanGracza
    {
        public StankoniecGry()
        {
            
        }
       

        @Override
        public String pobierzStrumienWejsciowy(String strumien) {
           
            
                output.println(ZLA_INSTRUKCJA);
            
        return "";
        }

        @Override
        public Boolean sprawdzCzyKoniec() {
            return true;
        }

        @Override
        public void zmienTure() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void zakonczGre() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void wygrana() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remis() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

       
    }
    public class StanFabrykaMojaTura extends IStanFabryka
    {

        @Override
        public IStanGracza stworzStan() {
            return new StanTuraGracza();
        }
        
        
    }
    public class StanFabrykaTuraPrzeciwnika extends IStanFabryka
    {
        @Override
        public IStanGracza stworzStan()
        {
            return new StanTuraPrzeciwnika();
        }
    }
    
}
