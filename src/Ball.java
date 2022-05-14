import Vectors.Vec2d;

import java.awt.*;

public class Ball {
    //double maxSpeed = 50;
    //double decelerationRate = 0.0001;
    Color color;
    Shape shape;
    Vec2d pos;
    Vec2d speed;
    double r;

    public Ball(Vec2d pos, Vec2d speed, double r, Shape shape){
        this.shape = shape;
        this.r = r;
        this.speed = speed;
        this.pos = pos;
        this.color = new Color(168, 255, 98);
    }
    public Ball(Vec2d pos, Vec2d speed, double r){
        this.r = r;
        this.speed = speed;
        this.pos = pos;
        this.shape = Main.getCircle(pos, r);
        this.color = new Color(168, 255, 98);
    }
    public Ball(Vec2d pos, double r){
        this.shape = Main.getCircle(pos, r);
        this.r = r;
        this.speed = new Vec2d();
        this.pos = pos;
        this.color = new Color(168, 255, 98);
    }





    void decelerate(double x, double y, double angle){
        this.speed.setX(speed.getX() * x);
        this.speed.setY(speed.getY() * y);
    }

    public void move(){
        this.pos.add(this.speed);
        update();
    }
    public void move(Vec2d shift){
        this.pos.add(shift);
        update();
    }
    public void move(double xShift, double yShift){
        this.pos.add(new Vec2d(xShift, yShift));
        update();
    }

    public void update(){
        this.shape = Main.getCircle(pos, r);
    }
}
