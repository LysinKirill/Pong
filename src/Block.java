import Vectors.Vec2d;

import java.awt.*;

abstract public class Block {
    Vec2d pos;  //??????
    //Vec2d speed;
    double speedValue;
    Shape shape;
    Vec2d[] route;
    int step = 0;


    abstract public Vec2d getN(Vec2d point);
    abstract public Vec2d getIntersection(Vec2d line);
    abstract public void move();
    abstract public void move(Vec2d speed);
    abstract public void move(double x, double y);
    abstract public void moveTo(Vec2d speed);
    abstract public void moveTo(double x, double y);
    abstract public Vec2d getIntersection(Ball ball);
    abstract public void bounce(Ball ball);
    abstract public void scale(double k);
}
