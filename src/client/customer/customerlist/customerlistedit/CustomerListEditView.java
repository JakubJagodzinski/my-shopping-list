package client.customer.customerlist.customerlistedit;

import client.components.*;
import client.Icons;
import common.shoppinglistapi.CurrencyType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerListEditView extends DarkPanel {

    private final RoundButton refreshButton;
    private final RoundButton addProductButton;
    private final RoundButton clearButton;
    private final RoundButton reloadButton;
    private final RoundButton saveButton;
    private final RoundButton cloneButton;
    private final RoundButton shareButton;
    private final RoundButton deleteButton;
    private final RoundButton returnButton;

    private final DarkPanel shoppingListPanel;

    private final DarkPanel listTotalPricePanel;
    private DarkComboBox listCurrencyComboBox;

    public CustomerListEditView(String shopName, String shoppingListName, boolean hasFullAccess) {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new FlowLayout(FlowLayout.CENTER));

        DarkPanel buttonsPanel = new DarkPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints buttonsPanelGbc = VerticalGridBagLayout.getGridBagConstraints();

        this.refreshButton = new RoundButton("Refresh " + Icons.RELOAD_ICON);
        buttonsPanel.add(this.refreshButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.addProductButton = new RoundButton("Add product " + Icons.PLUS_SIGN_ICON);
        buttonsPanel.add(this.addProductButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.clearButton = new RoundButton("Clear " + Icons.CROSS_ICON);
        buttonsPanel.add(this.clearButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.reloadButton = new RoundButton("Reload " + Icons.RELOAD_ICON);
        buttonsPanel.add(this.reloadButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.saveButton = new RoundButton("Save " + Icons.TICK_ICON);
        buttonsPanel.add(this.saveButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.cloneButton = new RoundButton("Clone " + Icons.SHOPPING_LIST_ICON + Icons.PLUS_SIGN_ICON + Icons.PLUS_SIGN_ICON);
        buttonsPanel.add(this.cloneButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.shareButton = new RoundButton("Share " + Icons.SHARE_ICON + Icons.SHARE_ICON + Icons.SHARE_ICON);
        buttonsPanel.add(this.shareButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.deleteButton = new RoundButton("Delete " + Icons.DELETE_ICON);
        buttonsPanel.add(this.deleteButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        buttonsPanel.add(this.returnButton, buttonsPanelGbc);

        centerPanel.add(buttonsPanel);

        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();
        DarkPanel listPanel = new DarkPanel(new GridBagLayout());

        GridPanel headerPanel = new GridPanel(2, 1);
        HeaderLabel shopNameLabel = new HeaderLabel(shopName);
        shopNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(shopNameLabel);

        HeaderLabel shoppingListNameLabel = new HeaderLabel(shoppingListName + (hasFullAccess ? "" : " (read only)"));
        shoppingListNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(shoppingListNameLabel);

        listPanel.add(headerPanel, gbc);
        gbc.gridy++;

        this.shoppingListPanel = new DarkPanel();
        listPanel.add(this.shoppingListPanel, gbc);
        gbc.gridy++;

        this.listTotalPricePanel = new DarkPanel();
        this.listTotalPricePanel.setPreferredSize(new Dimension(1600, 40));
        this.listTotalPricePanel.setBorder(new LineBorder(Color.WHITE));
        listPanel.add(this.listTotalPricePanel, gbc);

        centerPanel.add(listPanel);
        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void disableButtonsForFullAccessMode() {
        this.shareButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
    }

    public void disableButtonsForReadOnlyMode() {
        this.addProductButton.setEnabled(false);
        this.clearButton.setEnabled(false);
        this.reloadButton.setEnabled(false);
        this.saveButton.setEnabled(false);
        this.shareButton.setEnabled(false);
        this.deleteButton.setEnabled(false);
    }

    public void setShoppingListPane(JScrollPane shoppingListPane) {
        if (shoppingListPane != null) {
            this.shoppingListPanel.removeAll();
            this.shoppingListPanel.add(shoppingListPane);
            this.revalidate();
            this.repaint();
        }
    }

    public void setListTotalPriceLabel(BoldLabel listTotalPriceLabel) {
        if (listTotalPriceLabel != null) {
            this.listTotalPricePanel.removeAll();
            this.listTotalPricePanel.add(listTotalPriceLabel);
            this.revalidate();
            this.repaint();
        }
    }

    public void setListCurrencyComboBox(DarkComboBox listCurrencyComboBox) {
        this.listCurrencyComboBox = listCurrencyComboBox;
        this.listTotalPricePanel.add(this.listCurrencyComboBox);
        this.revalidate();
        this.repaint();
    }

    public DarkComboBox getListCurrencyComboBox() {
        return this.listCurrencyComboBox;
    }

    public CurrencyType getSelectedCurrencyType() {
        return CurrencyType.valueOf((String) this.listCurrencyComboBox.getSelectedItem());
    }

    public void setSaveButtonColorRed() {
        this.saveButton.setBackgroundColor(Colors.RED_BUTTON_COLOR);
        this.saveButton.repaint();
    }

    public void setSaveButtonColorNormal() {
        this.saveButton.setBackgroundColor(Colors.BUTTON_COLOR);
    }

    public void addAddProductButtonListener(ActionListener actionListener) {
        this.addProductButton.addActionListener(actionListener);
    }

    public void addClearButtonListener(ActionListener actionListener) {
        this.clearButton.addActionListener(actionListener);
    }

    public void addReloadButtonListener(ActionListener actionListener) {
        this.reloadButton.addActionListener(actionListener);
    }

    public void addSaveButtonListener(ActionListener actionListener) {
        this.saveButton.addActionListener(actionListener);
    }

    public void addCloneButtonListener(ActionListener actionListener) {
        this.cloneButton.addActionListener(actionListener);
    }

    public void addShareButtonListener(ActionListener actionListener) {
        this.shareButton.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        this.deleteButton.addActionListener(actionListener);
    }

    public void addRefreshButtonListener(ActionListener actionListener) {
        this.refreshButton.addActionListener(actionListener);
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

    public void addListCurrencyComboBoxListener(ActionListener actionListener) {
        this.listCurrencyComboBox.addActionListener(actionListener);
    }

}
