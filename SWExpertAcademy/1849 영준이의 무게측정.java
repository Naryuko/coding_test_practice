import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.StringTokenizer;

public class Solution {
    static int[] arr;
    static long[] weight;
    static int[] rank;

    public static void main(String[] arg) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            StringTokenizer token = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(token.nextToken());
            int M = Integer.parseInt(token.nextToken());
            arr = new int[N+1];
            rank = new int[N+1];
            weight = new long[N+1];
            for (int j = 0; j < N+1; j++) {
                arr[j] = j;
            }
            bw.write("#"+Integer.toString(i+1));

            for (int j = 0; j < M; j++) {
                token = new StringTokenizer(br.readLine());
                if (token.nextToken().equals("!")) {
                    int a = Integer.parseInt(token.nextToken())-1;
                    int b = Integer.parseInt(token.nextToken())-1;
                    int c = Integer.parseInt(token.nextToken());
                    unionParent(a,b,c);
                } else {
                    int a = Integer.parseInt(token.nextToken())-1;
                    int b = Integer.parseInt(token.nextToken())-1;
                    int aa = findParent(a);
                    int bb = findParent(b);
                    if (aa == bb) {
                        bw.write(" "+Long.toString(weight[a] - weight[b]));
                    } else {
                        bw.write(" UNKNOWN");
                    }
                }
            }
            bw.write("\n");
        }
        bw.flush();
        bw.close();
        br.close();
    }

    public static int findParent (int a) {
        if (arr[a] == a) {
            return a;
        }

        int aa = arr[a];
        arr[a] = findParent(aa);
        weight[a] += weight[aa];
        return arr[a];
    }

    public static void unionParent (int a, int b, long cost) {
        int aa = findParent(a);
        int bb = findParent(b);
        if (aa == bb) {
            return;
        }

        long diff = weight[b]-weight[a];
        if (rank[aa] > rank[bb]) {
            int temp = aa;
            aa = bb;
            bb = temp;
            diff *= -1;
            cost *= -1;
        }
        weight[aa] = cost + diff;
        arr[aa] = bb;
        if (rank[aa] == rank[bb]) {
            rank[bb]++;
        }
    }
}

// 알고리즘 자체는 쉬운데 최적화가 어려운 문제...
