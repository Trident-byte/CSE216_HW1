import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SquareSymmetries implements Symmetries<Square>{
    public SquareSymmetries(){
        super();
    }

    public boolean areSymmetric(Square s1, Square s2){
        List<Point> firstSquarePoints = s1.getPoints();
        List<Point> secondSquarePoints = s2.getPoints();
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
                System.out.println(i * 90);
                symmetricSquares.add(test);
            }
        }
        List<Square> possibleSymmetries = new ArrayList<>();
        possibleSymmetries.add(horizontalReflection(s));
        possibleSymmetries.add(verticalReflection(s));
        possibleSymmetries.add(diagonalReflection(s));
        possibleSymmetries.add(counterDiagonalReflection(s));
        for(Square test: possibleSymmetries){
            if(areSymmetric(test, s)){
                symmetricSquares.add(test);
            }
        }
        return symmetricSquares;
    }

    public Square horizontalReflection(Square s){
        List<Point> points = new ArrayList<>();
        Point center = s.center();
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        for(Point point: s.getPoints()){
            double newX = 0;
            if(point.x < center.x){
                newX = point.x + sideLength;
            }
            else{
                newX = point.x - sideLength;
            }
            points.add(new Point(point.name, newX, point.y));
        }
        Point[] input = new Point[4];
        return new Square(points.toArray(input));
    }

    public Square verticalReflection(Square s){
        List<Point> points = new ArrayList<>();
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        Point center = s.center();
        for(Point point: s.getPoints()){
            double newY = 0;
            if(point.y < center.y){
                newY = point.y + sideLength;
            }
            else{
                newY = point.y - sideLength;
            }
            points.add(new Point(point.name, point.x,newY));
        }
        Point[] input = new Point[4];
        return new Square(points.toArray(input));
    }

    public Square diagonalReflection(Square s){
        List<Point> points = new ArrayList<>();
        Point center = s.center();
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        for(Point point: s.getPoints()){
            double newX = point.x;
            double newY = point.y;
            if(point.x < center.x && point.y < center.y){
                newX += sideLength;
                newY += sideLength;
            }
            else if(point.x > center.x && point.y > center.y){
                newX -= sideLength;
                newY -= sideLength;
            }
            points.add(new Point(point.name, newX,newY));
        }
        Point[] input = new Point[4];
        return new Square(points.toArray(input));
    }

    public Square counterDiagonalReflection(Square s){
        List<Point> points = new ArrayList<>();
        Point center = s.center();
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        for(Point point: s.getPoints()){
            double newX = point.x;
            double newY = point.y;
            if(point.x < center.x && point.y > center.y){
                newX += sideLength;
                newY -= sideLength;
            }
            else if(point.x > center.x && point.y < center.y){
                newX -= sideLength;
                newY += sideLength;
            }
            points.add(new Point(point.name, newX,newY));
        }
        Point[] input = new Point[4];
        return new Square(points.toArray(input));
    }

    private List<Point> sortPoints(List<Point> points){
        return null;
    }
}
