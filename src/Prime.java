import java.util.function.Predicate;

public class Prime {

    public static void main(String[] args) {

        Predicate<Integer> checkPrime = new Predicate<Integer>() {
            @Override
            public boolean test(Integer num) {
                if (num < 2) return false;
                else if (num == 2) return true;
                else if (num % 2 ==0) return false;
                else {
                    for(int i = 3; i <= Math.sqrt(num); i+=2){  // i < sqrt(num)
                        if (num % i == 0) return false;
                    }
                }
                return true;
            }
        };

        if (checkPrime.test(17)) System.out.println("Prime :)");
        else System.out.println("Not Prime :(");

    }
}
