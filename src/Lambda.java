import java.util.*;

public class Lambda {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>(Arrays.asList("Lokesh","Aman","Jayesh"));

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        names.forEach(System.out::println);

        Collections.sort(names, (s1,s2) -> {return s1.compareTo(s2);});

        names.forEach(System.out::println);

        Collections.sort(names, String::compareTo);

        names.forEach(System.out::println);

        }
    }
