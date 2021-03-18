import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    static int N;
    static int M;
    static int max = 0;
    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};
    static int zeroNum = 0;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(tk.nextToken());
        M = Integer.parseInt(tk.nextToken());
        int[][] board = new int[N][M];

        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int t = Integer.parseInt(tk.nextToken());
                if (t == 0) {
                    zeroNum++;
                }
                board[i][j] = t;
            }
        }

        go(board, 0, 0, 0);

        bw.write(Integer.toString(max));
        bw.flush();
        bw.close();
        br.close();
    }

    public static void go (int[][] board, int i, int j, int count) {
        if (count == 3) {
            check(board);
            return;
        }
        if (i >= N) {
            return;
        }

        int jj = j+1;
        int ii = i;
        if (jj >= M) {
            jj = 0;
            ii++;
        }

        if (board[i][j] == 0) {
            int[][] temp = new int[N][M];
            for (int p = 0; p < N; p++) {
                temp[p] = board[p].clone();
            }
            temp[i][j] = 1;
            go(temp, ii, jj, count+1);
        }
        go(board, ii, jj, count);

    }

    public static void check (int[][] board) {
        int num = zeroNum - 3;
        int[][] tempBoard = new int[N][M];
        for (int i = 0; i < N; i++) {
            tempBoard[i] = board[i].clone();
        }
        boolean[][] checkBoard = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (tempBoard[i][j] == 2 && !checkBoard[i][j]) {
                    num -= bfs(tempBoard, checkBoard, i, j);
                }
            }
        }
        if (num > max) {
            max = num;
        }
    }

    public static int bfs (int[][] board, boolean[][] checkBoard, int row, int col) {
        int num = 0;
        checkBoard[row][col] = true;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {row, col});

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            for (int i = 0; i < 4; i++) {
                int tRow = temp[0] + dRow[i];
                int tCol = temp[1] + dCol[i];
                if (tRow < 0 || tCol < 0 || tRow >= N || tCol >= M) {
                    continue;
                }

                if (board[tRow][tCol] == 0) {
                    board[tRow][tCol] = 2;
                    num++;
                    checkBoard[tRow][tCol] = true;
                    queue.add(new int[] {tRow, tCol});
                }
            }
        }
        return num;
    }



}
