public class Rectangle implements Shape{
    private Point point;
    private Point second;
    public Rectangle(Point point, Point second) {
        this.point = point;
        this.second = second;
    }
    public Point center(){
        double midX = point.getX()/2 + second.getX()/2;
        double midY = point.getY()/2 + second.getY()/2;
        return new Point(midX,midY);
    }
    public double area(){
        double width = point.getX()-second.getX();
        double height = point.getY()-second.getY();
        return width*height;
    }
}