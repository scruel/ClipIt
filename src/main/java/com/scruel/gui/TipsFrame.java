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

    public TipsFrame() {
        super();
        jLabel = new JLabel();
        jLabel.setText("上传中……");
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

    private int totalNeededUploadSum = 0;
    private int currNeededUploadSumSuccess = 0;
    private int currNeededUploadSumFail = 0;

    public void setTotalNeededUploadSum(int totalNeededUploadSum) {
        if (totalNeededUploadSum == 0) finish("无内容需处理！");
        this.totalNeededUploadSum = totalNeededUploadSum;
        updateJLable();
    }


    private void updateJLable() {
        jLabel.setText("上传中…… " + (currNeededUploadSumFail + currNeededUploadSumSuccess) + "/" + totalNeededUploadSum);
        jLabel.repaint();
    }

    public void notifyUploadSuccess() {
        currNeededUploadSumSuccess++;
        if (totalNeededUploadSum == 0 || totalNeededUploadSum == (currNeededUploadSumFail + currNeededUploadSumSuccess)) {
            finish();
        }
        else {
            updateJLable();
        }
    }

    public void notifyUploadFail() {
        currNeededUploadSumFail++;
        if (totalNeededUploadSum == 0 || totalNeededUploadSum == (currNeededUploadSumFail + currNeededUploadSumSuccess)) {
            finish();
        }
        else {
            updateJLable();
        }
    }

    public void finish(String s) {
        jLabel.setIcon(null);
        jLabel.setText(s);
        jLabel.repaint();
        try {
            if (currNeededUploadSumFail != 0) {
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
        if (currNeededUploadSumFail != 0)
            ClipboardUtil.setClipBoard(QiNiuUtil.getSb().toString());
    }

    private void finish() {
        String failstr = "";
        if (currNeededUploadSumFail != 0) {
            failstr = "失败:" + currNeededUploadSumFail;
        }
        finish("上传复制完成! " + failstr);
    }


}
