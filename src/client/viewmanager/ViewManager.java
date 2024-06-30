package client.viewmanager;

import javax.swing.*;
import java.awt.*;

public class ViewManager {

    private static CardLayout cardLayout;
    private static JPanel cardPanel;

    public static void initialize(CardLayout initialCardLayout, JPanel initialCardPanel) {
        cardLayout = initialCardLayout;
        cardPanel = initialCardPanel;
    }

    public static void add(JPanel newView, ViewNames viewName) {
        cardPanel.add(newView, viewName.name());
    }

    private static Component get(ViewNames viewName) {
        for (var view : cardPanel.getComponents()) {
            if (view.equals(cardPanel.getClientProperty(viewName.name()))) {
                return view;
            }
        }
        return null;
    }

    public static void remove(ViewNames viewName) {
        Component view = get(viewName);
        if (view != null) {
            cardPanel.remove(view);
            cardPanel.revalidate();
            cardPanel.repaint();
        }
    }

    private static void refresh(ViewNames viewName) {
        Component view = get(viewName);
        if (view != null) {
            view.revalidate();
            view.repaint();
        }
    }

    public static void show(ViewNames viewName) {
        if (cardLayout != null && cardPanel != null) {
            refresh(viewName);
            cardLayout.show(cardPanel, viewName.name());
        }
    }

}
