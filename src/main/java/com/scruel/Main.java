package com.scruel;

import com.scruel.model.TipsFrame;
import com.scruel.util.IOUnit;
import com.scruel.util.PropertiesUtil;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
@SuppressWarnings("unchecked")
public class Main {
    private static TipsFrame tipsFrame;

    public static void main(String[] args) {
        // String accessKey = args[0];
        // String secretKey = args[1];
        // registerHotKey();
        if (!"false".equals(PropertiesUtil.getProperties().getProperty("windowTips")))
            tipsFrame = new TipsFrame();
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
            if (tipsFrame != null) tipsFrame.setVisible(true);
            if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
                FileListProcesser((List<File>) clipboard.getData(DataFlavor.javaFileListFlavor));
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                ImageProcesser((Image) clipboard.getData(DataFlavor.imageFlavor));
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {
                HTMLProcesser((String) clipboard.getData(DataFlavor.allHtmlFlavor));
            }
            else {
                if (tipsFrame != null) tipsFrame.finish("无内容需要上传！");
            }
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void HTMLProcesser(String data) {
        Document doc = Jsoup.parse(data);
        Elements elements = doc.select("img");
        System.out.println(elements.size());
        if (tipsFrame != null)
            tipsFrame.setTotalNeededUploadSum(elements.size());
        for (Element element : elements) {
            String filePath = element.attr("src");
            // new Thread(() -> QiNiuUtil.fileUpload(new File(filePath))).start();
            if (filePath.matches("[a-zA-Z]:.*")) {
                new UploadThread(new File(filePath)).start();
            }
            else if (filePath.startsWith("http")) {
                try {
                    new UploadThread(new URL(filePath)).start();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println(filePath);
                if (tipsFrame != null) tipsFrame.notifyUpload();
            }
        }
    }

    private static void ImageProcesser(Image data) {
        byte[] imgBytes = IOUnit.getImgBytes(data);
        if (tipsFrame != null)
            tipsFrame.setTotalNeededUploadSum(1);
        new UploadThread(imgBytes).start();
    }


    private static void FileListProcesser(List<File> fileList) {
        if (tipsFrame != null)
            tipsFrame.setTotalNeededUploadSum(fileList.size());
        //TODO ThreadPool
        for (File file : fileList) {
            new UploadThread(file).start();
        }
    }

    static class UploadThread extends Thread {
        private Object uploadObj;

        public UploadThread(Object uploadObj) {
            this.uploadObj = uploadObj;
        }

        @Override
        public void run() {
            if (uploadObj instanceof File) {
                QiNiuUtil.fileUpload((File) uploadObj);
            }
            else if (uploadObj instanceof URL) {
                QiNiuUtil.urlImgUpload((URL) uploadObj);
            }
            else if (uploadObj instanceof byte[]) {
                QiNiuUtil.uploadByBytes((byte[]) uploadObj);
            }
            if (tipsFrame != null) tipsFrame.notifyUpload();
        }
    }
}
