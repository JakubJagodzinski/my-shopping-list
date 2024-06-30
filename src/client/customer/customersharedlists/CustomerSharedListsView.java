package client.customer.customersharedlists;

import client.Icons;
import client.components.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerSharedListsView extends DarkPanel {

    private final PlainLabel emptyLabel;
    private final PlainLabel legendLabel;
    private final DarkPanel shoppingListsComboBoxPanel;
    private DarkComboBox sharedListsPathsComboBox;
    private final RoundButton chooseButton;
    private final RoundButton returnButton;

    public CustomerSharedListsView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Shared with me"), gbc);
        gbc.gridy++;

        this.emptyLabel = new PlainLabel("None shares lists with you yet");
        centerPanel.add(this.emptyLabel, gbc);
        gbc.gridy++;

        this.legendLabel = new PlainLabel("list owner / shop / shopping list / granted access");
        centerPanel.add(this.legendLabel, gbc);
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

    public void setSharedListsPathsComboBox(ArrayList<String> sharedListsPaths) {
        if (sharedListsPaths != null && !sharedListsPaths.isEmpty()) {
            this.sharedListsPathsComboBox = new DarkComboBox(sharedListsPaths);
            this.sharedListsPathsComboBox.setSelectedIndex(0);
            this.shoppingListsComboBoxPanel.add(this.sharedListsPathsComboBox);
            this.emptyLabel.setVisible(false);
            this.legendLabel.setVisible(true);
            this.chooseButton.setVisible(true);
        } else {
            this.emptyLabel.setVisible(true);
            this.legendLabel.setVisible(false);
            this.chooseButton.setVisible(false);
        }
    }

    public String getSelectedSharedListPath() {
        return (String) this.sharedListsPathsComboBox.getSelectedItem();
    }

    public void addChooseButtonListener(ActionListener actionListener) {
        this.chooseButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

}
