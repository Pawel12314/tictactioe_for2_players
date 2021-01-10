/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverproject1.Gracz.Stany;

/**
 *
 * @author Geoff
 */
public abstract class IStanGracza {
    
    public static String EXIT_MSG_KOLEJKA = "zakonczyles oczekiwanie na gre";
    public static String NIE_TWOJA_TURA = "nie mozesz ruszyc. trwa tura przeciwnika";
    public static String EXIT_MSG_GRA = "wyszedłeśs z gry";
    public static String ZLA_INSTRUKCJA = "podales zła instrukcjr";
    public static String BAD_MOVE="zadany ruch jest nie prawidlowy";
    public static String CANT_MOVE="nie mozesz wykonac ruchu";
    public abstract String pobierzStrumienWejsciowy(String strumien);
    
    public abstract Boolean sprawdzCzyKoniec();
    //public boolean czyMogeRuszyc();
    //public boolean rusz();
    //public boolean czyWygralem();
    
}
