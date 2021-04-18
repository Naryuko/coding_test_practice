import java.util.Queue;
import java.util.LinkedList;

class Solution {
    static int[] dRow;
    static int[] dCol;
    static boolean[][] check;
    static int mm;
    static int nn;

    public int[] solution(int m, int n, int[][] picture) {
        int numberOfArea = 0;
        int maxSizeOfOneArea = 0;
        dRow = new int[]{-1, 1, 0, 0};
        dCol = new int[]{0, 0, -1, 1};
        check = new boolean[m][n];
        mm = m;
        nn = n;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (picture[i][j] == 0) { continue; }
                if (!check[i][j]) {
                    int count = bfs(picture, i, j, picture[i][j]);
                    numberOfArea++;
                    maxSizeOfOneArea = Math.max(maxSizeOfOneArea, count);
                }
            }
        }

        int[] answer = new int[2];
        answer[0] = numberOfArea;
        answer[1] = maxSizeOfOneArea;
        return answer;
    }

    public int bfs (int[][] board, int row, int col, int value) {
        Queue<int[]> queue = new LinkedList<>();
        int ret = 0;

        queue.add(new int[] {row, col});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            if (check[current[0]][current[1]]) { continue; }
            check[current[0]][current[1]] = true;
            ret++;

            for (int i = 0; i < 4; i++) {
                int newR = current[0] + dRow[i];
                int newC = current[1] + dCol[i];
                if (newR < 0 || newC < 0 || newR >= mm || newC >= nn) { continue; }
                if (board[newR][newC] != value) { continue; }
                queue.add(new int[] {newR, newC});
            }
        }

        return ret;
    }

    public static void main (String[] arg) {
        int m = 3;
        int n = 3;
        int[][] board = {{0, 1, 0}, {1, 1, 0}, {0, 0, 0}};
        Solution s = new Solution();
        int [] a = s.solution(m, n, board);

        System.out.printf("%d %d",a[0], a[1]);
    }
}
