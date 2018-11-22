package pers.scruel.listener;

/**
 * The listener interface for receiving operation actions when
 * process the clipboard content like an agent.
 *
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public interface ActionListener {
    /**
     * Invoked when operation action has failed.
     */
    void actionFailed();

    /**
     * Invoked when operation action has succeed.
     */
    void actionSucceed();

    /**
     * Invoked when operation action has completed(whether it
     * has succeed or not). By default, this method will invoke
     * finish method in {@link pers.scruel.gui.TipsFrame}, and
     * invoke {@link #afterActionCompleted()} to do actual
     * operations like reset clipboard when action has completed.
     */
    void actionCompleted();

    /**
     * Invoked after operation action has completed(whether it
     * has succeed or not).
     */
    void afterActionCompleted();

    /**
     * Invoked
     */
    void updateActionSum(int sum);

    /**
     * Receives and update process result, discards old result and
     * replaces it by new result.
     *
     * @param stringBuffer
     */
    void updateResult(StringBuffer stringBuffer);

    /**
     * Receives process result, appends new result to the old one.
     *
     * @param stringBuffer
     */
    void appendResult(StringBuffer stringBuffer);

    void appendResult(String stringBuffer);
}
