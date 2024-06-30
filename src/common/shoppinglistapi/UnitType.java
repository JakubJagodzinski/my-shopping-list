package common.shoppinglistapi;

import java.util.Arrays;

public enum UnitType {

    kg,
    g,
    l,
    ml,
    pc,
    set;

    public static String[] getValues() {
        return Arrays.stream(UnitType.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

}
