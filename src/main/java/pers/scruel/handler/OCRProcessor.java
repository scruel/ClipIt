package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.PasteAction;
import pers.scruel.thread.OCRThread;
import pers.scruel.util.IOUtils;
import pers.scruel.util.QiNiuUtils;

import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class OCRProcessor extends BaseProcessor {
  public OCRProcessor(TipsFrame tipsFrame) {
    super(tipsFrame, OCRThread.class);
    tipsFrame.initJlabelTitle("OCRing");
    this.addActionListener(new PasteAction(tipsFrame));
  }

  @Override
  void htmlProcess(String data) throws Exception {
  }

  @Override
  void imageProcess(Image data) throws Exception {
    byte[] imgBytes = IOUtils.getImgBytes(data);
    updateActionSum(1);
    startThread(imgBytes);
  }

  @Override
  void fileListProcess(List<File> data) throws Exception {
    updateActionSum(data.size());
    for (File file : data) {
      if (!"unknown".equals(QiNiuUtils.getImgType(file.getName()))) {
        startThread(file);
      }
      else {
        notifyActionSucceed();
      }
    }
  }
}
