package client.components;

import javax.swing.*;
import java.util.ArrayList;

public class DarkComboBox extends JComboBox<String> {

    public DarkComboBox(ArrayList<String> values) {
        super(values.toArray(new String[]{}));

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
    }

    public DarkComboBox(String[] values) {
        super(values);

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
    }

}
