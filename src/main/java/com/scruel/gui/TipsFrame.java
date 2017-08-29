package com.scruel.gui;

import com.scruel.util.ClipboardUtil;
import com.scruel.util.QiNiuUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Scruel on 2017/8/19.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
public class TipsFrame extends JFrame {
    private static JLabel jLabel;
    private static JFrame jFrame;
    private String labTitle = "";
    private int totalNeededSum = 0;


    public TipsFrame() {
        super();
        jLabel = new JLabel();
        Icon icon = new ImageIcon(TipsFrame.class.getClassLoader().getResource("l.gif"));
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
        jFrame = this;
    }

    private int currSuccessSum = 0;
    private int currFailSum = 0;

    public void initJlabelTitle(String labTitle) {
        this.labTitle = labTitle;
        jLabel.setText(labTitle + "…… ");
        jLabel.repaint();
    }

    private void updateJLable() {
        jLabel.setText(labTitle + "…… " + (currFailSum + currSuccessSum) + "/" + totalNeededSum);
        jLabel.repaint();
    }

    public int getTotalNeededSum() {
        return totalNeededSum;
    }

    public void setTotalNeededSum(int totalNeededUploadSum) {
        if (totalNeededUploadSum == 0) finish("无内容需处理！");
        this.totalNeededSum = totalNeededUploadSum;
        updateJLable();
    }

    public int getCurrSuccessSum() {
        return currSuccessSum;
    }

    public int getCurrFailSum() {
        return currFailSum;
    }

    public void notifySuccess() {
        currSuccessSum++;
        if (totalNeededSum == 0 || totalNeededSum == (currFailSum + currSuccessSum)) {
            finish();
        }
        else {
            updateJLable();
        }
    }

    public void notifyFail() {
        currFailSum++;
        if (totalNeededSum == 0 || totalNeededSum == (currFailSum + currSuccessSum)) {
            finish();
        }
        else {
            updateJLable();
        }
    }

    public void finish(String s) {
        jLabel.setIcon(null);
        jLabel.setText(s);
        if (currFailSum != 0) {
            jLabel.setForeground(Color.red);
        }
        jLabel.repaint();
        try {
            if (currFailSum != 0) {
                Thread.sleep(2500);
            }
            else {
                Thread.sleep(1200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jFrame.setVisible(false);
        this.dispose();
        if ("uploading".equals(labTitle))
            ClipboardUtil.setClipBoard(QiNiuUtil.getSb().toString());
    }

    public void finish() {
        String failstr = "";
        if (currFailSum != 0) {
            failstr = "失败:" + currFailSum;
        }
        finish("完成! " + failstr);
    }


}
