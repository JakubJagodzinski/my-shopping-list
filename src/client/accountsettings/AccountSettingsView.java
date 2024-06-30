package client.accountsettings;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class AccountSettingsView extends DarkPanel {

    private final RoundButton changeUsernameButton;
    private final RoundButton changePasswordButton;
    private final RedButton deleteAccountButton;
    private final RoundButton returnButton;

    public AccountSettingsView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Account settings"), gbc);
        gbc.gridy++;

        this.changeUsernameButton = new RoundButton("Change username " + Icons.EDIT_ICON);
        centerPanel.add(this.changeUsernameButton, gbc);
        gbc.gridy++;

        this.changePasswordButton = new RoundButton("Change password " + Icons.CHANGE_PASSWORD_ICON);
        centerPanel.add(this.changePasswordButton, gbc);
        gbc.gridy++;

        this.deleteAccountButton = new RedButton("Delete account " + Icons.DELETE_ICON);
        centerPanel.add(this.deleteAccountButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void addChangeUsernameListener(ActionListener actionListener) {
        this.changeUsernameButton.addActionListener(actionListener);
    }

    public void addChangePasswordListener(ActionListener actionListener) {
        this.changePasswordButton.addActionListener(actionListener);
    }

    public void addDeleteAccountListener(ActionListener actionListener) {
        this.deleteAccountButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
