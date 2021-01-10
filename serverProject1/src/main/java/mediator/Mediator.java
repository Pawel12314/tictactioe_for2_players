/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediator;

import com.mycompany.serverproject1.Pionek.IPionek;
import com.mycompany.serverproject1.Plansza.IPlansza;

/**
 *
 * @author Geoff
 */
public class Mediator {
    private IPlansza plansza;
    
    public Mediator(IPlansza plansza)
    {
        this.plansza = plansza;
    }
    public void wstawPionka(IPionek p, int x, int y)
    {
        
    }
    public boolean SprawdzCzyWygrana()
    {
        return true;
    }
}
