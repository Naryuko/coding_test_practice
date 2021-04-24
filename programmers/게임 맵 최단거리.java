// 방문 check를 언제 해주느냐에 따라 시간차이가 많이 난다

import java.util.Queue;
import java.util.LinkedList;

class Solution {
    int[] dRow = {1, -1, 0, 0};
    int[] dCol = {0, 0, 1, -1};
    
    public int solution(int[][] maps) {
        int answer = bfs(maps);
        return answer;
    }
    
    public int bfs (int[][] board) {
        int rowMax = board.length-1;
        int colMax = board[0].length-1;
        int answer = -1;
        boolean[][] check = new boolean[rowMax+1][colMax+1];
        
        Queue<int[]> queue = new LinkedList<>(); // [row, col,count]
        queue.add(new int[] {0,0,0});
        
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            if (temp[0] == rowMax && temp[1] == colMax) {
                answer = temp[2]+1;
                break;
            }
            for (int i = 0; i < 4; i++) {
                int newRow = temp[0]+dRow[i];
                int newCol = temp[1]+dCol[i];
                if (newRow < 0 || newCol < 0 || newRow > rowMax || newCol > colMax) {
                    continue;
                }
                if (board[newRow][newCol] == 0 || check[newRow][newCol]) {
                    continue;
                }
                check[newRow][newCol] = true;
                queue.add(new int[] {newRow, newCol, temp[2]+1});
            }
        }
        return answer;
    }
}
