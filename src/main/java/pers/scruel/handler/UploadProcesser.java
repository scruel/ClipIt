package pers.scruel.handler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.UploadAction;
import pers.scruel.thread.UploadThread;
import pers.scruel.util.IOUtil;

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
    this.addActionListener(new UploadAction(tipsFrame));
  }

  @Override
  void htmlProcesser(String data) throws Exception {
    Document doc = Jsoup.parse(data);
    Elements elements = doc.select("img");
    updateActionSum(elements.size());
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
        notifyActionSucceed();
      }
    }
  }

  @Override
  void imageProcesser(Image data) throws Exception {
    byte[] imgBytes = IOUtil.getImgBytes(data);
    updateActionSum(1);
    startThread(imgBytes);
  }

  @Override
  void fileListProcesser(List<File> data) throws Exception {
    updateActionSum(data.size());
    for (File file : data) {
      startThread(file);
    }
  }
}
