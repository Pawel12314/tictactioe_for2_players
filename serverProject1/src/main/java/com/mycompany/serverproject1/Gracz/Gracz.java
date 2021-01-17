/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz;

import Obserwator.Gra;
import Obserwator.IGra;
import com.mycompany.serverproject1.Gracz.Polecenie.Polecenie;
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
import java.util.concurrent.LinkedBlockingQueue;
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
    public IStanGracza stan;
    Object graczMutex;
    public BlockingQueue<Polecenie>queuePolecenie;
    
    public BlockingQueue<String> queue;
    //Object serverMutex;
    public Gracz(IPionek p,Socket socket, IGra g)
    {
        graczMutex = new Object();
        this.socket=socket;
        this.pionek = p;
        queue= new LinkedBlockingDeque<String>();
        queuePolecenie = new LinkedBlockingQueue<Polecenie>();
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
            s = s.replace("\n", "").replace("\r", "");
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
              
             
              String command = getPlayerStream();
              
              if(!command.isEmpty() && !command.isBlank())
              {
                  stan.pobierzStrumienWejsciowy(command);
              }
              if(queuePolecenie.isEmpty())
              {
                  continue;
              }
              queuePolecenie.take().wykonaj();
             
            }
            
        }
        catch(Exception e)
        {
            
        }
        
        
    }

   
   
    private class StanOczekiwanieNaPrzeciwnika extends IStanGracza
    {
        
        public StanOczekiwanieNaPrzeciwnika()
        {
            
        }
        public synchronized String pobierzStrumienWejsciowy(String strumien)
        {
             //output.println(strumien);
            String[] command = strumien.split(" ");
            
            if(strumien.startsWith("exit"))
            {
                //output.write(EXIT_MSG_KOLEJKA);
                    gra_mediator.przerwijGre();
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
        public synchronized Boolean sprawdzCzyKoniec() {
            return false;
        }

        @Override
        public synchronized void zmienTure() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public synchronized void zakonczGre() {
            output.println(EXIT_MSG_KOLEJKA);
            stan = new StankoniecGry();
        }

        @Override
        public synchronized void wygrana() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public synchronized void remis() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void wstawPionka(char pionek, int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void rozpocznijGre(Character pionekRozpoczynajacy) {
           
            
              
                try
                {
                    if(pionekRozpoczynajacy.equals(pionek.getPionek()))
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

        
        
        
        
    }
    
    private class StanTuraGracza extends IStanGracza
    {
        public StanTuraGracza()
        {
            
        }

        @Override
        public synchronized String pobierzStrumienWejsciowy(String strumien) {
           // output.println(strumien);
            String[] command = strumien.split(" ");
          //  output.print("\n\n\n");
          //  output.println(command.length);
           // output.println(command[0]);
           // output.print("\n\n\n");
            if(command[0].equals("exit") && command.length==1)
            {
                //output.write(EXIT_MSG_KOLEJKA);
                    gra_mediator.przerwijGre();
            }
            
            
            
           
            
            
            else if(command[0].equals("move") && command.length==3)
            {
              //  output.println(command[1]+" "+command[2]+" endl");
                try
                {
                    int x = Integer.parseInt(command[1]);
                    int y = Integer.parseInt(command[2]);
                    if(gra_mediator.czyMogeRuszyc(x, y))
                    {
                        
                        gra_mediator.wstawPionka(x, y, pionek);
                        
                        
                        
                        
                        
                    }
                    else{
                 //       output.println("nie mozna ruszyc");
                        output.println(BAD_MOVE);
                    }
                    
                }
                catch(NumberFormatException e)
                {
                    e.printStackTrace();
                    output.println(BAD_MOVE);
                }
                catch(NoSuchFieldException e)
                {
                    e.printStackTrace();
                }
            }
            
            else
            {
               // output.println("zla komenda111");
                output.println(ZLA_INSTRUKCJA);
            }
            
            return "";
        }

        @Override
        public synchronized Boolean sprawdzCzyKoniec() {
            return false;
        }

        @Override
        public synchronized void zmienTure() {
            stan = new StanTuraPrzeciwnika();
        }

        @Override
        public  synchronized void zakonczGre() {
            output.println(EXIT_MSG_GRA);
            stan = new StankoniecGry();
        }

        @Override
        public synchronized void remis() {
            output.println(REMIS);
            stan = new StankoniecGry();
        }

        @Override
        public synchronized void wygrana() {
            output.println(YOU_WIN);
            stan = new StankoniecGry();
        }

        @Override
        public void wstawPionka(char pionek, int x, int y) {
            output.println("PUT "+pionek+" "+String.valueOf(x)+" "+String.valueOf(y));
                
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

        @Override
        public void rozpocznijGre(Character pionek) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class StanTuraPrzeciwnika extends IStanGracza{
        public StanTuraPrzeciwnika(){
            
        }

        @Override
        public synchronized String pobierzStrumienWejsciowy(String strumien) {
            // output.println(strumien);
            String[] command = strumien.split(" ");
            
            if(strumien.startsWith("exit"))
            {
              //  output.write(EXIT_MSG_GRA);
                    gra_mediator.przerwijGre();
            }
            else if(strumien.startsWith("move"))
            {
                output.println(NIE_TWOJA_TURA);
            }
            
            else
            {
                //output.println(strumien+"\n\n\n\n");
                output.println(ZLA_INSTRUKCJA);
            }
           
        return "";
        
        }

        @Override
        public synchronized Boolean sprawdzCzyKoniec() {
            return false;
        }

        @Override
        public synchronized void zmienTure() {
           stan = new StanTuraGracza();
        }

        @Override
        public synchronized void zakonczGre() {
            output.println(EXIT_MSG_GRA);
            stan = new StankoniecGry();
        }

        @Override
        public synchronized void remis() {
            output.println(REMIS);
            stan = new StankoniecGry();
        }

        @Override
        public synchronized void wygrana() {
            output.println(OPPONENT_WIN);
            stan = new StankoniecGry();
        }

        @Override
        public void wstawPionka(char pionek, int x, int y) {
            output.println("PUT "+pionek+" "+String.valueOf(x)+" "+String.valueOf(y));
        }

        @Override
        public void rozpocznijGre(Character pionek) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    private class StankoniecGry extends IStanGracza
    {
        public StankoniecGry()
        {
            
        }
       

        @Override
        public synchronized String pobierzStrumienWejsciowy(String strumien) {
           
            
                output.println(ZLA_INSTRUKCJA);
            
        return "";
        }

        @Override
        public synchronized Boolean sprawdzCzyKoniec() {
            return true;
        }

        @Override
        public synchronized void zmienTure() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public synchronized void zakonczGre() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public synchronized void wygrana() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public  synchronized void remis() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void wstawPionka(char pionek, int x, int y) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void rozpocznijGre(Character pionek) {
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
