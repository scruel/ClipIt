package pers.scruel;

import pers.scruel.gui.TipsFrame;
import pers.scruel.handler.BaseProcesser;
import pers.scruel.handler.OCRProcesser;
import pers.scruel.handler.SendToKindleProcesser;
import pers.scruel.handler.UploadProcesser;
import pers.scruel.util.PropertiesUtil;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
@SuppressWarnings("unchecked")
public class Main {

  public static void main(String[] args) {
    HashSet<String> optionsSet = new HashSet<String>();
    String command = null;
    for (String arg : args) {
      if (arg.startsWith("-")) {
        optionsSet.add(arg);
      }
      else if (command == null) {
        command = arg;
      }
      else {
        printUsage();
      }
    }

    if (command == null || optionsSet.contains("-h") || optionsSet.contains("--help")) {
      printUsage();
    }

    TipsFrame tipsFrame = new TipsFrame();

    try {
      PropertiesUtil.getProperties().getProperty("window.tips");
    } catch (IOException ignore) {
      tipsFrame = new TipsFrame();
      tipsFrame.setVisible(true);
      tipsFrame.finish("配置加载失败!", 5000, true);
    }

    BaseProcesser handler = null;
    if ("upload".equals(command)) {
      handler = new UploadProcesser(tipsFrame);
    }
    else if ("sendtokindle".equals(command)) {
      handler = new SendToKindleProcesser(tipsFrame);
    }
    else if ("ocr".equals(command)) {
      handler = new OCRProcesser(tipsFrame);
    }
    if (handler == null) {
      printUsage();
    }
    else {
      handler.process();
    }
  }

  private static void printUsage() {
    System.err.println("Usage: ClipIt command");
    System.err.println("  -h\tthis message");
    System.err.println("  comment:\t");
    System.err.println("          sendtokindle - auto send file to email.");
    System.err.println("          upload       - auto upload file to cloud.");
    System.err.println("          ocr          - ocr image and return text result.");
    System.exit(0);
  }
}
