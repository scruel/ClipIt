package pers.scruel.thread;


import pers.scruel.listener.BaseAction;

import java.io.File;
import java.net.URL;

/**
 * Created by Scruel on 2017/8/26.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public abstract class BaseThread extends Thread {
  protected Object uploadObj;
  protected BaseAction action;

  public BaseThread(Object uploadObj, BaseAction action) {
    this.uploadObj = uploadObj;
    this.action = action;
  }

  @Override
  public void run() {
    doRun();
  }

  public void doRun() {
    try {
      if (uploadObj instanceof File) {
        runFile((File) uploadObj);
      }
      else if (uploadObj instanceof URL) {
        runURL((URL) uploadObj);
      }
      else if (uploadObj instanceof byte[]) {
        runBytes((byte[]) uploadObj);
      }
    } catch (Exception ignore) {
      // e.printStackTrace();
      action.actionFailed();
      return;
    }
    action.actionSucceed();
  }

  /**
   * 对File进行处理
   *
   * @param file
   * @throws Exception
   */
  abstract void runFile(File file) throws Exception;

  /**
   * 对URL进行处理
   *
   * @param url
   * @throws Exception
   */
  abstract void runURL(URL url) throws Exception;

  /**
   * 对byte数组进行处理
   *
   * @param bytes
   * @throws Exception
   */
  abstract void runBytes(byte[] bytes) throws Exception;
}
