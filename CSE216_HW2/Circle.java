public class Circle implements Shape{
    private Point point;
    private double radius;
    public Circle(Point point, double radius){
        this.point = point;
        this.radius = radius;
    }
    public Point center(){
        return point;
    }
    public double area(){
        return Math.PI * Math.pow(radius, 2);
    }
}