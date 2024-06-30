package client.components;

import javax.swing.*;

public class DarkCheckBox extends JCheckBox {

    public DarkCheckBox(String text) {
        super(text);

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
    }

}
