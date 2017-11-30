package pers.scruel.listener;

import pers.scruel.gui.TipsFrame;
import pers.scruel.util.ClipboardUtil;

/**
 * Created by Scruel on 2017/11/30 030.
 * Github : https://github.com/scruel
 */
public class OCRAction extends BaseAction {
  public OCRAction(TipsFrame frame) {
    super(frame);
  }

  @Override
  public void afterActionCompleted() {
    if (super.getSucceedSum() > 0) {
      ClipboardUtil.setClipBoard(getResult().toString());
    }
  }
}
