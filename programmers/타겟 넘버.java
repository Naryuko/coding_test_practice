import java.util.Queue;
import java.util.LinkedList;

class Solution {
    public int solution(int[] numbers, int target) {
        int answer = bfs(numbers, target);
        return answer;
    }
    
    public int bfs (int[] number, int target) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] {0,0}); // sum, current index
        int maxIndex = number.length-1;
        int answer = 0;
        
        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            if (temp[1] > maxIndex) {
                if (temp[0] == target) {
                    answer++;
                }
                continue;
            }
            queue.add(new int[] {temp[0]+number[temp[1]], temp[1]+1});
            queue.add(new int[] {temp[0]-number[temp[1]], temp[1]+1});
        }
        return answer;
    }
}
