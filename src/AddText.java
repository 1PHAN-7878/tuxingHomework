import javax.swing.*;

import java.awt.*;
public class AddText extends JFrame{
    DrawingPanel drawingPanel = DrawingPanel.getInstance();
    public AddText(){

    }
    //控制字体大小
    private int fontSize;

    public DrawingPanel getDrawingPanel() {
        return drawingPanel;
    }

    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(int fontFamily) {
        this.fontFamily = fontFamily;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
    }

    //控制字体
    private int fontFamily;
    //控制斜体
    private boolean isItalic;
    public void addTextGo(){
        JDialog dialog = new JDialog(this, "Text Input", true);
        dialog.setLayout(new GridLayout(0, 2));

        JTextField xField = new JTextField();
        JTextField yField = new JTextField();
        JTextField textField = new JTextField();
        JTextField widthField = new JTextField("100"); // 默认文本框宽度
        JTextField heightField = new JTextField("20"); // 默认文本框高度

        dialog.add(new JLabel("X:"));
        dialog.add(xField);
        dialog.add(new JLabel("Y:"));
        dialog.add(yField);
        dialog.add(new JLabel("Text:"));
        dialog.add(textField);
        dialog.add(new JLabel("Width:"));
        dialog.add(widthField);
        dialog.add(new JLabel("Height:"));
        dialog.add(heightField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e1 -> {
            int x = Integer.parseInt(xField.getText());
            int y = Integer.parseInt(yField.getText());
            String text = textField.getText();
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            drawText(text, x, y, width, height);
            dialog.dispose(); // 关闭对话框
        });

        dialog.add(confirmButton);

        dialog.setSize(300, 200);
//            dialog.setLocationRelativeTo(parentFrame);
        dialog.setVisible(true);
    }

    private void drawText(String text, int x, int y, int width, int height){

        if (drawingPanel.getG2() != null) {
            System.out.println("writing");
            drawingPanel.getG2().setColor(drawingPanel.getCurrentColor());
            drawingPanel.getG2().setFont(new Font("Arial", Font.PLAIN, 12)); // 设置字体
            drawingPanel.getG2().drawString(text, x, y);
//            drawingPanel.getG2().drawRect(x, y, width, height); // 绘制文本框边框
            repaint();
        }
    }
}
