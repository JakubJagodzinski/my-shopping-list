package client;

import java.util.regex.Pattern;

public class InputValidator {

    public static final int CUSTOMER_LIST_NAME_MIN_LENGTH = 2;
    public static final int CUSTOMER_LIST_NAME_MAX_LENGTH = 30;
    public static final int SHOP_NAME_MIN_LENGTH = 2;
    public static final int SHOP_NAME_MAX_LENGTH = 30;
    public static final int USERNAME_MIN_LENGTH = 2;
    public static final int USERNAME_MAX_LENGTH = 30;
    public static final int PASSWORD_MIN_LENGTH = 5;
    public static final int PASSWORD_MAX_LENGTH = 30;
    public static final int PRODUCT_NAME_MIN_LENGTH = 2;
    public static final int PRODUCT_NAME_MAX_LENGTH = 30;
    public static final int CATEGORY_NAME_MIN_LENGTH = 2;
    public static final int CATEGORY_NAME_MAX_LENGTH = 30;

    public static boolean isProductNameValid(String productName) {
        if (productName.length() < PRODUCT_NAME_MIN_LENGTH || productName.length() > PRODUCT_NAME_MAX_LENGTH) {
            return false;
        }
        return Pattern.matches("[a-zA-Z0-9 '.]+", productName);
    }

    public static boolean isCategoryNameValid(String categoryName) {
        if (categoryName.length() < CATEGORY_NAME_MIN_LENGTH || categoryName.length() > CATEGORY_NAME_MAX_LENGTH) {
            return false;
        }
        return Pattern.matches("[a-zA-Z0-9 '.]+", categoryName);
    }

    public static boolean isCustomerListNameValid(String customerListName) {
        if (customerListName.length() < CUSTOMER_LIST_NAME_MIN_LENGTH || customerListName.length() > CUSTOMER_LIST_NAME_MAX_LENGTH) {
            return false;
        }
        return Pattern.matches("[a-zA-Z0-9 ']+", customerListName);
    }

    public static boolean isShopNameValid(String shopName) {
        if (shopName.length() < SHOP_NAME_MIN_LENGTH || shopName.length() > SHOP_NAME_MAX_LENGTH) {
            return false;
        }
        return Pattern.matches("[a-zA-Z0-9 ']+", shopName);
    }

    public static boolean isUsernameValid(String username) {
        if (username.length() < USERNAME_MIN_LENGTH || username.length() > USERNAME_MAX_LENGTH) {
            return false;
        }
        return Pattern.matches("[a-zA-Z0-9]+", username);
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
    }

}
