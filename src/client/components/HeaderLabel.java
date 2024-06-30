package client.components;

import javax.swing.*;
import java.awt.*;

public class HeaderLabel extends JLabel {

    public HeaderLabel(String text) {
        super(text);

        this.setForeground(Colors.FOREGROUND_COLOR);
        this.setSize(500, 500);
        this.setFont(new Font("Jokerman", Font.BOLD, 40));
    }

    public HeaderLabel(String text, Color foregroundColor, int size) {
        super(text);

        this.setForeground(foregroundColor);
        this.setSize(500, 500);
        this.setFont(new Font("Jokerman", Font.BOLD, size));
    }

}
