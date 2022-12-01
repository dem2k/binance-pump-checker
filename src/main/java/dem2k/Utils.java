package dem2k;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String toStringTrunc(double pNew) {
        String[] str = Double.toString(pNew).split("\\.");
        return str[0] + "." + str[1].substring(0, 5);
    }

    public static List<String> listToUpperCase(List<String> list) {
        return list.stream().map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    
}


