import Vectors.Vec2d;

public class Wall extends Box{
    public Wall(Vec2d pos, double speedValue){
        super(new Vec2d(pos.getX()*Main.wUnit, pos.getY()*Main.hUnit), speedValue, 30*Main.unit, 30*Main.unit, new Vec2d[]{});
    }
    public Wall(Vec2d pos, double speedValue, Vec2d[] route){
        super(new Vec2d(pos.getX()*Main.wUnit, pos.getY()*Main.hUnit), speedValue, 30*Main.unit, 30*Main.unit, route);
    }

    public Wall(double x, double y, double speedValue){
        super(new Vec2d(x*Main.wUnit, y*Main.hUnit), speedValue, 30*Main.unit, 30*Main.unit, new Vec2d[]{});
    }
    public Wall(double x, double y, double speedValue, Vec2d[] route){
        super(new Vec2d(x*Main.wUnit, y*Main.hUnit), speedValue, 30*Main.unit, 30*Main.unit, route);
    }
}
