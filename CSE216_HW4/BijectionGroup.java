import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BijectionGroup {
    //your code goes here

    private static <T> Set<Function<T,T>> bijectionsOf(Set<T> domain) {
        List<T> domainToList = new ArrayList<>(domain);
        Set<Function<T,T>> funcs = new HashSet<>();
        List<Map<T,T>> mappings = findOneToOneCorrespondanceMappings(domainToList, new HashSet<>(), 0);
        for(Map<T,T> mapping: mappings){
            Function<T,T> mappingFunc = (a) ->{
                Map<T,T> map = mapping;
                return map.get(a);
            };
            funcs.add(mappingFunc);
        }
        return funcs;
    }

    private static <T> BijectionGroup bijectionGroup(Set<T> domain){
        // List<Map> bijectionMappings = findOneToOneCorrespondanceMappings(new ArrayList<>(domain), new HashSet<>(), 0);
        return null;
    }

    private static <T> List<Map<T,T>> findOneToOneCorrespondanceMappings(List<T> domain, Set<T> codomain, int index){
        if(domain.size() == index){
            HashMap<T,T> map = new HashMap<>();
            List<Map<T,T>> empty = new ArrayList<>();
            empty.add(map);
            return empty;
        }
        T key = domain.get(index);
        List<Map<T,T>> ans = new ArrayList<>();
        for(T value: domain){
            if(!codomain.contains(value)){
                codomain.add(value);
                List<Map<T,T>> partialMappings = findOneToOneCorrespondanceMappings(domain, codomain, index + 1);
                codomain.remove(value);
                for(Map<T,T> map: partialMappings){
                    map.put(key, value);
                }
                ans.addAll(partialMappings);
            }
        }
        return ans;
    }

    public static void main(String... args) {
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
        // you have to figure out the data types in the lines below
        // some of these data types are functional objects
        // so, look into java.util.function.Function
        // __________________________ g = bijectionGroup(a_few);
        Function<Integer,Integer> f1 = bijectionsOf(a_few).stream().findFirst().get();
        // __________________________ f2 = g.inverseOf(f1);
        // __________________________ id = g.identity();
    }
//     public static void main(String... args) {
//         Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
// // you have to figure out the data type in the line below
//         List<Function<Integer,Integer>> bijections = bijectionsOf(a_few);
//         bijections.forEach(aBijection -> {
//             a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
//             System.out.println();
//         });
//     }
}
