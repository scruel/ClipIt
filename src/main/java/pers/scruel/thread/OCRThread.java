package pers.scruel.thread;

import pers.scruel.listener.BaseAction;
import pers.scruel.util.BaiduOCRUtil;

import java.io.File;
import java.net.URL;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class OCRThread extends BaseThread {
  public OCRThread(Object uploadObj, BaseAction action) {
    super(uploadObj, action);
  }

  @Override
  void runWithFile(File file) throws Exception {
    action.appendResult(BaiduOCRUtil.fileImgOCR(file));
  }

  @Override
  void runWithURL(URL url) throws Exception {
    action.appendResult(BaiduOCRUtil.urlImgOCR(url));
  }

  @Override
  void runWithBytes(byte[] bytes) throws Exception {
    action.appendResult(BaiduOCRUtil.bytesImgOCR(bytes));
  }
}
