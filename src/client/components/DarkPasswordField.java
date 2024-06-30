package client.components;

import javax.swing.*;
import java.awt.*;

public class DarkPasswordField extends JPasswordField {

    public DarkPasswordField(int columns) {
        super(columns);

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
        this.setCaretColor(Colors.FOREGROUND_COLOR);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
    }

}
