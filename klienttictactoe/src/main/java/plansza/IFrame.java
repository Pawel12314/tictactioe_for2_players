/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plansza;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Geoff
 */
public abstract class IFrame {
    public abstract void addVertical(int denominator,int counter);
    public abstract void addHorizontal(int denominator,int counter);
    public abstract Graphics2D getLinie();
}
