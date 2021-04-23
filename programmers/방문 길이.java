// java는 array도 reference type이기 때문에 LinkedList나 HashMap 등에 배열을 저장하려면 배열 자체로 저장하지 말고 String 형태로 저장해야한다. 그래야 contains를 제대로 사용할 수 있다.

import java.util.LinkedList;

class Solution {
    int[] dRow = {-1, 1, 0, 0};
    int[] dCol = {0, 0, -1, 1};
    LinkedList<String>[][] board = new LinkedList[11][11];
    int row = 5;
    int col = 5;
    
    public int solution(String dirs) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                board[i][j] = new LinkedList<>();
            }
        }
        
        int answer = 0;
        for (int i = 0; i < dirs.length(); i++) {
            String from = Integer.toString(row) + Integer.toString(col);
            int dir = 0;
            switch (dirs.charAt(i)) {
                case 'U':
                    dir = 0;
                    break;
                case 'D':
                    dir = 1;
                    break;
                case 'L':
                    dir = 2;
                    break;
                case 'R':
                    dir = 3;
                    break;
            }
            row += dRow[dir];
            col += dCol[dir];
            if (row < 0 || col < 0 || row >= 11 || col >= 11) {
                row -= dRow[dir];
                col -= dCol[dir];
                continue;
            }
            if (!board[row][col].contains(from)) {
                board[row][col].add(from);
                String to = Integer.toString(row)+Integer.toString(col);
                board[row-dRow[dir]][col-dCol[dir]].add(to);
                answer++;
            }
        }
        return answer;
    }
}
