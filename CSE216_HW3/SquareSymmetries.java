import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class SquareSymmetries implements Symmetries<Square>{
    public SquareSymmetries(){
        super();
    }

    public boolean areSymmetric(Square s1, Square s2){
        List<Point> firstSquarePoints = s1.points;
        List<Point> secondSquarePoints = s2.points;
        Point firstPoint = firstSquarePoints.get(0);
        Point secondPoint = firstSquarePoints.get(1);
        Point thirdPoint = secondSquarePoints.get(0);
        Point fourthPoint = secondSquarePoints.get(1);
        boolean shareFirstPoint = false;
        boolean shareSecondPoint = false;
        if(getSideLengths(firstPoint, secondPoint) != getSideLengths(thirdPoint, fourthPoint)){
            return false;
        }
        else{
            for(Point point: secondSquarePoints){
                if(point.x == firstPoint.x && point.y == firstPoint.y){
                    shareFirstPoint = true;
                }
                else if(point.x == secondPoint.x && point.y == secondPoint.y){
                    shareSecondPoint = true;
                }
            }
        }
        return shareFirstPoint && shareSecondPoint;
    }

    private double getSideLengths(Point p1, Point p2){
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + 
                        (p1.y - p2.y) * (p1.y - p2.y));
    }
    
    public List<Square> symmetriesOf(Square s){
        List<Square> symmetricSquares = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            Square test = s.rotateBy(i * 90);
            if(areSymmetric(s, test)){
                symmetricSquares.add(test);
            }
        }
        List<Square> possibleSymmetries = new ArrayList<>();
        possibleSymmetries.add(verticalReflection(s));
        possibleSymmetries.add(horizontalReflection(s));
        possibleSymmetries.add(diagonalReflection(s));
        possibleSymmetries.add(counterDiagonalReflection(s));
        for(Square test: possibleSymmetries){
            if(areSymmetric(test, s)){
                symmetricSquares.add(test);
            }
        }
        return symmetricSquares;
    }

    private Square horizontalReflection(Square s){
        Point[] points = new Point[4];
        List<Point> squarePoints = s.points;
        points[0] = new Point(squarePoints.get(1).name, squarePoints.get(0).x, squarePoints.get(0).y);
        points[1] = new Point(squarePoints.get(0).name, squarePoints.get(1).x, squarePoints.get(1).y);
        points[2] = new Point(squarePoints.get(3).name, squarePoints.get(2).x, squarePoints.get(2).y);
        points[3] = new Point(squarePoints.get(2).name, squarePoints.get(3).x, squarePoints.get(3).y);
        return new Square(points);
    }

    private Square verticalReflection(Square s){
        Point[] points = new Point[4];
        List<Point> squarePoints = s.points;
        points[0] = new Point(squarePoints.get(3).name, squarePoints.get(0).x, squarePoints.get(0).y);
        points[1] = new Point(squarePoints.get(2).name, squarePoints.get(1).x, squarePoints.get(1).y);
        points[2] = new Point(squarePoints.get(1).name, squarePoints.get(2).x, squarePoints.get(2).y);
        points[3] = new Point(squarePoints.get(0).name, squarePoints.get(3).x, squarePoints.get(3).y);
        return new Square(points);
    }

    private Square diagonalReflection(Square s){
        Point[] points = new Point[4];
        List<Point> squarePoints = s.points;
        points[0] = new Point(squarePoints.get(0).name, squarePoints.get(0).x, squarePoints.get(0).y);
        points[1] = new Point(squarePoints.get(3).name, squarePoints.get(1).x, squarePoints.get(1).y);
        points[2] = new Point(squarePoints.get(2).name, squarePoints.get(2).x, squarePoints.get(2).y);
        points[3] = new Point(squarePoints.get(1).name, squarePoints.get(3).x, squarePoints.get(3).y);
        return new Square(points);
    }

    private Square counterDiagonalReflection(Square s){
        Point[] points = new Point[4];
        List<Point> squarePoints = s.points;
        points[0] = new Point(squarePoints.get(2).name, squarePoints.get(0).x, squarePoints.get(0).y);
        points[1] = new Point(squarePoints.get(1).name, squarePoints.get(1).x, squarePoints.get(1).y);
        points[2] = new Point(squarePoints.get(0).name, squarePoints.get(2).x, squarePoints.get(2).y);
        points[3] = new Point(squarePoints.get(3).name, squarePoints.get(3).x, squarePoints.get(3).y);
        return new Square(points);
    }
}
