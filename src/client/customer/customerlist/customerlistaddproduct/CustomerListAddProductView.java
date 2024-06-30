package client.customer.customerlist.customerlistaddproduct;

import client.components.*;
import client.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerListAddProductView extends DarkPanel {

    private final RoundButton returnButton;
    private DarkComboBox shopCategoriesComboBox;
    private final DarkPanel shopCategoryPanel;
    private JScrollPane shopCategoryPane;

    public CustomerListAddProductView() {
        super(new BorderLayout());

        DarkPanel centerPanel = new DarkPanel(new GridBagLayout());
        GridBagConstraints gbc = VerticalGridBagLayout.getGridBagConstraints();

        centerPanel.add(new HeaderLabel("Add product"), gbc);
        gbc.gridy++;

        this.shopCategoryPanel = new DarkPanel();
        this.shopCategoryPanel.setLayout(new BoxLayout(this.shopCategoryPanel, BoxLayout.Y_AXIS));
        centerPanel.add(this.shopCategoryPanel, gbc);
        gbc.gridy++;

        this.returnButton = new RoundButton(Icons.LEFTWARDS_ARROW + " Return");
        centerPanel.add(this.returnButton, gbc);

        this.add(centerPanel, BorderLayout.CENTER);

        this.add(new Footer(), BorderLayout.SOUTH);
    }

    public void setShopIsEmptyLabel() {
        this.shopCategoryPanel.add(new BoldLabel("This shop is empty at the moment..."));
    }

    public void setShopCategoriesComboBox(ArrayList<String> categoriesNames) {
        this.shopCategoriesComboBox = new DarkComboBox(categoriesNames);
        this.shopCategoriesComboBox.setSelectedIndex(0);
    }

    public DarkComboBox getCategoriesComboBox() {
        return this.shopCategoriesComboBox;
    }

    public void setShopCategoryPane(JScrollPane shopCategoryPane) {
        this.shopCategoryPane = shopCategoryPane;
        this.shopCategoryPanel.add(this.shopCategoryPane);
        this.revalidate();
        this.repaint();
    }

    public void updateShopCategoryPane(JScrollPane shopCategoryPane) {
        this.shopCategoryPanel.remove(this.shopCategoryPane);
        this.setShopCategoryPane(shopCategoryPane);
    }

    public String getSelectedCategory() {
        return (String) this.shopCategoriesComboBox.getSelectedItem();
    }

    public void addReturnButtonListener(ActionListener actionListener) {
        this.returnButton.addActionListener(actionListener);
    }

    public void addCategoriesComboBoxListener(ActionListener actionListener) {
        this.shopCategoriesComboBox.addActionListener(actionListener);
    }

}
