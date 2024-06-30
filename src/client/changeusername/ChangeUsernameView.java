package client.changeusername;

import client.Icons;
import client.InputValidator;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ChangeUsernameView extends DarkPanel {

    private final RoundButton confirmButton;
    private final RoundButton cancelButton;
    private final DarkTextField newUsernameField;

    public ChangeUsernameView(String username) {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Change username"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Current username: " + username), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("New username (" + InputValidator.USERNAME_MIN_LENGTH + " - " + InputValidator.USERNAME_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.newUsernameField = new DarkTextField(20);
        centerPanel.add(this.newUsernameField, gbc);
        gbc.gridy++;

        this.confirmButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.confirmButton, gbc);
        gbc.gridy++;

        this.cancelButton = new RedButton("Cancel " + Icons.CROSS_ICON);
        centerPanel.add(this.cancelButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getNewUsername() {
        return this.newUsernameField.getText().strip();
    }

    public void addConfirmButtonListener(ActionListener actionListener) {
        this.confirmButton.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

}
