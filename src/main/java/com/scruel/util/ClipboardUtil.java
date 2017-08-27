package com.scruel.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class ClipboardUtil {
    public static void setClipBoard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
        if (!"false".equals(PropertiesUtil.getProperties().getProperty("auto.paste")))
            paste();
    }

    public static void paste() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            System.out.println("paste...");
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static Clipboard getClipboard() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        while (true) {
            Throwable throwable = null;
            try {
                clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor);
            } catch (IllegalStateException e) {
                throwable = e;
            }
            if (throwable == null) break;
        }
        return clipboard;
    }
}
