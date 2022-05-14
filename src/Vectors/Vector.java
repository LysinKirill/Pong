package Vectors;

public abstract class Vector {
    protected int dimension;
    protected double[] coords;

    public abstract Vector inv();
    public abstract Vector toUnitVector();
    public abstract double length();
    public abstract double lengthSqr();
    public void mult(double k){
        for(int i = 0; i < dimension; i++){
            this.coords[i] *= k;
        }
    }

    public void add(Vector vector){
        if(vector.getDimension() != this.dimension){
            throw new MismatchingDimensionException("Cannot add vectors with mismatching dimensions");
        }
        for(int i = 0; i < this.dimension; i++){
            this.coords[i] += vector.coords[i];
        }
    }

    public static Vector sum(Vector vec1, Vector vec2){
        if(vec1.getDimension() != vec2.getDimension()){
            throw new MismatchingDimensionException("Cannot add vectors with mismatching dimensions");
        }
        /*int n = vec1.getDimension();
        if(n == 2){
            return new Vectors.Vec2d(vec1.coords[0] + vec2.coords[0], vec1.coords[1] + vec2.coords[1]);
        }
        if(n == 3){
            return new Vectors.Vec3d(vec1.coords[0] + vec2.coords[0], vec1.coords[1] + vec2.coords[1], vec1.coords[2] + vec2.coords[2]);
        }
        return new Vectors.Vec2d();*/
        return VecN.sum(vec1.toVecN(), vec2.toVecN()).simplify();
    }

    public abstract VecN toVecN();
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder(this.getClass() + " = (").delete(0, 6);
        for(int i = 0; i < dimension; i++){
            if(i != 0){
                s.append(";");
            }
            s.append(coords[i]);
        }
        s.append(")");
        return s.toString();
    }
    public int getDimension(){return dimension;};
}
