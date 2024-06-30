package server;

import server.loadedcustomerlists.LoadedCustomerLists;
import server.loadedshopslist.LoadedShopLists;
import server.loggedinuserslist.LoggedInUsersList;

import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.util.Scanner;
import java.util.Vector;

public class Terminal extends Thread {

    private final ServerSocket serverSocket;
    private final Vector<Socket> connections;
    private final LoggedInUsersList loggedInUsers;
    private final LoadedShopLists loadedShopLists;
    private final LoadedCustomerLists loadedCustomerLists;
    private final long serverLaunchTime;

    public Terminal(ServerSocket serverSocket, Vector<Socket> connections, LoggedInUsersList users, LoadedShopLists loadedShopLists, LoadedCustomerLists loadedCustomerLists) {
        this.serverSocket = serverSocket;
        this.connections = connections;
        this.loggedInUsers = users;
        this.loadedShopLists = loadedShopLists;
        this.loadedCustomerLists = loadedCustomerLists;
        this.serverLaunchTime = System.currentTimeMillis();
    }

    private void printHelp() {
        System.out.println("Commands:");
        System.out.println("time - shows server launch and working time");
        System.out.println("clients - prints active clients connections");
        System.out.println("users - prints logged in users data");
        System.out.println("shops - prints shops loaded to server memory");
        System.out.println("lists - prints customer lists loaded to server memory");
        System.out.println("info - prints software info");
        System.out.println("shutdown - shuts down the server");
    }

    private void printUpTime() {
        System.out.println("Time zone: " + ZoneId.systemDefault());
        System.out.println("Current time: " + TimeManager.parseToDate(System.currentTimeMillis()));
        System.out.println("Server launched: " + TimeManager.parseToDate(this.serverLaunchTime));
        System.out.println("Server is up for: " + TimeManager.parseToTime(TimeManager.getTimeDiff(this.serverLaunchTime)));
    }

    private void printConnectedClients() {
        if (this.connections.isEmpty()) {
            System.out.println("There are no connected clients.");
            return;
        }
        System.out.println("Clients connected:");
        for (var client : this.connections) {
            System.out.println(client);
        }
        System.out.println("Total clients connected: " + this.connections.size());
    }

    private void printProgramInfo() {
        System.out.println("-".repeat(10) + "INFO" + "-".repeat(10));
        System.out.println("My Shopping List");
        System.out.println("Version: 2.0.0 (GUI)");
        System.out.println("Release date: 6th of June, 2024");
        System.out.println("© 2024 Jakub Jagodziński All Rights Reserved");
    }

    private void shutdown() {
        try {
            TextFormatter.printlnYellow("Shutting down the server...");
            TextFormatter.printlnYellow("Closing server socket...");
            this.serverSocket.close();
            TextFormatter.printlnYellow("Server socket closed.");
            TextFormatter.printlnYellow("Closing clients connections...");
            for (var clientSocket : this.connections) {
                clientSocket.close();
            }
            TextFormatter.printlnYellow("Waiting...");
            while (!this.connections.isEmpty()) {
                Thread.sleep(500);
            }
            TextFormatter.printlnYellow("Clients connections closed.");
            ServerLogsRegister.closeLogsRegister();
            TextFormatter.printlnYellow("Server shut down.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        TextFormatter.printlnYellow("Type \"help\" to get commands list");
        try {
            Scanner scanner = new Scanner(System.in);
            String command;
            while (true) {
                command = scanner.nextLine().trim().toLowerCase();
                switch (command) {
                    case "help":
                        printHelp();
                        break;
                    case "time":
                        printUpTime();
                        break;
                    case "clients":
                        printConnectedClients();
                        break;
                    case "users":
                        this.loggedInUsers.print();
                        break;
                    case "shops":
                        this.loadedShopLists.print();
                        break;
                    case "lists":
                        this.loadedCustomerLists.printCustomerLists();
                        break;
                    case "info":
                        this.printProgramInfo();
                        break;
                    case "shutdown":
                        shutdown();
                        return;
                    default:
                        TextFormatter.printlnRed("Unknown command \"" + command + "\"! Type \"help\" to get commands list.");
                }
            }
        } catch (Exception e) {
            TextFormatter.printErrorMessage("Terminal crashed");
        }
    }

}
