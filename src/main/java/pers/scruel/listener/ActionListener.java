package pers.scruel.listener;

/**
 * Created by Scruel on 2017/11/30 030.
 * Github : https://github.com/scruel
 */
public interface ActionListener {
  void actionFailed();

  void actionSucceed();

  void actionCompleted();

  void afterActionCompleted();

  void updateActionSum(int sum);

  void updateResult(StringBuffer stringBuffer);

  void appendResult(StringBuffer stringBuffer);

  void appendResult(String stringBuffer);
}
