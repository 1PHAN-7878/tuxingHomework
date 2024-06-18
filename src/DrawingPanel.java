import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawingPanel extends JPanel {

    private static DrawingPanel instance;

    public static DrawingPanel getInstance() {
        if (instance == null) {
            instance = new DrawingPanel();
        }
        return instance;
    }

    public enum Tool {
        DRAW, ERASE, LINE, CURVE, RECTANGLE, OVAL, TEXT, SOLID_OVAL, SOLID_RECTANGLE
    }

    private Image image;
    private Graphics2D g2;
    private int x, y, prevX, prevY;
    private Tool currentTool = Tool.LINE;
    private Color currentColor = Color.BLACK;

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

    private int strokeWidth = 2;
    private int startX, startY;
    private boolean drawingShape = false;
    private Shape currentShape = null;
    private JTextField textField;

    public DrawingPanel() {
        setDoubleBuffered(true);
//        setSize(50,50);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
//                currentTool = Tool.CURVE;
                System.out.println("Mouse pressed: drawing tool is " + getCurrentTool().name());
                if (currentTool == Tool.LINE || currentTool == Tool.RECTANGLE || currentTool == Tool.OVAL
                        || currentTool == Tool.SOLID_RECTANGLE || currentTool == Tool.SOLID_OVAL) {
                    startX = prevX;
                    startY = prevY;
                    drawingShape = true;
                } else if (g2 != null) {
                    if (currentTool == Tool.DRAW || currentTool == Tool.CURVE) {
                        g2.setStroke(new BasicStroke(strokeWidth));
                        g2.setColor(currentColor);
                    } else if (currentTool == Tool.ERASE) {
                        g2.setStroke(new BasicStroke(strokeWidth));
                        g2.setColor(Color.WHITE);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (drawingShape) {
                    int endX = e.getX();
                    int endY = e.getY();

                    g2.setStroke(new BasicStroke(strokeWidth));
                    g2.setColor(currentColor);

                    if (currentTool == Tool.LINE) {
                        g2.drawLine(startX, startY, endX, endY);
                    } else if (currentTool == Tool.RECTANGLE) {
                        g2.drawRect(Math.min(startX, endX), Math.min(startY, endY),
                                Math.abs(endX - startX), Math.abs(endY - startY));
                    } else if (currentTool == Tool.OVAL) {
                        g2.drawOval(Math.min(startX, endX), Math.min(startY, endY),
                                Math.abs(endX - startX), Math.abs(endY - startY));
                    }
                    else if (currentTool == Tool.SOLID_RECTANGLE) {
                        g2.fillRect(Math.min(startX, x), Math.min(startY, y),
                                Math.abs(x - startX), Math.abs(y - startY));
                    }
                    else if (currentTool == Tool.SOLID_OVAL) {
                        g2.fillOval(Math.min(startX, x), Math.min(startY, y),
                                Math.abs(x - startX), Math.abs(y - startY));
                    }
                    drawingShape = false;
                    repaint();
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();

                if (g2 != null && !drawingShape) {
                    g2.drawLine(prevX, prevY, x, y);
                    repaint();
                    prevX = x;
                    prevY = y;
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (drawingShape) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_ENTER) {
                        drawingShape = false;
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
        System.out.println("Painting: drawingShape is " + drawingShape);
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
            else if (currentTool == Tool.SOLID_RECTANGLE) {
                g2.fillRect(Math.min(startX, x), Math.min(startY, y),
                        Math.abs(x - startX), Math.abs(y - startY));
            }
            else if (currentTool == Tool.SOLID_OVAL) {
                g2.fillOval(Math.min(startX, x), Math.min(startY, y),
                        Math.abs(x - startX), Math.abs(y - startY));
            }
        }
    }

    public void clear() {
        g2.setPaint(Color.WHITE);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.BLACK);
        repaint();
    }


    public void setTool(Tool tool) {
            this.currentTool = tool;
            System.out.println("setTool: tool is " + tool.name());
            System.out.println("setTool: currentTool is " + currentTool.name());

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

    public void translateGraphics(int dx, int dy) {
        // 保存当前 Graphics2D 对象的状态
        AffineTransform savedTransform = g2.getTransform();

        // 创建一个新的仿射变换对象，用于平移
        AffineTransform translateTransform = new AffineTransform();
        translateTransform.translate(dx, dy);

        // 应用平移变换到 Graphics2D 对象
        g2.transform(translateTransform);

        // 重新绘制所有图形，这里假设已经在 paintComponent 方法中绘制了各种图形
        repaint();

        // 恢复原始的 Graphics2D 对象状态
        g2.setTransform(savedTransform);
    }

    public void rotateGraphics(double theta){
        g2.rotate(theta);
    }
    public void saveDrawing() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Drawing");

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try {
                // 获取绘制内容的图片
                Image image = this.createImage(this.getWidth(), this.getHeight());
                Graphics2D graphics2D = (Graphics2D) image.getGraphics();
                this.paint(graphics2D);

                // 保存为PNG格式图片
                ImageIO.write((BufferedImage) image, "PNG", fileToSave);
                graphics2D.dispose();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
