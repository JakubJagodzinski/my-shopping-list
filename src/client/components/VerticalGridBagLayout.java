package client.components;

import java.awt.*;

public class VerticalGridBagLayout {

    public static GridBagConstraints getGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }

}
