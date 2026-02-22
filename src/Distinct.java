import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Distinct {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(List.of(75,65,55,15,20,78,90,65));

        list.stream()
                .distinct().filter(num -> num > 50)
                .map(num -> num * num)
                .sorted(Comparator.comparing((Integer n) -> n))
                .forEach(System.out::println);
    }
}
