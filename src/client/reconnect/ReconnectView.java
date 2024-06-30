package client.reconnect;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ReconnectView extends DarkPanel {

    private final RoundButton reconnectButton;
    private final RoundButton quitButton;

    public ReconnectView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(ApplicationLogo.getApplicationNameLabel(), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Failed to connect to the server!"), gbc);
        gbc.gridy++;

        this.reconnectButton = new RoundButton("Reconnect " + Icons.RELOAD_ICON);
        centerPanel.add(this.reconnectButton, gbc);
        gbc.gridy++;

        this.quitButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Quit");
        centerPanel.add(this.quitButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void addReconnectButtonListener(ActionListener actionListener) {
        this.reconnectButton.addActionListener(actionListener);
    }

    public void addQuitButtonListener(ActionListener actionListener) {
        this.quitButton.addActionListener(actionListener);
    }

}
