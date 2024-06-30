package client.connect;

import client.components.*;

import java.awt.*;

public class ConnectView extends DarkPanel {

    public ConnectView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(ApplicationLogo.getApplicationNameLabel(), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Connecting to the server..."), gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

}
