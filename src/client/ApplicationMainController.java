package client;

import client.components.ApplicationLogo;
import client.components.DarkPanel;
import client.connect.ConnectController;
import client.connect.ConnectModel;
import client.connect.ConnectView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import javax.swing.*;
import java.awt.*;

public class ApplicationMainController extends JFrame {

    public ApplicationMainController() {
        super("My Shopping List");

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.setLayout(cardLayout);
        getContentPane().add(cardPanel, BorderLayout.CENTER);

        ViewManager.initialize(cardLayout, cardPanel);
        ViewManager.add(new DarkPanel(), ViewNames.BACKGROUND_SCREEN);
        ViewManager.show(ViewNames.BACKGROUND_SCREEN);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        this.setIconImage(ApplicationLogo.getApplicationImage());

        setVisible(true);
    }

    public void run() {
        SwingUtilities.invokeLater(() -> {
            ConnectModel connectModel = new ConnectModel();
            ConnectView connectView = new ConnectView();
            ConnectController connectionController = new ConnectController(connectModel);
            ViewManager.add(connectView, ViewNames.CONNECT);
            ViewManager.show(ViewNames.CONNECT);
            connectionController.connect();
        });
    }

}
