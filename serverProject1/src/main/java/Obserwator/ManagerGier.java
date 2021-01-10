/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Obserwator;

import com.mycompany.serverproject1.Gracz.Gracz;
import com.mycompany.serverproject1.Gracz.IGraczObserwator;
import com.mycompany.serverproject1.Pionek.PionekFabryka.FabrykaPustyPionek;
import com.mycompany.serverproject1.Pionek.PionekO;
import com.mycompany.serverproject1.Pionek.PionekX;
import com.mycompany.serverproject1.Plansza.PlanszaProxyWirtualne;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Geoff
 */
public class ManagerGier  {
    private Map<IGra,Set<IGraczObserwator>>gry = new ConcurrentHashMap<IGra,Set<IGraczObserwator>>();
    //ServerSocket socket;
    private Object socketMutex;
    
    private ExecutorService service;
    
  //  CustomThreadPool customThreadPool = new CustomThreadPool(2);
    public ManagerGier()
    {
        service = Executors.newFixedThreadPool(200);
        socketMutex = new Object();
    }
    public IGra znajdzGre()
    {
        for (Map.Entry<IGra, Set<IGraczObserwator>> gra : gry.entrySet()) {
            if(((Set)gra.getValue()).size()==1)
            {
                //((Set)gra.getValue()).add(g);
                return gra.getKey();
            }
        }
        IGra gra = new Gra(this,new PlanszaProxyWirtualne(new FabrykaPustyPionek()));
        Set<IGraczObserwator> gracze = new HashSet<IGraczObserwator>();
        //gracze.add(g);
        gry.put(gra, gracze);
        return gra;
        

    }
    
    public void poinformujONowejGrze(IGra gra)
    {
        Set<IGraczObserwator> gracze = gry.get(gra);
        //gra.RozpocznijGre(gracze);
        for(IGraczObserwator g : gracze)
        {
          g.updateRozpocznijGre();
        }
        
    }
    public Set<IGraczObserwator> pobierzGraczy(IGra gra)
    {
        Set<IGraczObserwator> gracze;
        
            gracze = gry.get(gra);
            System.out.println(Integer.toString(gracze.size())+"--liczba graczy");
            return gracze;
        
        
       
    }
    public void usunGre(IGra gra)
    {
        Set<IGraczObserwator> gracze = gry.get(gra);
        for(IGraczObserwator g: gracze)
        {
            System.out.println("usun gre");
            g.zakonczGre();
            
            
            
        }
        System.out.println("lista przed usunieciem "+gry.size());
        gry.remove(gra);
        System.out.println("lista po usunieciu "+gry.size());
    }
    public void register(Socket socket)
    {
        
                    IGra gra = znajdzGre();
                    Set<IGraczObserwator> gracze = gry.get(gra);
                    IGraczObserwator gracz;
                    if(gracze.size()==1)
                    {
                        gracz = new Gracz(new PionekX(),socket,gra);
                        
                        gracze.add(gracz);
                        service.execute(gracz);
                        poinformujONowejGrze(gra);
                        
                    }
                    else
                    {
                        if(gracze.size()==0)
                        {
                            gracz = new Gracz(new PionekO(),socket,gra);
                            
                            gracze.add(gracz);
                            //gracz.start();
                            service.execute(gracz);
                        }
                    }
                    System.out.println("liczba gier: "+gry.size()+" liczba graczy w grze: "+gracze.size());
                    
                    
                    
    }
 
}
