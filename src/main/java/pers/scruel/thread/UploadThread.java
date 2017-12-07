package pers.scruel.thread;

import pers.scruel.listener.BaseAction;
import pers.scruel.util.QiNiuUtil;

import java.io.File;
import java.net.URL;

/**
 * Created by Scruel on 2017/8/19.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class UploadThread extends BaseThread {

  public UploadThread(Object uploadObj, BaseAction action) {
    super(uploadObj, action);
  }

  @Override
  void runWithFile(File file) throws Exception {
    action.appendResult(QiNiuUtil.fileUpload(file));
  }

  @Override
  void runWithURL(URL url) throws Exception {
    action.appendResult(QiNiuUtil.urlImgUpload(url));
  }

  @Override
  void runWithBytes(byte[] bytes) throws Exception {
    action.appendResult(QiNiuUtil.uploadByBytes(bytes));
  }
}
