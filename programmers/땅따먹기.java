// 이전까지 계산을 누적하는 방법을 잘 생각하자.

class Solution {
    int solution(int[][] land) {
        for (int i = 0; i < land.length-1; i++) {
            for (int j = 0; j < 4; j++) {
                land[i+1][j] += findMax(land[i], j);
            }
        }


        return findMax(land[land.length-1], -1);
    }
    
    int findMax (int[] map, int index) {
        int maxx = 0;
        for (int i = 0; i < map.length; i++) {
            if (i == index) {
                continue;
            }
            if (map[i] > maxx) {
                maxx = map[i];
            }
        }
        return maxx;
    }
}
