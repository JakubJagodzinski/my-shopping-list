package client.components;

import javax.swing.*;
import java.awt.*;

public class PlainLabel extends JLabel {

    public PlainLabel(String text) {
        super(text);

        this.setForeground(Colors.FOREGROUND_COLOR);
        this.setSize(500, 500);
        this.setFont(new Font("Arial", Font.PLAIN, 14));
    }

}
