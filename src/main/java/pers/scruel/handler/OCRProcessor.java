package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.PasteAction;
import pers.scruel.thread.OCRThread;
import pers.scruel.util.QiNiuUtils;

import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * @author Scruel Tao <scruelt@hotmail.com>
 */
public class OCRProcessor extends BaseProcessor {
    private final String title = "OCRing";

    public OCRProcessor(TipsFrame tipsFrame) {
        super(tipsFrame, OCRThread.class);
        this.setActionListener(new PasteAction(tipsFrame));
    }

    @Override
    void htmlProcess(String data) throws Exception {
    }

    @Override
    void imageProcess(Image data) throws Exception {
        updateActionSum(1);
        startThread(data);
    }

    @Override
    void fileListProcess(List<File> data) throws Exception {
        updateActionSum(data.size());
        for (File file : data) {
            if (!"unknown".equals(QiNiuUtils.getImgType(file.getName()))) {
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
