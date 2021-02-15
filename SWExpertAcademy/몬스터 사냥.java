import java.util.Scanner;

public class Solution {
    public static void main(String[] arg) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        sc.nextLine();
        String[] arr = new String[T];
        for (int i = 0; i < T; i++) {
            arr[i] = sc.nextLine();
        }

        for (int i = 0; i < arr.length; i++) {
            String[] temp2 = arr[i].split(" ");
            int[] temp = new int[temp2.length];
            for (int j = 0; j < temp.length; j++) {
                temp[j] = Integer.valueOf(temp2[j]);
            }

            int D = temp[0];
            double L = temp[1];
            int N = temp[2];
            int sum = 0;
            for (int n = 0; n < N; n++) {
                sum += (int) Math.round(D*(1+n*L/100));
            }
            System.out.println(String.format("#%d %d", i+1, sum));
        }
    }
}
