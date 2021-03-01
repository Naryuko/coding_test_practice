// Arrays.sort는 n*log n보다 오래걸린다. 정렬이 필요하며 시간이 빡빡하면 우선순위큐를 이용하자.
// 문제 조건으 잘읽자... 연결이 다 안된다며 -1 출력하라느 글을 못 보아서 여러 번 틀렸다.

import java.io.*;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.PriorityQueue;

public class Main {
    static int N;
    static int C;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        N = Integer.parseInt(tk.nextToken());
        C = Integer.parseInt(tk.nextToken());

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = i;
        }

        int[][] field = new int[N][2];
        for (int i = 0; i < N; i++) {
            tk = new StringTokenizer(br.readLine());
            field[i][0] = Integer.parseInt(tk.nextToken());
            field[i][1] = Integer.parseInt(tk.nextToken());
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[2] - o2[2];
            }
        });

        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                queue.add(new int[] {i, j, (field[i][0] - field[j][0])*(field[i][0] - field[j][0]) + (field[i][1] - field[j][1])*(field[i][1] - field[j][1])});
            }
        }

        int sum = 0;
        int count = 0;

        while (!queue.isEmpty()) {
            int[] bridge = queue.poll();
            int from = bridge[0];
            int to = bridge[1];
            int length = bridge[2];

            if (length < C) {
                continue;
            }
            int aa = findParent(arr, from);
            int bb = findParent(arr, to);
            if (aa == bb) {
                continue;
            } else {
                unionParent(arr, aa, bb);
                sum += length;
                count++;
            }
            if (count == N-1) {
                break;
            }

        }
        if (count < N-1) {
            sum = -1;
        }
        bw.write(String.valueOf(sum));
        bw.close();
        br.close();
    }

    static int findParent (int[] arr, int a) {
        if (arr[a] == a) {
            return a;
        }

        return findParent(arr, arr[a]);
    }

    static void unionParent (int[] arr, int a, int b) {
        int aa = findParent(arr, a);
        int bb = findParent(arr, b);

        if (aa < bb) {
            arr[bb] = aa;
        } else {
            arr[aa] = bb;
        }
    }

}
