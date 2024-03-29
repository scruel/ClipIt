package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.BaseAction;
import pers.scruel.thread.BaseThread;
import pers.scruel.util.ClipboardUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.util.List;

/**
 * An abstract class that all functions are based on this class to process clipboard
 * content.
 * The {@link #process()} method will get content from system clipboard, recognize
 * it's {@link DataFlavor}, and then process it by specified method which should
 * and will be implement by subclasses.
 * Subclasses should split data to parts to the type which can be process by {@link BaseThread}
 * and then invoke {@link #startThread(Object)} method to process it via starting threads.
 *
 * @author Scruel Tao
 */
@SuppressWarnings("unchecked")
public abstract class BaseProcessor {
  private TipsFrame tipsFrame;
  private BaseAction action;
  private Class<? extends BaseThread> threadClazz;

  protected BaseProcessor(TipsFrame tipsFrame, Class<? extends BaseThread> threadClazz) {
    this.tipsFrame = tipsFrame;
    this.threadClazz = threadClazz;
  }

  public void process() {
    this.tipsFrame.initJLabelTitle(getTitle());
    Clipboard clipboard = ClipboardUtils.getClipboard();
    try {
      if (this.tipsFrame.needTips()) {
        this.tipsFrame.setVisible(true);
      }
      if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {
        fileListProcess((List<File>) clipboard.getData(DataFlavor.javaFileListFlavor));
      }
      else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
        imageProcess((Image) clipboard.getData(DataFlavor.imageFlavor));
      }
      else if (clipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {
        htmlProcess((String) clipboard.getData(DataFlavor.allHtmlFlavor));
      }
      else {
        if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
          this.action.actionCompleted();
          return;
        }
        this.stringProcess((String) clipboard.getData(DataFlavor.stringFlavor));
      }
      if (this.action.getTotalSum() == 0) {
        this.action.actionCompleted();
      }
    } catch (Exception ignore) {
      // e.printStackTrace();
    }
  }

  public void setActionListener(BaseAction l) {
    if (l != null) {
      this.action = l;
    }
  }

  /**
   * Processes html type data from clipboard.
   *
   * @param data
   * @throws Exception
   */
  abstract void htmlProcess(String data) throws Exception;

  /**
   * Processes string type data from clipboard.
   *
   * @param data
   * @throws Exception
   */
  abstract void stringProcess(String data) throws Exception;

  /**
   * Processes image type data from clipboard.
   *
   * @param data
   * @throws Exception
   */
  abstract void imageProcess(Image data) throws Exception;

  /**
   * Processes files type data from clipboard.
   *
   * @param data
   * @throws Exception
   */
  abstract void fileListProcess(List<File> data) throws Exception;

  public void startThread(Object obj) throws Exception {
    BaseThread thread = (BaseThread) this.threadClazz
        .getConstructor(Object.class, BaseAction.class)
        .newInstance(obj, this.action);
    thread.start();
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

  abstract String getTitle();
}
