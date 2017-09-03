package pers.scruel;

import pers.scruel.gui.TipsFrame;
import pers.scruel.handler.BaseProcesser;
import pers.scruel.handler.SendToKindleProcesser;
import pers.scruel.handler.UploadProcesser;
import pers.scruel.util.PropertiesUtil;

import java.util.HashSet;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
@SuppressWarnings("unchecked")
public class Main {
  private static TipsFrame tipsFrame;

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

    if (!"false".equals(PropertiesUtil.getProperties().getProperty("window.tips"))) {
      tipsFrame = new TipsFrame();
    }

    BaseProcesser handler = null;
    if ("upload".equals(command)) {
      handler = new UploadProcesser(tipsFrame);
    }
    else if ("sendtokindle".equals(command)) {
      handler = new SendToKindleProcesser(tipsFrame);
    }
    if (handler == null) {
      printUsage();
    }
    else {
      handler.process();
    }
  }

  private static void printUsage() {
    System.err.println("Usage: clipBoard command");
    System.err.println("  -h\tthis message");
    System.err.println("  comment:\t");
    System.err.println("          sendtokindle - auto send file to email");
    System.err.println("                 - auto upload file to cloud");
    System.exit(0);
  }
}
