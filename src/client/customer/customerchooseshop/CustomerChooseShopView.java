package client.customer.customerchooseshop;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerChooseShopView extends DarkPanel {

    private final DarkPanel shopsComboBoxPanel;
    private DarkComboBox shopsComboBox;
    private final RoundButton createNewShoppingListButton;
    private final RoundButton chooseShopButton;
    private final RoundButton returnButton;
    private final PlainLabel shopsLabel;

    public CustomerChooseShopView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Choose shop"), gbc);
        gbc.gridy++;

        this.shopsLabel = new PlainLabel("Shops you have lists in:");
        centerPanel.add(this.shopsLabel, gbc);
        gbc.gridy++;

        this.shopsComboBoxPanel = new DarkPanel();
        centerPanel.add(this.shopsComboBoxPanel, gbc);
        gbc.gridy++;

        this.chooseShopButton = new RoundButton("Choose " + Icons.RIGHTWARDS_ARROW);
        centerPanel.add(this.chooseShopButton, gbc);
        gbc.gridy++;

        this.createNewShoppingListButton = new RoundButton("Create new list" + Icons.PLUS_SIGN_ICON);
        centerPanel.add(this.createNewShoppingListButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void removeShopsComboBox() {
        this.shopsComboBoxPanel.removeAll();
    }

    public void setShopsComboBox(ArrayList<String> shopsNames) {
        if (shopsNames != null) {
            this.shopsComboBox = new DarkComboBox(shopsNames);
            this.shopsComboBox.setSelectedIndex(0);
            this.shopsComboBoxPanel.add(this.shopsComboBox);
            this.shopsLabel.setVisible(true);
            this.chooseShopButton.setVisible(true);
        } else {
            this.shopsLabel.setVisible(false);
            this.chooseShopButton.setVisible(false);
        }
    }

    public String getSelectedShopName() {
        return (String) this.shopsComboBox.getSelectedItem();
    }

    public void addNewShoppingListButtonListener(ActionListener actionListener) {
        this.createNewShoppingListButton.addActionListener(actionListener);
    }

    public void addChooseButtonListener(ActionListener actionListener) {
        this.chooseShopButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
