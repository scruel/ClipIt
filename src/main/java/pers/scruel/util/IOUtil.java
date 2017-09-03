package pers.scruel.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class IOUtil {

  public static byte[] getImgBytes(Image image) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      if (image instanceof BufferedImage) {
        ImageIO.write((BufferedImage) image, "PNG", out);
      }
      else {
        ImageIO.write(getBufferedImage(image), "PNG", out);
      }
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

  public static void copyFile(File oldFile, String newFileStr) {
    try {
      File newFile = new File(newFileStr);
      if (newFile.exists()) {
        return;
      }
      if (oldFile.equals(newFile)) {
        return;
      }
      int len = 0;
      byte[] buffer = new byte[1024];
      FileInputStream fin = new FileInputStream(oldFile);
      FileOutputStream fout = new FileOutputStream(newFile);
      while (-1 != (len = fin.read(buffer))) {
        fout.write(buffer, 0, len);
      }
      fin.close();
      fout.close();
    } catch (Exception e) {
      // System.out.println("error  ");
      e.printStackTrace();
    }
  }


  public static void deleteFileMatchByPrefix(String path, String filenameWithoutExtension) {
    File dir = new File(path);
    File[] files = dir.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.getName().startsWith(filenameWithoutExtension)) {
          file.delete();
        }
      }
    }

  }
}
