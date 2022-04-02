package pers.scruel.listener;

import pers.scruel.gui.TipsFrame;

/**
 * An default base class for receiving operation actions.
 * This class exists as convenience for use, override method{@link #afterActionCompleted()}
 * is one of the reason to extend this class, yet it's enough for most of the use.
 * Sum variables in this class are updated by the thread execute
 * results which really process the data, and then notify the frame window
 * to display the results.
 * Note: synchronized method or atom variable seems not necessary
 * for this class, so I didn't use them.
 *
 * @author Scruel Tao
 */
public class BaseAction implements ActionListener {
  private final int succeedTipsDelay = 1300;
  private final int failedTipsDelay = 2500;
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
    frame.updateJLabel((failedSum + succeedSum), totalSum);
    if (totalSum == 0 || totalSum == (failedSum + succeedSum)) {
      actionCompleted();
    }
  }

  @Override
  public void actionFailed(Exception e) {
    failedSum++;
    frame.updateJLabel((failedSum + succeedSum), totalSum, e.getMessage());
    if (totalSum == 0 || totalSum == (failedSum + succeedSum)) {
      actionCompleted();
    }
  }

  @Override
  public void actionSucceed() {
    succeedSum++;
    frame.updateJLabel((failedSum + succeedSum), totalSum);
    if (totalSum == 0 || totalSum == (failedSum + succeedSum)) {
      actionCompleted();
    }
  }

  @Override
  public void actionCompleted() {
    afterActionCompleted();
    String msg;
    boolean failed = false;

    if (failedSum != 0) {
      msg = "成功: " + succeedSum + " 失败: " + failedSum;
      failed = true;
    }
    else if (succeedSum != 0) {
      msg = "完成!";
    }
    else {
      msg = "无内容需被处理!";
    }
    frame.finish(msg, failed ? failedTipsDelay : succeedTipsDelay, failed);
  }

  @Override
  public void updateActionSum(int sum) {
    if (sum == 0) {
      actionCompleted();
    }
    this.totalSum = sum;
    frame.updateJLabel(0, totalSum);
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

  @Override
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
