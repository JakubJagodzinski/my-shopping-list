package client.shop.shopcreate;

import client.Icons;
import client.InputValidator;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class ShopCreateView extends DarkPanel {

    private final DarkTextField shopNameField;
    private final RoundButton createButton;
    private final RoundButton cancelButton;

    public ShopCreateView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Create new shop"), gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("New shop name (" + InputValidator.SHOP_NAME_MIN_LENGTH + " - " + InputValidator.SHOP_NAME_MAX_LENGTH + " chars):"), gbc);
        gbc.gridy++;

        this.shopNameField = new DarkTextField(30);
        centerPanel.add(this.shopNameField, gbc);
        gbc.gridy++;

        centerPanel.add(new PlainLabel("Requirements: shop name can contain only letters, digits, spaces and '"), gbc);
        gbc.gridy++;

        this.createButton = new GreenButton("Confirm " + Icons.TICK_ICON);
        centerPanel.add(this.createButton, gbc);
        gbc.gridy++;

        this.cancelButton = new RedButton("Cancel " + Icons.CROSS_ICON);
        centerPanel.add(this.cancelButton, gbc);
        gbc.gridy++;

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public String getShopName() {
        return this.shopNameField.getText().strip();
    }

    public void addCreateButtonListener(ActionListener actionListener) {
        this.createButton.addActionListener(actionListener);
    }

    public void addCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

}
