import java.util.function.*;

public class UsingReverse {

    public static void main(String[] args) {

        Reverse reverse = a -> {
            int revInt = 0;
            for(int i=0; a > 0; i++){
                revInt = (revInt * 10) + a%10;
                a/=10;
            }
            return revInt;
        };

        System.out.println(reverse.rev(456));

        Predicate<Integer> predicate = a -> a > 50;
        System.out.println(predicate.test(45));

        Consumer<String> consumer = str -> System.out.println("Hello, " + str);
        consumer.accept("Lokesh Punwani");

        Supplier<Integer> supplier = () -> (int) (Math.random() * 100);
        System.out.println(supplier.get());

        BiFunction<Integer, Integer, Integer> biFunction = (x,y) -> x - y;
        System.out.println(biFunction.apply(50,25));

        Function<String,Integer> function = str -> str.length();
        System.out.println("Java");
    }
}
