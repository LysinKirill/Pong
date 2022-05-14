package Vectors;

import java.awt.*;

public class Vec2d extends Vector {
    public static double getDistSqr(double x1, double y1, double x2, double y2) {
        return (x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1);
    }

    @Override
    public Vec2d inv(){
        Vec2d vec = new Vec2d(this.coords.clone());
        vec.mult(-1);
        return vec;
    }
    @Override
    public VecN toVecN(){
        return new VecN(this.coords);
    }
    @Override
    public Vec2d toUnitVector() {
        if((this.getY() == 0) && (this.getX() == 0)){
            return this;
        }
        return Vec2d.mult(this, 1/this.length());
    }

    @Override
    public double length() {
        return Math.sqrt(getX()*getX() + getY()*getY());
    }

    @Override
    public double lengthSqr(){    //   Faster than length()
        return getX()*getX() + getY()*getY();
    }

    public static double dotProduct(Vec2d vec1, Vec2d vec2){
        return (vec1.getX()*vec2.getX() + vec1.getY()*vec2.getY());
    }

    public static Vec3d crossProduct(Vec2d vec1, Vec2d vec2){
        return new Vec3d(0, 0, vec1.getX() * vec2.getY() - vec1.getY()*vec2.getX());
    }
    public static Vec2d mult(Vec2d vec, double k){
        return new Vec2d(vec.getX()*k, vec.getY()*k);
    }
    public static Vec2d sum(Vec2d vec1, Vec2d vec2) {
        return new Vec2d(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY());
    }

    public void draw(Graphics2D g2d, double x, double y, double scale){
        g2d.drawLine((int)x, (int)y, (int)(x + this.getX()*scale), (int)(y + this.getY()*scale));
        Vec2d temp = Vec2d.mult(Vec2d.getRotated(this.toUnitVector().inv(), Math.PI / 8f), 15);
        g2d.drawLine((int)Math.round(x + this.getX()*scale), (int)Math.round(y + this.getY()*scale), (int)Math.round(x + this.getX()*scale + temp.getX()), (int)Math.round(y + this.getY()*scale + temp.getY()));
        temp = Vec2d.getRotated(temp, -Math.PI/4f);
        g2d.drawLine((int)Math.round(x + this.getX()*scale), (int)Math.round(y + this.getY()*scale), (int)Math.round(x + this.getX()*scale + temp.getX()), (int)Math.round(y + this.getY() *scale+ temp.getY()));

        //g2d.drawLine((int) Math.round(end.pos.getX()), (int)Math.round(end.pos.getY()), (int)Math.round(end.pos.getX() + temp.getX()), (int)Math.round(end.pos.getY() + temp.getY()));
    }

    public double getX(){
        return this.coords[0];
    }
    public double getY(){
        return this.coords[1];
    }
    public void setX(double x){
        this.coords[0] = x;
    }
    public void setY(double y){
        this.coords[1] = y;
    }

    // CONSTRUCTORS
    public Vec2d(){
        this.dimension = 2;
        this.coords = new double[]{0, 0};
    }
    public Vec2d(double x, double y){
        this.dimension = 2;
        this.coords = new double[]{x, y};
    }
    public Vec2d(double[] coords){
        if(coords.length != 2){
            throw new MismatchingDimensionException("Cannot create a Vectors.Vec2d instance from an array with length " + coords.length);
        }
        this.dimension = 2;
        this.coords = coords;
    }

    public static Vec2d getRotated(Vec2d vec, double angle){
        return new Vec2d(vec.getX()*Math.cos(angle) - vec.getY()*Math.sin(angle),  vec.getX() * Math.sin(angle) + vec.getY()*Math.cos(angle));
    }

    public static boolean equals(Vec2d a, Vec2d b){
        return (a.getX() == b.getX()) && (a.getY() == b.getY());
    }

    public static double getDist(Vec2d l1, Vec2d l2, Vec2d p){
        return Math.sqrt(getDistSqr(l1, l2, p));
    }

    public static double getDistSqr(double l1x, double l1y, double l2x, double l2y, double px, double py){
        return getDistSqr(new Vec2d(l1x, l1y), new Vec2d(l2x, l2y), new Vec2d(px, py));
    }

    public static double getDist(double l1x, double l1y, double l2x, double l2y, double px, double py){
        return getDist(new Vec2d(l1x, l1y), new Vec2d(l2x, l2y), new Vec2d(px, py));
    }

    public static double getDistSqr(Vec2d l1, Vec2d l2, Vec2d p){
        if(l1.getX() == l2.getX()){
            return Math.abs(p.getX() - l1.getX());
        }
        double k = (l2.getY() - l1.getY())/(l2.getX() - l1.getX());
        double b = l1.getY() - k*l1.getX();
        return (((-k)*p.getX() + p.getY() - b)*((-k)*p.getX() + p.getY() - b)/(k*k + 1));
    }


    public Vec2d getBisector(Vec2d vec1, Vec2d vec2, double length){
       return  Vec2d.mult(Vec2d.sum(vec1.toUnitVector(), vec2.toUnitVector()).toUnitVector(), length);
    }
}
