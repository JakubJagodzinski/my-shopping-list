package client.shop.shopchoose;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShopChooseView extends DarkPanel {

    private final DarkPanel shopsComboBoxPanel;
    private DarkComboBox shopsComboBox;
    private final RoundButton createNewShopButton;
    private final RoundButton chooseShopButton;
    private final RoundButton returnButton;
    private final PlainLabel shopsLabel;

    public ShopChooseView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Choose shop"), gbc);
        gbc.gridy++;

        this.shopsLabel = new PlainLabel("Your shops:");
        centerPanel.add(this.shopsLabel, gbc);
        gbc.gridy++;

        this.shopsComboBoxPanel = new DarkPanel();
        centerPanel.add(this.shopsComboBoxPanel, gbc);
        gbc.gridy++;

        this.chooseShopButton = new RoundButton("Choose " + Icons.RIGHTWARDS_ARROW);
        centerPanel.add(this.chooseShopButton, gbc);
        gbc.gridy++;

        this.createNewShopButton = new RoundButton("New shop " + Icons.PLUS_SIGN_ICON);
        centerPanel.add(this.createNewShopButton, gbc);
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
            this.shopsLabel.setText("Your shops:");
            this.chooseShopButton.setVisible(true);
        } else {
            this.shopsLabel.setText("You don't have any shops yet");
            this.chooseShopButton.setVisible(false);
        }
    }

    public String getSelectedShopName() {
        return (String) this.shopsComboBox.getSelectedItem();
    }

    public void addCreateNewShopButtonListener(ActionListener actionListener) {
        this.createNewShopButton.addActionListener(actionListener);
    }

    public void addChooseShopButtonListener(ActionListener actionListener) {
        this.chooseShopButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
