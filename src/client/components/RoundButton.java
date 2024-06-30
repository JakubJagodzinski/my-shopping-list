package client.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {

    private Color backgroundColor;
    private Color hoverBackgroundColor;
    private Color pressBackgroundColor;
    private Color foregroundColor;
    private boolean isHovered;
    private boolean isPressed;
    private boolean isEnabled;

    public RoundButton(String text, int width, int height) {
        initializeButton(text);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

    public RoundButton(String text) {
        initializeButton(text);
        setPreferredSize(new Dimension(240, 60));
        setMinimumSize(new Dimension(240, 60));
        setMaximumSize(new Dimension(240, 60));
    }

    private void initializeButton(String text) {
        super.setText(text);
        this.setEnabled(true);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        this.backgroundColor = Colors.BUTTON_COLOR;
        this.hoverBackgroundColor = Colors.BUTTON_HOVER_COLOR;
        this.pressBackgroundColor = Colors.BUTTON_PRESS_COLOR;
        this.foregroundColor = Colors.BACKGROUND_COLOR;
        this.isHovered = false;
        this.isPressed = false;

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled) {
                    isPressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isEnabled) {
                    isPressed = false;
                    repaint();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled) {
                    isHovered = true;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled) {
                    isHovered = false;
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }
            }

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        RoundRectangle2D roundRectangle = new RoundRectangle2D.Float(0, 0, width, height, 20, 20);
        if (this.isPressed) {
            g2.setColor(this.pressBackgroundColor);
            g2.fill(roundRectangle);
        } else {
            g2.setColor(this.isHovered ? this.hoverBackgroundColor : this.backgroundColor);
            g2.fill(roundRectangle);
        }

        g2.setColor(this.foregroundColor);
        FontMetrics metrics = g2.getFontMetrics();
        int x = (width - metrics.stringWidth(getText())) / 2;
        int y = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2.drawString(getText(), x, y);

        g2.dispose();
    }

    public void setEnabled(boolean b) {
        super.setEnabled(b);
        this.isEnabled = b;
        if (this.isEnabled) {
            this.setBackgroundColor(this.backgroundColor);
        } else {
            this.setBackgroundColor(Colors.BUTTON_INACTIVE_COLOR);
        }
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setHoverBackgroundColor(Color color) {
        this.hoverBackgroundColor = color;
        repaint();
    }

    public void setPressBackgroundColor(Color color) {
        this.pressBackgroundColor = color;
        repaint();
    }

    public void setForegroundColor(Color color) {
        this.foregroundColor = color;
        repaint();
    }

}
