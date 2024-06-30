package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionResources {

    private static Socket clientSocket;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;

    public static void initialize(Socket initialClientSocket, ObjectInputStream initialOis, ObjectOutputStream initialOos) {
        clientSocket = initialClientSocket;
        ois = initialOis;
        oos = initialOos;
    }

    public static void reset() {
        clientSocket = null;
        ois = null;
        oos = null;
    }

    public static Socket getClientSocket() {
        return clientSocket;
    }

    public static ObjectInputStream getOis() {
        return ois;
    }

    public static ObjectOutputStream getOos() {
        return oos;
    }

}
