package pers.scruel.thread;

import pers.scruel.gui.TipsFrame;
import pers.scruel.util.QiNiuUtil;

import java.io.File;
import java.net.URL;

/**
 * Created by Scruel on 2017/8/19.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class UploadThread extends BaseThread {

  public UploadThread(Object uploadObj, TipsFrame tipsFrame) {
    super(uploadObj, tipsFrame);
  }

  @Override
  void runFile(File file) throws Exception {
    QiNiuUtil.fileUpload(file);
  }

  @Override
  void runURL(URL url) throws Exception {
    QiNiuUtil.urlImgUpload(url);
  }

  @Override
  void runBytes(byte[] bytes) throws Exception {
    QiNiuUtil.uploadByBytes(bytes);
  }
}
