package pers.scruel.thread;

import pers.scruel.listener.BaseAction;
import pers.scruel.util.BaiduOCRUtils;

import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 * @author Scruel Tao
 */
public class OCRThread extends BaseThread {
  public OCRThread(Object uploadObj, BaseAction action) {
    super(uploadObj, action);
  }

  @Override
  void runWithFile(File file) throws Exception {
    action.appendResult(BaiduOCRUtils.fileImgOCR(file));
  }

  @Override
  void runWithURL(URL url) throws Exception {
    action.appendResult(BaiduOCRUtils.urlImgOCR(url));
  }

  @Override
  void runWithBytes(byte[] bytes) throws Exception {
  }

  @Override
  void runWithImage(Image image) throws Exception {
    action.appendResult(BaiduOCRUtils.imageImgOCR(image));
  }
}
