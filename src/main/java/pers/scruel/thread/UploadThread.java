package pers.scruel.thread;

import pers.scruel.listener.BaseAction;
import pers.scruel.util.QiNiuUtils;

import java.io.File;
import java.net.URL;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
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
        action.appendResult(QiNiuUtils.uploadByBytes(bytes));
    }
}
