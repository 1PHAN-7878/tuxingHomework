import javax.swing.*;
import java.awt.*;

public class ToolBar extends JToolBar {

    public ToolBar(DrawingPanel drawingPanel) {
        // 添加绘图工具按钮
        JButton drawButton = new JButton("Draw");
        drawButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.DRAW));
        add(drawButton);

        // 添加橡皮擦工具按钮
        JButton eraseButton = new JButton("Erase");
        eraseButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.ERASE));
        add(eraseButton);

        // 添加直线工具按钮
        JButton lineButton = new JButton("Line");
        lineButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.LINE));
        add(lineButton);

        // 添加曲线工具按钮
        JButton curveButton = new JButton("Curve");
        curveButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.CURVE));
        add(curveButton);

        // 添加矩形工具按钮
        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.RECTANGLE));
        add(rectangleButton);

        // 添加椭圆工具按钮
        JButton ovalButton = new JButton("Oval");
        ovalButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.OVAL));
        add(ovalButton);

        // 添加颜色选择器按钮
        JButton colorButton = new JButton("Color");
        colorButton.addActionListener(e -> chooseColor(drawingPanel));
        add(colorButton);

        // 添加颜色选择器按钮
        JButton textButton = new JButton("Text");
        colorButton.addActionListener(e -> drawingPanel.setTool(DrawingPanel.Tool.TEXT));
        add(textButton);

        // 添加调节线条粗细的滑动条
        JSlider strokeSlider = new JSlider(JSlider.HORIZONTAL, 1, 20, 2);
        strokeSlider.setMajorTickSpacing(1);
        strokeSlider.setPaintTicks(true);
        strokeSlider.setPaintLabels(true);
        System.out.println("drawingPanel: " + drawingPanel);
        System.out.println("strokeSlider: " + strokeSlider);
        strokeSlider.addChangeListener(e -> drawingPanel.setStrokeWidth(strokeSlider.getValue()));
        add(new JLabel("Line Width: "));
        add(strokeSlider);
    }

    private void chooseColor(DrawingPanel drawingPanel) {
        Color initialColor = drawingPanel.getCurrentColor();
        Color chosenColor = JColorChooser.showDialog(this, "Choose Drawing Color", initialColor);
        if (chosenColor != null) {
            drawingPanel.setCurrentColor(chosenColor);
        }
    }
}
