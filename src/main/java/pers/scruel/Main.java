package pers.scruel;

import com.melloware.jintellitype.JIntellitype;
import pers.scruel.gui.TipsFrame;
import pers.scruel.hotkey.GlobalHotkeyListener;
import pers.scruel.util.PropertiesUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
 */
@SuppressWarnings("unchecked")
public class Main {
    public static void main(String[] args) throws AWTException {
        TipsFrame tipsFrame = new TipsFrame();
        // 试探加载 32/64 位系统 dll
        try {
            System.loadLibrary("JIntellitype");
        } catch (Throwable throwable) {
            File dll64 = new File("JIntellitype64.dll");
            if (dll64.exists()) {
                new File("JIntellitype.dll").delete();
                dll64.renameTo(new File("JIntellitype.dll"));
            }
            try {
                System.loadLibrary("JIntellitype");
            } catch (Throwable ignore) {
                tipsFrame.setVisible(true);
                tipsFrame.finish("加载热键相关 dll 失败", 5000, true);
                System.exit(1);
            }
        }
        // 加载配置文件
        try {
            PropertiesUtils.getProperties().getProperty("window.tips");
            PropertiesUtils.getProperties().getProperty("combination.key");
        } catch (IOException ignore) {
            tipsFrame.setVisible(true);
            tipsFrame.finish("配置加载失败!", 5000, true);
            System.exit(1);
        }
        // TODO 更新热键绑定
        GlobalHotkeyListener hotkeyListener = new GlobalHotkeyListener(tipsFrame);
        JIntellitype.getInstance().addHotKeyListener(hotkeyListener);

        // 增加托盘图标
        addTray(hotkeyListener);
        System.out.println("clipIt start.");
    }

    private static void addTray(GlobalHotkeyListener hotkeyListener) throws AWTException {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(TipsFrame.class.getClassLoader().getResource("ClipIt.png")));
        TrayIcon trayIcon = new TrayIcon(imageIcon.getImage());
        trayIcon.setToolTip("ClipIt");
        PopupMenu popupMenu = new PopupMenu();
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.addActionListener(actionEvent -> hotkeyListener.destroy());
        popupMenu.add(exitMenuItem);
        trayIcon.setPopupMenu(popupMenu);
        SystemTray systemTray = SystemTray.getSystemTray();
        systemTray.add(trayIcon);
    }

    private static void printUsage() {
        System.err.println("*****************ClipIt*****************");
        System.err.println("Expands clipboard to do more with shortcut keys.");
        System.err.println();
        System.err.println("GitHub: https://github.com/scruel/ClipIt");
        System.err.println("Author: Scruel");
        System.err.println("Email: scruel@vip.qq.com");
        System.err.println("****************************************");
        System.err.println();
        System.err.println("Usage: ClipIt command");
        System.err.println("  -h\tthis message");
        System.err.println("  comment:\t");
        System.err.println("          sendtokindle - auto send file to email.");
        System.err.println("          upload       - auto upload file to cloud.");
        System.err.println("          ocr          - ocr image and return text result.");
        System.exit(0);
    }
}
