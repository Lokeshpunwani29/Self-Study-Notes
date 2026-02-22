package stringmanipulation;

public class Pallindrome {

    public static boolean checkPalindrome(String string){
        String clean = string.toLowerCase().replaceAll("[^a-z0-9]","");
        String rev = new StringBuilder(clean).reverse().toString();
        return clean.equals(rev);

    }

    public static boolean checkPalindromeUsingArr(String string){

        String clean = string.toLowerCase();
        String rev = "";

        for( int i = clean.length() - 1; i >=0 ; i--) {
            rev = rev + clean.charAt(i);
        }

        return clean.equals(rev);
    }


    public static void main(String[] args) {

        String str = "rota tor  ";
        if (checkPalindrome(str))
        System.out.println("Palindrome :)");
        else System.out.println("Not Palindrome :(");

        if (checkPalindromeUsingArr(str))
            System.out.println("Palindrome :)");
        else System.out.println("Not Palindrome :(");
    }
}
