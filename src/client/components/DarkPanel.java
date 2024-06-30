package client.components;

import javax.swing.*;
import java.awt.*;

public class DarkPanel extends JPanel {

    public DarkPanel() {
        super();

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
    }

    public DarkPanel(LayoutManager layout) {
        super(layout);

        this.setBackground(Colors.BACKGROUND_COLOR);
        this.setForeground(Colors.FOREGROUND_COLOR);
    }

}
