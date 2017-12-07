package pers.scruel.thread;


import pers.scruel.listener.BaseAction;

import java.io.File;
import java.net.URL;

/**
 * An abstract class for processing data by starting a thread, Interact with the
 * {@link pers.scruel.listener.BaseAction} class.
 * This class will process specific type of data, like {@link File} etc., as follows, by
 * specific method which should and will be implement by subclasses.
 * <p>
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
        runWithFile((File) uploadObj);
      }
      else if (uploadObj instanceof URL) {
        runWithURL((URL) uploadObj);
      }
      else if (uploadObj instanceof byte[]) {
        runWithBytes((byte[]) uploadObj);
      }
    } catch (Exception ignore) {
      // e.printStackTrace();
      action.actionFailed();
      return;
    }
    action.actionSucceed();
  }

  /**
   * process data of {@link File} type.
   *
   * @param file
   * @throws Exception
   */
  abstract void runWithFile(File file) throws Exception;

  /**
   * process data of {@link URL} type.
   *
   * @param url
   * @throws Exception
   */
  abstract void runWithURL(URL url) throws Exception;

  /**
   * process data of byte array type.
   *
   * @param bytes
   * @throws Exception
   */
  abstract void runWithBytes(byte[] bytes) throws Exception;
}
