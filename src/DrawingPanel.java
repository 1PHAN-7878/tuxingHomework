import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class DrawingPanel extends JPanel {

    private static DrawingPanel instance;

    public static DrawingPanel getInstance() {
        if (instance == null) {
            instance = new DrawingPanel();
        }
        return instance;
    }


    public enum Tool {
        DRAW, ERASE, LINE, CURVE, RECTANGLE, OVAL, TEXT
    }

    private Image image;
    private Graphics2D g2;
    private int x, y, prevX, prevY;
    private Tool currentTool = Tool.DRAW;
    private Color currentColor = Color.BLACK;  // 默认绘图颜色为黑色

    public static void setInstance(DrawingPanel instance) {
        DrawingPanel.instance = instance;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Graphics2D getG2() {
        return g2;
    }

    public void setG2(Graphics2D g2) {
        this.g2 = g2;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPrevX() {
        return prevX;
    }

    public void setPrevX(int prevX) {
        this.prevX = prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public void setPrevY(int prevY) {
        this.prevY = prevY;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public boolean isDrawingShape() {
        return drawingShape;
    }

    public void setDrawingShape(boolean drawingShape) {
        this.drawingShape = drawingShape;
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(Shape currentShape) {
        this.currentShape = currentShape;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    private int strokeWidth = 2;  // 默认线条粗细
    private int startX, startY; // 形状的起始坐标
    private boolean drawingShape = false; // 是否正在绘制形状
    private Shape currentShape = null; // 当前正在绘制的形状
    private JTextField textField; // 用于接收文字输入的文本框

    public DrawingPanel() {
        setDoubleBuffered(true);
//        setFocusable(true); // 让面板可以获取焦点，以便接收键盘事件

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 获取鼠标点击时的X和Y坐标
                prevX = e.getX();
                prevY = e.getY();
                System.out.println("drawing tool is" + currentTool);
                // 如果当前工具是线、矩形或椭圆
                if (currentTool == Tool.LINE || currentTool == Tool.RECTANGLE || currentTool == Tool.OVAL) {
                    // 记录起始点的坐标
                    startX = prevX;
                    startY = prevY;
                    // 标记开始绘制形状
                    drawingShape = true;
                }
//        // 如果当前工具是文本（该部分代码已被注释掉）
//        else if (currentTool == Tool.TEXT) {
//            // 在鼠标点击位置创建一个文本输入框
//            textField = new JTextField(20);
//            textField.setBounds(prevX, prevY, 200, 20);
//            textField.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    // 获取文本框中的文本
//                    String text = textField.getText();
//                    // 在指定位置绘制文本
//                    drawText(text, prevX, prevY);
//                    // 添加文字后移除文本框
//                    remove(textField);
//                    textField = null;
//                    // currentTool = null; // 这行代码被注释掉了，防止工具被重置
//                }
//            });
//            // 将文本框添加到面板中
//            add(textField);
//            // 让文本框获得焦点
//            textField.requestFocus();
//        }
                // 如果当前的绘图对象(g2)不为空
                else if (g2 != null) {
                    // 如果当前工具是绘图或曲线
                    if (currentTool == Tool.DRAW || currentTool == Tool.CURVE) {
                        // 设置笔划宽度和颜色
                        g2.setStroke(new BasicStroke(strokeWidth));
                        g2.setColor(currentColor);
                    }
                    // 如果当前工具是橡皮擦
                    else if (currentTool == Tool.ERASE) {
                        // 设置笔划宽度并将颜色设为白色（假设白色是背景色，用于擦除）
                        g2.setStroke(new BasicStroke(strokeWidth));
                        g2.setColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // 如果正在绘制形状
                if (drawingShape) {
                    // 获取鼠标释放时的X和Y坐标
                    int endX = e.getX();
                    int endY = e.getY();

                    // 设置笔划宽度和颜色
                    g2.setStroke(new BasicStroke(strokeWidth));
                    g2.setColor(currentColor);

                    // 根据当前工具绘制相应的形状
                    if (currentTool == Tool.LINE) {
                        // 绘制线条
                        g2.drawLine(startX, startY, endX, endY);
                    } else if (currentTool == Tool.RECTANGLE) {
                        // 绘制矩形
                        g2.drawRect(Math.min(startX, endX), Math.min(startY, endY),
                                Math.abs(endX - startX), Math.abs(endY - startY));
                    } else if (currentTool == Tool.OVAL) {
                        // 绘制椭圆
                        g2.drawOval(Math.min(startX, endX), Math.min(startY, endY),
                                Math.abs(endX - startX), Math.abs(endY - startY));
                    }

                    // 结束绘制形状
                    drawingShape = false;

                    // 重新绘制组件以更新显示
                    repaint();
                }
            }

        });

        // 添加鼠标拖动事件监听器，处理鼠标拖动事件
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // 获取鼠标拖动时的X和Y坐标
                x = e.getX();
                y = e.getY();

                // 如果绘图对象(g2)不为空且不是在绘制形状
                if (g2 != null && !drawingShape) {
                    // 绘制从前一个点到当前点的线段
                    g2.drawLine(prevX, prevY, x, y);
                    // 重新绘制组件以更新显示
                    repaint();
                    // 更新前一个点的坐标为当前点的坐标
                    prevX = x;
                    prevY = y;
                }
            }
        });

// 添加键盘事件监听器，处理键盘事件
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // 如果正在绘制形状
                if (drawingShape) {
                    // 获取按下的键的键码
                    int keyCode = e.getKeyCode();
                    // 当按下回车键时结束绘制形状
                    if (keyCode == KeyEvent.VK_ENTER) {
                        drawingShape = false;
                        // 重新绘制组件以更新显示
                        repaint();
                    }
                }
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
        System.out.println("drawingShape is " + drawingShape);
        System.out.println("g2" + g2);
        // 绘制当前正在绘制的形状（如果存在）
        if (drawingShape && g2 != null) {
            g2.setColor(currentColor);
            if (currentTool == Tool.LINE) {
                g2.drawLine(startX, startY, x, y);
            } else if (currentTool == Tool.RECTANGLE) {
                int width = Math.abs(x - startX);
                int height = Math.abs(y - startY);
                g2.drawRect(Math.min(startX, x), Math.min(startY, y), width, height);
            } else if (currentTool == Tool.OVAL) {
                int width = Math.abs(x - startX);
                int height = Math.abs(y - startY);
                g2.drawOval(Math.min(startX, x), Math.min(startY, y), width, height);
            }
        }
//        repaint();
    }

    public void clear() {
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.BLACK);
        repaint();
    }

    public void setTool(Tool tool) {
        currentTool = tool;
        System.out.println("tool is" + tool.name());
//        if (textField != null) {
//            remove(textField); // 切换工具时移除文本框
//            textField = null;
//            repaint();
//        }
//        repaint();
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setStrokeWidth(int width) {
        strokeWidth = width;
    }

    private void drawText(String text, int x, int y) {
        if (g2 != null) {
            g2.setColor(currentColor);
            g2.drawString(text, x, y);
            repaint();
        }
    }


}
