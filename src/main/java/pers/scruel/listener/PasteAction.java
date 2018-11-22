package pers.scruel.listener;

import pers.scruel.gui.TipsFrame;
import pers.scruel.util.ClipboardUtils;
import pers.scruel.util.PropertiesUtils;

import java.io.IOException;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class PasteAction extends BaseAction {
    public PasteAction(TipsFrame frame) {
        super(frame);
    }

    @Override
    public void afterActionCompleted() {
        if (super.getSucceedSum() > 0) {
            ClipboardUtils.setClipBoard(getResult().toString());
            try {
                if (!"false".equals(PropertiesUtils.getProperties().getProperty("auto.paste"))) {
                    ClipboardUtils.paste();
                }
            } catch (IOException ignore) {
            }
        }
    }
}
