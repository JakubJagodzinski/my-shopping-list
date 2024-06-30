package client.shopmanagermenu;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ShopManagerMenuView extends DarkPanel {

    private final RoundButton myShopsButton;
    private final RoundButton accountSettingsButton;
    private final RoundButton signOutButton;

    private final DarkPanel welcomeLabelPanel;

    public ShopManagerMenuView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        this.welcomeLabelPanel = new DarkPanel();
        centerPanel.add(this.welcomeLabelPanel, gbc);
        gbc.gridy++;

        this.myShopsButton = new RoundButton("My shops " + Icons.SHOPPING_CART_ICON);
        centerPanel.add(this.myShopsButton, gbc);
        gbc.gridy++;

        this.accountSettingsButton = new RoundButton("Account settings " + Icons.ACCOUNT_SETTINGS_ICON);
        centerPanel.add(this.accountSettingsButton, gbc);
        gbc.gridy++;

        this.signOutButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Sign out");
        centerPanel.add(this.signOutButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void setWelcomeLabel(HeaderLabel welcomeLabel) {
        this.welcomeLabelPanel.add(welcomeLabel);
    }

    public void addMyShopsButtonListener(ActionListener actionListener) {
        this.myShopsButton.addActionListener(actionListener);
    }

    public void addAccountSettingsButtonListener(ActionListener actionListener) {
        this.accountSettingsButton.addActionListener(actionListener);
    }

    public void addSignOutButtonListener(ActionListener actionListener) {
        this.signOutButton.addActionListener(actionListener);
    }

}
