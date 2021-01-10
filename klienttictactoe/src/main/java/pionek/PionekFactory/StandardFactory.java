/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.PionekFactory;

/**
 *
 * @author Geoff
 */
public class StandardFactory extends IPionekFactory{
    private String X = "cross.png";
    private String O = "circle.png";

    @Override
    public String getBitmap_X() {
        return X;
    }

    @Override
    public String getBitmap_O() {
        return O;
    }
}
