public class Point implements Shape{
    private Point point;
    private double x;
    private double y;
    public Point(double x, double y){
        this.x =x;
        this.y =y;
        this.point = this;
    }
    public Point center(){
        return point;
    }
    public double area(){
        return 0;
    }
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }

    @Override
    public String toString(){
        return "(" + Math.round(x*100)/100.0 + ", " + Math.round(y*100)/100.0 + ")";
    }
}