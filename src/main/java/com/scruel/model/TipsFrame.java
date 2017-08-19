package com.scruel.model;

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
    private int currNeededUploadSum = 0;

    public void setTotalNeededUploadSum(int totalNeededUploadSum) {
        if (totalNeededUploadSum == 0) finish("无内容需要上传！");
        updateJLable();
        this.totalNeededUploadSum = totalNeededUploadSum;
    }


    private void updateJLable() {
        jLabel.setText("上传中…… " + currNeededUploadSum + "/" + totalNeededUploadSum);
        jLabel.repaint();
    }

    public void notifyUpload() {
        currNeededUploadSum++;
        if (totalNeededUploadSum == 0 || totalNeededUploadSum == currNeededUploadSum) {
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
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jFrame.setVisible(false);
        this.dispose();
        ClipboardUtil.setClipBoard(QiNiuUtil.getSb().toString());
    }

    private void finish() {
        finish("上传复制完成!");
    }


}
