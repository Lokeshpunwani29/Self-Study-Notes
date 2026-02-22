import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListofPages {

    public static int totalPages(List<Integer> pages){
        if (pages == null || pages.isEmpty()) {
            return 0;
        }

        return pages.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

    }

    public static void main(String[] args) {

        List<Integer> pages = new ArrayList<>();
        pages.add(10);
        pages.add(20);
        pages.add(30);
        pages.add(null);
        pages.add(40);
        System.out.println(totalPages(pages));

    }
}
