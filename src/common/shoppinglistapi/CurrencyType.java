package common.shoppinglistapi;

import java.util.Arrays;

public enum CurrencyType {

    PLN,
    USD,
    EUR,
    GBP;

    public static String[] getValues() {
        return Arrays.stream(CurrencyType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

}
