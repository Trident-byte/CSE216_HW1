import java.util.function.BiFunction;
import java.util.*;

public class HigherOrderUtils {
    static interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();

    }

    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "plus";
        }

        @Override
        public Double apply(Double o, Double o2) {
            return o + o2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "minus";
        }

        @Override
        public Double apply(Double o, Double o2) {
            return o - o2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "mult";
        }

        @Override
        public Double apply(Double o, Double o2) {
            return o * o2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double o, Double o2) {
            if(o2 == 0){
                throw new IllegalArgumentException("Can't divide by zero");
            }
            return o / o2;
        }
    };

    public static <T,S extends BiFunction<T,T,T>> T zip(List<T> args, List<S> bifunctions){
        for(int i = 0; i < bifunctions.size(); i++){
            S function = bifunctions.get(i);
            T arg1 = args.get(i);
            T args2 = args.get(i+1);
            args.set(i+1, function.apply(arg1, args2));
            System.out.println(args.toString());
        }
        return args.get(args.size() -1);
    }

    public static void main(String... args) {
        List<Double> numbers = Arrays.asList(-0.5, 2d, 3d, 0d, 4d); // documentation example
        List<NamedBiFunction<Double, Double, Double>> operations = Arrays.asList(add,multiply,add,divide);
        Double d = zip(numbers, operations); // expected correct value: 1.125
        System.out.println(d);
        // different use case, not with NamedBiFunction objects
        List<String> strings = Arrays.asList("a", "n", "t");
        // note the syntax of this lambda expression
        BiFunction<String, String, String> concat = (s, t) -> s + t;
        String s = zip(strings, Arrays.asList(concat, concat)); // expected correct value: "ant"
        System.out.println(s);
    }
}
