package client.customer.customerlist.customerlistshare;

import client.components.*;
import client.Icons;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class CustomerListShareView extends DarkPanel {

    private final GreenButton confirmButton;
    private final RoundButton returnButton;
    private final DarkCheckBox fullAccessCheckBox;
    private final DarkCheckBox readOnlyCheckBox;

    private final DarkTextField usernameField;

    public CustomerListShareView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Share this list"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("User you would like to share this list with:"), gbc);
        gbc.gridy++;

        this.usernameField = new DarkTextField(20);
        centerPanel.add(this.usernameField, gbc);
        gbc.gridy++;

        this.fullAccessCheckBox = new DarkCheckBox("full access");
        this.fullAccessCheckBox.setSelected(false);

        this.readOnlyCheckBox = new DarkCheckBox("read only");
        this.readOnlyCheckBox.setSelected(true);

        this.readOnlyCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.fullAccessCheckBox.setSelected(false);
            }
        });
        centerPanel.add(this.readOnlyCheckBox, gbc);
        gbc.gridy++;

        this.fullAccessCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.readOnlyCheckBox.setSelected(false);
            }
        });
        centerPanel.add(this.fullAccessCheckBox, gbc);
        gbc.gridy++;

        this.confirmButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.confirmButton, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getUsername() {
        return this.usernameField.getText().strip();
    }

    public void clearUsername() {
        this.usernameField.setText("");
    }

    public void resetCheckBoxes() {
        this.readOnlyCheckBox.setSelected(true);
        this.fullAccessCheckBox.setSelected(false);
    }

    public boolean isSharedWithFullAccess() {
        return this.fullAccessCheckBox.isSelected();
    }

    public void addConfirmButtonListener(ActionListener actionListener) {
        this.confirmButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
