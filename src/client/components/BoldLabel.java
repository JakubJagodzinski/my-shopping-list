package client.components;

import javax.swing.*;
import java.awt.*;

public class BoldLabel extends JLabel {

    public BoldLabel(String text) {
        super(text);

        this.setForeground(Colors.FOREGROUND_COLOR);
        this.setSize(500, 500);
        this.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public BoldLabel(String text, Color foregroundColor) {
        super(text);

        this.setForeground(foregroundColor);
        this.setSize(500, 500);
        this.setFont(new Font("Arial", Font.BOLD, 14));
    }

}
