import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployList {

    public static void main(String[] args) {
        List<Employ> list = new ArrayList<>();

        list.add(new Employ(60,"Lokesh",60000));
        list.add(new Employ(45,"Rohit",55000));
        list.add(new Employ(35,"Mayur",75000));
        list.add(new Employ(27,"Himesh",40000));
        list.add(new Employ(1,"Devansh",65000));

        System.out.println("\n-------------------Id-Name-Wise----------------\n");
        list.stream().sorted(Comparator.comparing((Employ employ) -> employ.id).thenComparing((Employ employ) -> employ.name).reversed()).forEach(System.out::println);

        System.out.println("\n-------------------Name-Wise----------------\n");


        ArrayList<Employ> newList = (ArrayList<Employ>) list.stream().sorted(Comparator.comparing(employ -> employ.name)).collect(Collectors.toList());

        System.out.println(newList);

        System.out.println("\n-------------------Salary Wise----------------\n");
        list.stream().max(Comparator.comparing(employ -> employ.basic)).ifPresent(System.out::println);

        System.out.println("\n-------------------Increment by 10%----------------\n");
        list.stream().map(employ -> {
            employ.basic = employ.basic + (employ.basic / 10);
            return employ;
        }).forEach(System.out::println);

        System.out.println("\n---------------------Min Salary-------------");
        list.stream().min(Comparator.comparing(employ -> employ.basic)).ifPresent(System.out::println);
    }
}
