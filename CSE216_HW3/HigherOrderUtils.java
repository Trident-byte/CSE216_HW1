import java.util.function.BiFunction;
import java.util.function.Function;

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
            return o + o2;
        }
    };

    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {
        @Override
        public String name() {
            return "div";
        }

        @Override
        public Double apply(Double o, Double o2) {
            return o + o2;
        }
    };
}
