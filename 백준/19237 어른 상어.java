import java.io.*;
import java.util.StringTokenizer;
import java.util.ArrayList;

public class Main {
    static int[] dRow = {-1, 1, 0, 0}; // 위, 아래, 왼쪽, 오른쪽 순서
    static int[] dCol = {0, 0, -1, 1};
    static int M; // 상어 마리수
    static int N; // 격자 크기
    static int k; // 냄새가 없어지는데 걸리는 시간
    static int time = 0;
    static int remainShark;
    static ArrayList<Integer>[] shark; // shark[i]는 i번째 상어의 위치와 방 [i, j, 방향] (총 3개 원소)
    static ArrayList<Integer>[][] sharkDir; // shardDir[i][j]의 arraylist는 i번째 상어가 j방향을 보고 있을때 [방향 우선순위] (총 4개 원소)
    static ArrayList<Integer>[][] board; // board[i][j]의 arraylist는 (i,j)칸의 냄새정보 [상어번호, 남은 턴] (총 2개 원소)

    public static void main (String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        M = Integer.parseInt(tk.nextToken());
        k = Integer.parseInt(tk.nextToken());

        remainShark = M;
        shark = new ArrayList[M];
        sharkDir = new ArrayList[M][4]; // 0123순서는 위, 아래, 왼쪽, 오른
        for (int i = 0; i < M; i++) {
            shark[i] = new ArrayList<>(3);
            for (int j = 0; j < 4; j++) {
                sharkDir[i][j] = new ArrayList<>(4);
            }
        }
        board = new ArrayList[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = new ArrayList<>(2);
            }
        }

        // board에 상어 초기 위치 냄새, shark에 상어 위치 정보 추가
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int temp = Integer.parseInt(tk.nextToken())-1;
                if (temp != -1) {
                    board[i][j].add(temp);
                    board[i][j].add(k);
                    shark[temp].add(i);
                    shark[temp].add(j);
                }
            }
        }

        // 방향 정보 추가
        tk = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            shark[i].add(Integer.parseInt(tk.nextToken())-1);
        }

        // 각 상어별 방향 우선순위 정보 추가, i번째 상어가 j방향을 보고 있을 때 우선순위가 tk가 된다.
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < 4; j++) {
                tk = new StringTokenizer(br.readLine());
                for (int k = 0; k < 4; k++) {
                    sharkDir[i][j].add(Integer.parseInt(tk.nextToken())-1);
                }
            }
        }

        while (remainShark > 1) {
            move();
            if (time > 1000) {
                time = -1;
                break;
            }
        }

        bw.write(Integer.toString(time));
        bw.close();
        br.close();
    }

    static void move () {
        for (int i = 0; i < M; i++) {
            sharkMove(i);
        }
        getout();
        tiktok();
        newSmell();
        time++;
    }

    static void tiktok () {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j].size() > 1) {
                    int newSmellTime = board[i][j].get(1)-1;
                    if (newSmellTime == 0) {
                        board[i][j].clear();
                        continue;
                    }
                    board[i][j].set(1,newSmellTime);
                }
            }
        }
    }

    static void newSmell () {
        for (int i = 0; i < M; i++) {
            int row = shark[i].get(0);
            if (row < 0) {
                continue;
            }
            int col = shark[i].get(1);
            board[row][col].clear();
            board[row][col].add(i);
            board[row][col].add(k);
        }
    }

    static void sharkMove (int sharkNum) {
        int row = shark[sharkNum].get(0);
        if (row < 0) {
            return;
        }
        int col = shark[sharkNum].get(1);
        int dir = shark[sharkNum].get(2);

        ArrayList<Integer> voidBoard = new ArrayList<>(4);
        ArrayList<Integer> selfSmell = new ArrayList<>(4);

        for (int i = 0; i < 4; i++) {
            int rowT = row + dRow[i];
            int colT = col + dCol[i];
            if (rowT < 0 || colT < 0 || rowT >= N || colT >= N) {
                continue;
            }
            if (board[rowT][colT].size() > 0) {
                if (board[rowT][colT].get(0) == sharkNum) {
                    selfSmell.add(i);
                }
                continue;
            }
            voidBoard.add(i);
        }

        if (voidBoard.size() == 1) {
            shark[sharkNum].set(0, row+dRow[voidBoard.get(0)]);
            shark[sharkNum].set(1, col+dCol[voidBoard.get(0)]);
            shark[sharkNum].set(2, voidBoard.get(0));
        } else if (voidBoard.size() > 1) {
            int actualDir = -1;
            boolean check = false;
            for (int p = 0; p < 4; p++) {
                for (int q = 0; q < voidBoard.size(); q++) {
                    if (sharkDir[sharkNum][dir].get(p) == voidBoard.get(q)) {
                        check = true;
                        actualDir = voidBoard.get(q);
                        break;
                    }
                }
                if (check) {
                    break;
                }
            }
            shark[sharkNum].set(0, row+dRow[actualDir]);
            shark[sharkNum].set(1, col+dCol[actualDir]);
            shark[sharkNum].set(2, actualDir);
        } else if (selfSmell.size() == 1) {
            shark[sharkNum].set(0, row+dRow[selfSmell.get(0)]);
            shark[sharkNum].set(1, col+dCol[selfSmell.get(0)]);
            shark[sharkNum].set(2, selfSmell.get(0));
        } else if (selfSmell.size() > 1) {
            int actualDir = -1;
            boolean check = false;
            for (int p = 0; p < 4; p++) {
                for (int q = 0; q < selfSmell.size(); q++) {
                    if (sharkDir[sharkNum][dir].get(p) == selfSmell.get(q)) {
                        check = true;
                        actualDir = selfSmell.get(q);
                        break;
                    }
                }
                if (check) {
                    break;
                }
            }
            shark[sharkNum].set(0, row+dRow[actualDir]);
            shark[sharkNum].set(1, col+dCol[actualDir]);
            shark[sharkNum].set(2, actualDir);
        }
    }

    static void getout () {
        for (int i = 0; i < M; i++) {
            int row = shark[i].get(0);
            int col = shark[i].get(1);
            if (row < 0) {
                continue;
            }
            for (int j = i+1; j < M; j++) {
                if (shark[j].get(0) < 0) {
                    continue;
                } else if (row == shark[j].get(0) && col == shark[j].get(1)){
                    shark[j].set(0,-1);
                    remainShark--;
                }
            }
        }
    }




}
