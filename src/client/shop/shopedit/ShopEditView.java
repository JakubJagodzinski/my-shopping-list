package client.shop.shopedit;

import client.Icons;
import client.components.*;
import common.shoppinglistapi.CurrencyType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShopEditView extends DarkPanel {

    private final RoundButton addProductButton;
    private final RoundButton clearButton;
    private final RoundButton saveLocalButton;
    private final RoundButton pushChangesButton;
    private final RoundButton reloadLocalButton;
    private final RoundButton reloadPublicButton;
    private final RoundButton deleteButton;
    private final RoundButton closeButton;

    private final DarkPanel shopPanel;

    private final DarkPanel shopTotalPricePanel;
    private DarkComboBox shopCurrencyComboBox;

    public ShopEditView(String shopName) {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new FlowLayout(FlowLayout.CENTER));

        DarkPanel buttonsPanel = new DarkPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints buttonsPanelGbc = VerticalGridBagLayout.getGridBagConstraints();

        this.addProductButton = new RoundButton("Add product " + Icons.PLUS_SIGN_ICON);
        buttonsPanel.add(this.addProductButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.clearButton = new RoundButton("Clear " + Icons.CROSS_ICON);
        buttonsPanel.add(this.clearButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.reloadLocalButton = new RoundButton("Reload local " + Icons.LOCAL_ICON + Icons.RELOAD_ICON);
        buttonsPanel.add(this.reloadLocalButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.saveLocalButton = new RoundButton("Save local " + Icons.LOCAL_ICON + Icons.TICK_ICON);
        buttonsPanel.add(this.saveLocalButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.reloadPublicButton = new RoundButton("Reload public " + Icons.SHARE_ICON + Icons.RELOAD_ICON);
        buttonsPanel.add(this.reloadPublicButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.pushChangesButton = new RoundButton("Push to public " + Icons.SHARE_ICON + Icons.TICK_ICON);
        buttonsPanel.add(this.pushChangesButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.deleteButton = new RoundButton("Delete " + Icons.DELETE_ICON);
        buttonsPanel.add(this.deleteButton, buttonsPanelGbc);
        buttonsPanelGbc.gridy++;

        this.closeButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        buttonsPanel.add(this.closeButton, buttonsPanelGbc);

        centerPanel.add(buttonsPanel);

        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();
        DarkPanel listPanel = new DarkPanel(new GridBagLayout());

        GridPanel headerPanel = new GridPanel(2, 1);
        HeaderLabel shopNameLabel = new HeaderLabel(shopName);
        shopNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(shopNameLabel);

        listPanel.add(headerPanel, gbc);
        gbc.gridy++;

        this.shopPanel = new DarkPanel();
        listPanel.add(this.shopPanel, gbc);
        gbc.gridy++;

        this.shopTotalPricePanel = new DarkPanel();
        this.shopTotalPricePanel.setPreferredSize(new Dimension(1600, 40));
        this.shopTotalPricePanel.setBorder(new LineBorder(Color.WHITE));
        listPanel.add(this.shopTotalPricePanel, gbc);

        centerPanel.add(listPanel);
        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void setShopPane(JScrollPane shopPane) {
        if (shopPane != null) {
            this.shopPanel.removeAll();
            this.shopPanel.add(shopPane);
            this.revalidate();
            this.repaint();
        }
    }

    public void setShopTotalPriceLabel(BoldLabel shopTotalPriceLabel) {
        if (shopTotalPriceLabel != null) {
            this.shopTotalPricePanel.removeAll();
            this.shopTotalPricePanel.add(shopTotalPriceLabel);
            this.revalidate();
            this.repaint();
        }
    }

    public void setShopCurrencyComboBox(DarkComboBox shopCurrencyComboBox) {
        this.shopCurrencyComboBox = shopCurrencyComboBox;
        this.shopTotalPricePanel.add(this.shopCurrencyComboBox);
        this.revalidate();
        this.repaint();
    }

    public DarkComboBox getShopCurrencyComboBox() {
        return this.shopCurrencyComboBox;
    }

    public CurrencyType getSelectedCurrencyType() {
        return CurrencyType.valueOf((String) this.shopCurrencyComboBox.getSelectedItem());
    }

    public void setSaveButtonColorRed() {
        this.saveLocalButton.setBackgroundColor(Colors.RED_BUTTON_COLOR);
    }

    public void setSaveButtonColorNormal() {
        this.saveLocalButton.setBackgroundColor(Colors.BUTTON_COLOR);
    }

    public void addAddProductButtonListener(ActionListener actionListener) {
        this.addProductButton.addActionListener(actionListener);
    }

    public void addClearButtonListener(ActionListener actionListener) {
        this.clearButton.addActionListener(actionListener);
    }

    public void addReloadLocalButtonListener(ActionListener actionListener) {
        this.reloadLocalButton.addActionListener(actionListener);
    }

    public void addReloadPublicButtonListener(ActionListener actionListener) {
        this.reloadPublicButton.addActionListener(actionListener);
    }

    public void addSaveLocalButtonListener(ActionListener actionListener) {
        this.saveLocalButton.addActionListener(actionListener);
    }

    public void addPushTuPublicButtonListener(ActionListener actionListener) {
        this.pushChangesButton.addActionListener(actionListener);
    }

    public void addDeleteButtonListener(ActionListener actionListener) {
        this.deleteButton.addActionListener(actionListener);
    }

    public void addCloseButtonListener(ActionListener actionListener) {
        this.closeButton.addActionListener(actionListener);
    }

    public void addShopCurrencyComboBoxListener(ActionListener actionListener) {
        this.shopCurrencyComboBox.addActionListener(actionListener);
    }

}
