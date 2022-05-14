package Vectors;

public class VecN extends Vector {
    @Override
    public VecN inv(){
        VecN vec = new VecN(this.coords.clone());
        vec.mult(-1);
        return vec;
    }
    @Override
    public VecN toVecN(){return this;}
    @Override
    public VecN toUnitVector() {
        return VecN.mult(this, 1/this.length());
    }

    @Override
    public double lengthSqr() {
        double sum = 0;
        for(int i = 0; i < this.dimension; i++){
            sum += (this.coords[i] * this.coords[i]);
        }
        return sum;
    }

    @Override
    public double length() {
        return Math.sqrt(lengthSqr());
    }

    public static VecN mult(VecN vec, double k){
        double[] coords = new double[vec.coords.length];
        for(int i = 0; i < coords.length; i++){
            coords[i] = vec.coords[i]*k;
        }
        return new VecN(coords);
    }
    public static VecN sum(VecN vec1, VecN vec2) {
        if(vec1.getDimension() != vec2.getDimension()){
            throw new MismatchingDimensionException("Cannot add vectors with mismatching dimensions");
        }
        double[] coords = new double[vec1.coords.length];
        for(int i = 0; i < coords.length; i++){
            coords[i] = vec1.coords[i] + vec2.coords[i];
        }
        return new VecN(coords);
    }

    public double getN(int n){
        return this.coords[n];
    }
    public void setN(int n, double value){
        this.coords[n] = value;
    }

    public Vector simplify(){
        if(this.dimension == 2){
            return new Vec2d(this.coords);
        }
        if(this.dimension == 3){
            return new Vec3d(this.coords);
        }
        else{
            return this;
        }
    }
    // CONSTRUCTORS
    public VecN(int n){
        this.dimension = n;
        this.coords = new double[n];
    }
    public VecN(double[] coords){
        this.dimension = coords.length;
        this.coords = coords;
    }
}
