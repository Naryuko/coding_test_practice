// long으로 반환해야 한다면 중간에 값이 long 범위인데 int로 할당하지 않았는지 잘 확인해야 한다.

import java.util.ArrayList;
import java.util.LinkedList;

class Solution {
    static ArrayList<Integer>[] list;
    static boolean[] check;
    static long answer = 0;
    static long[] aa;

    public long solution(int[] a, int[][] edges) {
        int[] temp = checkSum(a);
        if (temp[1] != 0) {
            return 0;
        } else if (temp[0] != 0) {
            return -1;
        }

        check = new boolean[a.length];
        aa = new long[a.length];
        list = new ArrayList[a.length];
        for (int i = 0; i < list.length; i++) {
            list[i] = new ArrayList<>();
            aa[i] = a[i];
        }
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            list[from].add(to);
            list[to].add(from);
        }

        dfs(0, -1);

        return answer;
    }

    public void dfs (int current, int parent) {
        check[current] = true;

        for (int i = 0; i < list[current].size(); i++) {
            if (!check[list[current].get(i)]) {
                dfs(list[current].get(i), current);
            }
        }
        answer += Math.abs(aa[current]);
        if (parent != -1) {
            aa[parent] += aa[current];
        }
    }

    public int[] checkSum (int[] a) {
        int sum = 0;
        int check = 0;
        boolean allZero = true;

        for (int i = 0; i < a.length; i++) {
            if (a[i] != 0) {
                sum += a[i];
                allZero = false;
            }
        }

        if (allZero) {
            check = 1;
        }
        return new int[] {sum, check};
    }

//    public static void main (String[] arg) {
//        int[][] edges = {{0,1},{3,4},{2,3},{0,3}};
//        int[] a = {-5,0,2,1,2};
//        Solution s = new Solution();
//        System.out.println(s.solution(a,edges));
//    }
}
