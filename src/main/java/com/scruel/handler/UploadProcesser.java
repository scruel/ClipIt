package com.scruel.handler;

import com.scruel.gui.TipsFrame;
import com.scruel.thread.UploadThread;
import com.scruel.util.ClipboardUtil;
import com.scruel.util.IOUtil;
import com.scruel.util.QiNiuUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Scruel on 2017/8/26.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
@SuppressWarnings("unchecked")
public class UploadProcesser extends BaseProcesser {

    public UploadProcesser(TipsFrame tipsFrame) {
        super(tipsFrame, UploadThread.class);
        tipsFrame.initJlabelTitle("uploading");
    }

    @Override
    public void process() {
        if (_process())
            ClipboardUtil.setClipBoard(QiNiuUtil.getSb().toString());
    }

    @Override
    void htmlProcesser(String data) throws Exception {
        Document doc = Jsoup.parse(data);
        Elements elements = doc.select("img");
        notifyFrameSum(elements.size());
        for (Element element : elements) {
            String filePath = element.attr("src");
            // new Thread(() -> QiNiuUtil.fileUpload(new File(filePath))).start();
            if (filePath.matches("[a-zA-Z]:.*")) {
                startThread(new File(filePath));
            }
            else if (filePath.startsWith("http")) {
                try {
                    startThread(new URL(filePath));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println(filePath);
                notifyFramSuccess();
            }
        }
    }

    @Override
    void imageProcesser(Image data) throws Exception {
        byte[] imgBytes = IOUtil.getImgBytes(data);
        notifyFrameSum(1);
        startThread(imgBytes);
    }

    @Override
    void fileListProcesser(List<File> data) throws Exception {
        notifyFrameSum(data.size());
        for (File file : data) {
            startThread(file);
        }
    }
}
