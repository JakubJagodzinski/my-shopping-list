package server.requesthandlers;

import common.request.RequestData;
import common.request.RequestType;
import server.*;
import server.databaseapi.DatabaseOperations;
import server.loadedcustomerlists.LoadedCustomerLists;
import server.loadedshopslist.LoadedShopLists;
import server.loggedinuserslist.LoggedInUsersList;
import server.loggedinuserslist.UserData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class ClientRequestHandler extends Thread {

    private final Socket clientSocket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final Vector<Socket> connections;
    private final LoggedInUsersList users;
    private final LoadedShopLists loadedShops;
    private final LoadedCustomerLists loadedCustomerLists;

    public ClientRequestHandler(Socket clientSocket, ObjectInputStream ois, ObjectOutputStream oos, Vector<Socket> connections, LoggedInUsersList users, LoadedShopLists loadedShops, LoadedCustomerLists loadedCustomerLists) {
        this.clientSocket = clientSocket;
        this.ois = ois;
        this.oos = oos;
        this.connections = connections;
        this.users = users;
        this.loadedShops = loadedShops;
        this.loadedCustomerLists = loadedCustomerLists;
    }

    private String logIn(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String password = data.get(1);
        boolean alreadyLoggedIn = false;
        boolean authenticated = false;
        String accountType = "";
        String authenticationToken = "";
        if (this.users.isUserLoggedIn(username)) {
            alreadyLoggedIn = true;
        } else if (DatabaseOperations.authorizeUser(username, password)) {
            authenticated = true;
            accountType = DatabaseOperations.getAccountType(username).toString();
            authenticationToken = AuthenticationTokenGenerator.generateNewToken();
            this.users.addUser(new UserData(username, accountType, authenticationToken));
        }
        ArrayList<String> response = new ArrayList<>();
        response.add(String.valueOf(alreadyLoggedIn));
        response.add(String.valueOf(authenticated));
        response.add(accountType);
        response.add(authenticationToken);
        this.oos.writeObject(response);
        if (authenticated) {
            return username;
        }
        return "";
    }

    private void logOut(RequestData requestData) {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String authenticationToken = data.get(1);
        if (this.users.checkAuthenticationToken(username, authenticationToken)) {
            this.users.removeUser(username);
        }
    }

    private void createAccount(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String password = data.get(1);
        String accountType = data.get(2);
        this.oos.writeObject(DatabaseOperations.createAccount(username, password, accountType));
    }

    private void deleteAccount(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String authenticationToken = data.get(1);
        if (this.users.checkAuthenticationToken(username, authenticationToken)) {
            boolean operationStatus = DatabaseOperations.deleteAccount(username);
            if (operationStatus) {
                this.users.removeUser(username);
            }
            this.oos.writeObject(operationStatus);
        } else {
            this.oos.writeObject(false);
        }
    }

    private String changeUsername(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String authenticationToken = data.get(1);
        String newUsername = data.get(2);
        if (this.users.checkAuthenticationToken(username, authenticationToken)) {
            boolean operationStatus = DatabaseOperations.changeUsername(username, newUsername);
            if (operationStatus) {
                this.users.changeUsername(username, newUsername);
                username = newUsername;
            }
            this.oos.writeObject(operationStatus);
        } else {
            this.oos.writeObject(false);
        }
        return username;
    }

    private void changePassword(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String password = data.get(1);
        String newPassword = data.get(2);
        boolean operationStatus = DatabaseOperations.changePassword(username, password, newPassword);
        this.oos.writeObject(operationStatus);
    }

    private void getDatabaseShopsNames() throws Exception {
        this.oos.writeObject(DatabaseOperations.getDatabaseShopsNames());
    }

    private void getUserShopsNames(RequestData requestData) throws Exception {
        String username = (String) requestData.data();
        this.oos.writeObject(DatabaseOperations.getUserShopsNames(username));
    }

    private void getCustomerSharedLists(RequestData requestData) throws Exception {
        this.oos.writeObject(DatabaseOperations.getCustomerSharedLists((String) requestData.data()));
    }

    private void getCustomerListsNames(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String shopName = data.get(1);
        this.oos.writeObject(DatabaseOperations.getCustomerListsNames(username, shopName));
    }

    private void createNewCustomerList(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String shopName = data.get(1);
        String shoppingListName = data.get(2);
        boolean operationStatus = DatabaseOperations.createCustomerList(username, shopName, shoppingListName);
        Changelog changelog = new Changelog(shoppingListName, username, shopName);
        changelog.pushOneEntry(username + ": \"" + shoppingListName + "\" shopping list created");
        this.oos.writeObject(operationStatus);
    }

    private void editCustomerList(RequestData requestData) {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String editorName = data.get(0);
        String ownerName = data.get(1);
        String shopName = data.get(2);
        String shoppingListName = data.get(3);
        CustomerListRequestHandler customerListRequestHandler = new CustomerListRequestHandler(this.clientSocket, this.ois, this.oos, this.loadedShops, this.loadedCustomerLists, shoppingListName, ownerName, editorName, shopName);
        customerListRequestHandler.run();
    }

    private void createNewShop(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String shopName = data.get(1);
        this.oos.writeObject(DatabaseOperations.createShop(username, shopName));
    }

    private void editShop(RequestData requestData) {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String username = data.get(0);
        String shopName = data.get(1);
        ShopManagerRequestHandler shopManagerRequestHandler = new ShopManagerRequestHandler(this.clientSocket, this.ois, this.oos, this.loadedShops, username, shopName);
        shopManagerRequestHandler.run();
    }

    public void run() {
        String username = "";
        while (true) {
            try {
                RequestData requestData = (RequestData) this.ois.readObject();
                RequestType requestType = requestData.header();
                String log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + Main.getSocketInfo(this.clientSocket) + ": request " + requestType;
                ServerLogsRegister.addLog(log);
                TextFormatter.printlnYellow(log);
                switch (requestType) {
                    case SIGN_IN:
                        username = this.logIn(requestData);
                        break;
                    case SIGN_OUT:
                        this.logOut(requestData);
                        this.users.removeUser(username);
                        username = "";
                        break;
                    case CREATE_ACCOUNT:
                        this.createAccount(requestData);
                        break;
                    case DELETE_ACCOUNT:
                        this.deleteAccount(requestData);
                        break;
                    case CHANGE_USERNAME:
                        String newUsername = this.changeUsername(requestData);
                        if (!newUsername.isEmpty()) {
                            username = newUsername;
                        }
                        break;
                    case CHANGE_PASSWORD:
                        this.changePassword(requestData);
                        break;
                    case GET_DATABASE_SHOPS_NAMES:
                        this.getDatabaseShopsNames();
                        break;
                    case GET_USER_SHOPS_NAMES:
                        this.getUserShopsNames(requestData);
                        break;
                    case GET_CUSTOMER_SHARED_LISTS:
                        this.getCustomerSharedLists(requestData);
                        break;
                    case GET_CUSTOMER_LISTS_NAMES:
                        this.getCustomerListsNames(requestData);
                        break;
                    case CUSTOMER_LIST_CREATE:
                        this.createNewCustomerList(requestData);
                        break;
                    case CUSTOMER_LIST_EDIT:
                        this.editCustomerList(requestData);
                        break;
                    case SHOP_LIST_CREATE:
                        this.createNewShop(requestData);
                        break;
                    case SHOP_LIST_EDIT:
                        this.editShop(requestData);
                        break;
                    case DISCONNECT:
                        this.connections.remove(this.clientSocket);
                        return;
                    default:
                        log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + Main.getSocketInfo(this.clientSocket) + ": request unknown - " + requestType;
                        ServerLogsRegister.addLog(log);
                        TextFormatter.printlnRed(log);
                        this.oos.writeObject(false);
                }
            } catch (Exception e) {
                String log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + Main.getSocketInfo(this.clientSocket) + ": " + e.getMessage();
                ServerLogsRegister.addLog(log);
                TextFormatter.printlnYellow(log);
                this.connections.remove(this.clientSocket);
                this.users.removeUser(username);
                return;
            }
        }
    }

}
