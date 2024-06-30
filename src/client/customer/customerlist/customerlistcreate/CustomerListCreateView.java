package client.customer.customerlist.customerlistcreate;

import client.InputValidator;
import client.components.*;
import client.Icons;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerListCreateView extends DarkPanel {

    private final DarkPanel shopsComboBoxPanel;
    private DarkComboBox shopsComboBox;
    private final DarkTextField customerListNameField;
    private final RoundButton createButton;
    private final RoundButton cancelButton;

    public CustomerListCreateView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Create new shopping list"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Available shops:"), gbc);
        gbc.gridy++;

        this.shopsComboBoxPanel = new DarkPanel();
        centerPanel.add(this.shopsComboBoxPanel, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("New shopping list name (" + InputValidator.CUSTOMER_LIST_NAME_MIN_LENGTH + " - " + InputValidator.CUSTOMER_LIST_NAME_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.customerListNameField = new DarkTextField(30);
        centerPanel.add(this.customerListNameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Requirements: shopping list name can contain only letters, digits and spaces."), gbc);
        gbc.gridy++;

        this.createButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.createButton, gbc);
        gbc.gridy++;

        this.cancelButton = new RedButton("Cancel " + Icons.CROSS_ICON);
        centerPanel.add(this.cancelButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void setShopsComboBox(ArrayList<String> shopsNames) {
        this.shopsComboBox = new DarkComboBox(shopsNames);
        this.shopsComboBox.setSelectedIndex(0);
        this.shopsComboBoxPanel.add(this.shopsComboBox);
    }

    public String getSelectedShopName() {
        return (String) this.shopsComboBox.getSelectedItem();
    }

    public String getCustomerListName() {
        return this.customerListNameField.getText().strip();
    }

    public void addCreateButtonListener(ActionListener actionListener) {
        this.createButton.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

}
