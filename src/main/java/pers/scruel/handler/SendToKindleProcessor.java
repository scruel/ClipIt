package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.BaseAction;
import pers.scruel.thread.SendToKindleThread;

import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class SendToKindleProcessor extends BaseProcessor {
    private final String title = "sending";

    public SendToKindleProcessor(TipsFrame tipsFrame) {
        super(tipsFrame, SendToKindleThread.class);
        this.setActionListener(new BaseAction(tipsFrame));
    }

    @Override
    void htmlProcess(String data) throws Exception {
    }

    @Override
    void imageProcess(Image data) throws Exception {
    }

    @Override
    void fileListProcess(List<File> data) throws Exception {
        updateActionSum(data.size());
        for (File file : data) {
            if (file.getName().endsWith("azw3") || file.getName().endsWith("azw")
                    || file.getName().endsWith("epub") || file.getName().endsWith(".mobi")) {
                startThread(file);
            }
            else {
                notifyActionSucceed();
            }
        }
    }

    @Override
    String getTitle() {
        return title;
    }
}





