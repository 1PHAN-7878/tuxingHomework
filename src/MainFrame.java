
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {


    private DrawingPanel drawingPanel = DrawingPanel.getInstance();

    public MainFrame() {
        // 设置主窗口属性
        setTitle("Java Swing Paint Tool");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
//        存留疑问，为什么在这里就不会出现不能的情况了呢？
        drawingPanel = new DrawingPanel();
        // 创建菜单栏和工具栏
//        createMenuBar();
        createToolBar();
        // 初始化绘图面板
//        drawingPanel = DrawingPanel.getInstance();

        add(drawingPanel, BorderLayout.CENTER);



        setVisible(true);
    }

//    private void createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
//        //文件菜单
//        JMenu fileMenu = new JMenu("File");
//        //字体菜单
//        JMenu textMenu = new JMenu("Text");
//
//        //文件子菜单
//        JMenuItem newItem = new JMenuItem("New");
//        newItem.addActionListener(e -> drawingPanel.clear());
//        JMenuItem exitItem = new JMenuItem("Exit");
//        exitItem.addActionListener(e -> System.exit(0));
//
//        //文字子菜单
//        JMenuItem addTextItem = new JMenuItem("Add Text");
//        addTextItem.addActionListener(e -> {
//            addText.addTextGo();
//
//        });
//
//
//        JMenu setTextFont = new JMenu("setFontFamily");
//        // 添加字体风格选项
//        JMenuItem fontMenuItem1 = new JMenuItem("Plain");
//        JMenuItem fontMenuItem2 = new JMenuItem("Bold");
//        JMenuItem fontMenuItem3 = new JMenuItem("Italic");
//
//        JMenuItem fontNameMenuItem1 = new JMenuItem("Arial");
//        JMenuItem fontNameMenuItem2 = new JMenuItem("Times New Roman");
//        JMenuItem fontNameMenuItem3 = new JMenuItem("Courier New");
//
//        setTextFont.add(fontNameMenuItem1);
//        setTextFont.add(fontNameMenuItem2);
//        setTextFont.add(fontNameMenuItem3);
//
//        setTextFont.addActionListener(e -> {
//
//        });
//        //文件
//        fileMenu.add(newItem);
//        fileMenu.add(exitItem);
//
//        //文字
//        textMenu.add(addTextItem);
//        textMenu.add(setTextFont);
//
//        menuBar.add(fileMenu);
//        menuBar.add(textMenu);
//        setJMenuBar(menuBar);
//    }

    private void createToolBar() {
        ToolBar toolBar = new ToolBar(drawingPanel);
        add(toolBar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
