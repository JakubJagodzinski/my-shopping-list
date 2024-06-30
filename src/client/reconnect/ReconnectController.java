package client.reconnect;

import client.connect.ConnectController;
import client.connect.ConnectModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReconnectController {

    private final ReconnectView view;
    private final ConnectController controller;

    public ReconnectController(ReconnectView view, ConnectController controller) {
        this.view = view;
        this.controller = controller;
        this.view.addReconnectButtonListener(new ReconnectListener());
        this.view.addQuitButtonListener(new QuitListener());
    }

    public class ReconnectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.connect();
        }
    }

    public static class QuitListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ConnectModel.disconnect();
            System.exit(0);
        }
    }

}
