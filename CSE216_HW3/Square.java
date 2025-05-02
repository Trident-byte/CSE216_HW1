import java.util.*;

/**
 * The class impementing squares.
 * Note: you can add more methods if you want, but additional methods must be <code>private</code> or <code>protected</code>.
 *
 * @author Brian Chau
 */

public class Square implements Shape {
    public List<Point> points;
    private static double tolerance = 0.0000001;
    /**
     * The constructor accepts an array of <code>Point</code>s to form the vertices of the square. If more than four
     * points are provided, only the first four are considered by this constructor.
     *
     * If less than four points are provided, or if the points do not form a valid square, the constructor throws
     * <code>java.lang.IllegalArgumentException</code>.
     *
     * @param vertices the array of vertices (i.e., <code>Point</code> instances) provided to the constructor.
     */
    public Square(Point... vertices) {
        points = new ArrayList<>();
        double prevAngle = -1;
        double angle;
        for(int i = 0; i < 4 && i < vertices.length; i++){
            Point curPoint = vertices[i];
            points.add(curPoint);
        }
        Point center = center();
        //If square is centered then test the square
        for(Point point: points){
            Point adjustedCenter = new Point("test", point.x - center.x, point.y - center.y);
            angle = determineAngle(adjustedCenter);
            if(angle < prevAngle){
                throw new IllegalArgumentException("Points not in the correct order");
            }
            prevAngle = angle;
        }
    }

    @Override
    public Square rotateBy(int degrees) {
        //Uses list rather than array to ensure abstractions can be used properly
        List<Point> newPoints = new ArrayList<>(4);
        Point center = this.center();
        //Precalculates the cosine and sine to prevent repeated calculations
        double cosine = Math.cos(Math.toRadians(degrees));
        double sine = Math.sin(Math.toRadians(degrees));
        Square adjusted = (Square) this.translateBy(-center.x, -center.y);
        for(Point point: adjusted.points){
            double newX = correction(point.x * cosine - point.y * sine);
            double newY = correction(point.x *sine + point.y * cosine);
            Point newPoint = new Point(point.name, newX, newY);
            newPoints.add(newPoint);
        }
        newPoints.sort(Comparator.comparing(this::determineAngle));
        //Needed so that I can use the toArray method that returns an array of type T
        Point[] argOfPoints = new Point[4];
        return (Square) (new Square(newPoints.toArray(argOfPoints))).translateBy(center.x, center.y);
    }

    @Override
    public Shape translateBy(double x, double y) {
        List<Point> newPoints = new ArrayList<>();
        for(Point point: points){
            newPoints.add(new Point(point.name, correction(point.x + x), correction(point.y + y)));
        }
        Point[] argOfPoints = new Point[4];
        return new Square(newPoints.toArray(argOfPoints));
    }

    @Override
    public String toString() {
        String representation = "[";
        for(Point point: points){
            representation += point.toString() + "; ";
        }
        return representation + "]";
    }

    @Override
    public Point center() {
        Set<Double> xValues = new HashSet<>();
        Set<Double> yValues = new HashSet<>();
        for(Point point: points){
            xValues.add(point.x);
            yValues.add(point.y);
        }
        double midX = xValues.stream().reduce(0.0, Double::sum)/xValues.size();
        double midY = yValues.stream().reduce(0.0, Double::sum)/yValues.size();
        return new Point("mid", midX, midY);
    }

    private static double correction(double value){
        double rounded = Math.round(value);
        if(Math.abs(rounded - value) < tolerance){
            return rounded;
        }
        else{
            return Math.round(value * 100)/100.0;
        }
    }

    public static void main(String... args) {
        Point  a = new Point("A", 1, 4);
        Point  b = new Point("B", 1, 1);
        Point  c = new Point("C", 4, 1);
        Point  d = new Point("D", 4, 4);

        Point p = new Point("P", 0.3, 0.3);

        Square sq1 = new Square(a, b, c, d); // throws an IllegalArgumentException
        Square sq2 = new Square(d, a, b, c); // forms a square
        Square sq3 = new Square(p, p, p, p); // forms a "trivial" square (this is a limiting case, but still valid)

        // prints: [(D, 4.0, 4.0); (A, 1.0, 4.0); (B, 1.0, 1.0); (C, 4.0, 1.0)]
        System.out.println(sq2);

        // prints: [(C, 4.0, 4.0); (D, 1.0, 4.0); (A, 1.0, 1.0); (B, 4.0, 1.0)]
        // note that the names denote which point has moved where
        System.out.println(sq2.rotateBy(90));
    }

    private double determineAngle(Point p){
        double angle = Math.atan2(p.y, p.x);
        if(angle < 0){
            angle += 2 * Math.PI;
        }
//        System.out.println(angle);
        return angle;
    }
}