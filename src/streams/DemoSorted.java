package streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DemoSorted {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(List.of(1,2,3,4,5,67,8,9));

        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);

        StringBuilder sb = new StringBuilder("Hello");
        sb.append(" World");

        System.out.println(sb);
    }
}
