// int[][]는 되지만 LinkedList<Integer>[], ArrayList<Integer>[]로 circle을 구현하면 틀린다. 이유가 뭘까?

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int N;
    static int M;
    static int T;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        M = Integer.parseInt(tk.nextToken());
        T = Integer.parseInt(tk.nextToken());

        int[][] circle = new int[N][M];
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                circle[i][j] = Integer.parseInt(tk.nextToken());
            }
        }

        int[][] sequence = new int[T][3];
        for (int i = 0; i < T; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < 3; j++) {
                sequence[i][j] = Integer.parseInt(tk.nextToken());
            }
        }

        for (int i = 0; i < T; i++) {
            move(circle, sequence[i]);
            if (!check(circle)) {
                cal(circle);
            }
        }
        int sum = getSum(circle);

        bw.write(String.valueOf(sum));
        bw.close();
        br.close();

    }

    static int getSum (int[][] circle) {
        int sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int temp = circle[i][j];
                if (temp != -1) {
                    sum += temp;
                }
            }
        }
        return sum;
    }

    static void move (int[][] circle, int[] seq) {
        int x = seq[0];
        int d = seq[1];
        int k = seq[2];
        for (int i = x-1; i < N; i += x) {
            for (int j = 0; j < k; j++) {
                // 0 시계방향, 1 반시계방향
                if (d == 0) {
                    int temp = circle[i][M-1];
                    int[] tt = circle[i].clone();
                    circle[i][0] = temp;
                    for (int r = 1; r < M; r++) {
                        circle[i][r] = tt[r-1];
                    }
                } else if (d == 1) {
                    int temp = circle[i][0];
                    int[] tt = circle[i].clone();
                    circle[i][M-1] = temp;
                    for (int r = 0; r < M-1; r++) {
                        circle[i][r] = tt[r+1];
                    }
                }
            }
        }
    }

    static void cal (int[][] circle) {
        int sum = 0; // -1이 아닌 수들의 합
        int num = 0; // -1이 아닌 수들의 개수
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int temp = circle[i][j];
                if (temp != -1) {
                    sum += temp;
                    num++;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int temp = circle[i][j];
                if (temp != -1 && temp*num < sum) {
                    circle[i][j]++;
                } else if (temp != -1 && temp*num > sum) {
                    circle[i][j]--;
                }
            }
        }

    }

    // -1로 지워지는 값이 있다면 true, 없다면 false 반환
    static boolean check (int[][] circle) {
        boolean ret = false;
        boolean[][] checkBoard = new boolean[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int temp = circle[i][j];

                if (temp == -1) {
                    continue;
                }

                if (temp == circle[i][(j+1)%M]) {
                    checkBoard[i][j] = true;
                    checkBoard[i][(j+1)%M] = true;
                    ret = true;
                }

                if (temp == circle[i][j-1 < 0 ? j+M-1:j-1]) {
                    checkBoard[i][j] = true;
                    checkBoard[i][j-1 < 0 ? j+M-1:j-1] = true;
                    ret = true;
                }

                if (i > 0) {
                    if (circle[i][j] == circle[i-1][j]) {
                        checkBoard[i][j] = true;
                        checkBoard[i-1][j] = true;
                        ret = true;
                    }
                }
            }
        }

        // ret == false 라면 false 반환
        if (!ret) {
            return false;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (checkBoard[i][j]) {
                    circle[i][j] = -1;
                }
            }
        }
        return true;
    }

}
