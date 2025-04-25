import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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
        Square adjusted = (Square) s.translateBy(-center.x, -center.y);
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        for(Point point: adjusted.getPoints()){
            double newX = point.x;
            if(point.x < 0){
                newX += sideLength;
            }
            else{
                newX -= sideLength;
            }
            points.add(new Point(point.name, newX, point.y));
        }
        points.sort(Comparator.comparing(this::determineAngle));
        Point[] input = new Point[4];
        return (Square)(new Square(points.toArray(input))).translateBy(center.x, center.y);
    }

    public Square verticalReflection(Square s){
        List<Point> points = new ArrayList<>();
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        Point center = s.center();
        Square adjusted = (Square) s.translateBy(-center.x, -center.y);
        for(Point point: adjusted.getPoints()){
            double newY;
            if(point.y < 0){
                newY = point.y + sideLength;
            }
            else{
                newY = point.y - sideLength;
            }
            points.add(new Point(point.name, point.x,newY));
        }
        points.sort(Comparator.comparing(this::determineAngle));
        Point[] input = new Point[4];
        return (Square)(new Square(points.toArray(input))).translateBy(center.x, center.y);
    }

    public Square diagonalReflection(Square s){
        List<Point> points = new ArrayList<>();
        Point center = s.center();
        Square adjusted = (Square) s.translateBy(-center.x, -center.y);
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        for(Point point: adjusted.getPoints()){
            double newX = point.x;
            double newY = point.y;
            if(point.x < 0 && point.y < 0){
                newX += sideLength;
                newY += sideLength;
            }
            else if(point.x > 0 && point.y > 0){
                newX -= sideLength;
                newY -= sideLength;
            }
            points.add(new Point(point.name, newX,newY));
        }
        points.sort(Comparator.comparing(this::determineAngle));
        Point[] input = new Point[4];
        return (Square)(new Square(points.toArray(input))).translateBy(center.x, center.y);
    }

    public Square counterDiagonalReflection(Square s){
        List<Point> points = new ArrayList<>();
        Point center = s.center();
        Square adjusted = (Square) s.translateBy(-center.x, -center.y);
        double sideLength = getSideLengths(s.getPoints().get(0), s.getPoints().get(1));
        for(Point point: adjusted.getPoints()){
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
        points.sort(Comparator.comparing(this::determineAngle));
        Point[] input = new Point[4];
        return (Square)(new Square(points.toArray(input))).translateBy(center.x, center.y);
    }

    private double determineAngle(Point p){
        double angle = Math.atan(p.y/p.x);
        if(p.y < 0 && p.x < 0){
            angle += Math.PI;
        }
        else if(angle < 0){
            if(p.y < 0) {
                angle += 2 * Math.PI;
            }
            else{
                angle += Math.PI;
            }
        }
//        System.out.println(angle);
        return angle;
    }

    private double[] adjustCoords(Point p, Point center){
        double[] adjusted = {p.x - center.x, p.y - center.y};
        return adjusted;
    }
}
