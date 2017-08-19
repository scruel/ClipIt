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
        this.setFocusable(false);
        this.setFocusableWindowState(false);
        this.getContentPane().add(jPanel);
        this.setUndecorated(true);
        this.setSize(150, 50);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(((int) screenSize.getWidth()) - 150,
            ((int) screenSize.getHeight() - 25 - 50));
        jFrame = this;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private int totalNeededUploadSum = 0;
    private int currNeededUploadSum = 0;

    public void setTotalNeededUploadSum(int totalNeededUploadSum) {
        jLabel.setText("上传中…… " + currNeededUploadSum + "/" + totalNeededUploadSum);
        jLabel.repaint();
        this.totalNeededUploadSum = totalNeededUploadSum;
    }

    public void notifyUpload() {
        if (totalNeededUploadSum == 0) {
            finish();
        }
        currNeededUploadSum++;
        if (totalNeededUploadSum == currNeededUploadSum) {
            finish();
        }
        else {
            jLabel.setText("上传中…… " + currNeededUploadSum + "/" + totalNeededUploadSum);
            jLabel.repaint();
        }
    }

    public void finish() {
        jLabel.setIcon(null);
        jLabel.setText("上传复制完成!");
        jLabel.repaint();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jFrame.setVisible(false);
        this.dispose();
        ClipboardUtil.setClipBoard(QiNiuUtil.getSb().toString());
    }

}
