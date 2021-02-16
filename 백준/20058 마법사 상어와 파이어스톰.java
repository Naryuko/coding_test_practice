import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.LinkedList;

public class Main {
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        int N = (int) Math.pow(2, Integer.parseInt(tk.nextToken()));
        int Q = Integer.parseInt(tk.nextToken());
        int[][] board = new int[N][N];
        int[] magic = new int[Q];
        int sum = 0;

        // make board
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(tk.nextToken());
                sum += board[i][j];
            }
        }

        tk = new StringTokenizer(br.readLine());
        for (int i = 0; i < Q; i++) {
            magic[i] = Integer.parseInt(tk.nextToken());
        }

        for (int i = 0; i < Q; i++) {
            int l = magic[i];
            matRot(board, l, N);
            sum = iceMelt(board, sum);
        }
        int max = check(board);
        bw.write(Integer.toString(sum));
        bw.newLine();
        bw.write(Integer.toString(max));

        bw.close();
        br.close();
    }

    public static int check(int[][]rec) {
        boolean[][] check = new boolean[rec.length][rec.length];
        int max = 0;
        for (int i = 0; i < rec.length; i++) {
            for (int j = 0; j < rec.length; j++) {
                int count = bfs(rec, check,i,j);
                if (max < count) {
                    max = count;
                }
            }
        }
        return max;
    }

    public static int bfs(int[][] rec, boolean[][] check, int i, int j) {
        if (rec[i][j] == 0 || check[i][j]) {
            return 0;
        }

        int count = 0;
        Queue<Integer[]> queue = new LinkedList<>();
        queue.add(new Integer[] {i,j});

        while (!queue.isEmpty()) {
            Integer[] temp = queue.poll();
            if (temp[0] < 0 || temp[1] < 0 || temp[0] >= rec.length || temp[1] >= rec.length) {
                continue;
            }
            if (rec[temp[0]][temp[1]] == 0 || check[temp[0]][temp[1]]) {
                continue;
            }

            check[temp[0]][temp[1]] = true;
            count++;
            for (int k = 0; k < 4; k++) {
                queue.add(new Integer[] {temp[0] + dx[k], temp[1] + dy[k]});
            }
        }
        return count;
    }

    public static int iceMelt(int[][] rec, int sum) {
        LinkedList<Integer[]> list = new LinkedList<>();
        for (int i = 0; i < rec.length; i++) {
            for (int j = 0; j < rec[0].length; j++) {
                if (rec[i][j] == 0) {
                    continue;
                }

                int count = 0;
                for (int k = 0; k < 4; k++) {
                    int nextX = i+dx[k];
                    int nextY = j+dy[k];
                    if (nextX < 0 || nextX >= rec.length || nextY < 0 || nextY >= rec.length) {
                        continue;
                    }
                    if (rec[nextX][nextY] == 0) {
                        continue;
                    }
                    count++;
                }
                if (count < 3) {
                    Integer[] temp = {i, j};
                    list.add(temp);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            sum--;
            rec[list.get(i)[0]][list.get(i)[1]]--;
        }
        return sum;
    }

    public static void matRot(int[][] rec, int L, int N) {
        int level = (int) Math.pow(2, L);
        for (int i = 0; i < N; i += level) {
            for (int j = 0; j < N; j += level) {
                int rowS = i;
                int colS = j;
                int rowE = i + level - 1;
                int colE = j + level - 1;
                rotation(rec, rowS, rowE, colS, colE);
            }
        }
    }

    public static void rotation(int[][] rec, int rowS, int rowE, int colS, int colE) {
        int[] temp = new int[rowE - rowS];

        for (int i = colS+1; i <= colE; i++) {
            temp[i-1-colS] = rec[rowS][i];
        }

        // A -> B
        for (int i = rowS+1; i <= rowE; i++) {
            int tt = rec[i][colE];
            rec[i][colE] = temp[i-1-rowS];
            temp[i-1-rowS] = tt;
        }

        // B -> C
        for (int i = colE-1; i >= colS; i--) {
            int tt = rec[rowE][i];
            rec[rowE][i] = temp[colE-1-i];
            temp[colE-1-i] = tt;
        }

        // C -> D
        for (int i = rowE-1; i >= rowS; i--) {
            int tt = rec[i][colS];
            rec[i][colS] = temp[rowE-1-i];
            temp[rowE-1-i] = tt;
        }

        // D -> A
        for (int i = colS+1; i <= colE; i++) {
            rec[rowS][i] = temp[i-1-colS];
        }

        if (rowE - rowS > 2) {
            rotation(rec, rowS+1, rowE-1, colS+1, colE-1);
        }
    }
}
