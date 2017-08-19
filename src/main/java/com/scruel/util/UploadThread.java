package com.scruel.util;

import com.scruel.gui.TipsFrame;

import java.io.File;
import java.net.URL;

/**
 * Created by Scruel on 2017/8/19.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class UploadThread extends Thread {
    private Object uploadObj;
    private TipsFrame tipsFrame;

    public UploadThread(Object uploadObj, TipsFrame tipsFrame) {
        this.uploadObj = uploadObj;
        this.tipsFrame = tipsFrame;
    }

    @Override
    public void run() {
        try {

            if (uploadObj instanceof File) {
                QiNiuUtil.fileUpload((File) uploadObj);
            }
            else if (uploadObj instanceof URL) {
                QiNiuUtil.urlImgUpload((URL) uploadObj);
            }
            else if (uploadObj instanceof byte[]) {
                QiNiuUtil.uploadByBytes((byte[]) uploadObj);
            }
        } catch (Exception e) {
            //ignore
            if (tipsFrame != null) tipsFrame.notifyUploadFail();
            return;
        }
        if (tipsFrame != null) tipsFrame.notifyUploadSuccess();
    }
}
