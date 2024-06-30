package client.connect;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectModel {

    public boolean connectToTheServer() {
        for (int i = 0; i < 10; ++i) {
            try {
                Socket clientSocket = new Socket("localhost", 5000);
                ObjectOutputStream OOS = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream OIS = new ObjectInputStream(clientSocket.getInputStream());
                ConnectionResources.initialize(clientSocket, OIS, OOS);
                return true;
            } catch (Exception e) {
                try {
                    Thread.sleep(200);
                } catch (Exception ignore) {
                }
            }
        }
        return false;
    }

    public static void disconnect() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.DISCONNECT, null));
            ConnectionResources.getOos().close();
            ConnectionResources.getOis().close();
            ConnectionResources.getClientSocket().close();
            ConnectionResources.reset();
        } catch (Exception ignore) {
        }
    }

}
