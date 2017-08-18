package com.scruel.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class IOUnit {

    public static byte[] getImgBytes(Image image) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (image instanceof BufferedImage)
                ImageIO.write((BufferedImage) image, "PNG", out);
            else
                ImageIO.write(getBufferedImage(image), "PNG", out);
        } catch (IOException ex) {
            //ignore
        }
        return out.toByteArray();
    }

    public static BufferedImage getBufferedImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //Graphics2D g2d = bi.createGraphics();
        //g2d.drawImage(image, 0, 0, null);
        return bi;
    }

}
