package com.scruel.thread;

import com.scruel.gui.TipsFrame;
import com.scruel.util.IOUtil;
import com.scruel.util.MailUtil;
import com.scruel.util.PropertiesUtil;

import java.io.File;
import java.net.URL;

/**
 * Created by Scruel on 2017/8/26.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class SendToKindleThread extends BaseThread {
    private String path = "tools/kindle";

    public SendToKindleThread(Object uploadObj, TipsFrame tipsFrame) {
        super(uploadObj, tipsFrame);
        // path = SendToKindleThread.class.getClassLoader().getResource("tools/kindle").getPath();
    }

    @Override
    void runFile(File file) throws Exception {
        MailUtil se = new MailUtil(true);
        String filenameWithoutExtension = file.getName();
        filenameWithoutExtension = filenameWithoutExtension.substring(0, filenameWithoutExtension.lastIndexOf("."));
        File newFile = new File(path + "\\temp\\" + filenameWithoutExtension + ".mobi");

        if (!newFile.exists()) {
            IOUtil.copyFile(file, path + "\\temp\\" + file.getName());
            Process p = Runtime.getRuntime()
                .exec("cmd.exe /c start /wait mobi.bat", //path to executable
                    null, // env vars, null means pass parent env
                    new File(path)); // working directory
            p.waitFor();
        }
        se.doSendHtmlEmail("sendToKindle", "send", newFile);
        if ("true".equals(PropertiesUtil.getProperties().getProperty("auto.delete")))
            IOUtil.deleteFileMatchByPrefix(path + "\\temp\\", filenameWithoutExtension);
    }

    @Override
    void runURL(URL url) {

    }

    @Override
    void runBytes(byte[] bytes) {

    }
}
