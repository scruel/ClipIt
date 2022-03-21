package pers.scruel.thread;

import pers.scruel.listener.BaseAction;
import pers.scruel.util.QiNiuUtils;

import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 * @author Scruel Tao <scruelt@hotmail.com>
 */
public class UploadThread extends BaseThread {

    public UploadThread(Object uploadObj, BaseAction action) {
        super(uploadObj, action);
    }

    @Override
    void runWithFile(File file) throws Exception {
        action.appendResult(QiNiuUtils.fileUpload(file));
    }

    @Override
    void runWithURL(URL url) throws Exception {
        action.appendResult(QiNiuUtils.urlImgUpload(url));
    }

    @Override
    void runWithBytes(byte[] bytes) throws Exception {
    }

    @Override
    void runWithImage(Image image) throws Exception {
        action.appendResult(QiNiuUtils.uploadByImage(image));
    }
}
