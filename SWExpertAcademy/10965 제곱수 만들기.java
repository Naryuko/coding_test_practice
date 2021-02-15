import java.util.LinkedList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Queue;

public class Solution {
    public static void main(String[] arg) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < T; i++) {
            int temp = sc.nextInt();
            int sum = check(temp);

            System.out.println(String.format("#%d %d",i+1,sum));
        }
    }

    public static int check(int value) {
        int sum = 1;
        for (int i = 2; i*i <= value; i++) {
            int j = 0;
            while (value%i == 0) {
                value /= i;
                j++;
            }
            if (j%2 != 0) {
                sum *= i;
            }
        }
        if (value != 1) {
            sum *= value;
        }

        return sum;
    }
}
