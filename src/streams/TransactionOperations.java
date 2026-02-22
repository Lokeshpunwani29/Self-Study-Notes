package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TransactionOperations {

    public static void main(String[] args) {
        List<Transaction> transactions = Arrays.asList(
                new Transaction(1, 100, "GROCERY"),
                new Transaction(3, 80, "GROCERY"),
                new Transaction(6, 120, "GROCERY"),
                new Transaction(7, 40, "ELECTRONICS"),
                new Transaction(10, 50, "GROCERY")
        );

        System.out.println("\n--------Increase Value by 10%-----------\n");

        transactions.stream().map((Transaction t) -> t.getValue() + t.getValue()/10).forEach(System.out::println);

        System.out.println("\n--------Sort By ID-----------\n");

        transactions.stream().sorted(Comparator.comparing(Transaction::getId)).forEach(System.out::println);

        System.out.println("\n--------Delete Duplicate types-----------\n");

        transactions.stream().map(Transaction::getType).distinct().forEach(System.out::println);

    }



}
