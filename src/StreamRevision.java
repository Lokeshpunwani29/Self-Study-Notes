import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamRevision {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(3, 4, 7, 2, 4, 8, 6, 5, 10, 36, 12));

        list = list.stream()
                .sorted(
                        Comparator.comparing((Integer n) -> n % 2)
                                .thenComparing(n -> n)
                )
                .collect(Collectors.toList());

        System.out.print("\n\n");
        list.forEach(System.out::println);

        list = list.stream().map((Integer n) -> n * n).collect(Collectors.toList());

        System.out.print("\n\n");
        list.forEach(System.out::println);

        list = list.stream().sorted(Comparator.comparing((Integer n) -> n).reversed()).collect(Collectors.toList());

        System.out.print("\n\n");
        list.forEach(System.out::println);

        list.add(1);
        System.out.print("\n\n");
        list.forEach(System.out::println);

//        list.stream()
//                .sorted((a, b) -> {
//                    if (a % 2 == 0 && b % 2 != 0) return -1; // a even, b odd
//                    if (a % 2 != 0 && b % 2 == 0) return 1;  // a odd, b even
//                    return Integer.compare(a, b);           // both same type
//                })
//                .forEach(System.out::println);
//        //list.forEach(System.out::println);
//    }
    }
}
