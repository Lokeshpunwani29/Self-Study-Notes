public class CountChar {

    public static void main(String[] args) {
        String input = "Programming";
        String str = input.trim().toLowerCase();

        int [] freq = new int[256];

        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            freq[c]++;
        }

        for (int i = 0; i < 256; i++)
        {
            if (freq[i] > 0){
                System.out.println( (char) i + " -> " + freq[i]);
            }
            else continue;
        }
    }
}
