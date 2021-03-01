// 그래프를 인접배열(2차원 배열)로 표현하면 시간초과였다. bfs를 수행할 때 checkBoard의 상태를 저장하는 큐는 필요가 없다.

import java.io.*;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;

public class Main {
    static int N;
    static int Q;
    static LinkedList<int[]>[] board;
    static int inf = Integer.MAX_VALUE - 10000;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        Q = Integer.parseInt(tk.nextToken());
        board = new LinkedList[N];
        for (int i = 0; i < N; i++) {
            board[i] = new LinkedList<>();
        }

        for (int i = 0; i < N-1; i++) {
            tk = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(tk.nextToken())-1;
            int q = Integer.parseInt(tk.nextToken())-1;
            int r = Integer.parseInt(tk.nextToken());

            board[p].add(new int[] {q, r});
            board[q].add(new int[] {p, r});
        }

        for (int i = 0; i < Q; i++) {
            tk = new StringTokenizer(br.readLine());
            int k = Integer.parseInt(tk.nextToken());
            int v = Integer.parseInt(tk.nextToken())-1;
            int[] USADO = usado(v);

            /*
            for (int j = 0; j < USADO.length; j++) {
                System.out.printf("%d ",USADO[j]);
            }
            System.out.println();
            */

            int sum = count(USADO, k);
            bw.write(Integer.toString(sum));
            bw.newLine();
        }

        bw.close();
        br.close();
    }

    static int count (int[] arr, int k) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != inf && arr[i] >= k) {
                sum++;
            }
        }
        return sum;
    }

    static int[] usado (int v) {
        int[] ret = new int[N];
        for (int i = 0; i < N; i++) {
            ret[i] = inf;
        }

        for (int i = 0; i < board[v].size(); i++) {
            int[] temp = board[v].get(i);
            ret[temp[0]] = temp[1];
        }
        boolean[] check = new boolean[N];
        check[v] = true;

        Queue<Integer> numQueue = new LinkedList<>();
        numQueue.add(v);

        while (!numQueue.isEmpty()) {
            int current = numQueue.poll();

            for (int i = 0; i < board[current].size(); i++) {
                int[] destination = board[current].get(i);
                if (check[destination[0]]) {
                    continue;
                }
                check[destination[0]] = true;
                if (ret[destination[0]] > ret[current]) {
                    ret[destination[0]] = ret[current];
                }
                if (ret[destination[0]] > destination[1]) {
                    ret[destination[0]] = destination[1];
                }
                numQueue.add(destination[0]);
            }

        }


        return ret;
    }


}
