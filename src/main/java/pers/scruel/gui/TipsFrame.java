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
 * @author Scruel Tao <scruelt@hotmail.com>
 */
public class TipsFrame extends JFrame {
  private static JLabel jLabel;
  private final boolean needTips = getNeedTips();
  private final Icon icon = new ImageIcon(Objects.requireNonNull(TipsFrame.class.getClassLoader().getResource("l.gif")));
  private String labTitle = "";

  public TipsFrame() {
    super();
    jLabel = new JLabel();
    jLabel.setIcon(icon);
    JPanel jPanel = new JPanel();
    jPanel.setBackground(new Color(232, 238, 248));
    jPanel.add(jLabel);
    this.setAlwaysOnTop(true);
    this.setFocusable(false);
    this.setFocusableWindowState(false);
    this.getContentPane().add(jPanel);
    this.setUndecorated(true);
    this.setSize(150, 100);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation(((int) screenSize.getWidth()) - 150,
            ((int) screenSize.getHeight() - 40 - 50));
  }

  public void initJLabelTitle(String labTitle) {
    this.labTitle = labTitle;
    jLabel.setText(labTitle + "…… ");
    jLabel.setForeground(null);
    jLabel.setIcon(icon);
    jLabel.repaint();
  }

  public void updateJLabel(int current, int total) {
    jLabel.setText(String.format("%s…… %d/%d", labTitle, current, total));
    jLabel.setForeground(null);
    jLabel.setIcon(icon);
    jLabel.repaint();
  }

  public void updateJLabel(int current, int total, String msg) {
    jLabel.setText(String.format("%s…… %d/%d", labTitle, current, total));
    jLabel.setForeground(null);
    jLabel.setIcon(icon);
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
