package server;

import server.loadedcustomerlists.LoadedCustomerLists;
import server.loadedshopslist.LoadedShopLists;
import server.loggedinuserslist.LoggedInUsersList;
import server.requesthandlers.ClientRequestHandler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Main {

    public static final int PORT = 5000;

    public static final Vector<Socket> connections = new Vector<>();
    public static final LoggedInUsersList loggedInUsers = new LoggedInUsersList();
    public static final LoadedShopLists loadedShopLists = new LoadedShopLists();
    public static final LoadedCustomerLists loadedCustomerLists = new LoadedCustomerLists();

    public static ServerSocket openServer() {
        try {
            return new ServerSocket(PORT);
        } catch (Exception e) {
            return null;
        }
    }

    public static void connectWithClients(ServerSocket serverSocket) {
        String log;
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream OIS = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream OOS = new ObjectOutputStream(clientSocket.getOutputStream());
                ClientRequestHandler clientRequestHandler = new ClientRequestHandler(clientSocket, OIS, OOS, connections, loggedInUsers, loadedShopLists, loadedCustomerLists);
                clientRequestHandler.start();
                connections.add(clientSocket);
                log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + getSocketInfo(clientSocket) + ": connected";
                ServerLogsRegister.addLog(log);
                TextFormatter.printlnYellow(log);
            } catch (Exception e) {
                if (serverSocket.isClosed()) {
                    return;
                } else {
                    TextFormatter.printErrorMessage("Failed to connect with client");
                }
            }
        }
    }

    public static String getSocketInfo(Socket socket) {
        return "[" + socket.getInetAddress().getHostAddress() + " " + socket.getPort() + "]";
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = openServer()) {
            if (serverSocket == null) {
                TextFormatter.printErrorMessage("Failed to launch server");
                return;
            }
            TextFormatter.printlnYellow("\"My Shopping List\" server is listening on port " + PORT);
            Thread serverLogsRegisterThread = new Thread(ServerLogsRegister::run);
            serverLogsRegisterThread.start();
            Terminal terminalThread = new Terminal(serverSocket, connections, loggedInUsers, loadedShopLists, loadedCustomerLists);
            terminalThread.start();
            connectWithClients(serverSocket);
        } catch (Exception e) {
            TextFormatter.printErrorMessage("Server crashed");
        }
    }

}
