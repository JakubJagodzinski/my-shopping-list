package client.confirm;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ConfirmView extends DarkPanel {

    private final RoundButton yesButton;
    private final RoundButton noButton;

    public ConfirmView(String message) {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new BoldLabel(message), gbc);
        gbc.gridy++;

        centerPanel.add(new DarkPanel(), gbc);
        gbc.gridy++;

        GridPanel buttonsPanel = new GridPanel(1, 2);

        this.yesButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        buttonsPanel.add(this.yesButton);

        this.noButton = new RedButton("Cancel " + Icons.CROSS_ICON);
        buttonsPanel.add(this.noButton);

        centerPanel.add(buttonsPanel, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void addYesButtonListener(ActionListener actionListener) {
        this.yesButton.addActionListener(actionListener);
    }

    public void addNoButtonListener(ActionListener actionListener) {
        this.noButton.addActionListener(actionListener);
    }

}
