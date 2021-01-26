/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plansza;

import java.awt.Graphics;

/**
 *
 * @author Geoff
 */
public abstract class FactoryMethodBuilder {
    public abstract IFrame getFrame(int width,int heigth,Graphics graphic);
}
