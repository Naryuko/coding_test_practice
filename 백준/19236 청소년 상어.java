import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    static int[] dRow = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dCol = {0, -1, -1, -1, 0, 1, 1, 1};
    static int sum = 0;

    public static void main (String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk;

        // 물고기들의 row, col 좌표 저장
        ArrayList<Integer>[] fish = new ArrayList[16];
        for (int i = 0; i < 16; i++) {
            fish[i] = new ArrayList<>(2);
        }

        ArrayList<Integer>[][] board = new ArrayList[4][4];
        for (int i = 0; i < 4; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < 4; j++) {
                board[i][j] = new ArrayList<>(2);
                int fishNum = Integer.parseInt(tk.nextToken())-1;
                board[i][j].add(fishNum);
                board[i][j].add(Integer.parseInt(tk.nextToken())-1);
                fish[fishNum].add(i);
                fish[fishNum].add(j);
            }
        }

        int[] shark = new int[3]; // [row, col, direction]
        // 0,0 위치의 물고기를 먹는다.
        shark[0] = 0;
        shark[1] = 0;
        shark[2] = board[0][0].get(1);
        sum = board[0][0].get(0);
        fish[sum].set(0, -1);
        board[0][0].clear();
        sum++; // 1번 생선을 0번 생선이라고 저장했으므로 생선 번호 1 증가시켜준다.

        bfs(board, fish, shark);

        bw.write(Integer.toString(sum));
        bw.close();
        br.close();
    }

    static void bfs (ArrayList<Integer>[][] board, ArrayList<Integer>[] fish, int[] shark) {
        Queue<ArrayList<Integer>[][]> boardQueue = new LinkedList<>();
        Queue<ArrayList<Integer>[]> fishQueue = new LinkedList<>();
        Queue<int[]> sharkQueue = new LinkedList<>();
        Queue<Integer> sumQueue = new LinkedList<>();

        boardQueue.add(board);
        fishQueue.add(fish);
        sharkQueue.add(shark);
        sumQueue.add(sum);

        while (!boardQueue.isEmpty()) {
            ArrayList<Integer>[][] board2 = boardQueue.poll();
            ArrayList<Integer>[] fish2 = fishQueue.poll();
            int[] shark2 = sharkQueue.poll();
            int sum2 = sumQueue.poll();

            moveFish(board2,fish2,shark2);
            ArrayList<int[]> temp = sharkPossible(board2, shark2);
            if (temp.size() == 0) {
                continue;
            }

            for (int i = 0; i < temp.size(); i++) {
                ArrayList<Integer>[][] board3 = boardcopy(board2);
                ArrayList<Integer>[] fish3 = fishcopy(fish2);
                int[] shark3 = sharkcopy(shark2);

                int sum3 = sum2 + moveShark(board3, fish3, shark3, temp.get(i));
                if (sum3 > sum) {
                    sum = sum3;
                }
                boardQueue.add(board3);
                fishQueue.add(fish3);
                sharkQueue.add(shark3);
                sumQueue.add(sum3);
            }
        }
    }

    // 상어를 목표 위치로 이동시키고 해당 위치에 있던 생선의 번호를 반환
    static int moveShark (ArrayList<Integer>[][] board, ArrayList<Integer>[] fish, int[] shark, int[] tomove) {
        shark[0] = tomove[0];
        shark[1] = tomove[1];
        shark[2] = board[tomove[0]][tomove[1]].get(1);
        int fishNum = board[tomove[0]][tomove[1]].get(0);
        fish[fishNum].set(0,-1);
        board[tomove[0]][tomove[1]].clear();
        return fishNum+1;
    }

    // 상어가 움직일 수 있는 [row,col] 배열을 반환
    static ArrayList<int[]> sharkPossible (ArrayList<Integer>[][] board, int[] shark) {
        ArrayList<int[]> ret = new ArrayList<>(3);

        int row = shark[0];
        int col = shark[1];
        int dir = shark[2];

        for (int i = 1; i < 4; i++) {
            row += dRow[dir];
            col += dCol[dir];
            if (row < 0 || col < 0 || row >= 4 || col >= 4) {
                break;
            }
            if (board[row][col].size() != 0) {
                int[] temp = {row, col};
                ret.add(temp);
            }
        }

        return ret;
    }

    // 주어진 규칙대로 생선을 이동시킴
    static void moveFish (ArrayList<Integer>[][] board, ArrayList<Integer>[] fish, int[] shark) {
        for (int i = 0; i < 16; i++) {
            int row = fish[i].get(0);
            if (row == -1) {
                continue;
            }
            int col = fish[i].get(1);
            int dir = board[row][col].get(1);
            for (int j = 0; j < 8; j++) {
                int direction = (dir + j)%8;
                int rowT = row + dRow[direction];
                int colT = col + dCol[direction];
                if (rowT < 0 || colT < 0 || rowT >= 4 || colT >= 4) {
                    continue;
                }
                if (rowT == shark[0] && colT == shark[1]) {
                    continue;
                }
                if (board[rowT][colT].size() == 0) {
                    board[rowT][colT].add(i);
                    board[rowT][colT].add(direction);
                    board[row][col].clear();
                    fish[i].set(0, rowT);
                    fish[i].set(1, colT);
                } else {
                    int fishNumTemp = board[rowT][colT].get(0);
                    int fishDirTemp = board[rowT][colT].get(1);
                    board[rowT][colT].set(0,i);
                    board[rowT][colT].set(1,direction);
                    board[row][col].set(0,fishNumTemp);
                    board[row][col].set(1,fishDirTemp);
                    fish[i].set(0,rowT);
                    fish[i].set(1,colT);
                    fish[fishNumTemp].set(0,row);
                    fish[fishNumTemp].set(1,col);
                }
                break;
            }
        }
    }

    static int[] sharkcopy (int[] shark1) {
        int[] shark2 = new int[3];
        for (int i = 0; i < 3; i++) {
            shark2[i] = shark1[i];
        }
        return shark2;
    }

    static ArrayList<Integer>[] fishcopy (ArrayList<Integer>[] fish1) {
        ArrayList<Integer>[] fish2 = new ArrayList[16];
        for (int i = 0; i < 16; i++) {
            fish2[i] = new ArrayList<Integer>(2);
            for (int j = 0; j < fish1[i].size(); j++) {
                fish2[i].add(fish1[i].get(j));
            }
        }
        return fish2;
    }

    static ArrayList<Integer>[][] boardcopy (ArrayList<Integer>[][] board1) {
        ArrayList<Integer>[][] board2 = new ArrayList[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board2[i][j] = new ArrayList<>(2);
                for (int k = 0; k < board1[i][j].size(); k++) {
                    board2[i][j].add(board1[i][j].get(k));
                }
            }
        }
        return board2;
    }


}
