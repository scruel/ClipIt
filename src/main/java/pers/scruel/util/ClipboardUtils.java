package pers.scruel.util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

/**
 * @author Scruel Tao <scruelt@hotmail.com>
 */
public class ClipboardUtils {
    public static void setClipBoard(String text) {
        if (StringUtils.isBlank(text)) {
            return;
        }
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }

    public static void paste() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            // System.out.println("paste...");
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
            if (throwable == null) {
                break;
            }
        }
        return clipboard;
    }
}
