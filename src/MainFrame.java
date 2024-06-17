
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {


    private DrawingPanel drawingPanel;
    AddText addText;
    public MainFrame() {
        // 设置主窗口属性
        setTitle("Java Swing Paint Tool");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // 获取内容面板
        Container contentPane = getContentPane();
        // 设置内容面板的背景色为灰色
        contentPane.setBackground(Color.GRAY);
//        setSize(800, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        addText = new AddText();
//        存留疑问，为什么在这里就不会出现不能的情况了呢？
//        drawingPanel = new DrawingPanel();
        drawingPanel = DrawingPanel.getInstance();
        // 创建菜单栏和工具栏
        createMenuBar();
        createToolBar();
        // 初始化绘图面板
//        drawingPanel = DrawingPanel.getInstance();

        add(drawingPanel, BorderLayout.CENTER);



        setVisible(true);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        //文件菜单
        JMenu fileMenu = new JMenu("File");
        //字体菜单
        JMenu textMenu = new JMenu("Text");

        //文件子菜单
        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(e -> drawingPanel.clear());
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        //文字子菜单
        JMenuItem addTextItem = new JMenuItem("Add Text");
        addTextItem.addActionListener(e -> {
            addText.addTextGo();
        });

        //字体子菜单更改样式
        JMenu setTextFontStyle = new JMenu("setFontStyle");
        // 添加字体风格选项
        JMenuItem fontMenuItem1 = new JMenuItem("Plain");
        fontMenuItem1.addActionListener(e -> {
            addText.fontStyle = Font.PLAIN;
        });
        JMenuItem fontMenuItem2 = new JMenuItem("Bold");
        fontMenuItem2.addActionListener(e -> {
            addText.fontStyle = Font.BOLD;
        });
        JMenuItem fontMenuItem3 = new JMenuItem("Italic");
        fontMenuItem3.addActionListener(e -> {
            addText.fontStyle = Font.ITALIC;
        });
        JMenuItem fontNameMenuItem1 = new JMenuItem("Arial");
        JMenuItem fontNameMenuItem2 = new JMenuItem("Times New Roman");
        JMenuItem fontNameMenuItem3 = new JMenuItem("Courier New");

        //字体子菜单更改字体
        JMenuItem setTextFontFamily = new JMenuItem("setFontFamily");
        //字体选择窗口
        setTextFontFamily.addActionListener(e -> {
            addText.setFontFamily();
        });

        //字体子菜单更改大小
        JMenuItem setTextFontSize = new JMenuItem("setFontSize");
        //字体选择窗口
        setTextFontSize.addActionListener(e -> {
            addText.setFontSize();
        });

        setTextFontStyle.add(fontNameMenuItem1);
        setTextFontStyle.add(fontNameMenuItem2);
        setTextFontStyle.add(fontNameMenuItem3);

        setTextFontStyle.addActionListener(e -> {

        });
        //新建变换菜单
        JMenu transMenu = new JMenu("trans");
        //变换菜单下内容
        JMenuItem transformItem = new JMenuItem("transform");
        transformItem.addActionListener(e -> {
            drawingPanel.translateGraphics(10, 10);
        });

        JMenuItem rotateItem = new JMenuItem("rotate");
        rotateItem.addActionListener(e -> {
            drawingPanel.rotateGraphics(10.0);
        });

        //文件
        fileMenu.add(newItem);
        fileMenu.add(exitItem);

        //文字
        textMenu.add(addTextItem);
        textMenu.add(setTextFontStyle);
        textMenu.add(setTextFontFamily);
        textMenu.add(setTextFontSize);
        //变换
        transMenu.add(transformItem);
        transMenu.add(rotateItem);

        //添加到总面板
        menuBar.add(fileMenu);
        menuBar.add(textMenu);
        menuBar.add(transMenu);


        setJMenuBar(menuBar);
    }

    private void createToolBar() {
        ToolBar toolBar = new ToolBar(drawingPanel);
        add(toolBar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
