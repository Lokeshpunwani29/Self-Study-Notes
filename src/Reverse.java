public interface Reverse {
    abstract int rev(int a);

    default void msg(){
        System.out.println("Reversing the Integer...");
    }
}
