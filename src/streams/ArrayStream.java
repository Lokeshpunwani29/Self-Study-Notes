package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class ArrayStream {

    public static void main(String[] args) {

        int[] arr = new int[]{1,2,3,4,45,5,6,7,8};

        int[] rev = IntStream.of(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .toArray();

        System.out.println(Arrays.toString(rev));
    }
}
