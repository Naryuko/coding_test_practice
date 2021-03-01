// 생각보다 까다로운 문제였다. 풀이 방법 자체는 쉽지는 그것을 dfs로 구현하는 것이 햇갈렸다. 복습 필요.

import java.io.*;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.LinkedList;

public class Main {
    static int N;
    static mem[] member;

    public static void main (String[] arg) throws Exception {
        //System.setIn(new FileInputStream("./abc.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tk = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tk.nextToken());
        member = new mem[N];

        tk = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            member[i] = new mem(i);
            int t = Integer.parseInt(tk.nextToken());
            if (t != -1) {
                member[t].add(member[i]);
            }
        }

        int time = dfs(member[0]);
        bw.write(Integer.toString(time));
        bw.close();
        br.close();
    }

    public static int dfs (mem a) {
        if (a.child.size() == 0) {
            return 0;
        }

        a.child.sort(new Comparator<mem>() {
            @Override
            public int compare(mem o1, mem o2) {
                return dfs(o2) - dfs(o1);
            }
        });

        int time = 0;
        int p = 0;
        for (int i = 0; i < a.child.size(); i++) {
            p++;
            time = Math.max(time, dfs(a.child.get(i)) + p);
        }

        return time;
    }


    static class mem {
        int num;
        int order = 0;
        LinkedList<mem> child = new LinkedList<>();

        public mem (int num) {
            this.num = num;
        }

        public void add (mem a) {
            this.child.add(a);
        }
    }



}
