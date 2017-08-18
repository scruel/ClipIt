package com.scruel;

import com.scruel.util.IOUnit;
import com.scruel.util.QiNiuUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class Main {

    public static void main(String[] args) {
        // String accessKey = args[0];
        // String secretKey = args[1];
        // registerHotKey();
        checkClipboard();
    }

    private static void checkClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        while (true) {
            Throwable throwable = null;
            try {
                clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor);
            } catch (IllegalStateException e) {
                throwable = e;
            }
            if (throwable == null) break;
        }

        try {
            if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
                FileListProcesser((List<File>) clipboard.getData(DataFlavor.javaFileListFlavor));
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                ImageProcesser((Image) clipboard.getData(DataFlavor.imageFlavor));
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {
                HTMLProcesser((String) clipboard.getData(DataFlavor.allHtmlFlavor));
            }
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void HTMLProcesser(String data) {
        Document doc = Jsoup.parse(data);
        Elements elements = doc.select("img");
        System.out.println(elements.size());
        for (Element element : elements) {
            String filePath = element.attr("src");
            new Thread(() -> {
                QiNiuUtil.fileUpload(new File(filePath));
            }).start();
        }
    }

    public static void ImageProcesser(Image data) {
        byte[] imgBytes = IOUnit.getImgBytes(data);
        QiNiuUtil.uploadByBytes(imgBytes);
    }

    private static void FileListProcesser(List<File> fileList) {
        for (File file : fileList) {
            new Thread(() -> {
                QiNiuUtil.fileUpload(file);
            }).start();
        }
    }

}
