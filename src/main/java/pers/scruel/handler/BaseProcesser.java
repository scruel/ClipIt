package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.BaseAction;
import pers.scruel.thread.BaseThread;
import pers.scruel.util.ClipboardUtil;
import pers.scruel.util.PropertiesUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.List;

/**
 * Created by Scruel on 2017/8/26.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
@SuppressWarnings("unchecked")
public abstract class BaseProcesser {
  private TipsFrame tipsFrame;
  private BaseAction action;
  private Class<?> threadClazz;

  protected BaseProcesser(TipsFrame tipsFrame, Class<?> threadClazz) {
    this.tipsFrame = tipsFrame;
    this.threadClazz = threadClazz;
  }

  public void process() {
    Clipboard clipboard = ClipboardUtil.getClipboard();
    try {
      if ("true".equals(PropertiesUtil.getProperties().getProperty("window.tips"))) {
        this.tipsFrame.setVisible(true);
      }
      if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
        fileListProcesser((List<File>) clipboard.getData(DataFlavor.javaFileListFlavor));
      }
      else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
        imageProcesser((Image) clipboard.getData(DataFlavor.imageFlavor));
      }
      else if (clipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {
        htmlProcesser((String) clipboard.getData(DataFlavor.allHtmlFlavor));
      }
      else {
        this.action.actionCompleted();
        return;
      }
      // 无任务，结束。
      if (this.action.getTotalSum() == 0) {
        this.action.actionCompleted();
      }
    } catch (Exception ignore) {
      // e.printStackTrace();
    }
  }

  public void addActionListener(BaseAction l) {
    if (l == null) {
      return;
    }
    this.action = l;
  }

  abstract void htmlProcesser(String data) throws Exception;

  abstract void imageProcesser(Image data) throws Exception;

  abstract void fileListProcesser(List<File> data) throws Exception;

  public void startThread(Object obj) throws Exception {
    BaseThread thread = (BaseThread) this.threadClazz
        .getConstructor(new Class[]{Object.class, BaseAction.class})
        .newInstance(new Object[]{obj, this.action});
    thread.start();
    // while (true) {
    //     uploadThread.isAlive();
    // }
  }

  protected void updateActionSum(int size) {
    this.action.updateActionSum(size);
  }

  protected void notifyActionSucceed() {
    this.action.actionSucceed();
  }

  protected void notifyActionFailed() {
    this.action.actionFailed();
  }
}
