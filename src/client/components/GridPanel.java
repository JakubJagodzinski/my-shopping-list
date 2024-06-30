package client.components;

import java.awt.*;

public class GridPanel extends DarkPanel {

    public GridPanel(int rows, int cols) {
        this.setLayout(new GridLayout(rows, cols));
    }

}
