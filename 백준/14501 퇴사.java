import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int[] T;
    static int[] P;
    static int max = 0;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(tk.nextToken());
        T = new int[N+1];
        P = new int[N+1];

        for (int i = 1; i <= N; i++) {
            tk = new StringTokenizer(br.readLine());
            T[i] = Integer.parseInt(tk.nextToken());
            P[i] = Integer.parseInt(tk.nextToken());
        }

        back(0, 0,1, 1);
        bw.write(String.valueOf(max));
        bw.flush();
        bw.close();
        br.close();
    }

    public static void back(int tempSum, int pastP, int endTime, int current) {
        if (endTime == N + 1) {
            if (tempSum + pastP > max) {
                max = tempSum + pastP;
                return;
            }
        }
        if (endTime > N || current > N) {
            if (tempSum > max) {
                max = tempSum;
            }
            return;
        }

        tempSum += pastP;

        if (endTime > current) {
            back(tempSum, 0, endTime, current+1);
        } else {
            back(tempSum, P[current],current+T[current], current+1);
            back(tempSum,0, current+1, current+1);
        }

    }



}
