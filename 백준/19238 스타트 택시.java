// dfs는 재귀함수를 이용하므로 구현은 간편하지만 queue를 이용하는 bfs보다 느리다.

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};

    static int[][] board; // 0은 빈 공간, 1은 벽, 2 이상부터는 손님 (2번이 0번째 손님, 3번이 1번째 손님 등)
    static int[][] customerDestination;
    static int N;
    static int M;
    static int fuel;
    static int remainPeople;
    static int[] currentPoint = new int[2];

    public static void main (String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        M = Integer.parseInt(tk.nextToken());
        fuel = Integer.parseInt(tk.nextToken());

        board = new int[N][N];
        customerDestination = new int[M][2];
        remainPeople = M;

        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(tk.nextToken());
            }
        }

        tk = new StringTokenizer(br.readLine());
        for (int i = 0; i < 2; i++) {
            currentPoint[i] = Integer.parseInt(tk.nextToken())-1;
        }

        for (int j = 0; j < M; j++) {
            tk = new StringTokenizer(br.readLine());
            int pRow = Integer.parseInt(tk.nextToken())-1;
            int pCol = Integer.parseInt(tk.nextToken())-1;
            int dRow = Integer.parseInt(tk.nextToken())-1;
            int dCol = Integer.parseInt(tk.nextToken())-1;
            board[pRow][pCol] = 2 + j;
            customerDestination[j][0] = dRow;
            customerDestination[j][1] = dCol;
        }

        while (fuel > 0) {
            move();
            if (remainPeople == 0) {
                break;
            }
        }

        bw.write(Integer.toString(fuel));
        bw.close();
        br.close();
    }


    static int[] takePerson = new int[2];
    static int[] destination = new int[2];
    static int distance1;
    static int distance2;

    static void move() {
        takePerson[0] = -1;
        takePerson[1] = -1;
        destination[0] = -1;
        destination[1] = -1;
        distance1 = Integer.MAX_VALUE;
        distance2 = Integer.MAX_VALUE;
        boolean[][] check = new boolean[N][N];

        bfs(currentPoint[0],currentPoint[1],check,-1,-1);
        //dfs(currentPoint[0],currentPoint[1],0,check,-1,-1);

        if (distance1 > fuel) {
            fuel = -1;
            return;
        } else {
            fuel -= distance1;
        }

        check = new boolean[N][N];
        int[] desTemp = customerDestination[board[takePerson[0]][takePerson[1]]-2];
        bfs(takePerson[0],takePerson[1],check, desTemp[0], desTemp[1]);
        //dfs(takePerson[0],takePerson[1],0,check, desTemp[0], desTemp[1]);

        if (distance2 > fuel) {
            fuel = -1;
            return;
        } else {
            fuel += distance2;
            remainPeople--;
            board[takePerson[0]][takePerson[1]] = 0;
            currentPoint[0] = destination[0];
            currentPoint[1] = destination[1];
        }

    }

    static void bfs (int row, int col, boolean[][] check, int option1, int option2) {
        Queue<int[]> queue = new LinkedList<>();
        int dist = 0;
        int[] a = {row, col, dist};
        queue.add(a);

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            if (temp[0] >= N || temp[1] >= N || temp[0] < 0 || temp[1] < 0) {
                continue;
            }
            if (check[temp[0]][temp[1]] || board[temp[0]][temp[1]] == 1) {
                continue;
            }

            check[temp[0]][temp[1]] = true;
            if (option1 == -1) {
                if (board[temp[0]][temp[1]] > 1) {
                    if (temp[2] < distance1) {
                        distance1 = temp[2];
                        takePerson[0] = temp[0];
                        takePerson[1] = temp[1];
                    } else if (temp[2] == distance1) {
                        if (temp[0] < takePerson[0]) {
                            takePerson[0] = temp[0];
                            takePerson[1] = temp[1];
                        } else if (temp[0] == takePerson[0] && temp[1] < takePerson[1]) {
                            takePerson[1] = temp[1];
                        }
                    }
                }
            } else {
                if (temp[0] == option1 && temp[1] == option2 ) {
                    if (temp[2] < distance2) {
                        distance2 = temp[2];
                        destination[0] = temp[0];
                        destination[1] = temp[1];
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                int[] ttemp = {temp[0]+dRow[i], temp[1]+dCol[i], temp[2]+1};
                queue.add(ttemp);
            }
        }
    }

    // dfs
    /*
    static void dfs(int row, int col, int dist, boolean[][] check, int option1, int option2) {
        if (row >= N || col >= N || row < 0 || col < 0) {
            return;
        }
        if (check[row][col] || board[row][col] == 1) {
            return;
        }

        check[row][col] = true;
        if (option1 == -1) {
            if (board[row][col] > 1) {
                if (dist < distance1) {
                    distance1 = dist;
                    takePerson[0] = row;
                    takePerson[1] = col;
                } else if (dist == distance1) {
                    if (row < takePerson[0]) {
                        takePerson[0] = row;
                        takePerson[1] = col;
                    } else if (row == takePerson[0] && col < takePerson[1]) {
                        takePerson[1] = col;
                    }
                }
            }
        } else {
            if (row == option1 && col == option2) {
                if (dist < distance2) {
                    distance2 = dist;
                    destination[0] = row;
                    destination[1] = col;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            dfs(row+dRow[i], col+dCol[i],dist+1, check, option);
        }
        check[row][col] = false;
    }
    */

}
