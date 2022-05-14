package Vectors;

public class Vec3d extends Vector {
    @Override
    public Vec3d inv(){
        Vec3d vec = new Vec3d(this.coords.clone());
        vec.mult(-1);
        return vec;
    }
    @Override
    public VecN toVecN(){
        return new VecN(this.coords);
    }
    @Override
    public Vec3d toUnitVector() {
        return Vec3d.mult(this, 1/this.length());
    }

    @Override
    public double length() {
        return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
    }

    @Override
    public double lengthSqr() {
        return (getX()*getX() + getY()*getY() + getZ()*getZ());
    }



    public static Vec3d mult(Vec3d vec, double k){
        return new Vec3d(vec.getX()*k, vec.getY()*k, vec.getZ()*k);
    }
    public static Vec3d sum(Vec3d vec1, Vec3d vec2) {
        return new Vec3d(vec1.getX() + vec2.getX(), vec1.getY() + vec2.getY(), vec1.getZ() + vec2.getZ());
    }

    public double getX(){ return this.coords[0]; }
    public double getY(){ return this.coords[1]; }
    public double getZ(){ return this.coords[2]; }
    public void setX(double x){ this.coords[0] = x; }
    public void setY(double y){ this.coords[1] = y; }
    public void setZ(double z){ this.coords[2] = z; }


    // CONSTRUCTORS
    public Vec3d(){
        this.dimension = 3;
        this.coords = new double[]{0, 0, 0};
    }
    public Vec3d(double x, double y, double z){
        this.dimension = 3;
        this.coords = new double[]{x, y, z};
    }
    public Vec3d(double[] coords){
        if(coords.length != 3){
            throw new MismatchingDimensionException("Cannot create a Vectors.Vec3d instance from an array with length " + coords.length);
        }
        this.dimension = 3;
        this.coords = coords;
    }
}
