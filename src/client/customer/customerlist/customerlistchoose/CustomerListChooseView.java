package client.customer.customerlist.customerlistchoose;

import client.components.*;
import client.Icons;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerListChooseView extends DarkPanel {

    private final DarkPanel shoppingListsComboBoxPanel;
    private DarkComboBox shoppingListsComboBox;
    private final RoundButton chooseButton;
    private final RoundButton returnButton;
    private final PlainLabel listsLabel;

    public CustomerListChooseView(String shopName) {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel(shopName), gbc);
        gbc.gridy++;

        this.listsLabel = new PlainLabel("Your lists in this shop:");
        centerPanel.add(this.listsLabel, gbc);
        gbc.gridy++;

        this.shoppingListsComboBoxPanel = new DarkPanel();
        centerPanel.add(this.shoppingListsComboBoxPanel, gbc);
        gbc.gridy++;

        this.chooseButton = new RoundButton("Choose " + Icons.RIGHTWARDS_ARROW);
        centerPanel.add(this.chooseButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void removeShoppingListComboBox() {
        this.shoppingListsComboBoxPanel.removeAll();
    }

    public void setShoppingListsComboBox(ArrayList<String> shoppingListsNames) {
        if (shoppingListsNames != null) {
            this.shoppingListsComboBox = new DarkComboBox(shoppingListsNames);
            this.shoppingListsComboBox.setSelectedIndex(0);
            this.shoppingListsComboBoxPanel.add(this.shoppingListsComboBox);
            this.listsLabel.setVisible(true);
            this.chooseButton.setVisible(true);
        } else {
            this.listsLabel.setVisible(false);
            this.chooseButton.setVisible(false);
        }
    }

    public String getSelectedShoppingListName() {
        return (String) this.shoppingListsComboBox.getSelectedItem();
    }

    public void addChooseButtonListener(ActionListener actionListener) {
        this.chooseButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
