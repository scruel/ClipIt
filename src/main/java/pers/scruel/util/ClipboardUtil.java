package pers.scruel.util;

import org.jsoup.helper.StringUtil;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class ClipboardUtil {
  public static void setClipBoard(String text) {
    if (StringUtil.isBlank(text)) {
      return;
    }
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    StringSelection selection = new StringSelection(text);
    clipboard.setContents(selection, selection);
    try {
      if (!"false".equals(PropertiesUtil.getProperties().getProperty("auto.paste"))) {
        paste();
      }
    } catch (IOException ignore) {
    }
  }

  public static void paste() {
    try {
      Robot robot = new Robot();
      robot.keyPress(KeyEvent.VK_CONTROL);
      robot.keyPress(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_V);
      robot.keyRelease(KeyEvent.VK_CONTROL);
      // System.out.println("paste...");
    } catch (AWTException e) {
      e.printStackTrace();
    }
  }

  public static Clipboard getClipboard() {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    while (true) {
      Throwable throwable = null;
      try {
        clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor);
      } catch (IllegalStateException e) {
        throwable = e;
      }
      if (throwable == null) {
        break;
      }
    }
    return clipboard;
  }
}
