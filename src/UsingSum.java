public class UsingSum {
    public static void main(String[] args) {
        Sum sum = (a,b) -> a + b;
        sum.message();
        System.out.println(sum.add(4,5));
    }
}
