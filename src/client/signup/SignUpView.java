package client.signup;

import client.Icons;
import client.InputValidator;
import client.components.*;
import common.AccountType;

import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpView extends DarkPanel {

    private final DarkTextField usernameField;
    private final DarkPasswordField passwordField;
    private final DarkPasswordField confirmPasswordField;
    private final RoundButton confirmButton;
    private final RoundButton returnButton;
    private final DarkCheckBox isShopManagerCheckbox;

    public SignUpView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Sign up"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Username (" + InputValidator.USERNAME_MIN_LENGTH + " - " + InputValidator.USERNAME_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.usernameField = new DarkTextField(15);
        centerPanel.add(this.usernameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Password (" + InputValidator.PASSWORD_MIN_LENGTH + " - " + InputValidator.PASSWORD_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.passwordField = new DarkPasswordField(15);
        centerPanel.add(this.passwordField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Confirm password:"), gbc);
        gbc.gridy++;

        this.confirmPasswordField = new DarkPasswordField(15);
        centerPanel.add(this.confirmPasswordField, gbc);
        gbc.gridy++;

        this.isShopManagerCheckbox = new DarkCheckBox("I am shop manager");
        centerPanel.add(this.isShopManagerCheckbox, gbc);
        gbc.gridy++;

        this.confirmButton = new RoundButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.confirmButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getUsername() {
        return this.usernameField.getText().strip();
    }

    public String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return String.valueOf(this.confirmPasswordField.getPassword());
    }

    public AccountType getAccountType() {
        return this.isShopManagerCheckbox.isSelected() ? AccountType.SHOP_MANAGER : AccountType.CUSTOMER;
    }

    public void addConfirmButtonListener(ActionListener actionListener) {
        this.confirmButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
