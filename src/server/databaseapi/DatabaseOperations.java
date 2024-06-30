package server.databaseapi;

import common.AccountType;
import server.ServerLogsRegister;
import server.TimeManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

public class DatabaseOperations {

    public static synchronized boolean userExists(String username) {
        return !getAccountType(username).equals(AccountType.NON_EXISTENT);
    }

    public static synchronized boolean authorizeUser(String username, String password) {
        DatabaseRecord userData = getUserData(username);
        if (userData == null) {
            return false;
        }
        return userData.getPassword().equals(password);
    }

    public static synchronized AccountType getAccountType(String username) {
        DatabaseRecord userData = getUserData(username);
        if (userData == null) {
            return AccountType.NON_EXISTENT;
        }
        return AccountType.valueOf(userData.getAccountType());
    }

    private static synchronized DatabaseRecord getUserData(String username) {
        try {
            File usersDatabase = new File(DatabasePath.USERS_DATABASE_FILE);
            Scanner fileReader = new Scanner(usersDatabase);
            while (fileReader.hasNextLine()) {
                DatabaseRecord userData = new DatabaseRecord(fileReader.nextLine());
                if (userData.getUsername().equalsIgnoreCase(username)) {
                    return userData;
                }
            }
            fileReader.close();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private static synchronized ArrayList<DatabaseRecord> getUsersData() {
        try {
            File usersDatabase = new File(DatabasePath.USERS_DATABASE_FILE);
            Scanner fileReader = new Scanner(usersDatabase);
            ArrayList<DatabaseRecord> usersData = new ArrayList<>();
            while (fileReader.hasNextLine()) {
                usersData.add(new DatabaseRecord(fileReader.nextLine()));
            }
            fileReader.close();
            return usersData;
        } catch (Exception e) {
            return null;
        }
    }

    private static synchronized boolean addUserToDatabase(String username, String password, AccountType accountType) {
        if (userExists(username)) {
            return false;
        }
        try {
            FileWriter fileWriter = new FileWriter(DatabasePath.USERS_DATABASE_FILE, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(String.valueOf(new DatabaseRecord(username, password, accountType.name())));
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static synchronized void removeUserFromDatabase(String username) {
        try {
            ArrayList<DatabaseRecord> existingUsersData = getUsersData();
            if (existingUsersData == null) {
                return;
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DatabasePath.USERS_DATABASE_FILE));
            for (var userData : existingUsersData) {
                if (!userData.getUsername().equalsIgnoreCase(username)) {
                    bufferedWriter.write(String.valueOf(userData));
                    bufferedWriter.newLine();
                }
            }
            bufferedWriter.close();
        } catch (Exception ignore) {
        }
    }

    public static synchronized boolean shopExistsInDatabase(String shopName) {
        ArrayList<String> databaseShopsNames = getDatabaseShopsNames();
        return databaseShopsNames != null && databaseShopsNames.contains(shopName);
    }

    public static synchronized ArrayList<String> getDatabaseShopsNames() {
        File directoryPath = new File(DatabasePath.SHOPS_DIR);
        String[] directoryContent = directoryPath.list();
        if (directoryContent == null || directoryContent.length == 0) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(directoryContent));
    }

    public static synchronized ArrayList<String> getUserShopsNames(String username) {
        if (!userExists(username)) {
            return null;
        }
        File directoryPath = new File(DatabasePath.userDirectory(username));
        String[] directoryContent = directoryPath.list();
        if (directoryContent == null) {
            return null;
        }
        ArrayList<String> shopsNames = new ArrayList<>();
        for (var filename : directoryContent) {
            if (!filename.endsWith(".txt")) {
                shopsNames.add(filename);
            }
        }
        if (shopsNames.isEmpty()) {
            return null;
        }
        ArrayList<String> databaseShopsNames = getDatabaseShopsNames();
        if (databaseShopsNames == null) {
            return null;
        }
        ArrayList<String> shopsToDelete = new ArrayList<>();
        for (var shopName : shopsNames) {
            if (!databaseShopsNames.contains(shopName)) {
                shopsToDelete.add(shopName);
            }
        }
        for (var shopToDeleteName : shopsToDelete) {
            shopsNames.remove(shopToDeleteName);
        }
        return shopsNames;
    }

    public static ArrayList<String> getCustomerListsNames(String customerName, String shopName) {
        if (!userExists(customerName) || !shopExistsInDatabase(shopName)) {
            return null;
        }
        File userShopDirectory = new File(DatabasePath.customerShopDirectory(customerName, shopName));
        String[] userShopDirectoryContent = userShopDirectory.list();
        if (userShopDirectoryContent == null || userShopDirectoryContent.length == 0) {
            return null;
        }
        return new ArrayList<>(Arrays.asList(userShopDirectoryContent));
    }

    public static void pushEntriesToChangelog(String listOwnerName, String shopName, String shoppingListName, ConcurrentSkipListMap<Long, String> entries) {
        try {
            File listChangelogFile;
            if (shoppingListName.equals(shopName)) {
                listChangelogFile = new File(DatabasePath.shopLocalChangelogFile(listOwnerName, shopName));
            } else {
                listChangelogFile = new File(DatabasePath.customerListChangelogFile(listOwnerName, shopName, shoppingListName));
            }
            FileWriter fileWriter = new FileWriter(listChangelogFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (var entry : entries.entrySet()) {
                bufferedWriter.write(TimeManager.parseToDate(entry.getKey()) + " " + entry.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception ignore) {
        }
    }

    public static void pushLogsToFile(ConcurrentSkipListMap<Long, String> logs) {
        try {
            File serverLogsRegister = new File(DatabasePath.SERVER_LOGS_REGISTER);
            FileWriter fileWriter = new FileWriter(serverLogsRegister, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (var log : logs.entrySet()) {
                bufferedWriter.write(TimeManager.parseToDate(log.getKey()) + " " + log.getValue());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception ignore) {
        }
    }

    private static boolean removeDirectory(File directoryToBeDeleted) {
        File[] contents = directoryToBeDeleted.listFiles();
        if (contents != null) {
            for (var file : contents) {
                removeDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static synchronized boolean createShop(String shopOwnerName, String shopName) {
        if (shopExistsInDatabase(shopName)) {
            return false;
        }
        try {
            if (createShopLocalDirectory(shopOwnerName, shopName)) {
                if (createShopPublicDirectory(shopName)) {
                    addEntryToAccountHistoryFile(shopOwnerName, "\"" + shopOwnerName + "\" created \"" + shopName + "\" shop list");
                    ServerLogsRegister.addLog("\"" + shopOwnerName + "\" created \"" + shopName + "\" shop");
                    return true;
                } else {
                    removeDirectory(new File(DatabasePath.shopLocalDirectory(shopOwnerName, shopName)));
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createShopPublicDirectory(String shopName) throws Exception {
        File publicShopDirectory = new File(DatabasePath.shopPublicDirectory(shopName));
        if (publicShopDirectory.mkdir()) {
            File publicListFile = new File(DatabasePath.shopPublicListFile(shopName));
            return publicListFile.createNewFile();
        }
        return false;
    }

    public static boolean createShopLocalDirectory(String shopOwnerName, String shopName) throws Exception {
        File localShopDirectory = new File(DatabasePath.shopLocalDirectory(shopOwnerName, shopName));
        boolean operationStatus = localShopDirectory.mkdirs();
        if (operationStatus) {
            File localShopFile = new File(DatabasePath.shopLocalListFile(shopOwnerName, shopName));
            File localChangelogFile = new File(DatabasePath.shopLocalChangelogFile(shopOwnerName, shopName));
            if (!localShopFile.createNewFile() || !localChangelogFile.createNewFile()) {
                return false;
            }
            FileWriter fileWriter = new FileWriter(localChangelogFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(TimeManager.parseToDate(System.currentTimeMillis()) + " " + shopOwnerName + " created \"" + shopName + "\" shop");
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return true;
        }
        return false;
    }

    public static boolean removeShop(String shopOwnerName, String shopName) {
        if (!shopExistsInDatabase(shopName)) {
            return false;
        }
        try {
            if (deleteShopLocalDirectory(shopOwnerName, shopName)) {
                addEntryToAccountHistoryFile(shopOwnerName, "\"" + shopOwnerName + "\" deleted \"" + shopName + "\" shop list");
                ServerLogsRegister.addLog("\"" + shopOwnerName + "\" deleted \"" + shopName + "\" shop");
                return deleteShopPublicDirectory(shopName);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private static boolean deleteShopPublicDirectory(String shopName) {
        File shopDirectory = new File(DatabasePath.shopPublicDirectory(shopName));
        return removeDirectory(shopDirectory);
    }

    private static boolean deleteShopLocalDirectory(String shopManagerName, String shopName) {
        File shopDirectory = new File(DatabasePath.shopLocalDirectory(shopManagerName, shopName));
        return removeDirectory(shopDirectory);
    }

    public static synchronized boolean createAccount(String username, String password, String accountType) {
        if (userExists(username)) {
            return false;
        }
        File userDirectory = new File(DatabasePath.userDirectory(username));
        if (userDirectory.mkdirs() && addUserToDatabase(username, password, AccountType.valueOf(accountType))) {
            try {
                if (accountType.equals(AccountType.CUSTOMER.toString())) {
                    File userSharedFile = new File(DatabasePath.customerSharedFile(username));
                    if (!userSharedFile.createNewFile()) {
                        deleteAccount(username);
                        return false;
                    }
                }
                File accountHistoryFile = new File(DatabasePath.userAccountHistoryFile(username));
                if (!accountHistoryFile.createNewFile()) {
                    deleteAccount(username);
                    return false;
                }
                addEntryToAccountHistoryFile(username, "\"" + username + "\" " + getAccountType(username) + " account created");
                return true;
            } catch (Exception e) {
                deleteAccount(username);
                return false;
            }
        }
        deleteAccount(username);
        return false;
    }

    public static boolean deleteAccount(String username) {
        if (!userExists(username)) {
            return false;
        }
        if (getAccountType(username).equals(AccountType.SHOP_MANAGER)) {
            ArrayList<String> userShopsNames = getUserShopsNames(username);
            if (userShopsNames != null && !userShopsNames.isEmpty()) {
                for (var shopName : userShopsNames) {
                    removeShop(username, shopName);
                    ServerLogsRegister.addLog("\"" + shopName + "\" shop deleted");
                }
            }
        }
        removeUserFromDatabase(username);
        removeDirectory(new File(DatabasePath.userDirectory(username)));
        ServerLogsRegister.addLog("\"" + username + "\" account deleted");
        return true;
    }

    public static synchronized boolean changeUsername(String username, String newUsername) {
        if (username.equals(newUsername) || !userExists(username) || userExists(newUsername)) {
            return false;
        }
        try {
            AccountType accountType = getAccountType(username);
            String password = getUserData(username).getPassword();
            removeUserFromDatabase(username);
            addUserToDatabase(newUsername, password, accountType);
            File userDirectory = new File(DatabasePath.userDirectory(username));
            File newUserDirectory = new File(DatabasePath.userDirectory(newUsername));
            if (!userDirectory.renameTo(newUserDirectory)) {
                removeUserFromDatabase(newUsername);
                addUserToDatabase(username, password, accountType);
                return false;
            }
            addEntryToAccountHistoryFile(newUsername, "username changed from \"" + username + "\" to \"" + newUsername + "\"");
            ServerLogsRegister.addLog("Account name changed from \"" + username + "\" to \"" + newUsername + "\"");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void addEntryToAccountHistoryFile(String username, String entry) {
        if (userExists(username)) {
            try {
                FileWriter fileWriter = new FileWriter(DatabasePath.userAccountHistoryFile(username), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(TimeManager.parseToDate(System.currentTimeMillis()) + " " + entry);
                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
            } catch (Exception ignore) {
            }
        }
    }

    public static boolean changePassword(String username, String password, String newPassword) {
        if (!authorizeUser(username, password)) {
            return false;
        }
        try {
            AccountType accountType = getAccountType(username);
            removeUserFromDatabase(username);
            addUserToDatabase(username, newPassword, accountType);
            addEntryToAccountHistoryFile(username, "password changed");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean createCustomerList(String customerName, String shopName, String customerListName) {
        try {
            Files.createDirectories(Path.of(DatabasePath.customerShopDirectory(customerName, shopName)));
            Files.createDirectories(Path.of(DatabasePath.customerListDirectory(customerName, shopName, customerListName)));
            Files.createFile(Path.of(DatabasePath.customerListFile(customerName, shopName, customerListName)));
            Files.createFile(Path.of(DatabasePath.customerListChangelogFile(customerName, shopName, customerListName)));
            addEntryToAccountHistoryFile(customerName, "\"" + customerName + "\" created \"" + customerListName + "\" list in \"" + shopName + "\" shop");
            ServerLogsRegister.addLog("\"" + customerName + "\" created \"" + customerListName + "\" customer list in \"" + shopName + "\" shop");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean deleteCustomerList(String customerName, String shopName, String customerListName) {
        boolean operationStatus = removeDirectory(new File(DatabasePath.customerListDirectory(customerName, shopName, customerListName)));
        File customerShopDirectory = new File(DatabasePath.customerShopDirectory(customerName, shopName));
        String[] shopContent = customerShopDirectory.list();
        if (shopContent != null && shopContent.length == 0) {
            customerShopDirectory.delete();
        }
        if (operationStatus) {
            addEntryToAccountHistoryFile(customerName, "\"" + customerName + "\" deleted \"" + customerListName + "\" customer list in \"" + shopName + "\" shop");
            ServerLogsRegister.addLog("\"" + customerName + "\" deleted \"" + customerListName + "\" customer list in \"" + shopName + "\" shop");
        }
        return operationStatus;
    }

    public static boolean shareCustomerList(String customerName, String shopName, String shoppingListName, String userToShareWith, boolean fullAccess) {
        if (!getAccountType(userToShareWith).equals(AccountType.CUSTOMER) || hasCustomerAccessToSharedList(userToShareWith, customerName, shopName, shoppingListName, fullAccess)) {
            return false;
        }
        try {
            FileWriter fileWriter = new FileWriter(DatabasePath.customerSharedFile(userToShareWith), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(customerName + "/" + shopName + "/" + shoppingListName + "/" + (fullAccess ? "full access" : "read only"));
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void removeCustomerSharedListPermission(String customerName, String listOwnerName, String shopName, String shoppingListName) {
        ArrayList<String> customerSharedLists = getCustomerSharedLists(customerName);
        if (customerSharedLists == null) {
            return;
        }
        ArrayList<String> temp = new ArrayList<>();
        for (var listPath : customerSharedLists) {
            String[] data = listPath.split("/");
            if (!(data[0].equals(listOwnerName) && data[1].equals(shopName) && data[2].equals(shoppingListName))) {
                temp.add(listPath);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(DatabasePath.customerSharedFile(customerName));
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (var listPath : temp) {
                bufferedWriter.write(listPath);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception ignore) {
        }
    }

    public static synchronized boolean hasCustomerAccessToSharedList(String customerName, String listOwnerName, String shopName, String listName, boolean willHaveFullAccess) {
        ArrayList<String> customerSharedLists = getCustomerSharedLists(customerName);
        if (customerSharedLists == null) {
            return false;
        }
        boolean alreadyHasAccess = false;
        boolean alreadyHasFullAccess = false;
        for (var listPath : customerSharedLists) {
            String[] data = listPath.split("/");
            if (data[0].equals(listOwnerName) && data[1].equals(shopName) && data[2].equals(listName)) {
                alreadyHasAccess = true;
                if (data[3].equals("full access")) {
                    alreadyHasFullAccess = true;
                }
            }
        }
        if (!alreadyHasAccess) {
            return false;
        }
        if (!alreadyHasFullAccess && willHaveFullAccess) {
            removeCustomerSharedListPermission(customerName, listOwnerName, shopName, listName);
            return false;
        }
        return true;
    }

    public static ArrayList<String> getCustomerSharedLists(String customerName) {
        if (!userExists(customerName)) {
            return null;
        }
        try {
            ArrayList<String> sharedListsPaths = new ArrayList<>();
            File usersDatabase = new File(DatabasePath.customerSharedFile(customerName));
            Scanner fileReader = new Scanner(usersDatabase);
            while (fileReader.hasNextLine()) {
                sharedListsPaths.add(fileReader.nextLine());
            }
            return sharedListsPaths;
        } catch (Exception e) {
            return null;
        }
    }

}
