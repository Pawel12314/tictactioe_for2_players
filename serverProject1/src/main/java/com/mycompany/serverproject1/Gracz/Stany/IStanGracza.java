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
    public static String OPPONENT_WIN="przeciwnik wygral";
    public static String YOU_WIN="wygrales";
    public static String REMIS="remis";
    public static String RUCH="move";
    public abstract void wygrana();
    public abstract String pobierzStrumienWejsciowy(String strumien);
    public abstract void zmienTure();
    public abstract Boolean sprawdzCzyKoniec();
    public abstract void zakonczGre();
    public abstract void remis();
    
    //public boolean czyMogeRuszyc();
    //public boolean rusz();
    //public boolean czyWygralem();
    
}
