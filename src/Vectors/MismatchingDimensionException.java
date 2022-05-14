package Vectors;

public class MismatchingDimensionException extends RuntimeException{
    public MismatchingDimensionException(){
        super();
    }

    public MismatchingDimensionException(String message){
        super(message);
    }
}
