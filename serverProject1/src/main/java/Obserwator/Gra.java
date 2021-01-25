/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obserwator;

import com.mycompany.serverproject1.Gracz.Gracz;
import com.mycompany.serverproject1.Gracz.Polecenie.PolecenieRozpocznijGre;
import com.mycompany.serverproject1.Gracz.Polecenie.PolecenieWstawPionka;
import com.mycompany.serverproject1.Gracz.Polecenie.PolecenieWygrana;
import com.mycompany.serverproject1.Gracz.Polecenie.PolecenieWyjdzZgry;
import com.mycompany.serverproject1.Gracz.Polecenie.PolecenieZmianaTury;
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
import com.mycompany.serverproject1.Plansza.FactoryMethodChar;
import com.mycompany.serverproject1.Plansza.FactoryMethodEmptyChar;
import com.mycompany.serverproject1.Plansza.FactoryMethodNotDefinedChar;
import com.mycompany.serverproject1.Plansza.FactoryMethodOPlayer;
import com.mycompany.serverproject1.Plansza.FactoryMethodPlayerXChar;
import com.mycompany.serverproject1.Plansza.IPlansza;
import com.mycompany.serverproject1.Plansza.Pamiatka;
import com.mycompany.serverproject1.Plansza.Plansza;
import com.mycompany.serverproject1.Plansza.PlanszaProxyWirtualne;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.file.Files;
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
    public static final String ROZPOCZYNA_X="1001";
    public static final String ROZPOCZYNA_O="1002";
    public static final String ZMIANA_TURY = "2000";
    public static final String ZAKONCZONO_GRE="3000";
    public static final String KONIEC_GRY="3001";
    public static final String KTOS_WYGRAL="3002";
    public static final String KONTYNUUJ_GRE="4000";
// private Object mutex;
    
    
    private FactoryMethodChar factoryEmptyChar;
    private FactoryMethodChar factoryNDchar;
    private Map<Character,Gracz> gracze = new ConcurrentHashMap<Character,Gracz>();
    private String copyfilename="pamiatka.bin";
    //private Map<IGra,Set<Gracz>>gry = new ConcurrentHashMap<IGra,Set<Gracz>>();
    
    public boolean sprawdzCzyPelna()
    {
        IMetodaPlansza metoda = new MetodaIteratorPionowy();
        IIteratorPlansza iterator = plansza.Pobierziterator(metoda);
        while(iterator.czyNastepny())
        {
            System.out.println("-"+iterator.obecny()+"- ITERATOR");
            if(iterator.obecny()==factoryEmptyChar.getChar()|| iterator.obecny()==factoryNDchar.getChar() )
            {
                return false;
            }
            iterator.nastepny();
        }
        System.out.println("---");
        return true;
    }
    public Gra(IPlansza plansza)
    {
        super(plansza);
        queue   = new LinkedBlockingQueue<String>();
        
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(copyfilename)));
            Pamiatka pam = (Pamiatka)ois.readObject();
            plansza.przywroc(pam);
            
            
            ois.close();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch(IOException e )
        {
            e.printStackTrace();
        }
        
        
        factoryEmptyChar = new FactoryMethodEmptyChar();
        factoryNDchar = new FactoryMethodNotDefinedChar();
        if(sprawdzCzyPelna()==true||sprawdzCzyWygrana()==true)
        {
            System.out.println("plansza jest pelna");
            this.plansza =null;
            this.plansza= new PlanszaProxyWirtualne();
            /*if(sprawdzCzyPelna()==true)
            {
                System.out.println("plansza nie wyczyszczona");
                
            }
            else
            {
                System.out.println("plansza wyczyszczona");
            }*/
            //File dir = new File("./");
            //dir.mkdirs();
            //File tmp = new File(dir, "tmp.txt");
            new File("./"+copyfilename).delete();
          // File.delete(copyfilename);
        }
        
    //    this.mutex = new Object();
    }
    @Override
    public void register(Socket  socket)
    {
        
        if(gracze.size()==1)
        {
            FactoryMethodChar p = new FactoryMethodPlayerXChar();
            Gracz g = new Gracz(p,socket,this);
            g.start();
            gracze.put(p.getChar(), g);
            RozpocznijGre();
        }
        else
        {
            FactoryMethodChar p = new FactoryMethodOPlayer();
            Gracz g = new Gracz(p,socket,this);
            g.start();
            gracze.put(p.getChar(),g);
        }
           
                    
    }
   @Override
    public synchronized void wstawPionka(int x, int y, char p)
    {
        plansza.dodajPionka(x, y, p);
    
            //g.queue.add(PUT_PIONEK_COMMAND+" "+ p.getPionek()+" "+String.valueOf(x)+" "+String.valueOf(y));
            for(Gracz g : gracze.values())
            {
                
                g.queuePolecenie.add(new PolecenieWstawPionka(g, p ,x, y));
                
            }
        
        
    }
    @Override
    public synchronized void wyslijPionka(int x, int y,char p,Gracz g)
    {
        
                
                g.queuePolecenie.add(new PolecenieWstawPionka(g, p ,x, y));
                
            
    }
        
     
    @Override
    protected synchronized void RozpocznijGre()
    {
        
        for(Gracz g:gracze.values())
        {
            g.queuePolecenie.add(new PolecenieRozpocznijGre(g,'x'));
            
                
        }
       
    }
    
    
   @Override
    public synchronized void pobierzPlansze(Gracz g)
    {
        
        for(int i =0;i<3;i++)
        {
            for(int u=0;u<3;u++)
            {
                if(plansza.pobierzPionka(i, u)!=factoryEmptyChar.getChar())
                wyslijPionka(i, u, plansza.pobierzPionka(i, u), g);
            }
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
        if(!sprawdzCzyKoniec() && !sprawdzCzyWygrana())
        {
            Pamiatka pamiatka = plansza.stworzPamiatke();
            try
            {
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(copyfilename)));
                oos.writeObject(pamiatka);
                oos.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        
        
        
        
        
        //oos.close();
        for(Gracz g: gracze.values())
        {
            g.queuePolecenie.add(new PolecenieWyjdzZgry(g));
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
            g.queuePolecenie.add(new PolecenieZmianaTury(g));
        }
        
    }

    @Override
    public void poinformujOWygranej() {
        //queue.add(KONIEC_GRY);
       // plansza= new PlanszaProxyWirtualne();
            new File(copyfilename).delete();
        for(Gracz g : gracze.values())
        {
            g.queuePolecenie.add(new PolecenieWygrana(g));
        }
    }
    @Override
    protected boolean iteruj(IIteratorPlansza iter)
    {
        while(iter.czyNastepny())
        {
            try
            {
                char p1 = iter.obecny();
                iter.nastepny();
                char p2 = iter.obecny();
                iter.nastepny();
                char p3 = iter.obecny();
                if(iter.czyNastepny())
                iter.nastepny();
                
                    if(p1==p2&&p2==p3&&p1==p3 && p1!=factoryEmptyChar.getChar())
                    {
                        System.out.println(p1);
                        return true;
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
                    System.out.print(plansza.pobierzPionka(i, u));
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
       if(iteruj(iterpionowy)) {
          // plansza= new PlanszaProxyWirtualne();
            new File(copyfilename).delete();
       
           return true;
       }
       
        
        IMetodaPlansza metodaPoziomy = new MetodaIteratorPoziomy();
        IIteratorPlansza iterpoziomy = plansza.Pobierziterator(metodaPoziomy);
       if(iteruj(iterpoziomy)) {
           //plansza= new PlanszaProxyWirtualne();
           new File(copyfilename).delete();
       
           return true;
       }
       
       IMetodaPlansza metodaUkosny1 = new MetodaIteratorUkosnyDoPrzodu();
       IIteratorPlansza iterUkosny1 = plansza.Pobierziterator(metodaUkosny1);
       if(iteruj(iterUkosny1)) {
          /// plansza= new PlanszaProxyWirtualne();
            new File(copyfilename).delete();
       
           return true;
       }
       
       IMetodaPlansza metodaUkosny2 = new MetodaIteratorUkosnyDoTylu();
       IIteratorPlansza iterUkosny2 = plansza.Pobierziterator(metodaUkosny2);
       if(iteruj(iterUkosny2)) 
       {
           //plansza= new PlanszaProxyWirtualne();
            new File(copyfilename).delete();
       
           return true;
       }
       return false;
        
    }

    @Override
    public boolean sprawdzCzyKoniec() {
        IMetodaPlansza metoda = new MetodaIteratorPionowy();
        IIteratorPlansza iterator = plansza.Pobierziterator(metoda);
        while(iterator.czyNastepny())
        {
            char p = iterator.nastepny();
            if(p ==' ')
            {
                for(Gracz g: gracze.values())
                {
                   // g.queue.add(KONTYNUUJ_GRE);
                }
                return false;
            }
            
        }
        new File(copyfilename).delete();
        return true;
    }
    
   
    
    
}
