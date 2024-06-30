package client.customer.customerlist.customerlistclone;

import client.InputValidator;
import client.components.*;
import client.Icons;

import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerListCloneView extends DarkPanel {

    private final GreenButton confirmButton;
    private final RoundButton returnButton;

    private final DarkTextField clonedListNameField;

    public CustomerListCloneView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Clone this list"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Notice: this operation will clone current state of the list"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Cloned list name (" + InputValidator.CUSTOMER_LIST_NAME_MIN_LENGTH + " - " + InputValidator.CUSTOMER_LIST_NAME_MAX_LENGTH + " chars) :"), gbc);
        gbc.gridy++;

        this.clonedListNameField = new DarkTextField(20);
        centerPanel.add(this.clonedListNameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Requirements: shopping list name can contain only letters, digits and spaces."), gbc);
        gbc.gridy++;

        this.confirmButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.confirmButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getClonedListName() {
        return this.clonedListNameField.getText().strip();
    }

    public void addConfirmButtonListener(ActionListener actionListener) {
        this.confirmButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
