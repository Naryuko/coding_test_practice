// 구현은 어렵지 않았지만 이동한 후 루프를 벗어나느 조건이 약가 햇갈렸다. 45번째 줄으 루프를 도는 과정에서 중간에 check = true가 되었어도 다음 for 루프를 돌며 check = false가 되고, 계속 while문으 도는
// 문제가 있었다. check = true일 때 바로 for 루프르 벗어나게 하는 코드를 추가한 것으로 해결했다. 좀 더 꼼꼼히 구현하느 연습을 해야겠다.

import java.io.*;
import java.util.StringTokenizer;
import java.util.LinkedList;

public class Main {
    static int N;
    static int K;
    static int[][] colorBoard;
    static LinkedList<Integer>[][] pieceBoard;
    static piece[] pcs;
    static int[] dRow = {0, 0, -1, 1};
    static int[] dCol = {1, -1, 0, 0};
    static int turn = 0;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        K = Integer.parseInt(tk.nextToken());

        colorBoard = new int[N][N];
        pieceBoard = new LinkedList[N][N];
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                pieceBoard[i][j] = new LinkedList<>();
                colorBoard[i][j] = Integer.parseInt(tk.nextToken());
            }
        }

        pcs = new piece[K];
        for (int i = 0; i < K; i++) {
            tk = new StringTokenizer(br.readLine());
            int row = Integer.parseInt(tk.nextToken())-1;
            int col = Integer.parseInt(tk.nextToken())-1;
            int dir = Integer.parseInt(tk.nextToken())-1;
            pcs[i] = new piece(i, row, col, dir);
            pieceBoard[row][col].add(i);
        }

        boolean check = false;
        while (!check && turn < 1000) {
            for (int i = 0; i < pcs.length; i++) {
                check = pcs[i].move();
                if (check) {
                    break;
                }
            }

            /*
            for (piece a : pcs) {
                System.out.printf("num: %d, row: %d, col: %d, order: %d\n",a.num, a.row, a.col, a.order);
            }
            System.out.println();
            */
            turn++;
        }

        if (turn >= 1000) {
            turn = -1;
        }

        bw.write(String.valueOf(turn));
        bw.close();
        br.close();
    }

    static class piece {
        int num;
        int order = 0;
        int row;
        int col;
        int dir;

        public piece (int num, int row, int col, int dir) {
            this.num = num;
            this.row = row;
            this.col = col;
            this.dir = dir;
        }


        public void changeDir() {
            if (this.dir % 2 == 0) {
                this.dir++;
            } else {
                this.dir--;
            }
        }

        public void set (int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void addOrder (int n) {
            this.order += n;
        }

        public void changeOrder (int n) {
            this.order = Math.abs(n-this.order-1);
        }

        public boolean move () {
            if (this.order != 0) {
                return false;
            }

            int rowT = this.row + dRow[this.dir];
            int colT = this.col + dCol[this.dir];

            // 보드 밖이거나 파란색 칸인 경우
            if (rowT < 0 || colT < 0 || rowT >= N || colT >= N) {
                this.changeDir();
                rowT = this.row + dRow[this.dir];
                colT = this.col + dCol[this.dir];
                if (colorBoard[rowT][colT] == 2) {
                    return false;
                }
            }
            if (colorBoard[rowT][colT] == 2) {
                this.changeDir();
                rowT = this.row + dRow[this.dir];
                colT = this.col + dCol[this.dir];
                if (rowT < 0 || colT < 0 || rowT >= N || colT >= N) {
                    return false;
                } else if (colorBoard[rowT][colT] == 2) {
                    return false;
                }
            }


            // 파란 칸이나 보드 밖이 아닌 곳으로 이동하는 경우
            int toTemp = pieceBoard[rowT][colT].size();
            int fromTemp = pieceBoard[this.row][this.col].size();

            // 빨간 칸이라면 피스들 이동 전에 순서를 바꾸어준다.
            if (colorBoard[rowT][colT] == 1) {
                for (int a: pieceBoard[this.row][this.col]) {
                    pcs[a].changeOrder(fromTemp);
                }
            }

            int pastRow = this.row;
            int pastCol = this.col;
            while (pieceBoard[pastRow][pastCol].size() > 0) {
                int a = pieceBoard[pastRow][pastCol].removeFirst();
                pcs[a].set(rowT, colT);
                pcs[a].addOrder(toTemp);
                if (pcs[a].order >= 3) {
                    return true;
                }
                pieceBoard[rowT][colT].add(a);
            }
            return false;
        }


    }


}
