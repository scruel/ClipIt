package pers.scruel.hotkey;

/**
 * @author Scruel Tao
 * @version 1.0
 */

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import pers.scruel.gui.TipsFrame;
import pers.scruel.handler.OCRProcessor;
import pers.scruel.handler.SendToKindleProcessor;
import pers.scruel.handler.UploadProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

public class GlobalHotkeyListener implements HotkeyListener {
  private final TipsFrame tipsFrame;
  private final File workingLockFile = new File("working");
  private FileOutputStream stream;

  public GlobalHotkeyListener(TipsFrame tipsFrame) {
    this.tipsFrame = tipsFrame;
    for (HotkeyType hotKey : HotkeyType.values()) {
      JIntellitype.getInstance()
                  .registerHotKey(hotKey.getIdentifier(), hotKey.getModifier(), hotKey.getKeycode());
    }
    // 禁止重复运行
    try {
      stream = new FileOutputStream(workingLockFile);
      FileLock fileLock = stream.getChannel().tryLock();
      if (fileLock == null) {
        System.out.println("working");
        System.exit(1);
      }
    } catch (FileNotFoundException ignore) {
    } catch (IOException e) {
      System.exit(1);
    }
  }

  @Override
  public void onHotKey(int identifier) {
    HotkeyType hotKey = HotkeyType.getHotKeyTypeByIdentifier(identifier);
    switch (hotKey) {
      case OCR:
        new OCRProcessor(tipsFrame).process();
        break;
      case UPLOAD:
        new UploadProcessor(tipsFrame).process();
        break;
      case SEND_TO_KIND:
        new SendToKindleProcessor(tipsFrame).process();
        break;
      default:
    }
  }

  public void destroy() {
    for (HotkeyType hotKey : HotkeyType.values()) {
      JIntellitype.getInstance().unregisterHotKey(hotKey.getIdentifier());
    }
    try {
      stream.close();
    } catch (IOException ignore) {
    }
    workingLockFile.delete();
    System.exit(0);
  }
}
