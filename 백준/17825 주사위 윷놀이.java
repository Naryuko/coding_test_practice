// dfs 연습을 위해 dfs로 풀었지만 너무 복잡했다. 경우의 수가 몇 안되었으니 하드코딩을 하면 더 빠르지 않을까?

import java.io.*;
import java.util.StringTokenizer;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    static int[] arr = new int[10];

    static int[] state1 = {10, 13, 16, 19};
    static int[] state2 = {20, 22, 24};
    static int[] state3 = {30, 28, 27, 26};
    static int[] state4 = {25, 30, 35, 40};

    static int score = 0;

    public static void main (String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        for (int i = 0; i < 10; i++) {
            arr[i] = Integer.parseInt(tk.nextToken());
        }

        boolean[] state0board = new boolean[21];
        boolean[] state1board = new boolean[4];
        boolean[] state2board = new boolean[3];
        boolean[] state3board = new boolean[4];
        boolean[] state4board = new boolean[4];
        boolean[][] totalboard = new boolean[5][];
        totalboard[0] = state0board;
        totalboard[1] = state1board;
        totalboard[2] = state2board;
        totalboard[3] = state3board;
        totalboard[4] = state4board;

        piece pc1 = new piece();
        piece pc2 = new piece();
        piece pc3 = new piece();
        piece pc4 = new piece();
        piece[] list = {pc1, pc2, pc3, pc4};

        bfs(list, totalboard);

        bw.write(Integer.toString(score));
        bw.close();
        br.close();
    }

    static void bfs (piece[] list, boolean[][] totalboard) {
        Queue<piece[]> pcQueue = new LinkedList<>();
        Queue<Integer> numQueue = new LinkedList<>();
        Queue<boolean[][]> boardQueue = new LinkedList<>();
        Queue<Integer> scoreQueue = new LinkedList<>();

        pcQueue.add(list);
        boardQueue.add(totalboard);
        numQueue.add(0);
        scoreQueue.add(0);

        while (!pcQueue.isEmpty()) {
            piece[] temp = pcQueue.poll();
            int num = numQueue.poll();
            boolean[][] tempboard = boardQueue.poll();
            int scoree = scoreQueue.poll();

            if (num >= 10) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                if (!temp[i].canMove(arr[num],tempboard[0],tempboard[1],tempboard[2],tempboard[3],tempboard[4])) {
                    continue;
                }
                boolean[][] board2 = boardCopy(tempboard);
                piece pc2 = pcCopy(temp[i]);
                int tempScore = scoree + pc2.move(arr[num],board2[0],board2[1],board2[2],board2[3],board2[4]);
                piece[] tempArr = pcArrCopy(temp);
                tempArr[i] = pc2;

                pcQueue.add(tempArr);
                numQueue.add(num+1);
                boardQueue.add(board2);
                scoreQueue.add(tempScore);

                if (tempScore > score) {
                    score = tempScore;
                }
            }
        }
    }

    static boolean[][] boardCopy (boolean[][] board1) {
        boolean[][] board2 = new boolean[5][];
        for (int i = 0; i < 5; i++) {
            board2[i] = new boolean[board1[i].length];
            for (int j = 0; j < board1[i].length; j++) {
                board2[i][j] = board1[i][j];
            }
        }
        return board2;
    }

    static piece[] pcArrCopy (piece[] arr) {
        piece[] arr2 = new piece[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = pcCopy(arr[i]);
        }
        return arr2;
    }

    static piece pcCopy (piece pc1) {
        piece pc2 = new piece(pc1.state, pc1.current, pc1.end);
        return pc2;
    }

    static class piece {
        int state;
        int current;
        boolean end;

        public piece () {
            this.state = 0;
            this.current = 0;
            this.end = false;
        }

        public piece (int state, int current, boolean end) {
            this.state = state;
            this.current = current;
            this.end = end;
        }

        public boolean canMove (int i, boolean[] state0board, boolean[] state1board, boolean[] state2board, boolean[] state3board, boolean[] state4board) {
            boolean ret = true;
            if (this.end) {
                return false;
            }

            if (this.state == 0) {
                if (this.current + i <= 20 && state0board[this.current+i]) {
                   ret = false;
                }
            } else if (this.state == 1) {
                if (this.current == 3 && i == 5) {
                    ret = true;
                } else if (this.current+i < 4 && state1board[this.current+i]) {
                    ret = false;
                } else if (this.current+i >= 4) {
                    int temp = this.current + i - 4;
                    if (state4board[temp]) {
                        ret = false;
                    }
                }
            } else if (this.state == 2) {
                if (this.current == 2 && i == 5) {
                    ret = true;
                } else if (this.current+i < 3 && state2board[this.current+i]) {
                    ret = false;
                } else if (this.current + i >= 3){
                    int temp = this.current + i - 3;
                    if (state4board[temp]) {
                        ret = false;
                    }
                }
            } else if (this.state == 3) {
                if (this.current == 3 && i == 5) {
                    ret = true;
                } else if (this.current+i < 4 && state3board[this.current+i]) {
                    ret = false;
                } else if (this.current+i >= 4){
                    int temp = this.current + i - 4;
                    if (state4board[temp]) {
                        ret = false;
                    }
                }
            } else if (this.state == 4) {
                if (this.current + i < 4 && state4board[this.current+i]) {
                    ret = false;
                }
            }

            return ret;
        }


        public int move (int i, boolean[] state0board, boolean[] state1board, boolean[] state2board, boolean[] state3board, boolean[] state4board) {
            int ret = 0;

            if (this.state == 0) {
                state0board[this.current] = false;
                this.current += i;
                if (this.current > 20) {
                    this.end = true;
                    return 0;
                }
                state0board[this.current] = true;
                ret = this.current*2;

                if (this.current == 5) {
                    this.state = 1;
                    this.current = 0;
                    state1board[0] = true;
                } else if (this.current == 10) {
                    this.state = 2;
                    this.current = 0;
                    state2board[0] = true;
                } else if (this.current == 15) {
                    this.state = 3;
                    this.current = 0;
                    state3board[0] = true;
                } else if (this.current == 20) {
                    this.state = 4;
                    this.current = 3;
                    state4board[3] = true;
                }
            } else if (this.state == 1) {
                state1board[this.current] = false;
                if (this.current == 0) {
                    state0board[5] = false;
                }
                if (this.current == 3 && i == 5) {
                    this.end = true;
                } else if (this.current+i < 4) {
                    this.current += i;
                    state1board[this.current] = true;
                    ret = state1[this.current];
                } else {
                    this.current = this.current + i - 4;
                    state4board[this.current] = true;
                    if (this.current == 3) {
                        state0board[20] = true;
                    }
                    this.state = 4;
                    ret = state4[this.current];
                }
            } else if (this.state == 2) {
                state2board[this.current] = false;
                if (this.current == 0) {
                    state0board[10] = false;
                }
                if (this.current == 2 && i == 5) {
                    this.end = true;
                } else if (this.current+i < 3) {
                    this.current += i;
                    state2board[this.current] = true;
                    ret = state2[this.current];
                } else {
                    this.current = this.current + i - 3;
                    state4board[this.current] = true;
                    if (this.current == 3) {
                        state0board[20] = true;
                    }
                    this.state = 4;
                    ret = state4[this.current];
                }
            } else if (this.state == 3) {
                state3board[this.current] = false;
                if (this.current == 0) {
                    state0board[15] = false;
                }
                
                if (this.current == 3 && i == 5) {
                    this.end = true;
                } else if (this.current+i < 4) {
                    this.current += i;
                    state3board[this.current] = true;
                    ret = state3[this.current];
                } else {
                    this.current = this.current + i - 4;
                    state4board[this.current] = true;
                    if (this.current == 3) {
                        state0board[20] = true;
                    }
                    this.state = 4;
                    ret = state4[this.current];
                }
            } else if (this.state == 4) {
                state4board[this.current] = false;
                if (this.current == 3) {
                    state0board[20] = false;
                }

                if (this.current + i > 3) {
                    this.end = true;
                } else if (this.current+i <= 3) {
                    this.current += i;
                    state4board[this.current] = true;
                    if (this.current == 3) {
                        state0board[20] = true;
                    }
                    ret = state4[this.current];
                }
            }
            return ret;
        }
    }

}
