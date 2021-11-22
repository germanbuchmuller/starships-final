package utils;

public class Random {

    public static int get(int min, int max){
        java.util.Random r = new java.util.Random();
        return r.nextInt(max-min) + min;
    }
}
