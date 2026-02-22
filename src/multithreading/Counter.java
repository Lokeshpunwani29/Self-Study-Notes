package multithreading;

public class Counter {
    public static int count = 1000;

     public static void increment(){
        count+=10;
    }
    public static void decrement(){
        count-=10;
    }
}
