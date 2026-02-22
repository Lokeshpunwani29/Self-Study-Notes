public class MissingNumber {

    public static void main(String[] args) {
        int [] arr = new int[]{1,2,3,4,5,6,7,8,9,10};
        int n = arr.length;
        int expected = (n * (n + 1)) / 2;
        int actual = 0;
        for(int i : arr){
            actual += i;
        }

        System.out.println("Missing Number is -> " + (expected - actual));
    }
}
