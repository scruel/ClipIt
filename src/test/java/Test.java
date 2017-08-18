import com.scruel.util.IOUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Scruel on 2017/8/18.
 * Personal blog : http://blog.csdn.net/scruelt
 * Github : https://github.com/scruel
 */
@SuppressWarnings("unchecked")
public class Test implements ClipboardOwner {
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private boolean going; //控制开关
    private static JFrame jFrame;

    /************
     * 测试代码 *
     * **********/
    public static void main(String[] args) {
        Test tmp = new Test();
        tmp.begin(); //开始监视
        jFrame = new JFrame();
        jFrame.setVisible(true);// 软件窗口
    }

    /*****************
     * 开始监视剪贴板 *
     * ***************/
    public void begin() {
        going = true;
        //将剪贴板中内容的ClipboardOwner设置为自己
        //这样当其中内容变化时，就会触发lostOwnership事件
        clipboard.setContents(clipboard.getContents(null), this);
    }

    /*****************
     * 停止监视剪贴板 *
     * ***************/
    public void stop() {
        going = false;
    }

    /*******************************************
     * 如果剪贴板的内容改变，则系统自动调用此方法 *
     *******************************************/
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        if (going) { //如果是进行中状态，则操作
            // 如果不暂停一下，经常会抛出IllegalStateException
            // 猜测是操作系统正在使用系统剪切板，故暂时无法访问
            while (true) {
                Throwable throwable = null;
                try {
                    clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor);
                } catch (IllegalStateException e) {
                    throwable = e;
                }
                if (throwable == null) break;
            }
            // try {
            //     Thread.sleep(10);
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            // 取出文本并进行一次文本处理
            // 如果剪贴板中有文本:
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                try {
                    String text = (String) clipboard.getData(DataFlavor.stringFlavor);
                    System.out.println("text:" + text);
                    // 存入剪贴板，并注册自己为所有者
                    // 这样下次剪贴板内容改变时，仍然可以触发此事件
                    StringSelection tmp = new StringSelection(text);
                    clipboard.setContents(tmp, this);
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.javaFileListFlavor)) {

                try {
                    List<File> text = (List<File>) clipboard.getData(DataFlavor.javaFileListFlavor);
                    System.out.println("file:" + text.get(0).toString());
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }

                // 如果剪贴板中没有文本，仍然将自己设置为它的ClipboardOwner
                clipboard.setContents(clipboard.getContents(null), this);
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {

                try {
                    Image image = ((Image) clipboard.getData(DataFlavor.imageFlavor));
                    jFrame.setIconImage(image);
                    // Panel panel = new Panel();
                    // Graphics g =
                    // panel.add(text);
                    // jFrame.getContentPane().add(panel);
                    byte[] bytes = IOUnit.getImgBytes(image);
                    System.out.println(new String(bytes));
                    System.out.println("img:" + image.toString());
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }

                // 如果剪贴板中没有文本，仍然将自己设置为它的ClipboardOwner
                clipboard.setContents(clipboard.getContents(null), this);
            }
            else if (clipboard.isDataFlavorAvailable(DataFlavor.allHtmlFlavor)) {

                try {
                    String text = ((String) clipboard.getData(DataFlavor.allHtmlFlavor));
                    System.out.println("html:" + text);

                    Document doc = Jsoup.parse(text);
                    Elements elements = doc.select("img");
                    System.out.println(elements.size());
                    for (Element element : elements) {
                        String filePath = element.attr("src");
                        System.out.println(filePath);
                        System.out.println(element.text());
                    }
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }

                // 如果剪贴板中没有文本，仍然将自己设置为它的ClipboardOwner
                clipboard.setContents(clipboard.getContents(null), this);
            }
            else {
                Transferable transferable = clipboard.getContents(null);
                for (DataFlavor dataFlavor : transferable.getTransferDataFlavors()) {
                    System.out.println("ukn:" + dataFlavor);
                }
                // System.out.println("ukn:" +transferable.isDataFlavorSupported());
            }
        }
    }

}
