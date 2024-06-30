package client.components;

import javax.swing.*;
import java.awt.*;

public class DarkTextField extends JTextField {

    public DarkTextField(int columns) {
        super(columns);

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
        this.setCaretColor(Colors.FOREGROUND_COLOR);
        this.setFont(new Font("Arial", Font.PLAIN, 20));
    }

    public DarkTextField(int columns, int textSize) {
        super(columns);

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
        this.setCaretColor(Colors.FOREGROUND_COLOR);
        this.setFont(new Font("Arial", Font.PLAIN, textSize));
    }

}
