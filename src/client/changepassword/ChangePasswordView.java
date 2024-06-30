package client.changepassword;

import client.Icons;
import client.InputValidator;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ChangePasswordView extends DarkPanel {

    private final RoundButton confirmButton;
    private final RoundButton cancelButton;
    private final DarkPasswordField passwordField;
    private final DarkPasswordField newPasswordField;
    private final DarkPasswordField confirmNewPasswordField;

    public ChangePasswordView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Change password"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Current password:"), gbc);
        gbc.gridy++;

        this.passwordField = new DarkPasswordField(20);
        centerPanel.add(this.passwordField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("New password (" + InputValidator.PASSWORD_MIN_LENGTH + " - " + InputValidator.PASSWORD_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.newPasswordField = new DarkPasswordField(20);
        centerPanel.add(this.newPasswordField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Confirm new password:"), gbc);
        gbc.gridy++;

        this.confirmNewPasswordField = new DarkPasswordField(20);
        centerPanel.add(this.confirmNewPasswordField, gbc);
        gbc.gridy++;

        this.confirmButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.confirmButton, gbc);
        gbc.gridy++;

        this.cancelButton = new RedButton("Cancel " + Icons.CROSS_ICON);
        centerPanel.add(this.cancelButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }

    public String getNewPassword() {
        return String.valueOf(this.newPasswordField.getPassword());
    }

    public String getConfirmNewPassword() {
        return String.valueOf(this.confirmNewPasswordField.getPassword());
    }

    public void addConfirmButtonListener(ActionListener actionListener) {
        this.confirmButton.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

}
