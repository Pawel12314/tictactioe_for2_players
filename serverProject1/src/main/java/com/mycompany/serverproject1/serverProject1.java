/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1;

import Obserwator.Gra;
import Obserwator.IGra;
//import Obserwator.ManagerGier;
import com.mycompany.serverproject1.Gracz.Gracz;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.IMetodaPlansza;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.MetodaIteratorPoziomy;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.MetodaIteratorUkosnyDoPrzodu;
import com.mycompany.serverproject1.IteratorPoPlanszy.IIteratorPlansza;
import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Pionek.PionekFabryka.FabrykaPustyPionek;
import com.mycompany.serverproject1.Pionek.PionekO;
import com.mycompany.serverproject1.Pionek.PionekX;
import com.mycompany.serverproject1.Plansza.IPlansza;
import com.mycompany.serverproject1.Plansza.PlanszaProxyWirtualne;
import java.awt.Frame;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Geoff
 */
public class serverProject1 {
     public static void main(String[] args) throws Exception{
        
        
         try (var listener = new ServerSocket(58901)) {
            // listener.bind(new InetSocketAddress("127.0.0.1", 58901));
            System.out.println("Tic Tac Toe Server is Running...");
            //var pool = Executors.newFixedThreadPool(2);
            //ManagerGier manager = new ManagerGier();
            //var future = listener.accept();
            IGra gra = new Gra(new PlanszaProxyWirtualne());
            while (gra.getGraczeSize()!=2) {
                //manager.register(listener.accept());
                //Future<AsynchronousSocketChannel> asyncFuture = listener
            //  .accept();
                try
                {
                    //final AsynchronousSocketChannel asyncChannel = asyncFuture.get(10,TimeUnit.NANOSECONDS);
                    gra.register(listener.accept());
                }
                catch(Exception e)
                {
                }
                System.out.println("registered new player");
                
                
                
               

            }
        }
        
    }
}
