package pers.scruel;

import pers.scruel.gui.TipsFrame;
import pers.scruel.handler.BaseProcessor;
import pers.scruel.handler.OCRProcessor;
import pers.scruel.handler.SendToKindleProcessor;
import pers.scruel.handler.UploadProcessor;
import pers.scruel.util.PropertiesUtil;

import java.io.IOException;
import java.util.HashSet;

/**
 * @author Scruel Tao <scruel@vip.qq.com>
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

    BaseProcessor handler = null;
    if ("upload".equals(command)) {
      handler = new UploadProcessor(tipsFrame);
    }
    else if ("sendtokindle".equals(command)) {
      handler = new SendToKindleProcessor(tipsFrame);
    }
    else if ("ocr".equals(command)) {
      handler = new OCRProcessor(tipsFrame);
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
