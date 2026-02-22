@FunctionalInterface
public interface Sum {
    abstract public int add(int a, int b);

    default void message(){
        System.out.println("Doing Addition");
    }
}

