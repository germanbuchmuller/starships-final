package misc.utils;

public class MyRandomGenerator implements Random{
    @Override
    public int get(int min, int max) {
        java.util.Random r = new java.util.Random();
        return r.nextInt(max-min) + min;
    }
}
