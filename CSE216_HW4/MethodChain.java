import java.util.*;
import java.util.stream.Collectors;

public class MethodChain{
    /**
     * Flattens a map to a list of <code>String</code>s, where each element in the list is formattedmap stream java
     * as "key -> value" (i.e., each key-value pair is converted to a string in this specific format).
     *
     * @param aMap the specified input map.
     * @param <K> the type parameter of keys in <code>aMap</code>.
     * @param <V> the type parameter of values in <code>aMap</code>.
     * @return the flattened list representation of <code>aMap</code>.
     */
    public static <K, V> List<String> flatten(Map<K, V> aMap){
        return aMap.keySet().stream()
                            .map((a) -> a.toString() + " -> " + aMap.get(a).toString())
                            .collect(Collectors.toList());
    }

    public static void main(String[] args){
        System.out.println("Print");
    }
}