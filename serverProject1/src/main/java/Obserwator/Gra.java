/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obserwator;

import com.mycompany.serverproject1.Gracz.IGraczObserwator;
import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Plansza.IPlansza;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Geoff
 */
public class Gra extends IGra{
    //private ManagerGier manager;
    
    // private Object mutex;
    
    public Gra(ManagerGier manager,IPlansza plansza)
    {
        super(manager,plansza);
        
    //    this.mutex = new Object();
    }
   @Override
    public synchronized void wstawPionka(int x, int y, IPionek p)
    {
        plansza.dodajPionka(x, y, p);
    }
    public synchronized Boolean sprawdzCzWygrana()
    {
        return false;
    }  
    @Override
    protected void RozpocznijGre(Set<IGraczObserwator> gracze)
    {
        synchronized(mutex)
        {
             for(IGraczObserwator g : gracze)
            {
                g.updateRozpocznijGre();
            }   
        }
       
    }
    @Override
  public boolean czyMogeRuszyc(int x, int y)
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
    public synchronized int zakonczGre(/*IGraczObserwator gracz*/) {
        /*Set<IGraczObserwator>gracze  = manager.pobierzGraczy(this);
        for(IGraczObserwator g : gracze)
        {
            g.zakonczGre();
            
            
        }*/
        manager.usunGre(this);
        return 1;
    }
    
   
    
    
}
