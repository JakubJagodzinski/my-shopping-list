package client.customer.customermenu;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerMenuView extends DarkPanel {

    private final RoundButton myShoppingListsButton;
    private final RoundButton sharedListsButton;
    private final RoundButton accountSettingsButton;
    private final RoundButton signOutButton;
    private final DarkPanel welcomeLabelPanel;

    public CustomerMenuView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        this.welcomeLabelPanel = new DarkPanel();
        centerPanel.add(this.welcomeLabelPanel, gbc);
        gbc.gridy++;

        this.myShoppingListsButton = new RoundButton("My shopping lists " + Icons.SHOPPING_LIST_ICON);
        centerPanel.add(this.myShoppingListsButton, gbc);
        gbc.gridy++;

        this.sharedListsButton = new RoundButton("Shared lists " + Icons.SHARE_ICON + Icons.SHARE_ICON + Icons.SHARE_ICON);
        centerPanel.add(this.sharedListsButton, gbc);
        gbc.gridy++;

        this.accountSettingsButton = new RoundButton("Account Settings " + Icons.ACCOUNT_SETTINGS_ICON);
        centerPanel.add(this.accountSettingsButton, gbc);
        gbc.gridy++;

        this.signOutButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Sign out");
        centerPanel.add(this.signOutButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void setWelcomeLabel(HeaderLabel welcomeLabel) {
        this.welcomeLabelPanel.add(welcomeLabel);
    }

    public void addMyShoppingListsListener(ActionListener actionListener) {
        this.myShoppingListsButton.addActionListener(actionListener);
    }

    public void addSharedListsListener(ActionListener actionListener) {
        this.sharedListsButton.addActionListener(actionListener);
    }

    public void addAccountSettingsListener(ActionListener actionListener) {
        this.accountSettingsButton.addActionListener(actionListener);
    }

    public void addSignOutListener(ActionListener actionListener) {
        this.signOutButton.addActionListener(actionListener);
    }

}
