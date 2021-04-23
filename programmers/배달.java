// 단순한 다익스트라 구현문제다.

import java.util.LinkedList;
import java.util.PriorityQueue;

class Solution {
    int inf = Integer.MAX_VALUE;
    
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        LinkedList<int[]>[] list = new LinkedList[N+1];
        for (int i = 0; i <= N; i++) {
            list[i] = new LinkedList<>();
        }
        for (int i = 0; i < road.length; i++) {
            int from = road[i][0];
            int to = road[i][1];
            int cost = road[i][2];
            list[from].add(new int[] {to, cost});
            list[to].add(new int[] {from, cost});
        }
        
        PriorityQueue<int[]> pq = new PriorityQueue<>((int[] a, int[] b) -> a[1]-b[1]);
        int[] cost = new int[N+1];
        for (int i = 0; i <= N; i++) {
            cost[i] = inf;
        }
        cost[1] = 0;
        pq.add(new int[] {1, 0});
        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            if (current[1] > cost[current[0]]) {
                continue;
            }
            for (int i = 0; i < list[current[0]].size(); i++) {
                int[] next = list[current[0]].get(i);
                int nextDistance = next[1] + current[1];
                if (cost[next[0]] > nextDistance) {
                    cost[next[0]] = nextDistance;
                    pq.add(new int[] {next[0], nextDistance});
                }
            }
        }
        
        for (int i = 0; i < cost.length; i++) {
            if (cost[i] <= K) {
                answer++;
            }
        }

        return answer;
    }
}
