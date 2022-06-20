package pers.scruel.handler;

import pers.scruel.gui.TipsFrame;
import pers.scruel.listener.PasteAction;
import pers.scruel.thread.SingleFileThread;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.List;

public class SingleFileProcessor extends BaseProcessor {
  private final String TITLE = "saving";

  public SingleFileProcessor(TipsFrame tipsFrame) {
    super(tipsFrame, SingleFileThread.class);
    this.setActionListener(new PasteAction(tipsFrame));
  }

  @Override
  void htmlProcess(String data) throws Exception {
  }

  @Override
  void stringProcess(String data) throws Exception {
    if (data.startsWith("http")) {
      this.startThread(new URL(data));
    }
    else {
      this.startThread(data);
    }

  }

  @Override
  void imageProcess(Image data) {
  }

  @Override
  void fileListProcess(List<File> data) {
  }

  @Override
  String getTitle() {
    return TITLE;
  }
}
