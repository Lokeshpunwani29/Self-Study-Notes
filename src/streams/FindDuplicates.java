package streams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FindDuplicates {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,2,3,4,2,3,5,7,8,9);

        Set<Integer> seen = new HashSet<>();

        Set<Integer> duplicates = list.stream().filter(e-> !seen.add(e)).collect(Collectors.toSet());

        System.out.println("Duplicates " + duplicates);
    }


}
