package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.thread.SendToKindleThread;

import java.awt.Image;
import java.io.File;
import java.util.List;

/**
 * Created by Scruel on 2017/8/26.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class SendToKindleProcesser extends BaseProcesser {

  public SendToKindleProcesser(TipsFrame tipsFrame) {
    super(tipsFrame, SendToKindleThread.class);
    tipsFrame.initJlabelTitle("sending");
  }

  @Override
  void htmlProcesser(String data) throws Exception {

  }

  @Override
  void imageProcesser(Image data) throws Exception {

  }

  @Override
  void fileListProcesser(List<File> data) throws Exception {
    notifyFrameSum(data.size());
    for (File file : data) {
      if (file.getName().endsWith("azw3") || file.getName().endsWith("azw")
          || file.getName().endsWith("epub") || file.getName().endsWith(".mobi")) {
        startThread(file);
      }
      else {
        notifyFramSuccess();
      }
    }
  }
}





