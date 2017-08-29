package com.scruel.handler;

import com.scruel.gui.TipsFrame;
import com.scruel.thread.BaseThread;
import com.scruel.util.ClipboardUtil;

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
    private Class<?> threadClazz;

    protected BaseProcesser(TipsFrame tipsFrame, Class<?> threadClazz) {
        this.tipsFrame = tipsFrame;
        this.threadClazz = threadClazz;
    }

    /**
     * @return true则为发生了上传并且所有上传全部成功
     */
    public void process() {
        Clipboard clipboard = ClipboardUtil.getClipboard();
        try {
            if (tipsFrame != null) tipsFrame.setVisible(true);
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
                if (tipsFrame != null) tipsFrame.finish("无内容需要上传！");
            }
        } catch (Exception e) {
            // e.printStackTrace();
            System.err.println(e);
        }
        if (tipsFrame.getTotalNeededSum() == 0) tipsFrame.finish();
    }

    public TipsFrame getTipsFrame() {
        return tipsFrame;
    }

    abstract void htmlProcesser(String data) throws Exception;

    abstract void imageProcesser(Image data) throws Exception;

    abstract void fileListProcesser(List<File> data) throws Exception;

    public void startThread(Object obj) throws Exception {
        BaseThread uploadThread = (BaseThread) threadClazz.getConstructor(new Class[]{Object.class, TipsFrame.class})
            .newInstance(new Object[]{obj, tipsFrame});
        uploadThread.start();
        // while (true) {
        //     uploadThread.isAlive();
        // }
    }

    public void notifyFrameSum(int size) {
        if (tipsFrame != null)
            tipsFrame.setTotalNeededSum(size);
    }

    public void notifyFramSuccess() {
        if (tipsFrame != null)
            tipsFrame.notifySuccess();
    }

}
