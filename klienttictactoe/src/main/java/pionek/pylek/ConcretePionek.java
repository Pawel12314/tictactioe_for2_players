/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pionek.pylek;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import pionek.IPionek;

/**
 *
 * @author Geoff
 */
public class ConcretePionek extends IPionek {
    private Graphics2D pole;
    private static Map<String,IPionek> pionki = new HashMap<String,IPionek>();
    public ConcretePionek(Graphics2D g)
    {
        this.pole = g;
    }
    @Override
    public void draw(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public static IPionek getByBitmap(String path) throws IOException{
        if(pionki.containsKey(path))
        {
            return pionki.get(path);
        }
        else
        {
            //URI uri = URI.create(path);
            
            //File file = new File(path);
            try
            {
                //FileInputStream instream = new  FileInputStream(file);
            BufferedImage im = ImageIO.read(new File(path));
            Graphics2D bmp = im.createGraphics();
            //Bitmap bitmap;// = BitmapFactory.decodeStream(instream);
            IPionek p = new ConcretePionek(bmp);
            pionki.put(path, p);
            return p;
            }
            catch(IOException e)
            {
                throw new IOException("no such file");
            }
            
        }
    }
    
}
