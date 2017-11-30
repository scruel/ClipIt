package pers.scruel.thread;

import pers.scruel.listener.BaseAction;
import pers.scruel.util.BaiduOCRUtil;

import java.io.File;
import java.net.URL;

/**
 * Created by Scruel on 2017/11/30 030.
 * Github : https://github.com/scruel
 */
public class OCRThread extends BaseThread {
  public OCRThread(Object uploadObj, BaseAction action) {
    super(uploadObj, action);
  }

  @Override
  void runFile(File file) throws Exception {
    action.appendResult(BaiduOCRUtil.fileImgOCR(file));
  }

  @Override
  void runURL(URL url) throws Exception {
    action.appendResult(BaiduOCRUtil.urlImgOCR(url));
  }

  @Override
  void runBytes(byte[] bytes) throws Exception {
    action.appendResult(BaiduOCRUtil.bytesImgOCR(bytes));
  }
}
