package streams;

import java.util.ArrayList;
import java.util.List;

public class EvenOddSum {

    public static void main(String args[]){

        List<Integer> list = new ArrayList<>(List.of(1,2,3,4,5,6,7,8,9,10,11,12,13));

        int evenSum = list.stream().filter(n -> n%2==0).mapToInt(Integer::intValue).sum();

        int oddSum = list.stream().filter(n -> n%2!=0).mapToInt(Integer::intValue).sum();

        System.out.println(evenSum);
        System.out.println(oddSum
        );

    }


}
