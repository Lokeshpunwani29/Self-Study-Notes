package stringmanipulation;

import java.util.Scanner;

public class StringOperations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString;
        int[] inputArray;
        int element;
        int threshold = 5;

        // Take user input for the string
        System.out.print("Enter a string: ");
        inputString = scanner.nextLine();

        // Take user input for the array
        System.out.print("Enter the size of the array: ");
        int size = scanner.nextInt();
        inputArray = new int[size];
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i< size; i++) {
            inputArray[i] = scanner.nextInt();
        }

        // Take user input for the element to find
        System.out.print("Enter the element to find: ");
        element = scanner.nextInt();

        // Menu for user to choose the case
        int choice;
        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Replace every second character in each word of a string with 'x'");
            System.out.println("2. Find the sum of the index positions of all occurrences of a given element in an array");
            System.out.println("3. Replace every vowel in the string with its lowercase counterpart");
            System.out.println("4. Return the modified string and the sum of index positions of the element");
            System.out.println("5. Return the string in uppercase if the sum is greater than a threshold value");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            switch(choice){
                case 1 : FirstChoice(inputString);
                case 2 : SecondChoice(inputArray, element);
                case 3 : ThirdChoice(inputString);
                case 4 : FourthChoice(inputString);
                case 5 : FifthChoice(inputString);
                //default: System.exit(0);
            }
        }  while (true);
    }

    public static void FirstChoice(String str){

        str = str.trim();
        String[] words = str.split("\\s+");
        for (int i = 0; i < words.length; i++){
            char[] tempWord = words[i].toCharArray();
            tempWord[1] = 'x';
            words[i] = new String(tempWord);
        }
        String res =  String.join(" ",words);
        System.out.println(res);
    }

    public static void SecondChoice(int[] arr, int element){
        int sum = 0;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i] == element) sum += i;
        }
        System.out.println("Sum of occurence index of " + element + " is " + sum);
    }

    public static void ThirdChoice(String str){

        str = str.trim();
        String[] words = str.split("\\s+");
        for (int i = 0; i < words.length; i++){
            char[] tempWord = words[i].toCharArray();
            for (int j =0; j < tempWord.length; j++){
                if (tempWord[j] == 'A' || tempWord[j] == 'E' || tempWord[j] == 'I' || tempWord[j] == 'O' || tempWord[j] == 'U' ) {
                    tempWord[j] += 32;
                }
            }
            words[i] = new String(tempWord);
        }
        String res =  String.join(" ",words);
        System.out.println(res);

    }

    public static void FourthChoice(String str){

    }

    public static void FifthChoice(String str){

    }

}