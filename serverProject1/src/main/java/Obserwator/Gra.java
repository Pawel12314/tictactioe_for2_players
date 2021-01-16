/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obserwator;

import com.mycompany.serverproject1.Gracz.Gracz;
import com.mycompany.serverproject1.Gracz.Stany.IStanFabryka;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.IMetodaPlansza;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.MetodaIteratorPionowy;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.MetodaIteratorPoziomy;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.MetodaIteratorUkosnyDoPrzodu;
import com.mycompany.serverproject1.IteratorPoPlanszy.Gosc.MetodaIteratorUkosnyDoTylu;
import com.mycompany.serverproject1.IteratorPoPlanszy.IIteratorPlansza;
import com.mycompany.serverproject1.IteratorPoPlanszy.IteratorPionowy;
//import com.mycompany.serverproject1.Gracz.IGraczObserwator;
import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Pionek.PionekO;
import com.mycompany.serverproject1.Pionek.PionekPusty;
import com.mycompany.serverproject1.Pionek.PionekX;
import com.mycompany.serverproject1.Plansza.IPlansza;
import java.net.Socket;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Geoff
 */
public class Gra extends IGra{
    
    //private ManagerGier manager;
    public static final String PUT_PIONEK_COMMAND = "PUT";
    public static final String ROZPOCZYNA_X="X_ZACZYNA_GRE";
    public static final String ROZPOCZYNA_O="O_ZACZYNA_GRE";
    public static final String ZMIANA_TURY = "ZMIANA_TURY";
    public static final String ZAKONCZONO_GRE="ZAKOCZONO_GRE";
    public static final String KONIEC_GRY="KONIEC_GRY";
    public static final String KTOS_WYGRAL="KTOS_WYGRAL";
    public static final String KONTYNUUJ_GRE="KONTYNUUJ";
// private Object mutex;
    
    
    
    private Map<Character,Gracz> gracze = new ConcurrentHashMap<Character,Gracz>();
    
    //private Map<IGra,Set<Gracz>>gry = new ConcurrentHashMap<IGra,Set<Gracz>>();
    public Gra(IPlansza plansza)
    {
        super(plansza);
        queue   = new LinkedBlockingQueue<String>();
        
        
    //    this.mutex = new Object();
    }
    @Override
    public void register(Socket  socket)
    {
        
        if(gracze.size()==1)
        {
            IPionek p = new PionekX();
            Gracz g = new Gracz(p,socket,this);
            g.start();
            gracze.put('x', g);
            RozpocznijGre();
        }
        else
        {
            IPionek p = new PionekO();
            Gracz g = new Gracz(p,socket,this);
            g.start();
            gracze.put('o',g);
        }
           
                    
    }
   @Override
    public synchronized void wstawPionka(int x, int y, IPionek p) throws NoSuchFieldException
    {
        plansza.dodajPionka(x, y, p);
    
            //g.queue.add(PUT_PIONEK_COMMAND+" "+ p.getPionek()+" "+String.valueOf(x)+" "+String.valueOf(y));
            for(Gracz g : gracze.values())
            {
                g.queue.add(PUT_PIONEK_COMMAND+" "+ p.getPionek()+" "+String.valueOf(x)+" "+String.valueOf(y));
            }
        
        
    }
     
    @Override
    protected synchronized void RozpocznijGre()
    {
        
        for(Gracz g:gracze.values())
        {
            g.queue.add(ROZPOCZYNA_X);
        }
       
    }
    @Override
  public synchronized boolean czyMogeRuszyc(int x, int y)
    {
        
        if (plansza.SprawdzCzyPoleIstnieje(x, y)==false){
            return false;
        }
        if (plansza.SprawdzCzyPoleZajete(x, y)==true)
        {
            return false;
        }
        return true;
    }
    @Override
    public synchronized int przerwijGre(/*IGraczObserwator gracz*/) {
        //Set<IGraczObserwator>gracze  = manager.pobierzGraczy(this);
        
        for(Gracz g: gracze.values())
        {
            g.queue.add(ZAKONCZONO_GRE);
        }
        
        return 1;
    }

    @Override
    public int getGraczeSize() {
        return gracze.size();
    }

    @Override
    public void zmienTure() {
        for(Gracz g : gracze.values())
        {
            g.queue.add(ZMIANA_TURY);
        }
        
    }

    @Override
    public void poinformujOWygranej() {
        //queue.add(KONIEC_GRY);
        for(Gracz g : gracze.values())
        {
            g.queue.add(KTOS_WYGRAL);
        }
    }
    @Override
    protected boolean iteruj(IIteratorPlansza iter)
    {
        while(iter.czyNastepny())
        {
            try
            {
                IPionek p1 = iter.obecny();
                iter.nastepny();
                IPionek p2 = iter.obecny();
                iter.nastepny();
                IPionek p3 = iter.obecny();
                if(iter.czyNastepny())
                iter.nastepny();
                try
                {
                    if(p1.getPionek()==p2.getPionek()&&p2.getPionek()==p3.getPionek()&&p1.getPionek()==p3.getPionek())
                    {
                        System.out.println(p1.getPionek());
                        return true;
                    }
                }
                catch(NoSuchFieldException e)
                {
                    //System.out.println("pole jest puste");
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                e.printStackTrace();
                //System.out.println("błąd w iteratorze");
            }
            
        }
        return false;
    }
    @Override
    public Boolean sprawdzCzyWygrana() {
        
        for(int i = 0;i<3;i++)
        {
            for(int u = 0 ; u<3 ; u++)
            {
                try
                {
                    System.out.print(plansza.pobierzPionka(i, u).getPionek());
                }
                catch(Exception e){
                    System.out.print("-");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
        IMetodaPlansza metodaPionowy = new MetodaIteratorPionowy();
        IIteratorPlansza iterpionowy = plansza.Pobierziterator(metodaPionowy);
       if(iteruj(iterpionowy)) return true;
       
        
        IMetodaPlansza metodaPoziomy = new MetodaIteratorPoziomy();
        IIteratorPlansza iterpoziomy = plansza.Pobierziterator(metodaPoziomy);
       if(iteruj(iterpoziomy)) return true;
       
       IMetodaPlansza metodaUkosny1 = new MetodaIteratorUkosnyDoPrzodu();
       IIteratorPlansza iterUkosny1 = plansza.Pobierziterator(metodaUkosny1);
       if(iteruj(iterUkosny1)) return true;
       
       IMetodaPlansza metodaUkosny2 = new MetodaIteratorUkosnyDoTylu();
       IIteratorPlansza iterUkosny2 = plansza.Pobierziterator(metodaUkosny2);
       if(iteruj(iterUkosny2)) return true;
       
       return false;
        
    }

    @Override
    public boolean sprawdzCzyKoniec() {
        IMetodaPlansza metoda = new MetodaIteratorPionowy();
        IIteratorPlansza iterator = plansza.Pobierziterator(metoda);
        while(iterator.czyNastepny())
        {
            IPionek p = iterator.nastepny();
            if(p instanceof PionekPusty)
            {
                for(Gracz g: gracze.values())
                {
                    g.queue.add(KONTYNUUJ_GRE);
                }
                return false;
            }
            
        }
        return true;
    }
    
   
    
    
}
