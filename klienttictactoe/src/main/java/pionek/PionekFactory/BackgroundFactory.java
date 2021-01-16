/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.PionekFactory;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import pionek.IPionek;
import pionek.pylek.ConcretePionek;

/**
 *
 * @author Geoff
 */
public class BackgroundFactory extends IBackgroundFactory {
    private String path;
    private Image image;
    
    public BackgroundFactory() throws IOException
    {
        path = "frame.png";
        
        try
            {
                //FileInputStream instream = new  FileInputStream(file);
            
            //Bitmap bitmap;// = BitmapFactory.decodeStream(instream);
            image= ImageIO.read(new File(path));
            
            }
            catch(IOException e)
            {
                throw new IOException("no such file");
            }
    }
    @Override
    public Image getBackgorund() {
       return image;
    }
}
