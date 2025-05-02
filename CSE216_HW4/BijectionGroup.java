import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BijectionGroup {
    private static <T> List<Function<T, T>> bijectionsOf(Set<T> domain) {
        List<T> domainToList = new ArrayList<>(domain);
        List<Function<T,T>> funcs = new ArrayList<>();
        return funcs;
    }

    private static <T> List<Map<T,T>> findOneToOneCorrespondanceMappings(Set<T> domain, T input){
        if(domain.isEmpty()){
            return null;
        }
        return null;
    }
    // your methods go here
    public static void main(String... args) {
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
// you have to figure out the data type in the line below
        List<Function<Integer,Integer>> bijections = bijectionsOf(a_few);
        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });
    }
}
