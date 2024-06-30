package server.databaseapi;

public class DatabasePath {

    public static final String DATABASE_DIR = System.getProperty("user.dir") + "/database";
    public static final String SHOPS_DIR = DATABASE_DIR + "/shops";
    public static final String USERS_DIR = DATABASE_DIR + "/users";
    public static final String USERS_DATABASE_FILE = DATABASE_DIR + "/$USERS_DATABASE.txt";
    public static final String SHARED_FILE = "/$SHARED.txt";
    public static final String ACCOUNT_HISTORY_FILE = "/$ACCOUNT_HISTORY.txt";
    public static final String CHANGELOG_FILE = "/$CHANGELOG.txt";
    public static final String SERVER_LOGS_FILE = "/$SERVER_LOGS.txt";
    public static final String SERVER_LOGS_REGISTER = DATABASE_DIR + SERVER_LOGS_FILE;

    public static String shopPublicDirectory(String shopName) {
        return SHOPS_DIR + "/" + shopName;
    }

    public static String shopPublicListFile(String shopName) {
        return shopPublicDirectory(shopName) + "/" + shopName + ".txt";
    }

    public static String shopLocalDirectory(String shopOwnerName, String shopName) {
        return userDirectory(shopOwnerName) + "/" + shopName;
    }

    public static String shopLocalListFile(String shopOwnerName, String shopName) {
        return shopLocalDirectory(shopOwnerName, shopName) + "/" + shopName + ".txt";
    }

    public static String shopLocalChangelogFile(String shopOwnerName, String shopName) {
        return shopLocalDirectory(shopOwnerName, shopName) + CHANGELOG_FILE;
    }

    public static String userDirectory(String username) {
        return USERS_DIR + "/" + username;
    }

    public static String userAccountHistoryFile(String username) {
        return userDirectory(username) + ACCOUNT_HISTORY_FILE;
    }

    public static String customerSharedFile(String customerName) {
        return userDirectory(customerName) + SHARED_FILE;
    }

    public static String customerShopDirectory(String customerName, String shopName) {
        return userDirectory(customerName) + "/" + shopName;
    }

    public static String customerListDirectory(String customerName, String shopName, String listName) {
        return customerShopDirectory(customerName, shopName) + "/" + listName;
    }

    public static String customerListFile(String customerName, String shopName, String listName) {
        return customerListDirectory(customerName, shopName, listName) + "/" + listName + ".txt";
    }

    public static String customerListChangelogFile(String customerName, String shopName, String listName) {
        return customerListDirectory(customerName, shopName, listName) + CHANGELOG_FILE;
    }

}
