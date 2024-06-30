package client.connect;

import client.reconnect.ReconnectController;
import client.reconnect.ReconnectView;
import client.start.StartController;
import client.start.StartView;
import client.viewmanager.ViewManager;
import client.viewmanager.ViewNames;

import javax.swing.*;

public class ConnectController {

    private final ConnectModel model;

    public ConnectController(ConnectModel model) {
        this.model = model;
    }

    public void connect() {
        ViewManager.show(ViewNames.CONNECT);
        SwingUtilities.invokeLater(() -> {
            if (model.connectToTheServer()) {
                StartView startView = new StartView();
                StartController startController = new StartController(startView);
                ViewManager.add(startView, ViewNames.START);
                ViewManager.show(ViewNames.START);
                ViewManager.remove(ViewNames.CONNECT);
                ViewManager.remove(ViewNames.RECONNECT);
            } else {
                ReconnectView reconnectView = new ReconnectView();
                ReconnectController reconnectController = new ReconnectController(reconnectView, this);
                ViewManager.add(reconnectView, ViewNames.RECONNECT);
                ViewManager.show(ViewNames.RECONNECT);
            }
        });
    }

}
