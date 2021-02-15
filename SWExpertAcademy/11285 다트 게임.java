import java.util.Scanner;

public class Solution {
    public static void main(String[] arg) throws Exception {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < T; i++) {
            int t = sc.nextInt();
            sc.nextLine();
            int sum = 0;
            for (int j = 0; j < t; j++) {
                String[] temp = sc.nextLine().split(" ");
                int a = Integer.valueOf(temp[0]);
                int b = Integer.valueOf(temp[1]);
                sum += check(a,b);
            }

            System.out.println(String.format("#%d %d",i+1,sum));
        }
    }

    public static int check (int a, int b) {
        int ret = 0;
        int R = a*a + b*b;
        for (int i = 1; i <= 10; i++) {
            if (20*20*(10-i)*(10-i) < R && R <= 20*20*(11-i)*(11-i)) {
                ret = i;
            }
        }
        if (a == 0 && b == 0) {
            ret = 10;
        }
        return ret;
    }
}
