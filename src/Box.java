import Vectors.Vec2d;

import java.awt.*;

public class Box extends Block{
    double[] xs;
    double[] ys;
    Vec2d speed = new Vec2d();
    //double speedValue; // Какого амогуса я дважды задаю скорость по-разному????
    @Override
    public Vec2d getN(Vec2d point) {
        return null;
    }


    public Vec2d getN(int i1, int i2){
        if(this.xs[i1] == this.xs[i2]){
            return new Vec2d(1, 0);
        }
        if(this.ys[i1] == this.ys[i2]){
            return new Vec2d(0, 1);
        }
        return new Vec2d(1, ((xs[i1] - xs[i2])/(ys[i2] - ys[i1]))).toUnitVector();
    }
    @Override
    public Vec2d getIntersection(Vec2d line) {
        return null;
    }

    @Override
    public Vec2d getIntersection(Ball ball) {
        return null;
    }
    @Override
    public void scale(double k){
        for(int i = 0; i < xs.length; i++){
            xs[i] = pos.getX() + (xs[i] - pos.getX()) * k;
            ys[i] = pos.getY() + (ys[i] - pos.getY()) * k;
        }
        this.update();
    }

    public void bounce(Ball ball){
        for(int i = 0; i < xs.length; i++){
            double x1, x2, y1, y2, k, b, x0, y0, r;
            x0 = ball.pos.getX();
            y0 = ball.pos.getY();
            r = ball.r;
            int ind1 = i % xs.length;
            int ind2 = (i + 1)%xs.length;


            x1 = xs[ind1];
            y1 = ys[ind1];
            x2 = xs[ind2];
            y2 = ys[ind2];


            if(x2 == x1){ //   Вертикальные отрезки (строны)
                if(Math.abs(x0 - x1) <= r){
                    if((y0 >= Math.min(y1, y2)) && (y0 <= Math.max(y1, y2))){
                        //ball.speed.setX(-ball.speed.getX() + this.speed.getX());
                        ball.speed.setX(-ball.speed.getX() + this.speed.getX()*1.1);
                        //ball.speed = Vec2d.sum(ball.speed.inv(), this.speed);
                        Main.hit = new Vec2d(x1, y0);
                        Main.dir = Vec2d.mult(getN(ind1, ind2), 20);
                        break;
                    }else if((y0 + r >= Math.min(y1, y2)) && (y0 - r <= Math.min(y1, y2))){
                        ball.speed = Vec2d.sum(Vec2d.mult(new Vec2d(x0 - x1, y0 - Math.min(y1, y2)).toUnitVector(), ball.speed.length()), this.speed);
                        Main.hit = new Vec2d(x1, Math.min(y1, y2));
                        Main.dir = new Vec2d(x0 - x1, y0 - Math.min(y1, y2));
                        break;
                    }else if(y0 - r <= Math.max(y1, y2) && (y0 + r >= Math.max(y1, y2))){
                        ball.speed = Vec2d.sum(Vec2d.mult(new Vec2d(x0 - x1, y0 - Math.max(y1, y2)).toUnitVector(), ball.speed.length()), this.speed);
                        Main.hit = new Vec2d(x1, Math.max(y1, y2));
                        Main.dir = new Vec2d(x0 - x1, y0 - Math.max(y1, y2));
                        break;
                    }
                }
                /*if((x0 - x1)*(x0 - x1) + (y0 - y1)*(y0 - y1) <= r*r){
                    double tempX = xs[Math.floorMod(i - 1, xs.length)];
                    double tempY = ys[Math.floorMod(i - 1, xs.length)];

                }*/
                continue;
            }else{
                k = (y2 - y1)/(x2 - x1);
                b = y1 - (k*x1);
            }

            double distanceSqr = Vec2d.getDistSqr(x1, y1, x2, y2, x0, y0);
            if(distanceSqr <= r*r){

                double normalX = (x0 + k*y0 - b * k)/(k*k + 1);
                if((Math.max(x1, x2) >= normalX) && (Math.min(x1, x2) <= normalX)){
                       // https://ru.wikipedia.org/wiki/Расстояние_от_точки_до_прямой_на_плоскости
                    double normalY = ((-k)*(-x0 - k * y0) + b)/(k*k + 1);
                    Vec2d normal = new Vec2d(x0 - normalX, y0 - normalY);
                    if(y2 == y1){
                        //ball.speed.setY(-ball.speed.getY() + this.speed.getY());
                        ball.speed.setY(-ball.speed.getY() + this.speed.getY());
                        //ball.speed = Vec2d.sum(new Vec2d(-ball.speed.getY(), ball.speed.getY()), this.speed);
                        Main.hit = new Vec2d(normalX, normalY);
                        Main.dir = normal;
                        break;
                    }else{
                        double angle = Math.acos(Math.abs(Vec2d.dotProduct(ball.speed, normal.toUnitVector()) / (ball.speed.length())));
                        if(Vec2d.crossProduct(ball.speed, normal).getZ() < 0){
                            ball.speed = Vec2d.sum(Vec2d.getRotated(ball.speed.inv(), 2 * angle), this.speed);
                        }else{
                            ball.speed = Vec2d.sum(Vec2d.getRotated(ball.speed.inv(), (-2) * angle), this.speed);
                        }

                        Main.dir = normal;
                    }

                    Main.hit = new Vec2d(normalX, k*normalX + b);
                    break;
                }else if(Vec2d.getDistSqr(x1, y1, x0, y0) <= r*r){
                    ball.speed = Vec2d.sum(Vec2d.mult(new Vec2d(x0 - x1, y0 - y1).toUnitVector(), ball.speed.length()), this.speed);
                    Main.hit = new Vec2d(x1, y1);
                    Main.dir = new Vec2d(x0 - x1, y0 - y1);
                    break;
                }else if(Vec2d.getDistSqr(x2, y2, x0, y0) <= r*r){
                    ball.speed = Vec2d.sum(Vec2d.mult(new Vec2d(x0 - x2, y0 - y2).toUnitVector(), ball.speed.length()), this.speed);
                    Main.hit = new Vec2d(x2, y2);
                    Main.dir = new Vec2d(x0 - x2, y0 - y2);
                    break;
                }
            }
        }
    }





    void update(){
        this.shape = new Polygon(doubleToInt(this.xs), doubleToInt(this.ys), this.xs.length);
    }

    public void moveTo(Vec2d nPos){
        move(-pos.getX() + nPos.getX(), -pos.getY() + nPos.getY());
    }
    public void moveTo(double x, double y){
        move(-pos.getX() + x, -pos.getY() + y);
    }

    @Override
    public void move(Vec2d speed){
        pos.add(speed);
        for(int i = 0; i < 8; i++){
            this.xs[i] += speed.getX();
            this.ys[i] += speed.getY();
        }
        update();
    }
    @Override
    public void move(double x, double y){
        pos.setX(pos.getX() + x);
        pos.setY(pos.getY() + y);
        for(int i = 0; i < 8; i++){
            this.xs[i] += x;
            this.ys[i] += y;
        }
        update();
    }

    @Override
    public void move(){
        followRoute();
    }

    void followRoute() {
        if (route.length == 0) {
            return;
        }
        if (((pos.getX() - route[step].getX()) * (pos.getX() - route[step].getX()) + (pos.getY() - route[step].getY()) * (pos.getY() - route[step].getY())) < (speedValue * speedValue)) {
            //pos.setX(route[step].getX());
            //pos.setY(route[step].getY());
            this.speed = Vec2d.mult(new Vec2d(route[step].getX() - pos.getX(), route[step].getY() - pos.getY()).toUnitVector(), speedValue);
            moveTo(route[step].getX(), route[step].getY());

            step += 1;
        } else {
            this.speed = Vec2d.mult(new Vec2d(route[step].getX() - pos.getX(), route[step].getY() - pos.getY()).toUnitVector(), speedValue);
            move(speed);
        }
        step %= route.length;

    }
    public Box(Vec2d pos, double speedValue, Polygon shape, Vec2d[] route){
        this.pos = pos;
        this.speedValue = speedValue;
        this.route = route;
        this.shape = shape;
        this.xs = intToDouble(shape.xpoints);
        this.ys = intToDouble(shape.ypoints);
    }



    public Box(Vec2d pos, double speedValue, double width, double height, Vec2d[] route){
        this.pos = pos;
        this.speedValue = speedValue;
        this.route = route;
        double k = 0.2;
        double p = k * (width/height);
        this.xs = new double[]{pos.getX() - width*(0.5 - k), pos.getX() + width*(0.5 - k), pos.getX() + width/2f, pos.getX() + width/2f, pos.getX() + width*(0.5 - k), pos.getX() - width*(0.5 - k), pos.getX() - width/2f, pos.getX() - width/2f};
        this.ys = new double[]{pos.getY() - height/2f, pos.getY() - height/2f, pos.getY() - height * (0.5 - p), pos.getY() + height*(0.5 - p), pos.getY() + height/2f, pos.getY() + height/2f, pos.getY() + height*(0.5 - p), pos.getY() - height*(0.5 - p)};
        int[] xCoords = doubleToInt(xs);
        int[] yCoords = doubleToInt(ys);
        this.shape = new Polygon(xCoords, yCoords, 8);
    }


    int[] doubleToInt(double[] d){
        int[] newArr = new int[d.length];
        for(int i = 0; i < d.length; i++){
            newArr[i] = (int)(d[i]);     //    Math.round()????
        }
        return newArr;
    }

    double[] intToDouble(int[] d) {
        double[] newArr = new double[d.length];
        for (int i = 0; i < d.length; i++) {
            newArr[i] = (d[i]);
        }
        return newArr;
    }
}
