package pers.scruel.gui;


import pers.scruel.util.PropertiesUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * A simple bubble tip frame window that displays the processing status
 * and result messages.
 *
 * @author Scruel Tao <scruel@vip.qq.com>
 */
public class TipsFrame extends JFrame {
  private final boolean needTips = getNeedTips();
  private static JLabel jLabel;
  private String labTitle = "";

  public TipsFrame() {
    super();
    jLabel = new JLabel();
    Icon icon = new ImageIcon(Objects.requireNonNull(TipsFrame.class.getClassLoader().getResource("l.gif")));
    jLabel.setIcon(icon);
    JPanel jPanel = new JPanel();
    jPanel.setBackground(new Color(232, 238, 248));
    jPanel.add(jLabel);
    this.setAlwaysOnTop(true);
    this.setFocusable(false);
    this.setFocusableWindowState(false);
    this.getContentPane().add(jPanel);
    this.setUndecorated(true);
    this.setSize(150, 50);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(((int) screenSize.getWidth()) - 150,
        ((int) screenSize.getHeight() - 40 - 50));
  }

  public void initJlabelTitle(String labTitle) {
    this.labTitle = labTitle;
    jLabel.setText(labTitle + "…… ");
    jLabel.repaint();
  }

  public void updateJLable(int current, int total) {
    jLabel.setText(String.format("%s…… %d/%d", labTitle, current, total));
    jLabel.repaint();
  }

  public void finish(String s) {
    finish(s, 2500, false);
  }

  public void finish(String s, long millis, boolean failed) {
    jLabel.setIcon(null);
    jLabel.setText(s);
    if (failed) {
      jLabel.setForeground(Color.red);
    }
    jLabel.repaint();

    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.setVisible(false);
  }

  private boolean getNeedTips() {
    try {
      return "true".equals(PropertiesUtils.getProperties().getProperty("window.tips"));
    } catch (IOException ignore) {
      return false;
    }
  }

  public boolean needTips() {
    return this.needTips;
  }
}
