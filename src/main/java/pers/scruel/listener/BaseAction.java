package pers.scruel.listener;

import pers.scruel.gui.TipsFrame;

/**
 * Created by Scruel on 2017/11/30 030.
 * Github : https://github.com/scruel
 */
public class BaseAction implements ActionListener {
  protected TipsFrame frame;
  private int totalSum = 0;
  private int failedSum = 0;
  private int succeedSum = 0;
  private StringBuffer result = new StringBuffer();

  public BaseAction(TipsFrame frame) {
    this.frame = frame;
  }

  @Override
  public void actionFailed() {
    failedSum++;
    frame.updateJLable((failedSum + succeedSum), totalSum);
    if (totalSum == 0 || totalSum == (failedSum + succeedSum)) {
      actionCompleted();
    }
  }

  @Override
  public void actionSucceed() {
    succeedSum++;
    frame.updateJLable((failedSum + succeedSum), totalSum);
    if (totalSum == 0 || totalSum == (failedSum + succeedSum)) {
      actionCompleted();
    }
  }

  @Override
  public void actionCompleted() {
    String msg;
    long millis = 2500;
    boolean failed = false;

    if (failedSum != 0) {
      msg = "失败:" + failedSum;
      failed = true;
    }
    else if (succeedSum != 0) {
      millis = 1500;
      msg = "完成!";
    }
    else {
      msg = "无内容需被处理！";
    }
    frame.finish(msg, millis, failed);
    afterActionCompleted();
    System.exit(0);
  }

  @Override
  public void updateActionSum(int sum) {
    if (sum == 0) actionCompleted();
    this.totalSum = sum;
    frame.updateJLable(0, totalSum);
  }

  @Override
  public void afterActionCompleted() {
  }

  @Override
  public void updateResult(StringBuffer stringBuffer) {
    this.result = stringBuffer;
  }

  @Override
  public void appendResult(StringBuffer stringBuffer) {
    this.result.append(stringBuffer);
  }

  public void appendResult(String stringBuffer) {
    this.result.append(stringBuffer);
  }

  public StringBuffer getResult() {
    return result;
  }

  public int getTotalSum() {
    return totalSum;
  }

  public int getFailedSum() {
    return failedSum;
  }

  public int getSucceedSum() {
    return succeedSum;
  }
}
