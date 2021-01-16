/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obserwator;

import com.mycompany.serverproject1.Gracz.Gracz;
import com.mycompany.serverproject1.IteratorPoPlanszy.IIteratorPlansza;
//import com.mycompany.serverproject1.Gracz.IGraczObserwator;
import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Plansza.IPlansza;
import java.net.Socket;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**s
 *
 * @author Geoff
 */
public abstract class IGra {
    //private ArrayList<Gracz> obserwujacyGracze;
    protected Object mutex = new Object();
    //protected ManagerGier manager;
    protected IPlansza plansza;
    protected abstract void RozpocznijGre();
    public abstract void wstawPionka(int x, int y, IPionek p) throws NoSuchFieldException;
    public abstract boolean czyMogeRuszyc(int x, int y);
    //public ExecutorService executors;
    public abstract int getGraczeSize();
    public abstract void register(Socket socket);
    
    public abstract void zmienTure();
    public abstract void poinformujOWygranej();
    public abstract Boolean sprawdzCzyWygrana();
    public BlockingQueue<String> queue;
    protected abstract boolean iteruj(IIteratorPlansza iter);
    public abstract boolean sprawdzCzyKoniec();
    public Object serverMutex;
    public IGra( IPlansza plansza)
    {
        //executors = Executors.newFixedThreadPool(2);
        //this.serverMutex=serverMut;
        //this.manager=manager;
        this.plansza = plansza;
    }
    /*
    public void RejestrujGracza(Gracz g)
    {
        obserwujacyGracze.add(g);
    }
    public void WyrejestrujGracza(Gracz g)
    {
        if(obserwujacyGracze.contains(g))
        {
            obserwujacyGracze.remove(g);
        }
        
    }*/
     public abstract int przerwijGre(/*IGraczObserwator gracz*/);
    
    public  Object getMutex()
    {
        return mutex;
    }

    
           

}
    
    
